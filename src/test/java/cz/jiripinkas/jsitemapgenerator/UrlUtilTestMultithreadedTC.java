package cz.jiripinkas.jsitemapgenerator;

import org.junit.jupiter.api.Test;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;

class UrlUtilTestMultithreadedTC extends MultithreadedTestCase {

    void thread1() {
    	//System.out.println(Thread.currentThread().getId());
        assertAll(
                () -> Assertions.assertEquals("https://javalibs.com", UrlUtil.connectUrlParts("https://javalibs.com", null)),
                () -> Assertions.assertEquals("https://javalibs.com/page", UrlUtil.connectUrlParts("https://javalibs.com", "page")),
                () -> Assertions.assertEquals("https://javalibs.com/page", UrlUtil.connectUrlParts("https://javalibs.com", "/page"))
        );
    }

    void thread2() {
        assertAll(
                () -> Assertions.assertEquals("https://javalibs.com", UrlUtil.connectUrlParts("https://javalibs.com", null)),
                () -> Assertions.assertEquals("https://javalibs.com/page", UrlUtil.connectUrlParts("https://javalibs.com", "page")),
                () -> Assertions.assertEquals("https://javalibs.com/page", UrlUtil.connectUrlParts("https://javalibs.com", "/page"))
        );
    }
    
    @Test
    public void connectUrlParts() throws Throwable {
    	TestFramework.runManyTimes(new UrlUtilTestMultithreadedTC(), 100);
    }
}