package spyr.powers;

import java.lang.reflect.Field;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;

public class SleepPower extends SpyrPower {

	public static final String POWER_ID = "spyr:sleep";

	private byte moveByte;
	private AbstractMonster.Intent moveIntent;
	private EnemyMoveInfo move;

	public SleepPower(AbstractCreature owner, int amount) {
		super(POWER_ID, owner, amount);
	}

	@Override
	public void updateDescription() {
		this.description = this.powerStrings.DESCRIPTIONS[0];
	}

	@Override
	public void atEndOfRound() {
		if (this.amount <= 0) {
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
		} else {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
		}
	}

	@Override
	public void onInitialApplication() {
		AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
			@Override
			public void update() {
				if (SleepPower.this.owner instanceof AbstractMonster) {
					AbstractMonster monster = (AbstractMonster) SleepPower.this.owner;
					SleepPower.this.moveByte = monster.nextMove;
					SleepPower.this.moveIntent = monster.intent;
					try {
						Field move_field = AbstractMonster.class.getDeclaredField("move");
						move_field.setAccessible(true);
						move = (EnemyMoveInfo) move_field.get(SleepPower.this.owner);
						move.intent = AbstractMonster.Intent.SLEEP;
						((AbstractMonster) owner).createIntent();
					} catch (IllegalAccessException | NoSuchFieldException e) {
						e.printStackTrace();
					}
				}
				this.isDone = true;
			}
		});
	}

	@Override
	public void onRemove() {
		if (this.owner instanceof AbstractMonster) {
			AbstractMonster monster = (AbstractMonster) owner;
			if (this.move != null) {
				monster.setMove(this.moveByte, this.moveIntent, this.move.baseDamage, this.move.multiplier,
						this.move.isMultiDamage);
			} else {
				monster.setMove(this.moveByte, this.moveIntent);
			}
			monster.createIntent();
			monster.applyPowers();
		}
	}

}
