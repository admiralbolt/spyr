package spyr.cards.gray;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.utils.FormHelper;

/**
 * Switches between dark & light forms.
 */
public class Invert extends SpyrCard {

	public static final String ID = "spyr:invert";

	private static final int COST = 0;

	public Invert() {
		super(ID, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		FormHelper.swapOrChooseForm(p);
	}

	@Override
	public void doUpgrade() {
		this.retain = true;
		this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;
		this.initializeDescription();
	}

	/**
	 * Retain gets reset by the RestoreRetainedCardsAction, which happens every
	 * turn. So at the start of turn we just re-override it.
	 */
	@Override
	public void atTurnStart() {
		this.retain = this.upgraded;
	}

}
