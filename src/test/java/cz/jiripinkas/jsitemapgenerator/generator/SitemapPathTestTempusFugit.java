package cz.jiripinkas.jsitemapgenerator.generator;

import cz.jiripinkas.jsitemapgenerator.W3CDateFormat;
import cz.jiripinkas.jsitemapgenerator.WebPage;
import org.junit.jupiter.api.BeforeEach;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;

import org.junit.Rule;
import org.junit.Test;

import java.text.DateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SitemapPathTestTempusFugit {

    private DateFormat dateFormat;
    @Rule
	public ConcurrentRule concurrently = new ConcurrentRule();
	@Rule
	public RepeatingRule rule = new RepeatingRule();

    @Test
    @Concurrent(count = 2)
	@Repeating(repetition = 100)
    public void testSitemapPaths() {
    	dateFormat = new W3CDateFormat();
        SitemapGenerator sitemapGenerator = SitemapGenerator.of("http://www.javavids.com/");
        Date lastModDate = new Date();
        sitemapGenerator.addPage(WebPage.builder()
                .name("/index.php")
                .priority(1.0)
                .changeFreqNever()
                .lastMod(lastModDate)
                .build());
        sitemapGenerator.addPage(WebPage.of("basepath", "latest.php"));
        sitemapGenerator.addPage(WebPage.of("basepath", "contact.php"));

        String actualSitemap = sitemapGenerator.toString();
        final String expectedSitemap = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n"
                + "<url>\n"
                + "<loc>http://www.javavids.com/index.php</loc>\n"
                + "<lastmod>" + dateFormat.format(lastModDate) + "</lastmod>\n"
                + "<changefreq>never</changefreq>\n"
                + "<priority>1.0</priority>\n"
                + "</url>\n"
                + "<url>\n"
                + "<loc>http://www.javavids.com/basepath/contact.php</loc>\n"
                + "</url>\n" + "<url>\n"
                + "<loc>http://www.javavids.com/basepath/latest.php</loc>\n"
                + "</url>\n"
                + "</urlset>";
        assertEquals(expectedSitemap, actualSitemap);
    }

}
