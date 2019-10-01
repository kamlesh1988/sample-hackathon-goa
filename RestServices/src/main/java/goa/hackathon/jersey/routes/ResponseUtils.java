/**
 *
 */
package goa.hackathon.jersey.routes;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.message.ParameterizedMessageFactory;
import org.json.JSONElement;
import org.json.JSONObject;

import com.google.gson.JsonElement;

public class ResponseUtils {

	public static final String							ERROR_RESPONSE		= "{\"status\":\"error\"}";
	public static final String							SUCCESS_RESPONSE	= "{\"status\":\"success\"}";
	private static final ParameterizedMessageFactory	MESSAGE_FACTORY		= new ParameterizedMessageFactory();

	public static Response ok() {
		return ok(SUCCESS_RESPONSE);
	}

	public static Response ok(String response) {
		return ok(response, MediaType.APPLICATION_JSON);
	}

	public static Response ok(JSONElement jsonObject) {
		return ok(jsonObject.toString(), MediaType.APPLICATION_JSON);
	}

	public static Response ok(JsonElement json) {
		return ok(json.toString(), MediaType.APPLICATION_JSON);
	}

	public static Response ok(String response, Object... params) {
		return ok(MESSAGE_FACTORY.newMessage(response, params).getFormattedMessage());
	}

	public static Response ok(String response, String responseType) {
		return Response.ok().entity(response).type(responseType).build();
	}

	public static Response error(Status status, String response) {
		return error(status, response, MediaType.APPLICATION_JSON);
	}

	public static Response badRequest() {
		return error(Status.BAD_REQUEST, ERROR_RESPONSE, MediaType.APPLICATION_JSON);
	}

	public static Response forbidden() {
		return error(Status.FORBIDDEN, ERROR_RESPONSE, MediaType.APPLICATION_JSON);
	}

	public static Response badRequest(String reason) {
		JSONObject responseJson = new JSONObject(ERROR_RESPONSE).put("reason", reason);
		return error(Status.BAD_REQUEST, responseJson.toString(), MediaType.APPLICATION_JSON);
	}

	public static Response badRequest(String reason, Object... values) {
		return badRequest(MESSAGE_FACTORY.newMessage(reason, values).getFormattedMessage());
	}

	public static Response badRequest(JSONObject error) {
		return error(Status.BAD_REQUEST, error.toString(), MediaType.APPLICATION_JSON);
	}

	public static Response error(Status status, String response, String responseType) {
		return Response.status(status).entity(response).type(responseType).build();
	}

	public static Response response(Status status, String response, String responseType) {
		return Response.status(status).entity(response).type(responseType).build();
	}

	public static Response response(Status status, String response) {
		return Response.status(status).entity(response).build();
	}

	public static Response serverError(String reason) {
		JSONObject responseJson = new JSONObject(ERROR_RESPONSE).put("reason", reason);
		return error(Status.INTERNAL_SERVER_ERROR, responseJson.toString(), MediaType.APPLICATION_JSON);
	}

	public static Response successData(JSONElement json) {
		return Response.ok().entity(new JSONObject().put("status", "success").put("data", json).toString()).type(MediaType.APPLICATION_JSON).build();
	}

	public static Response successData(String data) {
		return Response.ok().entity(new JSONObject().put("status", "success").put("data", data).toString()).type(MediaType.APPLICATION_JSON).build();
	}

}
