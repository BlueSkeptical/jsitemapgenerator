package cz.jiripinkas.jsitemapgenerator.robots;

import org.junit.jupiter.api.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;

class RobotsTxtGeneratorTestMultithreadedTC extends MultithreadedTestCase {

    void thread1() {
    	//System.out.println(Thread.currentThread().getId());
        String actual = RobotsTxtGenerator.of("https://example.com")
                .addSitemap("sitemap.xml")
                .addRule(RobotsRule.builder().userAgentAll().allowAll().build())
                .toString();
        String expected = "Sitemap: https://example.com/sitemap.xml\n" +
                "User-agent: *\n" +
                "Allow: /";
        Assertions.assertEquals(expected, actual);
    }
    
    void thread2() {
        String actual = RobotsTxtGenerator.of("https://example.com")
                .addSitemap("sitemap.xml")
                .addRule(RobotsRule.builder().userAgentAll().allowAll().build())
                .toString();
        String expected = "Sitemap: https://example.com/sitemap.xml\n" +
                "User-agent: *\n" +
                "Allow: /";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void constructRobotsTxtString() throws Throwable {
    	TestFramework.runManyTimes(new RobotsTxtGeneratorTestMultithreadedTC(), 100);
    }
    
}