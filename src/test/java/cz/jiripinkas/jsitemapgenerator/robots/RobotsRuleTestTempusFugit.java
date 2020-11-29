package cz.jiripinkas.jsitemapgenerator.robots;

import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;

public class RobotsRuleTestTempusFugit {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void testBuilderOK() {
    	//System.out.println(Thread.currentThread().getId());
        RobotsRule robotsRule = RobotsRule.builder().allowAll().userAgentAll().build();
        assertEquals("/", robotsRule.getAllows().get(0));
        assertEquals("*", robotsRule.getUserAgent());
        assertTrue(robotsRule.getDisallows().isEmpty());
    }

}