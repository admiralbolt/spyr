package spyr.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DarkEcoPower extends SpyrPower {

	public static final String POWER_ID = "spyr:eco_dark";

	public DarkEcoPower(AbstractCreature owner, int strAmt) {
		super(POWER_ID, owner, strAmt);
	}

	@Override
	public void reducePower(int reduceAmount) {
		this.amount -= reduceAmount;
		if (this.amount == 0) {
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
		}
	}

	@Override
	public void atEndOfTurn(boolean isPlayer) {
		if (!isPlayer) {
			return;
		}
		if (AbstractDungeon.player.hasPower(NirvannaPower.POWER_ID)) {
			return;
		}
		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player,
				DarkEcoPower.POWER_ID, AbstractDungeon.player.getPower(DarkEcoPower.POWER_ID).amount));
	}

}
