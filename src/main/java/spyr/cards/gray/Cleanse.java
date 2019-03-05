package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;

/**
 * Remove all powers from an enemy.
 */
public class Cleanse extends SpyrCard {

	public static final String ID = "spyr:cleanse";
	public static final String NAME = "Cleanse";
	public static final String DESCRIPTION = "Remove all powers from an enemey. NL Exhaust.";

	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;

	public Cleanse() {
		super(ID, NAME, DESCRIPTION, COST, AbstractCard.CardType.SKILL,
				CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ENEMY);
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new RemoveAllPowersAction(m, /* debuffsOnly= */false));
	}

	@Override
	public void doUpgrade() {
		this.upgradeBaseCost(UPGRADED_COST);
	}

}
