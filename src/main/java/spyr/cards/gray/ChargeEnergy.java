package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

/**
 * Gain Strength & Dexterity. If in shadow form gain weaken, if in light form
 * gain vulnerable.
 */
public class ChargeEnergy extends SpyrCard {

	public static final String ID = "spyr:charge_energy";
	public static final int COST = 1;
	public static final int BUFF_AMOUNT = 1;
	public static final int UPGRADE_BUFF_AMOUNT = 1;

	public ChargeEnergy() {
		super(ID, COST, AbstractCard.CardType.POWER, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF, /* is_dual= */true);
		this.magicNumber = this.baseMagicNumber = 1;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(p, p, new WeakPower(p, this.magicNumber, false), this.magicNumber));
		}
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(p, p, new VulnerablePower(p, this.magicNumber, false), this.magicNumber));
		}
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_BUFF_AMOUNT);
	}

}
