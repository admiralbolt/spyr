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
import spyr.powers.DualFormPower;
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
		applyPamphlet(p);
		// Handle dual form specially.
		if (p.hasPower(DarkEcoPower.POWER_ID) && p.hasPower(LightEcoPower.POWER_ID)) {
			int lightAmount = p.getPower(LightEcoPower.POWER_ID).amount;
			int darkAmount = p.getPower(DarkEcoPower.POWER_ID).amount;
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
					new LightEcoPower(p, darkAmount - lightAmount), darkAmount - lightAmount));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
					new DarkEcoPower(p, lightAmount - darkAmount), lightAmount - darkAmount));
			return;
		}
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			int darkAmount = p.getPower(DarkEcoPower.POWER_ID).amount;
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, DarkEcoPower.POWER_ID, darkAmount));
			AbstractDungeon.actionManager
					.addToBottom(new ApplyPowerAction(p, p, new LightEcoPower(p, darkAmount), darkAmount));
		} else if (p.hasPower(LightEcoPower.POWER_ID)) {
			int lightAmount = p.getPower(LightEcoPower.POWER_ID).amount;
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, LightEcoPower.POWER_ID, lightAmount));
			AbstractDungeon.actionManager
					.addToBottom(new ApplyPowerAction(p, p, new DarkEcoPower(p, lightAmount), lightAmount));
		}
	}

	public static void applyLightStacks(AbstractPlayer p) {
		applyLightStacks(p, 1);
	}

	public static void applyLightStacks(AbstractPlayer p, int amount) {
		// Consume flicker stacks instead of applying form.
		if (maybeReduceFlicker(p)) {
			return;
		}
		// If we already have light form stacks, increment them.
		if (p.hasPower(LightEcoPower.POWER_ID) || p.hasPower(DualFormPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightEcoPower(p, amount), amount));
			return;
		}
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			int darkAmount = p.getPower(DarkEcoPower.POWER_ID).amount;
			if (darkAmount >= amount) {
				AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, DarkEcoPower.POWER_ID, amount));
			} else {
				AbstractDungeon.actionManager
						.addToBottom(new ReducePowerAction(p, p, DarkEcoPower.POWER_ID, darkAmount));
				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(p, p, new LightEcoPower(p, amount - darkAmount), amount - darkAmount));
			}
		}
	}

	public static void applyShadowStacks(AbstractPlayer p) {
		applyShadowStacks(p, 1);
	}

	public static void applyShadowStacks(AbstractPlayer p, int amount) {
		// Consume flicker stacks instead of applying form.
		if (maybeReduceFlicker(p)) {
			return;
		}
		// If we already have shado form stacks, increment them.
		if (p.hasPower(DarkEcoPower.POWER_ID) || p.hasPower(DualFormPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DarkEcoPower(p, 1), 1));
			return;
		}
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			int lightAmount = p.getPower(LightEcoPower.POWER_ID).amount;
			if (lightAmount >= amount) {
				AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, LightEcoPower.POWER_ID, amount));
			} else {
				AbstractDungeon.actionManager
						.addToBottom(new ReducePowerAction(p, p, LightEcoPower.POWER_ID, lightAmount));
				AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(p, p, new DarkEcoPower(p, amount - lightAmount), amount - lightAmount));
			}
		}
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
