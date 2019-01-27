package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

/**
 * Annihilates opponents. Removes both Dark & Light Eco.
 */
public class AnnihilatorBeam extends SpyrCard {

	public static final String ID = "spyr:annihilator_beam";
	public static final String NAME = "Annihilator Beam";
	public static final String DESCRIPTION = "Deal !D! damage to ALL enemies. Lose all forms. Deal !M! bonus damage for each form lost.";

	private static final int COST = 2;
	private static final int POWER = 10;
	private static final int LOST_FORM_BONUS_DAMAGE = 15;
	private static final int UPGRADE_LOST_FORM_DAMAGE = 10;

	public AnnihilatorBeam() {
		super(ID, NAME, DESCRIPTION, COST, AbstractCard.CardType.ATTACK,
				CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.ALL_ENEMY);
		this.damage = this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = LOST_FORM_BONUS_DAMAGE;
	}

  @Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager
					.addToBottom(new ReducePowerAction(p, p, LightEcoPower.POWER_ID, 1));
		}
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager
					.addToBottom(new ReducePowerAction(p, p, DarkEcoPower.POWER_ID, 1));
		}
		AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(p,
				new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1f));
		AbstractDungeon.actionManager
				.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
						this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
	}

	@Override
	public float calculateModifiedCardDamage(AbstractPlayer player,
			AbstractMonster mo, float tmp) {
		int numPowers = 0;
		if (player.hasPower(DarkEcoPower.POWER_ID)) {
			numPowers++;
		}
		if (player.hasPower(LightEcoPower.POWER_ID)) {
			numPowers++;
		}
		return tmp + numPowers * this.magicNumber;
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_LOST_FORM_DAMAGE);
	}

}
