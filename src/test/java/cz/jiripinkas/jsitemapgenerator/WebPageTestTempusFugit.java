package cz.jiripinkas.jsitemapgenerator;

import cz.jiripinkas.jsitemapgenerator.exception.InvalidPriorityException;
import cz.jiripinkas.jsitemapgenerator.exception.InvalidUrlException;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;

public class WebPageTestTempusFugit {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

	@Test(expected = InvalidUrlException.class)
	@Concurrent(count = 2)
	@Repeating(repetition = 100)
	public void testConstruct() {
		//System.out.println(Thread.currentThread().getId());
		SitemapGenerator.of("www.javavids.com");
	}

}
