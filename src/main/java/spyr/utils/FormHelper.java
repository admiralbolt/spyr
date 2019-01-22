package spyr.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import spyr.actions.ChooseAction;
import spyr.cards.gray.Invert;
import spyr.powers.DarkEcoPower;
import spyr.powers.FlickerPower;
import spyr.powers.LightEcoPower;

/**
 * Utils for dealing with Shadow / Light form nonsense.
 */
public class FormHelper {

	public static void swapOrChooseForm(final AbstractPlayer p) {
		// Consume flicker stacks instead of applying form.
		if (maybeReduceFlicker(p)) {
			return;
		}
		// If DualForm is active do nothing.
		if (p.hasPower(DarkEcoPower.POWER_ID) && p.hasPower(LightEcoPower.POWER_ID)) {
			return;
		}
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, DarkEcoPower.POWER_ID, 1));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightEcoPower(p, 1), 1));
			return;
		} else if (p.hasPower(LightEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, LightEcoPower.POWER_ID, 1));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DarkEcoPower(p, 1), 1));
			return;
		}
		// If the p doesn't have a form, give them choice of form.
		AbstractCard swapForm = new Invert();
		ChooseAction choice = new ChooseAction(swapForm, null, "Choose a form.");
		choice.add("LightForm", "Switch to LightForm.", () -> {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightEcoPower(p, 1), 1));
		});
		choice.add("ShadowForm", "Switch to ShadowForm.", () -> {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DarkEcoPower(p, 1), 1));
		});
		AbstractDungeon.actionManager.addToBottom(choice);
	}

	public static void maybeSwitchToLightForm(AbstractPlayer p) {
		// Consume flicker stacks instead of applying form.
		if (maybeReduceFlicker(p)) {
			return;
		}
		// Already in light form OR dual form so we're done.
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			return;
		}
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, DarkEcoPower.POWER_ID, 1));
		}
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightEcoPower(p, 1), 1));
	}

	public static void maybeSwitchToShadowForm(AbstractPlayer p) {
		// Consume flicker stacks instead of applying form.
		if (maybeReduceFlicker(p)) {
			return;
		}
		// Already in shadow form OR dual form so we're done.
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			return;
		}
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, LightEcoPower.POWER_ID, 1));
		}
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DarkEcoPower(p, 1), 1));
	}

	// Reduce flicker if the player has it instead of applying any new forms.
	public static boolean maybeReduceFlicker(AbstractPlayer p) {
		if (p.hasPower(FlickerPower.POWER_ID)) {
			AbstractPower flicker = p.getPower(FlickerPower.POWER_ID);
			flicker.reducePower(1);
			return true;
		}
		return false;
	}

}
