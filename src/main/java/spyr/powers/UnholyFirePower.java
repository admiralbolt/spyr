package spyr.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import spyr.actions.UnholyFireLoseHPAction;

public class UnholyFirePower extends SpyrPower {

	public static final String POWER_ID = "spyr:unholy_fire";

	private AbstractCreature source;

	public UnholyFirePower(AbstractCreature owner, AbstractCreature source, int strAmt) {
		super(POWER_ID, owner, strAmt);
		this.source = source;

	}

	@Override
	public void atStartOfTurn() {
		if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
				&& !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
			flashWithoutSound();
			AbstractDungeon.actionManager.addToBottom(
					new UnholyFireLoseHPAction(owner, this.source, amount, AbstractGameAction.AttackEffect.POISON));
		}
	}

	@Override
	public void updateDescription() {
		this.description = this.powerStrings.DESCRIPTIONS[0] + this.amount + this.powerStrings.DESCRIPTIONS[1]
				+ this.amount + this.powerStrings.DESCRIPTIONS[2];
	}

}
