package utcec;

import cz.jiripinkas.jsitemapgenerator.Image;
import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;

import java.time.LocalDateTime;

//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class SitemapGeneratorTest {

	private Boolean ifEqualActor1, ifEqualActor2;

	public SitemapGeneratorTest () {
		this.ifEqualActor1 = true;
		this.ifEqualActor2 = true;
	}

	@Actor
	public void actor1() {
	    SitemapGenerator sitemapGenerator;
	    sitemapGenerator = SitemapGenerator.of("http://www.javavids.com");
		sitemapGenerator.addPage(WebPage.builder().name("index.php").priority(1.0).changeFreqNever().lastMod(LocalDateTime.of(2019, 1, 1, 0, 0)).build());
		sitemapGenerator.addPage(WebPage.builder().name("latest.php").build());
		sitemapGenerator.addPage(WebPage.builder().name("contact.php").build());
		Image image = new Image();
		image.setLoc("http://example.com/image");
		String actualImageString = sitemapGenerator.constructImage(image);
		String expectedImageString = "<image:image>\n<image:loc>http://example.com/image</image:loc>\n</image:image>\n";
		if (!expectedImageString.equals(actualImageString)) {
		    ifEqualActor1 = false;
        }
	}

	@Actor
	public void actor2() {
        SitemapGenerator sitemapGenerator;
	    sitemapGenerator = SitemapGenerator.of("http://www.javavids.com");
		sitemapGenerator.addPage(WebPage.builder().name("index.php").priority(1.0).changeFreqNever().lastMod(LocalDateTime.of(2019, 1, 1, 0, 0)).build());
		sitemapGenerator.addPage(WebPage.builder().name("latest.php").build());
		sitemapGenerator.addPage(WebPage.builder().name("contact.php").build());
		Image image = new Image();
		image.setLoc("http://example.com/image");
		String actualImageString = sitemapGenerator.constructImage(image);
		String expectedImageString = "<image:image>\n<image:loc>http://example.com/image</image:loc>\n</image:image>\n";
		if (!expectedImageString.equals(actualImageString)) {
		    ifEqualActor2 = false;
        }
	}

	@Arbiter
	public void arbiter(ZZ_Result r) {
		r.r1 = ifEqualActor1;
		r.r2 = ifEqualActor2;
	}
}
