package spyr.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class MeditateAction extends AbstractGameAction {

	public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString("spyr:meditate_action");

	private int numCardsFromDeck;
	private ArrayList<AbstractCard> cards = new ArrayList<>();

	public MeditateAction(int numCardsFromDeck, int numCardsToKeep) {
		this.amount = numCardsToKeep;
		this.numCardsFromDeck = numCardsFromDeck;
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
			// If there's only one card give them the card.
			if (AbstractDungeon.player.drawPile.size() == 1) {
				AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
				AbstractDungeon.player.drawPile.removeCard(card);
				AbstractDungeon.player.hand.addToTop(card);
				AbstractDungeon.player.hand.refreshHandLayout();
				AbstractDungeon.player.hand.applyPowers();
				this.isDone = true;
				return;
			}
			for (int i = 0; i < Math.min(this.numCardsFromDeck, AbstractDungeon.player.drawPile.size()); ++i) {
				this.cards
						.add(AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
			}
			ChooseAction.openChooseScreen(this.cards, UI_STRINGS.TEXT[0], /* allowSkip= */false);
			this.tickDuration();
			return;
		}
		for (AbstractCard c : this.cards) {
			// Using the reward screen hack interacts with the codexCard. If we encounter a
			// card that's not our selected, move it to the discard.
			if (c != AbstractDungeon.cardRewardScreen.codexCard) {
				AbstractDungeon.player.drawPile.moveToDiscardPile(c);
				continue;
			}
			// Draw the card.
			if (AbstractDungeon.player.hand.size() == 10) {
				AbstractDungeon.player.drawPile.moveToDiscardPile(c);
				AbstractDungeon.player.createHandIsFullDialog();
				continue;
			}
			AbstractDungeon.player.drawPile.removeCard(c);
			AbstractDungeon.player.hand.addToTop(c);
			AbstractDungeon.player.hand.refreshHandLayout();
			AbstractDungeon.player.hand.applyPowers();
			AbstractDungeon.cardRewardScreen.codexCard = null;
		}
		this.isDone = true;
	}

}
