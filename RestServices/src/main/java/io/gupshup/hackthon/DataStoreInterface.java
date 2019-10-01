package io.gupshup.hackthon;

import org.json.JSONObject;

public interface DataStoreInterface
{
	JSONObject getData();

	boolean postData(JSONObject json);
}