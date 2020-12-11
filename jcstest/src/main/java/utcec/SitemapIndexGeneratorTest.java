package utcec;

import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapIndexGenerator;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;
//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class SitemapIndexGeneratorTest {

	private Boolean ifEqualActor1, ifEqualActor2;

	public SitemapIndexGeneratorTest () {
		this.ifEqualActor1 = true;
		this.ifEqualActor2 = true;
	}

	@Actor
	public void actor1() {
	    SitemapIndexGenerator sitemapIndexGenerator;
	    sitemapIndexGenerator = SitemapIndexGenerator.of("http://javalibs.com");
		sitemapIndexGenerator.addPage(WebPage.of("sitemap-plugins.xml"));
		sitemapIndexGenerator.addPage(WebPage.of("sitemap-archetypes.xml"));
		WebPage webPage = WebPage.builder().name("sitemap-plugins.xml").build();
		String constructUrl = sitemapIndexGenerator.constructUrl(webPage);
		if (!constructUrl.equals("<sitemap>\n<loc>http://javalibs.com/sitemap-plugins.xml</loc>\n</sitemap>\n")) {
		    ifEqualActor1 = false;
        }
	}

	@Actor
	public void actor2() {
        SitemapIndexGenerator sitemapIndexGenerator;
	    sitemapIndexGenerator = SitemapIndexGenerator.of("http://javalibs.com");
		sitemapIndexGenerator.addPage(WebPage.of("sitemap-plugins.xml"));
		sitemapIndexGenerator.addPage(WebPage.of("sitemap-archetypes.xml"));
		WebPage webPage = WebPage.builder().name("sitemap-plugins.xml").build();
		String constructUrl = sitemapIndexGenerator.constructUrl(webPage);
		if (!constructUrl.equals("<sitemap>\n<loc>http://javalibs.com/sitemap-plugins.xml</loc>\n</sitemap>\n")) {
		    ifEqualActor2 = false;
        }
	}

	@Arbiter
	public void arbiter(ZZ_Result r) {
		r.r1 = ifEqualActor1;
		r.r2 = ifEqualActor2;
	}
}
