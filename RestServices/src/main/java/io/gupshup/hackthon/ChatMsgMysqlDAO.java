package io.gupshup.hackthon;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import io.gupshup.persistent.dao.generic.AbstractGenericDao;
import io.gupshup.wpp.resources.managers.MysqlDataSource;

public class ChatMsgMysqlDAO extends AbstractGenericDao<Integer, ChatMsgObject> implements DataStoreInterface
{

	public ChatMsgMysqlDAO()
	{
		super(ChatMsgObject.class, MysqlDataSource.getInstance());
	}

	@Override
	public JSONObject getData(int id)
	{
		JSONObject returnObject = new JSONObject();
		try
		{
			List<ChatMsgObject> chatList = getList("id > ?", id);
			Gson gson = new Gson();
			JSONArray jsonArray = new JSONArray();
			for (ChatMsgObject chatObject : chatList)
			{
				String jsonString = gson.toJson(chatObject);
				JSONObject json = new JSONObject(jsonString);
				jsonArray.put(json);
			}
			returnObject.put("count", chatList.size());
			returnObject.put("status", "succcess");
			returnObject.put("data", jsonArray);
		}
		catch (Exception e)
		{
			returnObject.put("count", 0);
			returnObject.put("status", "failure");
			returnObject.put("data", new JSONArray());
		}
		return returnObject;

	}

	@Override
	public boolean postData(JSONObject json)
	{
		boolean ret = false;
		try
		{
			Gson gson = new Gson();
			ChatMsgObject object = gson.fromJson(json.toString(), ChatMsgObject.class);
			save(object);
			ret = true;
		}
		catch (Exception e)
		{
			ret = false;
		}

		return ret;
	}

}
