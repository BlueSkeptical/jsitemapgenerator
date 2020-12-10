package cz.jiripinkas.jsitemapgenerator.generator;

import cz.jiripinkas.jsitemapgenerator.W3CDateFormat;
import cz.jiripinkas.jsitemapgenerator.WebPage;
import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SitemapPathTestMultithreadedTC extends MultithreadedTestCase {

    private DateFormat dateFormat;

    void thread1() {
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
        Assert.assertEquals(expectedSitemap, actualSitemap);
    }
    
    void thread2() {
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
        Assert.assertEquals(expectedSitemap, actualSitemap);
    }

    @Test
    public void testSitemapPaths() throws Throwable {
    	TestFramework.runManyTimes(new SitemapPathTestMultithreadedTC(), 100);
    }
}
