package spyr.cards;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomCard;
import spyr.Spyr;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

/**
 * Abstract base class for all cards. This provides little functionality except
 * removing some boilerplate and auto-calculating the image path.
 *
 * The image path is auto-calculated as spyr/images/cards/[color]/[type]/[name].png.
 */
public abstract class SpyrCard extends CustomCard {

	public SpyrCard(String id, String name, String description, int cost, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name,
				String.format("spyr/images/cards/%s/%s/%s.png",
						Spyr.COLOR_MAP.get(color), type.toString().toLowerCase(),
						id.split(":")[1]),
				cost, description, type, color, rarity, target);
	}

	/**
	 * The actual upgrade to apply to a card. Again this is to reduce boilerplate,
	 * removing the need for a if not upgraded clause in each upgrade() function.
	 */
	public abstract void doUpgrade();

  @Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.doUpgrade();
		}
	}

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
