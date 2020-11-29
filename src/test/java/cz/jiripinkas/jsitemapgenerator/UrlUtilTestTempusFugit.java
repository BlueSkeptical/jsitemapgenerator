package cz.jiripinkas.jsitemapgenerator;

import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;

public class UrlUtilTestTempusFugit {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();
	
    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void connectUrlParts() {
    	//System.out.println(Thread.currentThread().getId());
        assertAll(
                () -> assertEquals("https://javalibs.com", UrlUtil.connectUrlParts("https://javalibs.com", null)),
                () -> assertEquals("https://javalibs.com/page", UrlUtil.connectUrlParts("https://javalibs.com", "page")),
                () -> assertEquals("https://javalibs.com/page", UrlUtil.connectUrlParts("https://javalibs.com", "/page"))
        );
    }
}