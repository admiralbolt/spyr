package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.utils.FormHelper;

/**
 * Switches between dark & light forms AND draw. Woooho.
 */
public class QuickChange extends SpyrCard {

	public static final String ID = "spyr:quick_change";
	public static final String NAME = "Quick Change";
	public static final String DESCRIPTION = "Draw !M! card. NL Switch between ShadowForm and LightForm.";
  public static final String UPGRADED_DESCRIPTION = "Retain. NL Draw !M! card. NL Switch between ShadowForm and LightForm.";

	private static final int COST = 0;
	private static final int CARD_DRAW = 1;

	public QuickChange() {
		super(ID, NAME, DESCRIPTION, COST, AbstractCard.CardType.SKILL,
				CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = CARD_DRAW;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new DrawCardAction(p, this.magicNumber));
		FormHelper.invertStacks(p);
	}

	@Override
	public void doUpgrade() {
    this.retain = true;
    this.rawDescription = this.UPGRADED_DESCRIPTION;
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
