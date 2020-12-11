package utcec;

import cz.jiripinkas.jsitemapgenerator.robots.RobotsRule;
import cz.jiripinkas.jsitemapgenerator.robots.RobotsRuleException;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.ZZ_Result;
//@JCStressTest
// Outline the outcomes here. The default outcome is provided, you need to remove it:
@Outcome(id = "true, true", expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@State
public class RobotsRuleTest2 {

	private Boolean ifEqualActor1, ifEqualActor2;

	public RobotsRuleTest2 () {
		this.ifEqualActor1 = true;
		this.ifEqualActor2 = true;
	}

	@Actor
	public void actor1() {
	    try {
            RobotsRule.builder().allowAll().disallowAll().build();
        } catch (RobotsRuleException e) {
            return;
        }
	    ifEqualActor1 = false;
	}

	@Actor
	public void actor2() {
        try {
            RobotsRule.builder().allowAll().disallowAll().build();
        } catch (RobotsRuleException e) {
            return;
        }
	    ifEqualActor2 = false;
	}

	@Arbiter
	public void arbiter(ZZ_Result r) {
		r.r1 = ifEqualActor1;
		r.r2 = ifEqualActor2;
	}
}