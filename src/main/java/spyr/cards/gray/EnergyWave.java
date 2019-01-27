package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.actions.HealAllEnemiesAction;
import spyr.patches.CardEnum;
import spyr.utils.FormHelper;

/**
 * Deals damage to all characters in dark form, heals all characters in light
 * form.
 */
public class EnergyWave extends FormAffectedCard {

	public static final String ID = "spyr:energy_wave";
	public static final String NAME = "Energy Wave";

	private static final int COST = 1;
	private static final int POWER = 12;
	private static final int UPGRADE_BONUS = 6;

	public EnergyWave() {
		super(ID, NAME, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);
		this.damage = this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = POWER;
		this.isMultiDamage = true;
		this.exhaust = true;
		this.tags.add(CardTags.HEALING);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (FormHelper.shadowFormIsActive(p)) {
			AbstractDungeon.actionManager
					.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
							this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
			AbstractDungeon.actionManager.addToBottom(new DamageAction(p,
					new DamageInfo(p, this.damage, this.damageTypeForTurn)));
		}
		if (FormHelper.lightFormIsActive(p)) {
			AbstractDungeon.actionManager
					.addToBottom(new HealAllEnemiesAction(p, this.multiDamage));
			AbstractDungeon.actionManager
					.addToBottom(new HealAction(p, p, this.damage));
		}
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_BONUS);
		this.upgradeDamage(UPGRADE_BONUS);
	}

}
