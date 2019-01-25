package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.utils.FormHelper;

/**
 * Block a small amount. Look at top 3 cards of deck. Draw 1 and discard the
 * rest.
 */
public class Enlighten extends SpyrCard {

	public static final String ID = "spyr:enlighten";
	private static final int COST = 1;
	private static final int NUM_CARDS = 3;

	private static final int UPGRADED_COST = 0;

	public Enlighten() {
		super(ID, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = NUM_CARDS;
		this.tags.add(SpyrTags.LIGHT);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (FormHelper.lightFormIsActive(p)) {
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
		}
		FormHelper.maybeSwitchToLightForm(p);
	}

	@Override
	public void doUpgrade() {
		this.upgradeBaseCost(UPGRADED_COST);
	}

}
