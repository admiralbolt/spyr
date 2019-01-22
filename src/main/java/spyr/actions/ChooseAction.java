package spyr.actions;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.SingingBowlButton;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;

import basemod.ReflectionHacks;

/**
 * Shows a screen allowing the player to choose from different actions.
 *
 * Mostly stolen from the MadScientist mod
 * (https://github.com/twanvl/sts-mad-science-mod), so check them out.
 */
public class ChooseAction extends AbstractGameAction {

	AbstractCard baseCard;
	AbstractMonster target;
	ArrayList<AbstractCard> choices = new ArrayList<>();
	ArrayList<Runnable> actions = new ArrayList<>();
	String message;

	public ChooseAction(AbstractCard baseCard, AbstractMonster target, String message) {
		this.target = target;
		this.source = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.baseCard = baseCard;
		this.message = message;
		this.duration = Settings.ACTION_DUR_FASTER;
	}

	// Adds a new option for selection. Card art is used from the base card.
	public void add(String name, String description, Runnable action) {
		AbstractCard choice = baseCard.makeStatEquivalentCopy();
		choice.name = name;
		choice.rawDescription = description;
		choice.initializeDescription();
		choice.applyPowers();
		choices.add(choice);
		actions.add(action);
	}

	@Override
	public void update() {
		if (choices.isEmpty()) {
			this.isDone = true;
			return;
		}
		if (this.duration == Settings.ACTION_DUR_FASTER) {
			if (choices.size() == 1) {
				actions.get(0).run();
				this.isDone = true;
				return;
			}
			// Use card reward screen to make it look pretty.
			openChooseScreen(this.choices, this.message, /* allowSkip= */false);
			this.tickDuration();
			return;
		}
		// We immitate Nilry's Codex for selecting options to make it pretty and
		// centered. So we interact with the "codexCard" as the selected option.
		if (AbstractDungeon.cardRewardScreen.codexCard != null) {
			int i = choices.indexOf(AbstractDungeon.cardRewardScreen.codexCard);
			actions.get(i).run();
			AbstractDungeon.cardRewardScreen.codexCard = null;
			this.isDone = true;
		}
		this.tickDuration();
	}

	/**
	 * This is basically a direct copy of the codexOpen() method from
	 * CardRewardScreen.java.
	 */
	public static void openChooseScreen(ArrayList<AbstractCard> cards, String banner, boolean allowSkip) {
		CardRewardScreen crs = AbstractDungeon.cardRewardScreen;
		crs.rItem = null;
		// This is pretty silly, but since we set codex = true, clicking on a card
		// assigns it into the AbstractDungeon.cardRewardsScreen.codexCard variable.
		ReflectionHacks.setPrivate(crs, CardRewardScreen.class, "codex", true);
		ReflectionHacks.setPrivate(crs, CardRewardScreen.class, "draft", false);
		crs.codexCard = null;
		((SingingBowlButton) ReflectionHacks.getPrivate(crs, CardRewardScreen.class, "bowlButton")).hide();
		if (allowSkip) {
			((SkipCardButton) ReflectionHacks.getPrivate(crs, CardRewardScreen.class, "skipButton")).show();
		} else {
			((SkipCardButton) ReflectionHacks.getPrivate(crs, CardRewardScreen.class, "skipButton")).hide();
		}
		crs.onCardSelect = true;
		AbstractDungeon.topPanel.unhoverHitboxes();
		crs.rewardGroup = cards;
		AbstractDungeon.isScreenUp = true;
		AbstractDungeon.screen = AbstractDungeon.CurrentScreen.CARD_REWARD;
		AbstractDungeon.dynamicBanner.appear(banner);
		AbstractDungeon.overlayMenu.showBlackScreen();
		final float CARD_TARGET_Y = (float) Settings.HEIGHT * 0.45f;
		try {
			Method method = CardRewardScreen.class.getDeclaredMethod("placeCards", float.class, float.class);
			method.setAccessible(true);
			method.invoke(crs, (float) Settings.WIDTH / 2.0f, CARD_TARGET_Y);
		} catch (Exception ex) {
			System.out.println("Fuck");
		}
	}

}
