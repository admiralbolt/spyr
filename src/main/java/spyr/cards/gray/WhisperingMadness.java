package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.WhisperingMadnessPower;

/**
 * EOT if in ShadowForm add a madness+ to the top of your deck and discard pile.
 */
public class WhisperingMadness extends SpyrCard {

	public static final String ID = "spyr:whispering_madness";
	public static final String NAME = "Whispering Madness";
	public static final String DESCRIPTION = "At the end of your turn, if you are in ShadowForm add Maddness+ to the top of your deck and discard piles.";
	public static final String UPGRADE_DESCRIPTION = "Innate. NL At the end of your turn, if you are in ShadowForm add Maddness+ to the top of your deck and discard piles.";

	public static final int COST = 2;

	public WhisperingMadness() {
		super(ID, NAME, DESCRIPTION, COST, AbstractCard.CardType.POWER,
				CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(p, p, new WhisperingMadnessPower(p, 1), 1));
	}

	@Override
	public void doUpgrade() {
		this.isInnate = true;
		this.rawDescription = UPGRADE_DESCRIPTION;
		this.initializeDescription();
	}

}
