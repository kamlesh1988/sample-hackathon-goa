package io.gupshup.wpp.resources.managers;

import io.gupshup.hackthon.ChatMsgDAO;
import io.gupshup.hackthon.ChatMsgObject;

public class DatastoreDAOFactory
{
	public enum DataStore
	{
		MYSQL, HSQL;
	}

	public static ChatMsgDAO getDAO(ChatMsgObject chatMsgObject, DataStore datastore)
	{
		ChatMsgDAO dao = null;
		switch (datastore)
		{
		case HSQL:
			// dao = new ChatMsgDAO(chatMsgObject, PooledDataSource.getInstance());
			break;
		case MYSQL:
		default:
			// dao = new ChatMsgDAO(chatMsgObject.getClass(), PooledDataSource.getInstance());
		}
		return dao;
	}

	public static void main(String[] args)
	{

	}
}
