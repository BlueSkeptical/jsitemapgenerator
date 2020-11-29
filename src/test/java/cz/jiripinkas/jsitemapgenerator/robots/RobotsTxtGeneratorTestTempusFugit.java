package cz.jiripinkas.jsitemapgenerator.robots;

import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;

public class RobotsTxtGeneratorTestTempusFugit {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();
	
    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void constructRobotsTxtString() {
    	//System.out.println(Thread.currentThread().getId());
        String actual = RobotsTxtGenerator.of("https://example.com")
                .addSitemap("sitemap.xml")
                .addRule(RobotsRule.builder().userAgentAll().allowAll().build())
                .toString();
        String expected = "Sitemap: https://example.com/sitemap.xml\n" +
                "User-agent: *\n" +
                "Allow: /";
        assertEquals(expected, actual);
    }

}