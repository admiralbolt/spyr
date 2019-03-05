package spyr.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import spyr.powers.TemporaryDarkEcoPower;
import spyr.powers.TemporaryLightEcoPower;
import spyr.relics.Pamphlet;

/**
 * Utils for dealing with Shadow / Light form nonsense.
 */
public class FormHelper {

	public static void chooseForm(final AbstractPlayer p) {
		AbstractCard swapForm = new Invert();
		ChooseAction choice = new ChooseAction(swapForm, null, "Choose a form.");
		choice.add("LightForm", "Gain light energy.", () -> {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightEcoPower(p, 1), 1));
		});
		choice.add("ShadowForm", "Gain shadow energy.", () -> {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DarkEcoPower(p, 1), 1));
		});
		AbstractDungeon.actionManager.addToBottom(choice);
	}

	/**
	 * Swap shadow and light stacks.
	 */
	public static void invertStacks(AbstractPlayer p) {
		int powerAmount;
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			powerAmount = p.getPower(DarkEcoPower.POWER_ID).amount;
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, DarkEcoPower.POWER_ID, powerAmount));
			AbstractDungeon.actionManager
					.addToBottom(new ApplyPowerAction(p, p, new LightEcoPower(p, powerAmount), powerAmount));
		} else if (p.hasPower(LightEcoPower.POWER_ID)) {
			powerAmount = p.getPower(LightEcoPower.POWER_ID).amount;
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, LightEcoPower.POWER_ID, powerAmount));
			AbstractDungeon.actionManager
					.addToBottom(new ApplyPowerAction(p, p, new DarkEcoPower(p, powerAmount), powerAmount));
		}
	}

	public static void applyLightForm(AbstractPlayer p) {
		// Consume flicker stacks instead of applying form.
		if (maybeReduceFlicker(p)) {
			return;
		}
		// If we already have light form stacks, increment them.
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightEcoPower(p, 1), 1));
			return;
		}
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, DarkEcoPower.POWER_ID, 1));
			return;
		}
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightEcoPower(p, 1), 1));
		applyPamphlet(p);
	}

	public static void applyShadowForm(AbstractPlayer p) {
		// Consume flicker stacks instead of applying form.
		if (maybeReduceFlicker(p)) {
			return;
		}
		// If we already have shado form stacks, increment them.
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DarkEcoPower(p, 1), 1));
			return;
		}
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, LightEcoPower.POWER_ID, 1));
			return;
		}
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DarkEcoPower(p, 1), 1));
		applyPamphlet(p);
	}

	public static void applyPamphlet(AbstractPlayer p) {
		if (p.hasRelic(Pamphlet.ID)) {
			p.getRelic(Pamphlet.ID).flash();
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, Pamphlet.BLOCK));
		}
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

	public static boolean shadowFormIsActive(AbstractPlayer p) {
		return p.hasPower(DarkEcoPower.POWER_ID) || p.hasPower(TemporaryDarkEcoPower.POWER_ID);
	}

	public static boolean lightFormIsActive(AbstractPlayer p) {
		return p.hasPower(LightEcoPower.POWER_ID) || p.hasPower(TemporaryLightEcoPower.POWER_ID);
	}

}
