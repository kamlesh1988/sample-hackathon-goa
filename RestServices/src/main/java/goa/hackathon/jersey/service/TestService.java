package goa.hackathon.jersey.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

@Service
public class TestService {

	private static final Logger log = LogManager.getLogger();

	private static AtomicInteger count = new AtomicInteger(0);

	public TestService() {
		log.info("Constructor called count {}", count.incrementAndGet());
	}

	public boolean doTask() {
		return true;
	}

}
