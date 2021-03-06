package spyr.patches;

import java.util.Arrays;
import java.util.HashSet;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

@SpirePatch(cls = "com.megacrit.cardcrawl.actions.common.ApplyPowerAction", method = SpirePatch.CONSTRUCTOR, paramtypez = {
		AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class, boolean.class,
		AbstractGameAction.AttackEffect.class })
public class RadiancePatch {

	/**
	 * As it turns out, this is busted as fuck if we let it apply to everything.
	 * So let's limit it a bit yeah?
	 */
  public static final String[] POWER_IDS = {
		WeakPower.POWER_ID,
		VulnerablePower.POWER_ID,
		FrailPower.POWER_ID,
		StrengthPower.POWER_ID,
		LoseStrengthPower.POWER_ID,
		DexterityPower.POWER_ID,
		LoseDexterityPower.POWER_ID,
		// Powers from spyr.
		spyr.powers.BurnPower.POWER_ID,
		spyr.powers.DrowsyPower.POWER_ID,
		spyr.powers.FlickerPower.POWER_ID,
		spyr.powers.RadiancePower.POWER_ID
	};
	public static final HashSet<String> POWERS_TO_ENHANCE = new HashSet<>(Arrays.asList(POWER_IDS));

	/**
	 * More black magic. We are live editing the ApplyPowerAction() constructor. In
	 * this case we need to apply extra powers if the source has the radiance power.
	 * Exciting. Also incredibly flaky.
	 */
	@SpireInsertPatch(rloc = 13)
	public static void Insert(ApplyPowerAction action, AbstractCreature target, AbstractCreature source,
			AbstractPower powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect) {
		// SporeCloud power has a null source, so double check the source first.
		if (source != null && source.hasPower(spyr.powers.RadiancePower.POWER_ID) && POWERS_TO_ENHANCE.contains(powerToApply.ID)) {
			int radianceAmount = source.getPower(spyr.powers.RadiancePower.POWER_ID).amount;
			System.out.println(String.format("radianceAmount: %s, powerToApply.amount: %s, action.amount: %s",
					radianceAmount, powerToApply.amount, action.amount));
			// If we are applying a debuff we should invert the radianceAmount. Otherwise we
			// will do the opposite effect of the debuff.
			if (powerToApply.amount < 0) {
				radianceAmount *= -1;
			}
			powerToApply.amount += radianceAmount;
			action.amount += radianceAmount;
		}
	}

}
