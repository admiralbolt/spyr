package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

/**
 * A block card that either defends a large amount one time in dark form, or a
 * small amount several times in light form.
 */
public class IronSkin extends SpyrCard {

	public static final String ID = "spyr:iron_skin";

	private static final int COST = 1;
	private static final int BLOCK = 4;

	private static final int BLOCK_TIMES = 2;
	private static final int UPGRADE_BLOCK_TIMES = 1;

	public IronSkin() {
		super(ID, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		this.block = this.baseBlock = BLOCK;
		this.magicNumber = this.baseMagicNumber = BLOCK_TIMES;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		// Need to decide if we want to double the intraction with dual form or not.
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(
					new GainBlockAction(p, p, this.block * this.magicNumber));
		}
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			for (int i = 0; i < this.magicNumber; i++) {
				AbstractDungeon.actionManager
						.addToBottom(new GainBlockAction(p, p, this.block));
			}
		}
	}

	@Override
	public void applyPowers() {
		super.applyPowers();
		StringBuilder description = new StringBuilder();
		if (AbstractDungeon.player.hasPower(DarkEcoPower.POWER_ID)) {
			description.append(
					String.format("Gain %d Block. NL ", this.block * this.magicNumber));
		}
		if (AbstractDungeon.player.hasPower(LightEcoPower.POWER_ID)) {
			description.append(this.cardStrings.EXTENDED_DESCRIPTION[1]);
		}
		this.rawDescription = description.toString();
		this.initializeDescription();
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_BLOCK_TIMES);
	}

}
