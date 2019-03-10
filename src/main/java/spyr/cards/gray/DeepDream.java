package spyr.cards.gray;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.actions.ExhaustCardsFromDeckAction;
import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.utils.FormHelper;

/**
 * Switch to dark form. Exhaust 2 cards from your deck if already in dark form.
 */
public class DeepDream extends FormAffectedCard {

	public static final String ID = "spyr:deep_dream";
	public static final String NAME = "Deep Dream";

	private static final int COST = 1;
	private static final int UPGRADED_COST = 0;
	private static final int NUM_CARDS_EXHAUST = 2;

	public DeepDream() {
		super(ID, NAME, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = NUM_CARDS_EXHAUST;
		this.tags.add(SpyrTags.SHADOW);
	}

	@Override
	public String getShadow() {
		return "Exhhaust !M! cards from your deck.";
	}

	@Override
  public String getSuffix() {
    return "Gain 1 ShadowEnergy.";
  }

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (FormHelper.shadowFormIsActive(p)) {
			AbstractDungeon.actionManager.addToBottom(
					new ExhaustCardsFromDeckAction(this.magicNumber, /* random= */false));
		}
		FormHelper.applyShadowStacks(p);
	}

	@Override
	public void doUpgrade() {
		this.upgradeBaseCost(UPGRADED_COST);
	}

}
