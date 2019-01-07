package spyr.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import basemod.abstracts.CustomCard;
import spyr.Spyr;

/**
 * Abstract base class for all cards. This provides little functionality except
 * removing some boilerplate and auto-calculating the image path.
 * 
 * The name and description are loaded from the card strings located in
 * src/main/resources/localization/spyr_cards.json. The image path is
 * auto-calculated as images/cards/[color]/[type]/[name].png.
 */
public abstract class SpyrCard extends CustomCard {

	public SpyrCard(String id, int cost, CardType type, CardColor color,
			CardRarity rarity, CardTarget target) {
		this(id, CardCrawlGame.languagePack.getCardStrings(id), cost, type, color,
				rarity, target);
	}

	private SpyrCard(String id, CardStrings cardStrings, int cost, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, cardStrings.NAME,
				String.format("images/cards/%s/%s/%s.png", Spyr.COLOR_MAP.get(color),
						type.toString().toLowerCase(),
						cardStrings.NAME.toLowerCase().replace(" ", "_")),
				cost, cardStrings.DESCRIPTION, type, color, rarity, target);
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.doUpgrade();
		}
	}

	/**
	 * The actual upgrade to apply to a card. Again this is to reduce boilerplate,
	 * removing the need for a if not upgraded clause in each upgrade() function.
	 */
	public abstract void doUpgrade();

}
