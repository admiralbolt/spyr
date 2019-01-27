package spyr.cards.gray;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.actions.ExhaustCardsFromDeckAction;
import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;

/**
 * Exhaust your deck.
 */
public class Amnesia extends SpyrCard {

	public static final String ID = "spyr:amnesia";
	public static final String NAME = "Amnesia";
	public static final String DESCRIPTION = "Exhaust your deck. NL Exhaust.";

	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;

	public Amnesia() {
		super(ID, NAME, DESCRIPTION, COST, AbstractCard.CardType.SKILL,
				CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);
		this.exhaust = true;
	}

  @Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		System.out.println("drawSize: " + p.drawPile.size());
		AbstractDungeon.actionManager.addToBottom(
				new ExhaustCardsFromDeckAction(p.drawPile.size(), /* random= */false));
	}

	@Override
	public void doUpgrade() {
		this.upgradeBaseCost(UPGRADED_COST);
	}

}
