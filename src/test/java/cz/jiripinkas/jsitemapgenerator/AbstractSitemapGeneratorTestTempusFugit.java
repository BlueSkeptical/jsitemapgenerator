package cz.jiripinkas.jsitemapgenerator;

import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapIndexGenerator;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class AbstractSitemapGeneratorTestTempusFugit {
	@Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

    private SitemapIndexGenerator sitemapIndexGenerator;

    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void toPrettyString() {
    	sitemapIndexGenerator = SitemapIndexGenerator.of("http://javalibs.com");
        sitemapIndexGenerator.addPage(WebPage.builder().name("sitemap-plugins.xml").lastMod(LocalDateTime.of(2018, 1, 1, 0, 0)).build());
        sitemapIndexGenerator.addPage(WebPage.builder().name("sitemap-archetypes.xml").lastMod(LocalDateTime.of(2018, 1, 1, 0, 0)).build());
        String actualSitemapIndex = sitemapIndexGenerator.toPrettyString(2);
        String expectedSitemapIndex = "<sitemapindex xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n" +
                "  <sitemap>\n" +
                "    <loc>http://javalibs.com/sitemap-archetypes.xml</loc>\n" +
                "    <lastmod>2018-01-01</lastmod>\n" +
                "  </sitemap>\n" +
                "  <sitemap>\n" +
                "    <loc>http://javalibs.com/sitemap-plugins.xml</loc>\n" +
                "    <lastmod>2018-01-01</lastmod>\n" +
                "  </sitemap>\n" +
                "</sitemapindex>\n";
        assertEquals(expectedSitemapIndex, actualSitemapIndex);
    }

}