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
import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

/**
 * Basic defend card.
 */
public class EnergyWave extends SpyrCard {

	public static final String ID = "spyr:energy_wave";

	private static final int COST = 1;
	private static final int POWER = 12;
	private static final int UPGRADE_BONUS = 6;

	public EnergyWave() {
		super(ID, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED,
				AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);
		this.damage = this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = POWER;
		this.isMultiDamage = true;
		this.exhaust = true;
		this.tags.add(CardTags.HEALING);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		// This is done as two separate if statements to explicitly support
		// DualForm.
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager
					.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
							this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
			AbstractDungeon.actionManager.addToBottom(new DamageAction(p,
					new DamageInfo(p, this.damage, this.damageTypeForTurn)));
		}
		if (p.hasPower(LightEcoPower.POWER_ID)) {
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
	
	@Override
	public void applyPowers() {
		super.applyPowers();
		StringBuilder description = new StringBuilder();
		if (AbstractDungeon.player.hasPower(DarkEcoPower.POWER_ID)) {
			description.append(this.cardStrings.EXTENDED_DESCRIPTION[0]);
		}
		if (AbstractDungeon.player.hasPower(LightEcoPower.POWER_ID)) {
			description.append(this.cardStrings.EXTENDED_DESCRIPTION[1]);
		}
		description.append(this.cardStrings.DESCRIPTION);
		this.rawDescription = description.toString();
		this.initializeDescription();
	}

}
