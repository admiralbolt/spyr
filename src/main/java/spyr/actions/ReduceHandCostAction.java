package spyr.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ReduceHandCostAction extends AbstractGameAction {

	public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString("spyr:reduce_hand_cost_action");

	private boolean allCards;
	private AbstractPlayer p;
	private ArrayList<AbstractCard> cannotReduce = new ArrayList<AbstractCard>();

	public ReduceHandCostAction(boolean allCards) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.p = AbstractDungeon.player;
		this.duration = Settings.ACTION_DUR_FAST;
		this.allCards = allCards;
	}

	@Override
	public void update() {
		if (this.duration == Settings.ACTION_DUR_FAST) {
			if (this.allCards) {
				// Checking for negative values happens automatically inside
				// modifyCostForCombat().
				for (AbstractCard c : this.p.hand.group) {
					c.modifyCostForCombat(-1);
					c.superFlash();
				}
				this.isDone = true;
				return;
			}
			// Only reduce the cost of 1 card, maintain a list of all cards that
			// can't be reduced.
			for (AbstractCard c : this.p.hand.group) {
				if (c.cost > 0 || c.costForTurn > 0) {
					continue;
				}
				this.cannotReduce.add(c);
			}
			// If we can't reduce anything we're done.
			if (this.cannotReduce.size() == this.p.hand.group.size()) {
				this.isDone = true;
				return;
			}
			// If we can only reduce 1 thing, just reduce it.
			if (this.p.hand.group.size() - this.cannotReduce.size() == 1) {
				for (AbstractCard c : this.p.hand.group) {
					if (c.cost == 0 && c.costForTurn == 0) {
						continue;
					}
					c.modifyCostForCombat(-1);
					c.superFlash();
					this.isDone = true;
					return;
				}
			}
			// Remove all un-reduceable cards from hand, we'll add them back later.
			this.p.hand.group.removeAll(this.cannotReduce);
			AbstractDungeon.handCardSelectScreen.open(UI_STRINGS.TEXT[0], /* amount= */1, /* anyNumber= */false,
					/* canPickZero= */false, /* forTransform= */false, /* forUpgrade= */false, /* upTo= */true);
			this.tickDuration();
			return;
		}
		if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
			for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
				c.modifyCostForCombat(-1);
				c.superFlash();
				this.p.hand.addToTop(c);
				this.isDone = true;
			}
			this.returnCards();
			AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
			AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
			this.isDone = true;
		}
		this.tickDuration();
	}

	private void returnCards() {
		for (AbstractCard c : this.cannotReduce) {
			this.p.hand.addToTop(c);
		}
		this.p.hand.refreshHandLayout();
	}

}
