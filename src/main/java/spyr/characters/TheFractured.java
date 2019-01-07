package spyr.characters;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import spyr.patches.CharacterEnum;
import spyr.relics.Eco;
import spyr.cards.gray.StrikeFractured;
import spyr.patches.CardEnum;

public class TheFractured extends CustomPlayer {

	// Resource strings are loaded from
	// src/main/resources/localization/spyr_characters.json
	private static final CharacterStrings FRACTURED = CardCrawlGame.languagePack
			.getCharacterString("spyr:the_fractured");

	public static final int ENERGY_PER_TURN = 3;
	public static final String SHOULDER = "spyr/images/characters/the_fractured/shoulder.png";
	public static final String SHOULDER2 = "spyr/images/characters/the_fractured/shoulder2.png";
	public static final String CORPSE = "spyr/images/characters/the_fractured/corpse.png";

	public static final String BUTTON = "spyr/images/characters/the_fractured/ui/button.png";
	public static final String POTRAIT = "spyr/images/characters/the_fractured/ui/portrait.png";
	public static final Color COLOR = CardHelper.getColor(90.0f, 90.0f, 100.0f);

	public static final String SKELETON_ATLAS = "spyr/images/characters/the_fractured/idle/skeleton.atlas";
	public static final String SKELETON_JSON = "spyr/images/characters/the_fractured/idle/skeleton.json";

	public static final String[] ORB_TEXTURES = {
			"spyr/images/characters/the_fractured/orb/layer1.png",
			"spyr/images/characters/the_fractured/orb/layer2.png",
			"spyr/images/characters/the_fractured/orb/layer3.png",
			"spyr/images/characters/the_fractured/orb/layer4.png",
			"spyr/images/characters/the_fractured/orb/layer5.png",
			"spyr/images/characters/the_fractured/orb/layer6.png",
			"spyr/images/characters/the_fractured/orb/layer1d.png",
			"spyr/images/characters/the_fractured/orb/layer2d.png",
			"spyr/images/characters/the_fractured/orb/layer3d.png",
			"spyr/images/characters/the_fractured/orb/layer4d.png",
			"spyr/images/characters/the_fractured/orb/layer5d.png" };

	public static final String ORB_VFX = "spyr/images/characters/the_fractured/orb/vfx.png";

	public static final String IDLE_ANIMATION = "spyr/images/characters/the_fractured/spriter/the_fractured.scml";

	public TheFractured(String name) {
		super(name, CharacterEnum.FRACTURED, ORB_TEXTURES, ORB_VFX, null,
				new SpriterAnimation(IDLE_ANIMATION));

		initializeClass(null, SHOULDER2, SHOULDER, CORPSE, getLoadout(), 0.0f, -20f,
				240.0f, 240.0f, new EnergyManager(ENERGY_PER_TURN));

		this.loadAnimation(SKELETON_ATLAS, SKELETON_JSON, 1.0f);
	}

	@Override
	public CharSelectInfo getLoadout() {
		return new CharSelectInfo(FRACTURED.NAMES[0], FRACTURED.TEXT[0], 67, 67, 0,
				99, 5, this, getStartingRelics(), getStartingDeck(), false);
	}

	@Override
	public ArrayList<String> getStartingDeck() {
		ArrayList<String> startingDeck = new ArrayList<String>();
		startingDeck.add(StrikeFractured.ID);
		startingDeck.add(StrikeFractured.ID);
		return startingDeck;
	}

	@Override
	public ArrayList<String> getStartingRelics() {
		ArrayList<String> relics = new ArrayList<String>();
		relics.add(Eco.ID);
		return relics;
	}

	@Override
	public void doCharSelectScreenSelectEffect() {
		CardCrawlGame.sound.playA("BYRD_DEATH", MathUtils.random(-0.2f, 0.2f));
	}

	@Override
	public int getAscensionMaxHPLoss() {
		return 4;
	}

	@Override
	public CardColor getCardColor() {
		return CardEnum.FRACTURED;
	}

	@Override
	public Color getCardRenderColor() {
		return Color.LIGHT_GRAY;
	}

	@Override
	public Color getCardTrailColor() {
		return Color.LIGHT_GRAY;
	}

	@Override
	public String getCustomModeCharacterButtonSoundKey() {
		return "BYRD_DEATH";
	}

	@Override
	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontGreen;
	}

	@Override
	public String getLocalizedCharacterName() {
		return "The Fractured";
	}

	@Override
	public Color getSlashAttackColor() {
		return Color.LIGHT_GRAY;
	}

	@Override
	public AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[] {
				AbstractGameAction.AttackEffect.POISON,
				AbstractGameAction.AttackEffect.SMASH,
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
				AbstractGameAction.AttackEffect.POISON,
				AbstractGameAction.AttackEffect.FIRE,
				AbstractGameAction.AttackEffect.SLASH_VERTICAL };

	}

	@Override
	public String getSpireHeartText() {
		return "Ayyyyy";
	}

	@Override
	public AbstractCard getStartCardForEvent() {
		return new StrikeFractured();
	}

	@Override
	public String getTitle(PlayerClass arg0) {
		return "Fractured Title";
	}

	@Override
	public String getVampireText() {
		return "Vampires and stuff.";
	}

	@Override
	public AbstractPlayer newInstance() {
		return new TheFractured(this.name);
	}

}
