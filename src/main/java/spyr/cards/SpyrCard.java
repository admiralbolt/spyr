package spyr.cards;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;
import spyr.Spyr;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

/**
 * Abstract base class for all cards. This provides little functionality except
 * removing some boilerplate and auto-calculating the image path.
 * 
 * The name and description are loaded from the card strings located in
 * src/main/resources/localization/spyr_cards.json. The image path is
 * auto-calculated as spyr/images/cards/[color]/[type]/[name].png.
 */
public abstract class SpyrCard extends CustomCard {
	
	public CardStrings cardStrings;

	public SpyrCard(String id, int cost, CardType type, CardColor color,
			CardRarity rarity, CardTarget target) {
		this(id, CardCrawlGame.languagePack.getCardStrings(id), cost, type, color,
				rarity, target);
	}

	private SpyrCard(String id, CardStrings cardStrings, int cost, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, cardStrings.NAME,
				String.format("spyr/images/cards/%s/%s/%s.png",
						Spyr.COLOR_MAP.get(color), type.toString().toLowerCase(),
						id.split(":")[1]),
				cost, cardStrings.DESCRIPTION, type, color, rarity, target);
		this.cardStrings = cardStrings;
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
	
	/**
	 * Helper function that applies powers to block values.
	 */
	public static int applyBlock(float baseBlock) {
		float tmp = baseBlock;
    for (AbstractPower p : AbstractDungeon.player.powers) {
        tmp = p.modifyBlock(tmp);
    }
    if (tmp < 0.0f) {
        tmp = 0.0f;
    }
		return MathUtils.floor(tmp);
	}
	
	public void loadDualCardDescription() {
		StringBuilder description = new StringBuilder();
		if (AbstractDungeon.player.hasPower(DarkEcoPower.POWER_ID)) {
			description.append(this.cardStrings.EXTENDED_DESCRIPTION[0]);
		}
		if (AbstractDungeon.player.hasPower(LightEcoPower.POWER_ID)) {
			description.append(this.cardStrings.EXTENDED_DESCRIPTION[1]);
		}
		this.rawDescription = description.toString();
		this.initializeDescription();
	}

}
