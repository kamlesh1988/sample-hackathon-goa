package goa.hackathon.jersey.routes;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import goa.hackathon.jersey.service.TestService;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("api")
public class ChatRoutes {

	@Inject
	TestService service;

	/**
	 * Method handling HTTP GET requests. The returned object will be sent
	 * to the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Path("msg")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getMessage(@QueryParam("from") String fromMesgId) {
		// TODO : integrate
		return ResponseUtils.ok(new JSONArray());
		//		return "Got it! result => " + service.doTask();
	}

	@POST
	@Path("msg")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postMessage( //
			@FormParam("from") String from, //
			@FormParam("msg") String message //
	) {
		// TODO : integrate
		return ResponseUtils.ok();
	}
}
