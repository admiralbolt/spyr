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
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

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
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager
					.addToBottom(new ExhaustCardsFromDeckAction(this.magicNumber, /* random= */false));
		} else {
			if (p.hasPower(LightEcoPower.POWER_ID)) {
				AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, LightEcoPower.POWER_ID, 1));
			}
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DarkEcoPower(p, 1), 1));
		}
	}

	@Override
	public void doUpgrade() {
		this.upgradeBaseCost(UPGRADED_COST);
	}

}
