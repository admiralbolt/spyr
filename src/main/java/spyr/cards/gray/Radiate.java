package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.RadiancePower;

/**
 * Gain radiance AKA activate ez mode.
 */
public class Radiate extends SpyrCard {

	public static final String ID = "spyr:radiate";
	public static final String NAME = "Radiate";
	public static final String DESCRIPTION = "Gain !M! Radiance.";

	public static final int COST = 1;
	public static final int BUFF_AMOUNT = 1;
	public static final int UPGRADE_BUFF_AMOUNT = 1;

	public Radiate() {
		super(ID, NAME, DESCRIPTION, COST, AbstractCard.CardType.POWER,
				CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = 1;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new RadiancePower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_BUFF_AMOUNT);
	}

}
