package utcec;

import cz.jiripinkas.jsitemapgenerator.exception.InvalidUrlException;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;

//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class WebPageTest {
    private Boolean ifEqualActor1, ifEqualActor2;

    public WebPageTest () {
        ifEqualActor1 = true;
        ifEqualActor2 = true;
    }

    @Actor
    public void actor1() {
        try {
            SitemapGenerator.of("www.javavids.com");
        }
        catch (Exception e) {
            if (e instanceof InvalidUrlException) {
                ifEqualActor1 = true;
            } else {
                ifEqualActor1 = false;
            }
        }
    }

    @Actor
    public void actor2() {
        try {
            SitemapGenerator.of("www.javavids.com");
        }
        catch (Exception e) {
            if (e instanceof InvalidUrlException) {
                ifEqualActor1 = true;
            } else {
                ifEqualActor1 = false;
            }
        }
    }

    @Arbiter
    public void arbiter(ZZ_Result r) {
        r.r1 = ifEqualActor1;
        r.r2 = ifEqualActor2;
    }
}
