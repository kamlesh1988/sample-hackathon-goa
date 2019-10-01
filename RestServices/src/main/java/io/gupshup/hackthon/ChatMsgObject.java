package io.gupshup.hackthon;

import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

@Table(name = "MsgTable")
public class ChatMsgObject
{

	private int id;

	private String from;

	private String msg;

	private long timestamp;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

	public ChatMsgObject(int id, String message, String from, long timestamp)
	{
		this.id = id;
		this.msg = message;
		this.from = from;
		this.timestamp = timestamp;
	}

	public JSONObject toJSONObject() throws JSONException
	{
		String gsonString = new Gson().toJson(this);
		JSONObject json = new JSONObject(gsonString);
		return json;
	}

	public ChatMsgObject(JSONObject json)
	{
		Gson gson = new Gson();
		ChatMsgObject gsonParsedObject = gson.fromJson(json.toString(), ChatMsgObject.class);
		this.id = gsonParsedObject.getId();
		this.msg = gsonParsedObject.getMsg();
		this.from = gsonParsedObject.getFrom();
		this.timestamp = gsonParsedObject.getTimestamp();
	}

	public static void main(String[] args) throws JSONException
	{
		// ChatMsgObject chat = new ChatMsgObject();
		// chat.setFrom("ssjhsd");
		// chat.setId(1);
		// chat.setMsg("hi");
		// chat.setTimestamp(123456789l);
		// System.out.println(ChatMsgObject.toJSONObject());
		//
		// JSONObject json = new JSONObject();
		// json.put("id", 1);
		// json.put("from", "sagar");
		// json.put("msg", "hi");
		// json.put("timestamp", 123456789l);
		// ChatMsgObject chat2 = new ChatMsgObject(json);
		// System.out.println(chat2.getFrom());
	}
}