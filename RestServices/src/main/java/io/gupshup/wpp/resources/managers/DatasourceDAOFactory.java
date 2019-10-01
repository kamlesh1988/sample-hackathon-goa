package io.gupshup.wpp.resources.managers;

import io.gupshup.hackthon.ChatMsgHsqlDAO;
import io.gupshup.hackthon.ChatMsgMysqlDAO;
import io.gupshup.hackthon.ChatMsgObject;
import io.gupshup.persistent.dao.generic.AbstractGenericDao;

public class DatasourceDAOFactory
{
	public enum Datasource
	{
		MYSQL, HSQL;
	}

	public static AbstractGenericDao getDAO(ChatMsgObject chatMsgObject, Datasource datastore)
	{
		AbstractGenericDao dao = null;
		switch (datastore)
		{
		case HSQL:
			dao = new ChatMsgHsqlDAO();
			break;
		case MYSQL:
		default:
			dao = new ChatMsgMysqlDAO();
		}
		return dao;
	}


}
