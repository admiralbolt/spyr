package spyr.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * At the end of turn if in shadow form add a Madness to the top of yoru deck
 * and discard.
 */
public class WhisperingMadnessPower extends SpyrPower {

	public static final String POWER_ID = "spyr:whispering_madness";

	private boolean upgraded;

	public WhisperingMadnessPower(AbstractCreature owner, int strAmt) {
		super(POWER_ID, owner, strAmt);
		this.upgraded = upgraded;
	}

	@Override
	public void atEndOfTurn(boolean isPlayer) {
		this.flash();
		if (!isPlayer || !this.owner.hasPower(DarkEcoPower.POWER_ID)) {
			return;
		}
		AbstractCard madness = new Madness();
    madness.upgrade();
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(madness, this.amount, false, false));
		AbstractDungeon.actionManager.addToTop(new MakeTempCardInDiscardAction(madness, this.amount));
	}

	@Override
	public void updateDescription() {
    this.description = this.powerStrings.DESCRIPTIONS[0];
	}

}
