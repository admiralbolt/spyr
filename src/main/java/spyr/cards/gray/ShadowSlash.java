package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;

/**
 * Attack card that deals more damage in dark form.
 */
public class ShadowSlash extends SpyrCard {

	public static final String ID = "spyr:shadow_slash";

	private static final int COST = 1;
	private static final int POWER = 7;
	private static final int DARK_FORM_SCALING = 4;
	private static final int UPGRADE_POWER = 2;
	private static final int UPGRADE_SCALING = 3;

	public ShadowSlash() {
		super(ID, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED,
				AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ENEMY);
		this.damage = this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = DARK_FORM_SCALING;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
						AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

	}

	@Override
	public float calculateModifiedCardDamage(AbstractPlayer player,
			AbstractMonster mo, float tmp) {
		if (player.hasPower(DarkEcoPower.POWER_ID)) {
			return tmp + this.magicNumber;
		}
		return tmp;
	}

	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		super.calculateCardDamage(mo);
		this.rawDescription = this.cardStrings.DESCRIPTION;
		this.initializeDescription();
	}

	@Override
	public void doUpgrade() {
		this.upgradeDamage(UPGRADE_POWER);
		this.upgradeMagicNumber(UPGRADE_SCALING);
	}

}
