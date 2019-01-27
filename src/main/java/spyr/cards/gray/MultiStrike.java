package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.patches.CardEnum;
import spyr.powers.BurnPower;
import spyr.utils.FormHelper;

/**
 * Pummel esque card. Deals normal damage in dark form and applies burn in light
 * form.
 */
public class MultiStrike extends FormAffectedCard {

	public static final String ID = "spyr:multi_strike";
	public static final String NAME = "Multi Strike";

	private static final int COST = 1;
	private static final int POWER = 1;
	private static final int BURN = 1;
	private static final int TIMES = 5;
	private static final int UPGRADE_TIMES = 2;

	public MultiStrike() {
		super(ID, NAME, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.damage = this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = TIMES;
		// Amusingly this will work with perfect strike.
		this.tags.add(CardTags.STRIKE);
	}

	@Override
	public String getShadow() {
		return "Deal !D! damage !M! times.";
	}

	@Override
	public String getLight() {
		return String.format("Apply %d Burn !M! times.", BURN);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (FormHelper.shadowFormIsActive(p)) {
			for (int i = 0; i < this.magicNumber; i++) {
				AbstractDungeon.actionManager.addToBottom(
						new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
								AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
			}
		}
		if (FormHelper.lightFormIsActive(p)) {
			for (int i = 0; i < this.magicNumber; i++) {
				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(m, p, new BurnPower(m, p, BURN), BURN));
			}
		}
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_TIMES);
	}

}
