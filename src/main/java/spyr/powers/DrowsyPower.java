package spyr.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrowsyPower extends SpyrPower {

	public static final String POWER_ID = "spyr:drowsy";

	public DrowsyPower(AbstractCreature owner, int amount) {
		super(POWER_ID, owner, amount);
	}

	@Override
	public void atEndOfTurn(boolean isPlayer) {
		System.out.println("Highly suspicious.");
		this.flash();
		this.reducePower(1);
		if (this.amount <= 0) {
			AbstractDungeon.actionManager
					.addToBottom(new ApplyPowerAction(this.owner, this.owner, new SleepPower(this.owner, 1), 1));
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
		} else {
			this.updateDescription();
		}
	}

	@Override
	public void updateDescription() {
		this.description = this.powerStrings.DESCRIPTIONS[0] + this.amount
				+ ((this.amount == 1) ? " turn." : " turns.");
	}

}
