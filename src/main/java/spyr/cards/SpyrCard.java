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
import spyr.patches.SpyrTags;
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
	private boolean isDual;

	public SpyrCard(String id, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
		this(id, CardCrawlGame.languagePack.getCardStrings(id), cost, type, color, rarity, target, false);
	}

	public SpyrCard(String id, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target,
			boolean isDual) {
		this(id, CardCrawlGame.languagePack.getCardStrings(id), cost, type, color, rarity, target, isDual);
	}

	private SpyrCard(String id, CardStrings cardStrings, int cost, CardType type, CardColor color, CardRarity rarity,
			CardTarget target, boolean isDual) {
		super(id, cardStrings.NAME,
				String.format("spyr/images/cards/%s/%s/%s.png", Spyr.COLOR_MAP.get(color),
						type.toString().toLowerCase(), id.split(":")[1]),
				cost, cardStrings.DESCRIPTION, type, color, rarity, target);
		this.cardStrings = cardStrings;
		this.isDual = isDual;
		if (this.isDual) {
			this.tags.add(SpyrTags.IS_DUAL);
			this.initializeDualCardDescription();
		}
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.doUpgrade();
		}
	}

	@Override
	public void applyPowers() {
		super.applyPowers();
		if (this.isDual) {
			this.loadDualCardDescription();
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
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
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

	/**
	 * This is a helper function used to correctly generate dual card descriptions
	 * outside of combat. Card descriptions for dual cards are loaded in the
	 * following way from the strings in spyr_cards.json:
	 *
	 * DESCRIPTION -> Is a suffix that is always added if non-empty.
	 * EXTENDED_DESCRIPTION[0] -> Non-upgraded shadow form text.
	 * EXTENDED_DESCRIPTION[1] -> Non-upgraded light form text.
	 * EXTENDED_DESCRIPTION[2] -> Upgraded shadow form text (if non-empty).
	 * EXTENDED_DESCRIPTION[3] -> Upgraded light form text (if non-empty).
	 *
	 * New lines are added in between the shadow form text, light form text, and
	 * suffix text.
	 */
	public String getDualCardDescription(boolean hasShadow, boolean hasLight) {
		StringBuilder description = new StringBuilder();
		int shadowIndex = (this.upgraded && this.cardStrings.EXTENDED_DESCRIPTION.length > 2
				&& !this.cardStrings.EXTENDED_DESCRIPTION[2].isEmpty()) ? 2 : 0;
		int lightIndex = (this.upgraded && this.cardStrings.EXTENDED_DESCRIPTION.length > 3
				&& !this.cardStrings.EXTENDED_DESCRIPTION[3].isEmpty()) ? 3 : 1;
		if (hasShadow) {
			description.append("ShadowForm: ");
			description.append(this.cardStrings.EXTENDED_DESCRIPTION[shadowIndex]);
			description.append(" NL ");
		}
		if (hasLight) {
			description.append("LightForm: ");
			description.append(this.cardStrings.EXTENDED_DESCRIPTION[lightIndex]);
			description.append(" NL ");
		}
		if (this.cardStrings.DESCRIPTION != null && !this.cardStrings.DESCRIPTION.isEmpty()) {
			description.append(this.cardStrings.DESCRIPTION);
		}
		return description.toString();
	}

	public void initializeDualCardDescription() {
		this.rawDescription = this.getDualCardDescription(true, true);
		this.initializeDescription();
	}

	/**
	 * This is a helper function to be used to recalculate dual-card description
	 * text during combat.
	 */
	public void loadDualCardDescription() {
		this.rawDescription = this.getDualCardDescription(AbstractDungeon.player.hasPower(DarkEcoPower.POWER_ID),
				AbstractDungeon.player.hasPower(LightEcoPower.POWER_ID));
		this.initializeDescription();
	}

}
