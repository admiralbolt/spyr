package spyr.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class GrowingDarknessPower extends SpyrPower {

	public static final String POWER_ID = "spyr:growing_darkness";

	public GrowingDarknessPower(AbstractCreature owner, int strAmt) {
		super(POWER_ID, owner, strAmt);
	}

	@Override
	public void atStartOfTurnPostDraw() {
		this.flash();
		if (!this.owner.hasPower(DarkEcoPower.POWER_ID)) {
			return;
		}
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
	}

	@Override
	public void updateDescription() {
		this.description = this.powerStrings.DESCRIPTIONS[0] + this.amount + this.powerStrings.DESCRIPTIONS[1];
	}

}
