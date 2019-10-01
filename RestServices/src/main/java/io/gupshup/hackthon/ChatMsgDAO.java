package io.gupshup.hackthon;

import io.gupshup.db.connection.ConnectionPool;
import io.gupshup.persistent.dao.generic.AbstractGenericDao;

public class ChatMsgDAO extends AbstractGenericDao<Integer, ChatMsgObject>
{

	public ChatMsgDAO(Class<ChatMsgObject> type, ConnectionPool conManager)
	{
		super(type, conManager);
	}

}
