package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.FlickerPower;
import spyr.powers.RadiancePower;

/**
 * Gain radiance and flicker.
 */
public class FlareUp extends SpyrCard {

	public static final String ID = "spyr:flare_up";
	public static final String NAME = "Flare Up";
	public static final String DESCRIPTION = "Gain 1 Flicker. NL Gain !M! Radiance.";

	private static final int COST = 2;
	private static final int RADIANCE_AMOUNT = 2;
	private static final int UPGRADE_RADIANCE = 1;
	private static final int FLICKER_AMOUNT = 1;

	public int flicker;

	public FlareUp() {
		super(ID, NAME, DESCRIPTION, COST, AbstractCard.CardType.SKILL,
				CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = RADIANCE_AMOUNT;
		this.flicker = FLICKER_AMOUNT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new FlickerPower(p, this.flicker), this.flicker));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new RadiancePower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_RADIANCE);
	}

}
