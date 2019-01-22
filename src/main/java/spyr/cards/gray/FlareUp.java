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
 * Block a small amount. Look at top 3 cards of deck. Draw 1 and discard the
 * rest.
 */
public class FlareUp extends SpyrCard {

	public static final String ID = "spyr:flare_up";
	private static final int COST = 2;
	private static final int RADIANCE_AMOUNT = 2;
	private static final int UPGRADE_RADIANCE = 1;
	private static final int FLICKER_AMOUNT = 1;

	public int flicker;

	public FlareUp() {
		super(ID, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = RADIANCE_AMOUNT;
		this.flicker = FLICKER_AMOUNT;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(p, p, new FlickerPower(p, this.flicker), this.flicker));
		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(p, p, new RadiancePower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_RADIANCE);
	}

}
