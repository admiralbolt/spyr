package spyr.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/**
 * Shows a screen allowing the player to choose from different actions.
 *
 * Mostly stolen from the MadScientist mod
 * (https://github.com/twanvl/sts-mad-science-mod), so check them out.
 */
public class ChooseAction extends AbstractGameAction {

	AbstractCard baseCard;
	AbstractMonster target;
	CardGroup choices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
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
    choices.addToTop(choice);
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
			AbstractDungeon.gridSelectScreen.open(this.choices, 1, message, /* forUpgrade= */false,
					/* forTransform= */false, /* canCancel= */false, /* forPurge= */false);
      this.tickDuration();
			return;
		}
		if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
			AbstractCard pick = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			int i = choices.group.indexOf(pick);
			actions.get(i).run();
      this.isDone = true;
		}
		this.tickDuration();
	}

}
