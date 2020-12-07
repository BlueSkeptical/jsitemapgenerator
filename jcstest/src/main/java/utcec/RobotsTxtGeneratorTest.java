package utcec;

import cz.jiripinkas.jsitemapgenerator.robots.RobotsRule;
import cz.jiripinkas.jsitemapgenerator.robots.RobotsTxtGenerator;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;

@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class RobotsTxtGeneratorTest {
    private Boolean ifEqualActor1, ifEqualActor2;

    public RobotsTxtGeneratorTest () {
        ifEqualActor1 = true;
        ifEqualActor2 = true;
    }

    @Actor
    public void actor1() {
        String actual = RobotsTxtGenerator.of("https://example.com")
                .addSitemap("sitemap.xml")
                .addRule(RobotsRule.builder().userAgentAll().allowAll().build())
                .toString();
        String expected = "Sitemap: https://example.com/sitemap.xml\n" +
                "User-agent: *\n" +
                "Allow: /";
        if (!actual.equals(expected)) {
            ifEqualActor1 = false;
        }
    }

    @Actor
    public void actor2() {
        String actual = RobotsTxtGenerator.of("https://example.com")
                .addSitemap("sitemap.xml")
                .addRule(RobotsRule.builder().userAgentAll().allowAll().build())
                .toString();
        String expected = "Sitemap: https://example.com/sitemap.xml\n" +
                "User-agent: *\n" +
                "Allow: /";
        if (!actual.equals(expected)) {
            ifEqualActor1 = false;
        }
    }

    @Arbiter
    public void arbiter(ZZ_Result r) {
        r.r1 = ifEqualActor1;
        r.r2 = ifEqualActor2;
    }
}