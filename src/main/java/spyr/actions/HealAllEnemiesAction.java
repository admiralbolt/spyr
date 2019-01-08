package spyr.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HealAllEnemiesAction extends AbstractGameAction {

	public HealAllEnemiesAction(AbstractCreature source, int[] amount) {
		this.setValues(target, source, amount[0]);
		this.actionType = AbstractGameAction.ActionType.HEAL;
	}

	@Override
	public void update() {
		if (this.duration == 0.5f) {
			for (AbstractMonster monster : AbstractDungeon
					.getCurrRoom().monsters.monsters) {
				monster.heal(this.amount);
			}
		}
		this.tickDuration();
	}
}