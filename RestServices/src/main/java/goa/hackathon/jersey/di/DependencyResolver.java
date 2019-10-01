package goa.hackathon.jersey.di;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class DependencyResolver {
	private static final Logger logger = LoggerFactory.getLogger(MyDiscoverableFeature.class.getName());
	private static ServiceLocator serviceLocator;

	@Inject
	public DependencyResolver(ServiceLocator serviceLocator) {
		logger.info("serviceLocator {}", serviceLocator);
		DependencyResolver.serviceLocator = serviceLocator;
	}

	public <T> T getService(Class<T> t) {
		return serviceLocator.getService(t);
	}

}