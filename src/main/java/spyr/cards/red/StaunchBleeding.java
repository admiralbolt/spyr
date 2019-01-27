package spyr.cards.red;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.powers.StaunchBleedingPower;

public class StaunchBleeding extends SpyrCard {

	public static final String ID = "spyr:staunch_bleeding";
	public static final String NAME = "Staunch Bleeding";
	public static final String DESCRIPTION = "Whenever you lose HP from a card, gain !M! Regen.";

	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	private static final int REGEN_AMOUNT = 1;

	public StaunchBleeding() {
		super(ID, NAME, DESCRIPTION, COST, AbstractCard.CardType.POWER,
				AbstractCard.CardColor.RED, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = REGEN_AMOUNT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new StaunchBleedingPower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public void doUpgrade() {
		this.upgradeBaseCost(UPGRADED_COST);
	}

}
