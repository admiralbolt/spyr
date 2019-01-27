package spyr.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpirePatch(cls = "com.megacrit.cardcrawl.rooms.AbstractRoom", method = "endTurn")
public class BurnPatch {

	/**
	 * Burn & holy fire damage should be blockable. However, powers aren't applied
	 * until after block is lost during a turn. Ideally we'd modify the
	 * preTurnLogic() to deal burn damage before block is lost. But apparently
	 * block is not lost as an action, which means it does not interact with the
	 * actionManager, which means it's impossible to time correctly unless we
	 * apply an action before the MonsterStartTurnAction() is applied. This
	 * happens in AbstractRoom endTurn()
	 *
	 * Also rloc = 1 seems to work so fuck it.
	 */

	@SpireInsertPatch(rloc = 1)
	public static void Insert(AbstractRoom room) {
		for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
			if (m.hasPower(spyr.powers.BurnPower.POWER_ID)) {
				spyr.powers.BurnPower burnPower = (spyr.powers.BurnPower) m
						.getPower(spyr.powers.BurnPower.POWER_ID);
				AbstractDungeon.actionManager
						.addToBottom(new spyr.actions.BurnDamageAction(burnPower.owner,
								burnPower.source, burnPower.amount));
			}
			if (m.hasPower(spyr.powers.HolyFirePower.POWER_ID)) {
				spyr.powers.HolyFirePower holyFirePower = (spyr.powers.HolyFirePower) m
						.getPower(spyr.powers.HolyFirePower.POWER_ID);
				AbstractDungeon.actionManager.addToBottom(
						new spyr.actions.HolyFireDamageAction(holyFirePower.owner,
								holyFirePower.source, holyFirePower.amount));
			}
		}
	}

}
