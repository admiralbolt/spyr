package spyr.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import spyr.patches.SpyrTags;

public class TemporaryLightEcoPower extends SpyrPower {

	public static final String POWER_ID = "spyr:temporary_eco_light";

	public TemporaryLightEcoPower(AbstractCreature owner, int strAmt) {
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
	public void onUseCard(AbstractCard card, UseCardAction action) {
		if (card.hasTag(SpyrTags.SHADOW) || card.hasTag(SpyrTags.LIGHT)) {
			this.flash();
			this.reducePower(1);
		}
	}

	@Override
	public void atEndOfTurn(boolean isPlayer) {
		if (!isPlayer) {
			return;
		}
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
	}

}
