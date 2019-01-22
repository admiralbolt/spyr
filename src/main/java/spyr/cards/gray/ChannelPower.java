package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;
import spyr.utils.FormHelper;

/**
 * Deals high damage in shadow form, apply vulnerable and weak in light form.
 */
public class ChannelPower extends SpyrCard {

	public static final String ID = "spyr:channel_power";

	private static final int COST = 2;
	private static final int POWER = 20;
	private static final int UPGRADE_POWER = 8;

	private static final int STATUS_DURATION = 2;
	private static final int UPGRADE_DURATION = 1;

	public ChannelPower() {
		super(ID, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ENEMY, /* is_dual= */true);
		this.damage = this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = STATUS_DURATION;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (FormHelper.shadowFormIsActive(p)) {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
					new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		}
		if (FormHelper.lightFormIsActive(p)) {
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
		}
	}

	@Override
	public void doUpgrade() {
		this.upgradeDamage(UPGRADE_POWER);
		this.upgradeMagicNumber(UPGRADE_DURATION);
	}

}
