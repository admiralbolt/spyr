package spyr.cards;

import java.lang.reflect.InvocationTargetException;

import com.megacrit.cardcrawl.cards.AbstractCard;
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
 * auto-calculated as spyr/images/cards/[color]/[type]/[name].png.
 */
public abstract class SpyrCard extends CustomCard {

	public SpyrCard(String id, int cost, CardType type, CardColor color,
			CardRarity rarity, CardTarget target) {
		this(id, CardCrawlGame.languagePack.getCardStrings(id), cost, type, color,
				rarity, target);
		System.out.println("Instantiating id: [" + id + "], cardId: [" + this.cardID + "]");
		System.out.println(CardCrawlGame.languagePack.getCardStrings(this.cardID).NAME);
	}

	private SpyrCard(String id, CardStrings cardStrings, int cost, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, cardStrings.NAME,
				String.format("spyr/images/cards/%s/%s/%s.png", Spyr.COLOR_MAP.get(color),
						type.toString().toLowerCase(), id.split(":")[1]),
				cost, cardStrings.DESCRIPTION, type, color, rarity, target);
		System.out.println("Instantiating id: [" + id + "], cardId: [" + this.cardID + "]");
		System.out.println(cardStrings.NAME);
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
	
	/**
	 * Dynamically constructs an instance of the current card.
	 */
	@Override
	public AbstractCard makeCopy() {
		try {
			return this.getClass().getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

}
