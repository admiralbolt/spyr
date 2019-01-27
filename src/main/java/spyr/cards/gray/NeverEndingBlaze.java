package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.NeverEndingBlazePower;

/**
 * Burn stacks don't decrease while in light form.
 */
public class NeverEndingBlaze extends SpyrCard {

	public static final String ID = "spyr:never_ending_blaze";
	public static final String NAME = "Never Ending Blaze";
	public static final String DESCRIPTION = "Whihle in LightForm, Burn stacks don't decrease.";

	public static final int COST = 3;
	public static final int UPGRADED_COST = 2;

	public NeverEndingBlaze() {
		super(ID, NAME, DESCRIPTION, COST, AbstractCard.CardType.POWER,
				CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = 1;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new NeverEndingBlazePower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public void doUpgrade() {
		this.upgradeBaseCost(UPGRADED_COST);
	}

}
