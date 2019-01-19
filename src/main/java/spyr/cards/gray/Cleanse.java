package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.LightEcoPower;

/**
 * Remove all powers from an enemy.
 */
public class Cleanse extends SpyrCard {

	public static final String ID = "spyr:cleanse";

	private static final int COST = 2;
  private static final int UPGRADED_COST = 1;

	public Cleanse() {
		super(ID, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.magicNumber = this.baseMagicNumber = DURATION;
    this.exhaust = True;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
    AbstractDungeon.actionManager.addToBottom(new RemoveAllPowersAction(m, /*debuffsOnly=*/false));
  }

	@Override
	public void doUpgrade() {
    this.upgradeBaseCost(UPGRADED_COST);
	}

}
