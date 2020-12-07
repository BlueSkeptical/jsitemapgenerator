package utcec;

import cz.jiripinkas.jsitemapgenerator.robots.RobotsRule;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;


//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class RobotsRuleTest {
    private Boolean ifEqualActor1, ifEqualActor2;

    public RobotsRuleTest() {
        ifEqualActor1 = true;
        ifEqualActor2 = true;
    }

    @Actor
    public void actor1() {
        RobotsRule robotsRule = RobotsRule.builder().allowAll().userAgentAll().build();
        if (!"/".equals(robotsRule.getAllows().get(0))) {
            ifEqualActor1 = false;
        } else if (!"*".equals(robotsRule.getUserAgent())) {
            ifEqualActor1 = false;
        } else if (!robotsRule.getDisallows().isEmpty()) {
            ifEqualActor1 = false;
        }
    }

    @Actor
    public void actor2() {
        RobotsRule robotsRule = RobotsRule.builder().allowAll().userAgentAll().build();
        if (!"/".equals(robotsRule.getAllows().get(0))) {
            ifEqualActor1 = false;
        } else if (!"*".equals(robotsRule.getUserAgent())) {
            ifEqualActor1 = false;
        } else if (!robotsRule.getDisallows().isEmpty()) {
            ifEqualActor1 = false;
        }
    }

    @Arbiter
    public void arbiter(ZZ_Result r) {
        r.r1 = ifEqualActor1;
        r.r2 = ifEqualActor2;
    }
}
