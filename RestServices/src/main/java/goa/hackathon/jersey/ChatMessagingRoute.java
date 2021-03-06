package goa.hackathon.jersey;

import java.io.InputStream;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataParam;

import goa.hackathon.jersey.service.TestService;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/api/group/msg")
public class ChatMessagingRoute {

	@Inject
	TestService service;

	/**
	 * Method handling HTTP GET requests. The returned object will be sent
	 * to the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it! result => " + service.doTask();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String consumeResource(@FormDataParam("file") InputStream stream) {
		return "OK";
	}
}
