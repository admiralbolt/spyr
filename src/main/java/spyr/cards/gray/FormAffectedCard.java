package spyr.cards.gray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import spyr.cards.SpyrCard;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

/**
 * A base class for cards that are affected by shadow OR light form. This is
 * mostly used for dynamically generating the description text.
 */
public abstract class FormAffectedCard extends SpyrCard {

	public static final Pattern stripColorTag = Pattern.compile("](.+?)\\[");

	// Fast load form descriptions.
	private String noFormDesc;
	private String shadowFormOnlyDesc;
	private String lightFormOnlyDesc;
	private String dualFormDesc;

	/**
	 * No need for description text to be passed in, it is dynamically loaded.
	 */
	public FormAffectedCard(String id, String name, int cost, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name, "", cost, type, color, rarity, target);
		this.initializeFormDescriptions();
		this.loadCardDescription();
	}

	/**
	 * The following set of functions is used to have better control over the
	 * description of cards. Card descriptions will be loaded in the following
	 * order: getPrefix() getShadow() getLight() getSuffix()
	 *
	 * Additionally, during combat, the description text for shadow/light form
	 * will be grayed out if it is not active.
	 */
	public String getPrefix() {
		return "";
	}

	public String getShadow() {
		return "";
	}

	public String getLight() {
		return "";
	}

	public String getSuffix() {
		return "";
	}

	/**
	 * Rebuilding the same strings over and over is costly. Instead, we initialize
	 * descriptions for all possible combinations of powers for fast loading.
	 */
	public void initializeFormDescriptions() {
		StringBuilder noFormBuilder = new StringBuilder();
		StringBuilder shadowFormBuilder = new StringBuilder();
		StringBuilder lightFormBuilder = new StringBuilder();
		StringBuilder dualFormBuilder = new StringBuilder();
		// Load all parts of the description.
		String prefix = getPrefix();
		String shadow = getShadow();
		String light = getLight();
		String suffix = getSuffix();
		if (!prefix.isEmpty()) {
			for (StringBuilder builder : new StringBuilder[] { noFormBuilder,
					shadowFormBuilder, lightFormBuilder, dualFormBuilder }) {
				builder.append(prefix);
				builder.append(" NL ");
			}
		}
		// For shadow and light, if they aren't active we need to gray out the
		// description text.
		if (!shadow.isEmpty()) {
			for (StringBuilder builder : new StringBuilder[] { noFormBuilder,
					lightFormBuilder }) {
				builder.append("ShadowForm: ");
				this.addFormText(builder, shadow, /* hasShadow= */false);
			}
			for (StringBuilder builder : new StringBuilder[] { shadowFormBuilder,
					dualFormBuilder }) {
				builder.append("ShadowForm: ");
				this.addFormText(builder, shadow, /* hasShadow= */true);
			}
		}

		if (!light.isEmpty()) {
			for (StringBuilder builder : new StringBuilder[] { noFormBuilder,
					shadowFormBuilder }) {
				builder.append("LightForm: ");
				this.addFormText(builder, light, /* hasLight= */false);
			}
			for (StringBuilder builder : new StringBuilder[] { lightFormBuilder,
					dualFormBuilder }) {
				builder.append("LightForm: ");
				this.addFormText(builder, light, /* hasLight= */true);
			}
		}
		if (!suffix.isEmpty()) {
			for (StringBuilder builder : new StringBuilder[] { noFormBuilder,
					shadowFormBuilder, lightFormBuilder, dualFormBuilder }) {
				builder.append(suffix);
				builder.append(" NL ");
			}
		}
		this.noFormDesc = noFormBuilder.toString();
		this.shadowFormOnlyDesc = shadowFormBuilder.toString();
		this.lightFormOnlyDesc = lightFormBuilder.toString();
		this.dualFormDesc = dualFormBuilder.toString();
	}

	/**
	 * Loads the description for a card. This will gray out text depending on
	 * which form is active. If you are not in combat, no description text will be
	 * grayed out.
	 */
	public String getDescription() {
		boolean hasShadow, hasLight;
		hasShadow = hasLight = true;
		if (AbstractDungeon.getCurrMapNode() != null
				&& AbstractDungeon.getCurrRoom() != null && AbstractDungeon
						.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
			hasShadow = AbstractDungeon.player.hasPower(DarkEcoPower.POWER_ID);
			hasLight = AbstractDungeon.player.hasPower(LightEcoPower.POWER_ID);
		}
		if (hasShadow && hasLight) {
			return this.dualFormDesc;
		}
		if (hasShadow) {
			return this.shadowFormOnlyDesc;
		}
		if (hasLight) {
			return this.lightFormOnlyDesc;
		}
		return this.noFormDesc;
	}

	@Override
	public void applyPowers() {
		super.applyPowers();
		this.loadCardDescription();
	}

	public void loadCardDescription() {
		this.rawDescription = getDescription();
		this.initializeDescription();
	}

	/**
	 * We override the initializeCardDescription to properly load keywords. Again,
	 * STS does parsing based on words split by whitespace, which means when we
	 * surround a word with color brackets it's not interpreted correctly.
	 */
	@Override
	public void initializeDescription() {
		super.initializeDescription();
		this.keywords.clear();
		for (String word : this.rawDescription.toLowerCase().split("\\s+")) {
			String keyword = parseBracket(word);
			keyword = dedupeKeyword(stripPunctuation(keyword));
			if (GameDictionary.keywords.containsKey(keyword)
					&& !this.keywords.contains(keyword)) {
				this.keywords.add(keyword);
			}
		}
	}

	public static String parseBracket(String word) {
		if (!word.startsWith("[") || word.equals("[e]")) {
			return word;
		}
		// Regex to extract everything between ']' and '['.
		final Matcher matcher = stripColorTag.matcher(word);
		matcher.find();
		return matcher.group(1);
	}

	/**
	 * Copied from AbstractCard.
	 */
	public static String dedupeKeyword(String keyword) {
		String retVal = GameDictionary.parentWord.get(keyword);
		return (retVal == null) ? keyword : retVal;
	}

	public static String stripPunctuation(String keyword) {
		if (keyword.length() > 0
				&& !Character.isLetterOrDigit(keyword.charAt(keyword.length() - 1))) {
			return keyword.substring(0, keyword.length() - 1);
		}
		return keyword;
	}

	/**
	 * Helper function to add form text to a description. If you aren't currently
	 * in a form, text will still be displayed, but will be grayed out.
	 */
	private void addFormText(StringBuilder description, String textToAdd,
			boolean hasForm) {
		if (hasForm) {
			description.append(textToAdd);
			description.append(" NL ");
			return;
		}
		// Unfortunately, we have to surround EACH word with the color brackets.
		// This
		// doubly complicates things because of dynamic variables (!D!, !M!, !B!).
		// If we gray out the text it'll look like this: '[#999999]!D![]'. And it
		// gets rendered as a grayed out !D!, without substitution.
		//
		// The check for substitution happens in the
		// AbstractCard.renderDescription()
		// method, but the tag color parsing happens at a lower level in the
		// gdx BitMap font rendering, see here:
		// https://github.com/libgdx/libgdx/wiki/Color-Markup-Language
		//
		// It seems like we should be able to surround an entire block of text with
		// color tags as long as we omit the closing '[]' to keep the font the same
		// color. So we need to mimic the dynamic color setting / calculation by
		// hand.
		for (String word : textToAdd.split("\\s+")) {
			if (word.startsWith("!")) {
				word = getDynamicVariableText(word);
			}
			description.append(String.format("[#999999]%s[] ", word));
		}
		description.append(" NL ");
	}

	private String getDynamicVariableText(String word) {
		if (word.equals("!D!")) {
			return Integer.toString(this.damage);
		} else if (word.equals("!M!")) {
			return Integer.toString(this.magicNumber);
		} else if (word.equals("!B!")) {
			return Integer.toString(this.block);
		}
		return word;
	}

}
