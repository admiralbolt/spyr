package spyr.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import spyr.powers.BurnPower;
import spyr.powers.NeverEndingBlazePower;

/**
 * Damage action for burn. Effectively just duplicates DamageAction since burn
 * can be blocked.
 */
public class BurnDamageAction extends AbstractGameAction {
	private static final float DURATION = 0.33f;

	public BurnDamageAction(AbstractCreature target, AbstractCreature source, int amount) {
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
			AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect));
		}
		this.tickDuration();
		if (!this.isDone || !this.target.hasPower(BurnPower.POWER_ID) || target.currentHealth <= 0) {
			return;
		}
		this.target.tint.color = Color.RED.cpy();
		this.target.tint.changeColor(Color.WHITE.cpy());
		this.target.damage(new DamageInfo(this.source, this.amount, DamageInfo.DamageType.NORMAL));
		// Halve the burn, unless NeverEndingBlaze is active.
		if (this.source.hasPower(NeverEndingBlazePower.POWER_ID)) {
			return;
		}
		int reductionAmount = BurnPower.getReductionAmount(this.amount);
		AbstractPower burnPower = this.target.getPower(BurnPower.POWER_ID);
		burnPower.amount -= reductionAmount;
		if (burnPower.amount <= 0) {
			this.target.powers.remove(burnPower);
		}
		burnPower.updateDescription();
	}
}
