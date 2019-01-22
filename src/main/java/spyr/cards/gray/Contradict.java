package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.TemporaryDarkEcoPower;
import spyr.powers.TemporaryLightEcoPower;

/**
 * The next cards you play are affected by both forms.
 */
public class Contradict extends SpyrCard {

	public static final String ID = "spyr:contradict";
	private static final int COST = 1;
	private static final int DOUBLE_AMOUNT = 1;
	private static final int UPGRADE_DOUBLE_AMOUNT = 1;

	public Contradict() {
		super(ID, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = DOUBLE_AMOUNT;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(p, p, new TemporaryDarkEcoPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(p, p, new TemporaryLightEcoPower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_DOUBLE_AMOUNT);
		this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;
		this.initializeDescription();
	}

}
