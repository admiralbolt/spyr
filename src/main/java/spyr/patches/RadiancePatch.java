package spyr.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(cls = "com.megacrit.cardcrawl.actions.common.ApplyPowerAction", method = SpirePatch.CONSTRUCTOR, paramtypez = {
		AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class, boolean.class,
		AbstractGameAction.AttackEffect.class })
public class RadiancePatch {

	/**
	 * More black magic. We are live editing the ApplyPowerAction() constructor. In
	 * this case we need to apply extra powers if the source has the radiance power.
	 * Exciting.
   * Maybe rloc = 6 is correct?
	 */
	@SpireInsertPatch(rloc = 4, localvars = { "amount" })
	public static void Insert(ApplyPowerAction action, AbstractCreature target, AbstractCreature source,
			AbstractPower powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect,
			int amount) {
		System.out.println("AYYYY");
		if (source.hasPower(spyr.powers.RadiancePower.POWER_ID)) {
			int radianceAmount = source.getPower(spyr.powers.RadiancePower.POWER_ID).amount;
			powerToApply.amount += radianceAmount;
			amount += radianceAmount;
		}
	}

	// public static ExprEditor Instrument() {
	// return new ExprEditor() {
	// @Override
	// public void edit(MethodCall m) throws CannotCompileException {
	// System.out.println("HEY: " + m.getMethodName());
	// if (m.getClassName().equals(Constants.APPLY_POWER_ACTION_CLASS)
	// && m.getMethodName().equals("ApplyPowerAction")) {
	// m.replace(NEW_APPLY_POWER_ACTION_CODE);
	// }
	// }
	// };
	// }

}
