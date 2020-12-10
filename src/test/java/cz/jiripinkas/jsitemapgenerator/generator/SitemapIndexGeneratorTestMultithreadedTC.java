package cz.jiripinkas.jsitemapgenerator.generator;

import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.util.TestUtil;
import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SitemapIndexGeneratorTestMultithreadedTC extends MultithreadedTestCase {

	private SitemapIndexGenerator sitemapIndexGenerator;

	void thread1() {
		sitemapIndexGenerator = SitemapIndexGenerator.of("http://javalibs.com");
		sitemapIndexGenerator.addPage(WebPage.of("sitemap-plugins.xml"));
		sitemapIndexGenerator.addPage(WebPage.of("sitemap-archetypes.xml"));
		WebPage webPage = WebPage.builder().name("sitemap-plugins.xml").build();
		String constructUrl = sitemapIndexGenerator.constructUrl(webPage);
		Assert.assertEquals("<sitemap>\n<loc>http://javalibs.com/sitemap-plugins.xml</loc>\n</sitemap>\n", constructUrl);
	}
	
	void thread2() {
		sitemapIndexGenerator = SitemapIndexGenerator.of("http://javalibs.com");
		sitemapIndexGenerator.addPage(WebPage.of("sitemap-plugins.xml"));
		sitemapIndexGenerator.addPage(WebPage.of("sitemap-archetypes.xml"));
		WebPage webPage = WebPage.builder().name("sitemap-plugins.xml").build();
		String constructUrl = sitemapIndexGenerator.constructUrl(webPage);
		Assert.assertEquals("<sitemap>\n<loc>http://javalibs.com/sitemap-plugins.xml</loc>\n</sitemap>\n", constructUrl);
	}

	@Test
    public void testConstructUrl() throws Throwable {
    	TestFramework.runManyTimes(new SitemapIndexGeneratorTestMultithreadedTC(), 100);
    }
}
