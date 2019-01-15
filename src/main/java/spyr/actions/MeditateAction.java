package spyr.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class MeditateAction extends AbstractGameAction {

	public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString("spyr:meditate_action");

	private int numCardsFromDeck;
	private CardGroup tmpGroup;

	public MeditateAction(int numCardsFromDeck, int numCardsToKeep) {
		this.amount = numCardsToKeep;
		this.numCardsFromDeck = numCardsFromDeck;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
	}

	@Override
	public void update() {
		if (this.duration == Settings.ACTION_DUR_FAST) {
			if (AbstractDungeon.player.drawPile.isEmpty()) {
				this.isDone = true;
				return;
			}
			for (int i = 0; i < Math.min(this.numCardsFromDeck, AbstractDungeon.player.drawPile.size()); ++i) {
				this.tmpGroup.addToTop(
						AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
			}
			AbstractDungeon.gridSelectScreen.open(this.tmpGroup, this.amount, UI_STRINGS.TEXT[0],
					/* forUpgrade= */false);
			this.tickDuration();
			return;
		}
		for (AbstractCard c : this.tmpGroup.group) {
			if (!AbstractDungeon.gridSelectScreen.selectedCards.contains(c)) {
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
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
		}
		this.isDone = true;
	}

}
