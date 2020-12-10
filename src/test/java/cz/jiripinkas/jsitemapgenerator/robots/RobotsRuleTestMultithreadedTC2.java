package cz.jiripinkas.jsitemapgenerator.robots;

import org.junit.jupiter.api.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;

class RobotsRuleTestMultithreadedTC2 extends MultithreadedTestCase {

    void thread1() {
    	assertThrows(RobotsRuleException.class, () -> {
            RobotsRule.builder().allowAll().disallowAll().build();
        });
    }
    
    void thread2() {
    	assertThrows(RobotsRuleException.class, () -> {
            RobotsRule.builder().allowAll().disallowAll().build();
        });
    }
    
    @Test
    public void testBuilderMissingUserAgent() throws Throwable {
    	TestFramework.runManyTimes(new RobotsRuleTestMultithreadedTC2(), 100);
    }
}