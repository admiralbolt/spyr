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
	public static final String NAME = "Contradict";
	public static final String DESCRIPTION = "This turn, your next card is affected by both LightForm AND ShadowForm.";
	public static final String UPGRADE_DESCRIPTION = "This turn, your next !M! cards are affected by both LightForm AND ShadowForm.";

	private static final int COST = 1;
	private static final int DOUBLE_AMOUNT = 1;
	private static final int UPGRADE_DOUBLE_AMOUNT = 1;

	public Contradict() {
		super(ID, NAME, DESCRIPTION, COST, AbstractCard.CardType.SKILL,
				CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = DOUBLE_AMOUNT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new TemporaryDarkEcoPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new TemporaryLightEcoPower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_DOUBLE_AMOUNT);
		this.rawDescription = UPGRADE_DESCRIPTION;
		this.initializeDescription();
	}

}
