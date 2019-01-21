package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.actions.ReduceHandCostAction;
import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

/**
 * An energy card that has different effects depending on the form: ShadowForm:
 * Reduce the cost of 1 cards in your hand by 1. LightForm: Gain 2>3 energy.
 */
public class ShadowPlay extends SpyrCard {

	public static final String ID = "spyr:shadow_play";
	private static final int COST = 1;
	private static final int ENERGY = 2;
	private static final int UPGRADE_ENERGY = 1;

	public ShadowPlay() {
		super(ID, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF, /* is_dual= */true);
		this.magicNumber = this.baseMagicNumber = ENERGY;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ReduceHandCostAction(this.upgraded));
		}
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
		}
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_ENERGY);
	}

}
