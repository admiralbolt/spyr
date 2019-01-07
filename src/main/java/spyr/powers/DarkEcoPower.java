package spyr.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class DarkEcoPower extends SpyrPower {

	public static final String POWER_ID = "spyr:eco_dark";

	private static final PowerStrings DARK_ECO = CardCrawlGame.languagePack
			.getPowerStrings(POWER_ID);

	public static final String NAME = DARK_ECO.NAME;

	public DarkEcoPower(AbstractCreature owner, int strAmt) {
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
