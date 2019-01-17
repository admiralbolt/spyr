package spyr.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import spyr.Constants;

@SpirePatch(cls = "com.megacrit.cardcrawl.actions.GameActionManager", method = "getNextAction")
public class SleepPatch {

	/**
	 * Alright so this is the coolest black magic I've ever seen. What's happening
	 * here is some next level reflection bull-shit to edit the takeTurn() method of
	 * the AbstractMonster class. Documentation for this ExprEditor() is hard to
	 * come by, so here's some required reading:
	 * http://www2.sys-con.com/itsg/virtualcd/java/archives/0901/chiba/index.html
	 * The $_ = $proceed($$); is some special syntax that is translated as: Do what
	 * you were going to do.
	 * 
	 * So, if they have a sleep power we skip their turn. If they don't, we just
	 * call the takeTurn function as normal.
	 */

	// @formatter:off
	public static String NEW_TAKE_TURN_CODE = "" + 
      "if (!m.hasPower(spyr.powers.SleepPower.POWER_ID)) {" +
	  "  $_ = $proceed($$);" +
	  "}";
	// @formatter:on

	public static ExprEditor Instrument() {
		return new ExprEditor() {
			@Override
			public void edit(MethodCall m) throws CannotCompileException {
				if (m.getClassName().equals(Constants.ABSTRACT_MONSTER) && m.getMethodName().equals("takeTurn")) {
					m.replace(NEW_TAKE_TURN_CODE);
				}
			}
		};
	}

}
