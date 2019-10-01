package io.gupshup.wpp.resources.managers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;
import com.zaxxer.hikari.HikariConfig;

import io.gupshup.db.connection.ConnectionPool;
import io.gupshup.db.connection.impl.HikariConnectionPool;
import io.gupshup.wpp.Configurator;

public class PooledDataSource implements ConnectionPool
{

	private static final Logger log = LogManager.getLogger();

	private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";

	public static PooledDataSource connectionManager;

	private static HikariConnectionPool hikariConnectionPool;

	private PooledDataSource()
	{
		init();
	}

	private void init()
	{
		String databaseUrl = Configurator.getString("db.url");
		String username = Configurator.getString("db.user");
		String password = Configurator.getString("db.password");
		int maxIdle = Integer.parseInt(Configurator.getString("db.connection.maxIdle"));
		int maxTotal = Integer.parseInt(Configurator.getString("db.connection.maxTotal"));
		Properties properties = new Properties();
		// TODO : get configuration from property file
		properties.put("dataSource.cachePrepStmts", Boolean.TRUE);
		properties.put("dataSource.prepStmtCacheSize", 500);
		properties.put("dataSource.prepStmtCacheSqlLimit", 2048);
		properties.put("dataSource.useServerPrepStmts", Boolean.TRUE);
		properties.put("dataSource.useLocalSessionState", Boolean.TRUE);
		properties.put("dataSource.rewriteBatchedStatements", Boolean.TRUE);
		properties.put("dataSource.cacheResultSetMetadata", Boolean.TRUE);
		properties.put("dataSource.cacheServerConfiguration", Boolean.TRUE);
		properties.put("dataSource.elideSetAutoCommits", Boolean.TRUE);
		properties.put("dataSource.maintainTimeStats", Boolean.FALSE);
		HikariConfig config = new HikariConfig(properties);
		config.setUsername(username);
		config.setPassword(password);
		config.setJdbcUrl(databaseUrl);
		config.setMaximumPoolSize(maxTotal);
		config.setMinimumIdle(maxIdle);
		config.setDriverClassName(COM_MYSQL_JDBC_DRIVER);
		hikariConnectionPool = new HikariConnectionPool(config);
	}

	public static PooledDataSource getInstance()
	{
		if (connectionManager == null)
		{
			synchronized (PooledDataSource.class)
			{
				if (connectionManager == null)
				{
					connectionManager = new PooledDataSource();
				}
			}
		}
		return connectionManager;
	}

	public Connection getConnection()
	{
		return getConnection(0);
	}

	private Connection getConnection(int retryCount)
	{
		try
		{
			return hikariConnectionPool.getConnection();
		}
		catch (SQLException e)
		{
			log.error("Failed to get the mysql connection", e);
		}
		if (retryCount > 10)
		{
			// TODO : send slack notification
			// unable to connect to mysql
			return null;
		}
		shutdown();
		try
		{
			Thread.sleep(100 + (100 * retryCount));
		}
		catch (InterruptedException e)
		{
			log.error(e);
		}
		init();
		log.warn("Retry init connection pool count : {}", retryCount);
		return getConnection(retryCount++);
	}

	@Override
	public JsonObject getConnectionStatistics()
	{
		return hikariConnectionPool.getConnectionStatistics();
	}

	@Override
	public void shutdown()
	{
		try
		{
			hikariConnectionPool.shutdown();
		}
		catch (SQLException e)
		{
			log.error(e);
		}
	}

}
