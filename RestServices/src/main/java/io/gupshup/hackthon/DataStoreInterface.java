package io.gupshup.hackthon;

import org.json.JSONObject;

public interface DataStoreInterface
{
	JSONObject getData(int id);

	boolean postData(JSONObject json);
}