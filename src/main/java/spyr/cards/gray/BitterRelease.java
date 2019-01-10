package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

/**
 * Deals damage to all in dark form. Applies poison to all in light form.
 */
public class BitterRelease extends SpyrCard {

	public static final String ID = "spyr:bitter_release";

	private static final int COST = 1;
	private static final int POWER = 9;
	private static final int UPGRADE_POWER = 5;
	private static final int POISON = 5;
	private static final int UPGRADE_POISON = 3;

	public BitterRelease() {
		super(ID, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);
		this.damage = this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = POISON;
		this.initializeDualCardDescription();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager
					.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
							this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
		}
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
				this.flash();
				for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
					if (monster.isDead || monster.isDying)
						continue;
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
							monster, p, new PoisonPower(monster, p, this.magicNumber),
							this.magicNumber));
				}
			}
		}
	}

	@Override
	public void applyPowers() {
		this.loadDualCardDescription();
	}

	@Override
	public void doUpgrade() {
		this.upgradeDamage(UPGRADE_POWER);
		this.upgradeMagicNumber(UPGRADE_POISON);
	}

}
