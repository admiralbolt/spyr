package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

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
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
		} else if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, DarkEcoPower.POWER_ID, 1));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightEcoPower(p, 1), 1));
		}

	}

	@Override
	public void doUpgrade() {
		this.upgradeBaseCost(UPGRADED_COST);
	}

}
