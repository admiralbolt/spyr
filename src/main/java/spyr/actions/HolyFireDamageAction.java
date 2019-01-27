package spyr.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import spyr.powers.HolyFirePower;

/**
 * This is basically a direct copy of the PoisonLoseHPAction from the core game.
 * Not entirely sure why a separate action is needed for this, but in case we
 * decide we want interactions it's probably easier to have it separate.
 */
public class HolyFireDamageAction extends AbstractGameAction {

	private static final float DURATION = 0.33f;

	public HolyFireDamageAction(AbstractCreature target, AbstractCreature source,
			int amount) {
		this.setValues(target, source, amount);
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.attackEffect = AbstractGameAction.AttackEffect.FIRE;
		this.duration = DURATION;
	}

	@Override
	public void update() {
		if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
			isDone = true;
			return;
		}
		if (duration == DURATION && target.currentHealth > 0) {
			target.damageFlash = true;
			target.damageFlashFrames = 4;
			AbstractDungeon.effectList
					.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect));
		}
		this.tickDuration();
		if (this.isDone && this.target.hasPower(HolyFirePower.POWER_ID)) {
			if (target.currentHealth > 0) {
				this.target.tint.color = Color.CORAL.cpy();
				this.target.tint.changeColor(Color.WHITE.cpy());
				// Damage type is set to "THORNS" to avoid triggering things like the
				// Louse curl up.
				this.target.damage(new DamageInfo(this.source, this.amount,
						DamageInfo.DamageType.THORNS));
				// Double the fire power.
				AbstractPower firePower = this.target.getPower(HolyFirePower.POWER_ID);
				firePower.amount *= 2;
				firePower.updateDescription();
			}
		}
	}
}
