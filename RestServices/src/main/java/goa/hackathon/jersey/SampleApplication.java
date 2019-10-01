package goa.hackathon.jersey;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.gson.stream.JsonReader;

import goa.hackathon.jersey.di.DependencyResolver;

/**
 * Main class.
 *
 */
@ApplicationPath("/")
public class SampleApplication extends ResourceConfig {

	private static final Logger log = LogManager.getLogger();
	// Base URI the Grizzly HTTP server will listen on
	public static final String	PACKAGE_NAME			= "goa.hackathon";
	public static final String	SERVICE_LOCATOR_NAME	= "__HK2_Generated_0";

	private ServiceLocator sl;

	@Inject
	public SampleApplication(ServiceLocator serviceLocator) {
		System.out.println("GOA HACKATHON Application Initialized");
		sl = serviceLocator;
		register(org.glassfish.jersey.media.multipart.MultiPartFeature.class);
		registerServices(serviceLocator);
		//		MetricsRegistryService metricsRegistryService = serviceLocator.getService(MetricsRegistryService.class);
		//		register(new InstrumentedResourceMethodApplicationListener(metricsRegistryService.getMetricRegistry()));
		packages(PACKAGE_NAME);
		registerRoutes();
		//		Config config = sl.getService(Config.class);
		//		property(ServerProperties.MONITORING_STATISTICS_ENABLED, true);
		//		property(ServerProperties.TRACING_THRESHOLD, TracingLogger.Level.SUMMARY.name());
		//		if ("dev".equalsIgnoreCase(config.getEnv())) {
		//			property(ServerProperties.TRACING, TracingConfig.ALL.name());
		//		} else {
		//			property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());
		//		}
		//		property(ServerProperties.WADL_FEATURE_DISABLE, true);
		//		registerRoutes();
		initializeErrorHandlers();
		//		NotificationProvider notificationProvider = ServiceDependencyResolver.getDependency(NotificationProvider.class);
		//		notificationProvider.sendSuccess(SlackClientFactory.SlackChannel.PRIVATE,  "SMSAIApplication started");
	}

	private void initializeErrorHandlers() {
		//		ServiceErrorTracker serviceErrorTracker = ServiceDependencyResolver.getDependency(ServiceErrorTracker.class);
		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			log.error("Uncaught error : ", e);
			//			serviceErrorTracker.addError(ServiceErrorTracker.Topics.GENERIC, "Error message : " + e.getMessage() + " |  stacktrace" + e.toString());
		});
		//		OkHttpUtils.setErrorConsumer(responseObject -> {
		//			serviceErrorTracker.addError(responseObject.getResponse().request().url().toString(), "Response: " + responseObject.getBodyAsString());
		//		});
	}

	private void registerRoutes() {
		packages(Main.class.getPackage().getName(), "goa.hackathon");
	}

	private void registerServices(ServiceLocator serviceLocator) {
		//		register(new ServiceBinder());

		/**
		 * This is replacement of ServiceBinder....
		 */
		try {
			Class<?>[] ac = DependencyResolver.getClassesWithAnnotationService("io.gupshup");
			for (ActiveDescriptor<?> ad : ServiceLocatorUtilities.addClasses(serviceLocator, ac)) {
				//				LOG.debug("Added {}", ad.getClass().getCanonicalName());
			}
		} catch (ClassNotFoundException e) {
			log.error(e);
		}

		register(DependencyResolver.createDefaultServiceLocator(serviceLocator.getName()));
		register(JsonReader.class);


		//		register(AppListener.class);
	}

	public <T> T getService(Class<T> c) {
		return sl.createAndInitialize(c);
	}
}
