package utcec;

import cz.jiripinkas.jsitemapgenerator.W3CDateFormat;
import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;

import java.text.DateFormat;
import java.util.Date;

//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class SitemapPathTest {

	private Boolean ifEqualActor1, ifEqualActor2;

	public SitemapPathTest () {
		this.ifEqualActor1 = true;
		this.ifEqualActor2 = true;
	}

	@Actor
	public void actor1() {
	    DateFormat dateFormat = new W3CDateFormat();
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
        if (!expectedSitemap.equals(actualSitemap)) {
            ifEqualActor1 = false;
        }
	}

	@Actor
	public void actor2() {
        DateFormat dateFormat = new W3CDateFormat();
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
        if (!expectedSitemap.equals(actualSitemap)) {
            ifEqualActor2 = false;
        }
	}

	@Arbiter
	public void arbiter(ZZ_Result r) {
		r.r1 = ifEqualActor1;
		r.r2 = ifEqualActor2;
	}
}
