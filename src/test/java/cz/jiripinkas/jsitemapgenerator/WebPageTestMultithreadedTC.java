package cz.jiripinkas.jsitemapgenerator;

import cz.jiripinkas.jsitemapgenerator.exception.InvalidPriorityException;
import cz.jiripinkas.jsitemapgenerator.exception.InvalidUrlException;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class WebPageTestMultithreadedTC extends MultithreadedTestCase {

	void thread1() {
		//System.out.println(Thread.currentThread().getId());
		assertThrows(InvalidUrlException.class, () -> {
			SitemapGenerator.of("www.javavids.com");
		});
	}

	void thread2() {
		assertThrows(InvalidUrlException.class, () -> {
			SitemapGenerator.of("www.javavids.com");
		});
	}
	
	@Test
    public void testConstruct() throws Throwable {
    	TestFramework.runManyTimes(new WebPageTestMultithreadedTC(), 100);
    }
}
