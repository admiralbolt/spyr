package spyr.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * Makes all powers applied stronger. You might be asking yourself,
 * "But self, there's no functionality in this file." Well, you see a certain
 * level of black magic is necessary in order to make this work. So look in
 * spyr/patches/RadiancePatch.java.
 */
public class RadiancePower extends SpyrPower {

	public static final String POWER_ID = "spyr:radiance";

	public RadiancePower(AbstractCreature owner, int strAmt) {
		super(POWER_ID, owner, strAmt);
	}

	@Override
	public void reducePower(int reduceAmount) {
		this.amount -= reduceAmount;
		if (this.amount == 0) {
			AbstractDungeon.actionManager.addToTop(
					new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
		}
	}

  @Override
	public void updateDescription() {
		this.description = this.powerStrings.DESCRIPTIONS[0] + this.amount
				+ ((this.amount == 1) ? " time." : " times.");
	}

}
