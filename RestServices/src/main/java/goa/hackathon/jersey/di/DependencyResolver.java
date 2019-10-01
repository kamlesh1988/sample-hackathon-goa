package goa.hackathon.jersey.di;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.jvnet.hk2.annotations.Service;

import eu.infomas.annotation.AnnotationDetector;
import gov.va.oia.HK2Utilities.AnnotatedClasses;
import gov.va.oia.HK2Utilities.AnnotationReporter;
import gov.va.oia.HK2Utilities.HK2RuntimeInitializer;

public class DependencyResolver implements ContainerLifecycleListener {

	private static final Logger log = LogManager.getLogger();

	private static ServiceLocator serviceLocator;

	@Override
	public void onStartup(Container container) {
		log.info("onStartup : {}", container);
		init(container.getApplicationHandler().getServiceLocator());
	}

	private void init(ServiceLocator sl) {
		serviceLocator = sl;
	}

	@Override
	public void onReload(Container container) {
		log.info("onReload : {}", container);
		init(container.getApplicationHandler().getServiceLocator());
	}

	@Override
	public void onShutdown(Container container) {
		log.info("onShutdown : {}", container);
	}

	public synchronized static <T> T getDependency(Class<T> aclass) {
		log.debug("getDependency : {}", aclass);
		if (serviceLocator != null)
			return serviceLocator.getService(aclass);

		serviceLocator = createDefaultServiceLocator("smsai");
		return serviceLocator.getService(aclass);
	}

	public synchronized static ServiceLocator createDefaultServiceLocator(String name) {
		String[] packageNames = { "goa.hackathon" };
		try {
			return HK2RuntimeInitializer.init(name, true, packageNames);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return ServiceLocatorUtilities.createAndPopulateServiceLocator(name);
	}

	public static ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	public synchronized static Class<?>[] getClassesWithAnnotationService(String... packageNames) throws ClassNotFoundException {
		AnnotatedClasses ac = new AnnotatedClasses();

		try {
			AnnotationDetector cf = new AnnotationDetector(new AnnotationReporter(ac, new Class[] { Service.class }));
			if (packageNames == null || packageNames.length == 0) {
				cf.detect();
			} else {
				cf.detect(packageNames);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ac.getAnnotatedClasses();
	}
}
