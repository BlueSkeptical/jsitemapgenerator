package utcec;

import cz.jiripinkas.jsitemapgenerator.UrlUtil;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;

//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class UrlUtilTest {
    private Boolean ifEqualActor1, ifEqualActor2;

    public UrlUtilTest () {
        ifEqualActor1 = true;
        ifEqualActor2 = true;
    }

    @Actor
    public void actor1() {
        if (!"https://javalibs.com".equals(UrlUtil.connectUrlParts("https://javalibs.com", null))) {
            ifEqualActor1 = true;
        }
        else if (!"https://javalibs.com/page".equals(UrlUtil.connectUrlParts("https://javalibs.com", "page"))) {
            ifEqualActor1 = true;
        }
        else if (!"https://javalibs.com/page".equals(UrlUtil.connectUrlParts("https://javalibs.com", "/page"))) {
            ifEqualActor1 = true;
        }
    }

    @Actor
    public void actor2() {
        if (!"https://javalibs.com".equals(UrlUtil.connectUrlParts("https://javalibs.com", null))) {
            ifEqualActor1 = true;
        }
        else if (!"https://javalibs.com/page".equals(UrlUtil.connectUrlParts("https://javalibs.com", "page"))) {
            ifEqualActor1 = true;
        }
        else if (!"https://javalibs.com/page".equals(UrlUtil.connectUrlParts("https://javalibs.com", "/page"))) {
            ifEqualActor1 = true;
        }
    }

    @Arbiter
    public void arbiter(ZZ_Result r) {
        r.r1 = ifEqualActor1;
        r.r2 = ifEqualActor2;
    }
}
