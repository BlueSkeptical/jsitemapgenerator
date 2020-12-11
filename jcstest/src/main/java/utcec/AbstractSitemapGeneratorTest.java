package utcec;

import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapIndexGenerator;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;

import java.time.LocalDateTime;

//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class AbstractSitemapGeneratorTest {

	private Boolean ifEqualActor1, ifEqualActor2;

	public AbstractSitemapGeneratorTest () {
		this.ifEqualActor1 = true;
		this.ifEqualActor2 = true;
	}

	@Actor
	public void actor1() {
	    SitemapIndexGenerator sitemapIndexGenerator;
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
        if (!expectedSitemapIndex.equals(actualSitemapIndex)) {
            ifEqualActor1 = true;
        }
	}

	@Actor
	public void actor2() {
        SitemapIndexGenerator sitemapIndexGenerator;
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
        if (!expectedSitemapIndex.equals(actualSitemapIndex)) {
            ifEqualActor2 = true;
        }
	}

	@Arbiter
	public void arbiter(ZZ_Result r) {
		r.r1 = ifEqualActor1;
		r.r2 = ifEqualActor2;
	}
}