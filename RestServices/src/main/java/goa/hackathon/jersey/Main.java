package goa.hackathon.jersey;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import goa.hackathon.jersey.di.DependencyResolver;
import goa.hackathon.jersey.di.MyDiscoverableFeature;

public class Main {
	public static final String		BASE_URI				= "http://0.0.0.0:18080/chat-application";
	public static final String		SERVICE_LOCATOR_NAME	= null;
	private static ServiceLocator	locator;

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
	 * @return Grizzly HTTP server.
	 * @throws IOException 
	 */
	public static HttpServer startServer() throws IOException {
		// create a resource config that scans for JAX-RS resources and providers
		// in com.kamlesh.poc.jersey package
		locator = ServiceLocatorUtilities.createAndPopulateServiceLocator("__HK2_Generated_0");
		final SampleApplication main = new SampleApplication(locator);
		System.out.println(locator);
		main.register(MyDiscoverableFeature.class);
		main.register(MultiPartFeature.class);
		main.packages(true, SampleApplication.PACKAGE_NAME);
		HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), main, locator);
		httpServer.start();
		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		return httpServer;
	}

	/**
	 * Main method.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final HttpServer server = startServer();
		//		DependencyResolver dependencyResolver = DependencyResolver.getDependency(DependencyResolver.class);
		//		TestService service = dependencyResolver.getDependency(TestService.class);
		//		System.out.println(service.doTask());
		//		server.shutdown();
	}
}
