package spyr.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ExhaustCardsFromDeckAction extends AbstractGameAction {

	public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack
			.getUIString("spyr:exhaust_cards_from_deck_action");

	public ExhaustCardsFromDeckAction(int numCardsToExhaust) {
		this.amount = numCardsToExhaust;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
	}

	@Override
	public void update() {
		if (this.duration == Settings.ACTION_DUR_FAST) {
			// If there aren't any cards too bad!
			if (AbstractDungeon.player.drawPile.isEmpty()) {
				this.isDone = true;
				return;
			}
			// If there are less cards left in the deck than the number of cards to
			// exhaust, exhaust the rest of the deck.
			if (AbstractDungeon.player.drawPile.size() <= this.amount) {
				for (int i = 0; i < AbstractDungeon.player.drawPile.size(); i++) {
					AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
					AbstractDungeon.player.drawPile.moveToExhaustPile(card);
				}
				this.isDone = true;
				return;
			}
			CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
				tmp.addToRandomSpot(c);
			}
			AbstractDungeon.gridSelectScreen.open(tmp, this.amount, UI_STRINGS.TEXT[0], /* forUpgrade= */false);
			this.tickDuration();
			return;
		}
		if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
				AbstractDungeon.player.drawPile.moveToExhaustPile(c);
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
		}
		this.isDone = true;
	}

}
