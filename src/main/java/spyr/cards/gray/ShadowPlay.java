package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.actions.ReduceHandCostAction;
import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

/**
 * An energy card that has different effects depending on the form: ShadowForm:
 * Reduce the cost of 1 cards in your hand by 1. LightForm: Gain 2>3 energy.
 */
public class ShadowPlay extends FormAffectedCard {

	public static final String ID = "spyr:shadow_play";
	public static final String NAME = "Shadow Play";

	private static final int COST = 1;
	private static final int ENERGY = 2;
	private static final int UPGRADE_ENERGY = 1;

	public ShadowPlay() {
		super(ID, NAME, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = ENERGY;
	}

	@Override
	public String getShadow() {
		if (this.upgraded) {
			return "Reduce the cost of a card in your hand by 1 this combat.";
		}
		return "Reduce the cost of ALL cards in your hand by 1 this combat.";
	}

	@Override
	public String getLight() {
		return "Gain !M! Energy.";
	}

	@Override
	public String getSuffix() {
		return "Exhaust.";
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager
					.addToBottom(new ReduceHandCostAction(this.upgraded));
		}
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager
					.addToBottom(new GainEnergyAction(this.magicNumber));
		}
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_ENERGY);
	}

}
