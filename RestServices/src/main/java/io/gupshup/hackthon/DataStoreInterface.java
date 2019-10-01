package io.gupshup.hackthon;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

public interface DataStoreInterface
{
	List<ChatMsgObject> getData(int id) throws SQLException;

	boolean postData(ChatMsgObject json);
}