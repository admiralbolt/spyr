package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.patches.CardEnum;
import spyr.powers.BurnPower;
import spyr.utils.FormHelper;

/**
 * Deals damage to all in dark form. Applies poison to all in light form.
 */
public class BitterRelease extends FormAffectedCard {

	public static final String ID = "spyr:bitter_release";
	public static final String NAME = "Bitter Release";

	private static final int COST = 1;
	private static final int POWER = 9;
	private static final int UPGRADE_POWER = 5;
	private static final int BURN = 6;
	private static final int UPGRADE_BURN = 4;

	public BitterRelease() {
		super(ID, NAME, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);
		this.damage = this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = BURN;
	}

  @Override
	public String getShadow() {
		return "Deal !D! damage to ALL enemies.";
	}

	@Override
	public String getLight() {
		return "Apply !M! Burn to ALL enemies.";
	}

  @Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (FormHelper.shadowFormIsActive(p)) {
			AbstractDungeon.actionManager
					.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
							this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
		}
		if (FormHelper.lightFormIsActive(p)) {
			if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
				this.flash();
				for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
					if (monster.isDead || monster.isDying)
						continue;
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
							monster, p, new BurnPower(monster, p, this.magicNumber),
							this.magicNumber));
				}
			}
		}
	}

	@Override
	public void doUpgrade() {
		this.upgradeDamage(UPGRADE_POWER);
		this.upgradeMagicNumber(UPGRADE_BURN);
	}

}
