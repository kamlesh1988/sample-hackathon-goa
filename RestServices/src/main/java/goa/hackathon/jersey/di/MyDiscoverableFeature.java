package goa.hackathon.jersey.di;

import java.io.IOException;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ClasspathDescriptorFileFinder;
import org.glassfish.hk2.utilities.DuplicatePostProcessor;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import goa.hackathon.jersey.SampleApplication;
import gov.va.oia.HK2Utilities.HK2RuntimeInitializer;

public class MyDiscoverableFeature implements Feature {

	private static final Logger logger = LogManager.getLogger();

	@Override
	public boolean configure(FeatureContext context) {
		ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator(SampleApplication.SERVICE_LOCATOR_NAME);
		logger.info("MyDiscoverableFeature configure {}", locator);
		DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);
		try {
			HK2RuntimeInitializer.init(SampleApplication.SERVICE_LOCATOR_NAME, true, SampleApplication.PACKAGE_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Populator populator = dcs.getPopulator();
		try {
			populator.populate(new ClasspathDescriptorFileFinder(this.getClass().getClassLoader()), new DuplicatePostProcessor());
		} catch (IOException | MultiException ex) {
			logger.error(ex);
		}
		return true;
	}

}