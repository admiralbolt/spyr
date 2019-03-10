package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.SweepingBeamEffect;

import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.utils.FormHelper;

/**
 * Deals damage to all enemies and switches to light form. If already in light
 * form, applies 1 vulnerable. If upgraded applies 1 vulnerable and 1 weaken.
 */
public class LightRay extends FormAffectedCard {

	public static final String ID = "spyr:light_ray";
	public static final String NAME = "Light Ray";

	private static final int COST = 1;
	private static final int POWER = 6;
	private static final int UPGRADE_POWER = 3;
	private static final int STATUS_DURATION = 1;

	public LightRay() {
		super(ID, NAME, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ALL_ENEMY);
		this.damage = this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = STATUS_DURATION;
		this.isMultiDamage = true;
		this.tags.add(SpyrTags.LIGHT);
	}

	@Override
	public String getPrefix() {
		return "Deal !D! damage to ALL enemies.";
	}

	@Override
	public String getLight() {
		if (this.upgraded) {
			return "Apply 1 Weak and Vulnerable to ALL enemies.";
		}
		return "Apply 1 Vulnerable to ALL enemies.";
	}

	@Override
	public String getSuffix() {
		return "Gain 1 LightEnergy.";
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new SFXAction("ATTACK_DEFECT_BEAM"));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(p,
				new SweepingBeamEffect(AbstractDungeon.player.hb.cX,
						AbstractDungeon.player.hb.cY,
						AbstractDungeon.player.flipHorizontal),
				0.4f));
		AbstractDungeon.actionManager
				.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
						this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

		if (FormHelper.lightFormIsActive(p)) {
			for (AbstractMonster mo : AbstractDungeon
					.getCurrRoom().monsters.monsters) {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
						new VulnerablePower(mo, this.magicNumber, false), this.magicNumber,
						true, AbstractGameAction.AttackEffect.NONE));
				if (this.upgraded) {
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
							new WeakPower(mo, this.magicNumber, false), this.magicNumber,
							true, AbstractGameAction.AttackEffect.NONE));
				}
			}
		}
		FormHelper.applyLightStacks(p);
	}

	@Override
	public void doUpgrade() {
		this.upgradeDamage(UPGRADE_POWER);
		this.initializeFormDescriptions();
	}

}
