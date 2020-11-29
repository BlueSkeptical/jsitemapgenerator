package cz.jiripinkas.jsitemapgenerator.robots;

import org.junit.jupiter.api.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;

class RobotsRuleTestMultithreadedTC extends MultithreadedTestCase {

    void thread1() {
    	//System.out.println(Thread.currentThread().getId());
        RobotsRule robotsRule = RobotsRule.builder().allowAll().userAgentAll().build();
        Assertions.assertEquals("/", robotsRule.getAllows().get(0));
        Assertions.assertEquals("*", robotsRule.getUserAgent());
        Assertions.assertTrue(robotsRule.getDisallows().isEmpty());
    }
    
    void thread2() {
        RobotsRule robotsRule = RobotsRule.builder().allowAll().userAgentAll().build();
        Assertions.assertEquals("/", robotsRule.getAllows().get(0));
        Assertions.assertEquals("*", robotsRule.getUserAgent());
        Assertions.assertTrue(robotsRule.getDisallows().isEmpty());
    }
    
    @Test
    public void testBuilderOK() throws Throwable {
    	TestFramework.runManyTimes(new RobotsRuleTestMultithreadedTC(), 100);
    }
}