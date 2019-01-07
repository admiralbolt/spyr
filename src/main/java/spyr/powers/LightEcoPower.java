package spyr.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LightEcoPower extends SpyrPower {

	public static final String POWER_ID = "spyr:eco_light";

	public LightEcoPower(AbstractCreature owner, int strAmt) {
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

}
