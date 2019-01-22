package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;
import spyr.utils.FormHelper;

/**
 * High attack card in shadow form, strength reduction card in light form.
 */
public class EnvelopingShadow extends SpyrCard {

	public static final String ID = "spyr:enveloping_shadow";

	private static final int COST = 2;
	private static final int POWER = 6;
	private static final int STRENGTH_REDUCTION = 1;

	private static final int UPGRADE_POWER = 1;
	private static final int UPGRADE_COST = 1;

	public EnvelopingShadow() {
		super(ID, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.ENEMY, /* is_dual= */true);
		this.damage = this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = STRENGTH_REDUCTION;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (FormHelper.shadowFormIsActive(p)) {
			for (int i = 0; i < 3; i++) {
				AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
						new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
			}
		}
		if (FormHelper.lightFormIsActive(p)) {
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber), -this.magicNumber));
		}

	}

	@Override
	public void doUpgrade() {
		this.upgradeDamage(UPGRADE_POWER);
		this.upgradeBaseCost(UPGRADE_COST);
	}

}
