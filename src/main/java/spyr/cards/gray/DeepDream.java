package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.actions.ExhaustCardsFromDeckAction;
import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;
import spyr.utils.FormHelper;

/**
 * Switch to dark form. Exhaust 2 cards from your deck if already in dark form.
 */
public class DeepDream extends SpyrCard {

	public static final String ID = "spyr:deep_dream";
	private static final int COST = 1;
	private static final int UPGRADED_COST = 0;

	private static final int NUM_CARDS_EXHAUST = 2;

	public DeepDream() {
		super(ID, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = NUM_CARDS_EXHAUST;
		this.tags.add(SpyrTags.SHADOW);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (FormHelper.shadowFormIsActive(p)) {
			AbstractDungeon.actionManager
					.addToBottom(new ExhaustCardsFromDeckAction(this.magicNumber, /* random= */false));
		}
		FormHelper.maybeSwitchToShadowForm(p);
	}

	@Override
	public void doUpgrade() {
		this.upgradeBaseCost(UPGRADED_COST);
	}

}
