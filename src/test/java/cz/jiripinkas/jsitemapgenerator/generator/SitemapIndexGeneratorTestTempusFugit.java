package cz.jiripinkas.jsitemapgenerator.generator;

import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.util.TestUtil;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SitemapIndexGeneratorTestTempusFugit {

	private SitemapIndexGenerator sitemapIndexGenerator;
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();
	
	@Test
	@Concurrent(count = 2)
	@Repeating(repetition = 100)
	public void testConstructUrl() {
		sitemapIndexGenerator = SitemapIndexGenerator.of("http://javalibs.com");
		sitemapIndexGenerator.addPage(WebPage.of("sitemap-plugins.xml"));
		sitemapIndexGenerator.addPage(WebPage.of("sitemap-archetypes.xml"));
		WebPage webPage = WebPage.builder().name("sitemap-plugins.xml").build();
		String constructUrl = sitemapIndexGenerator.constructUrl(webPage);
		assertEquals("<sitemap>\n<loc>http://javalibs.com/sitemap-plugins.xml</loc>\n</sitemap>\n", constructUrl);
	}

}
