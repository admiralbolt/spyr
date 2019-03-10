package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.NirvannaPower;

/**
 * Light / dark stacks don't decay.
 */
public class Nirvanna extends SpyrCard {

	public static final String ID = "spyr:nirvanna";
	public static final String NAME = "Nirvanna";
	public static final String DESCRIPTION = "Light & Dark stacks don't decay.";

	private static final int COST = 3;
	private static final int UPGRADED_COST = 2;

	public Nirvanna() {
		super(ID, NAME, DESCRIPTION, COST, AbstractCard.CardType.POWER, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NirvannaPower(p, 1), 1));
	}

	@Override
	public void doUpgrade() {
		this.upgradeBaseCost(UPGRADED_COST);
	}

}
