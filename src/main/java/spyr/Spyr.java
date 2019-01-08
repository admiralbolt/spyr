package spyr;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import spyr.cards.gray.*;
import spyr.characters.TheFractured;
import spyr.patches.CardEnum;
import spyr.patches.CharacterEnum;
import spyr.relics.SpyrRelics;

@SpireInitializer
public class Spyr implements EditCardsSubscriber, EditCharactersSubscriber,
		EditRelicsSubscriber, EditStringsSubscriber {

	public static final String ATK = "spyr/images/cardui/512/bg_attack_fractured.png";

	@SuppressWarnings("serial")
	public static HashMap<CardColor, String> COLOR_MAP = new HashMap<CardColor, String>() {
		{
			put(CardColor.RED, "red");
			put(CardColor.BLUE, "blue");
			put(CardColor.GREEN, "green");
			put(CardEnum.FRACTURED_GRAY, "gray");
		}
	};

	public Spyr() {
		BaseMod.subscribe(this);

		BaseMod.addColor(CardEnum.FRACTURED_GRAY, Color.LIGHT_GRAY,
				get512("bg_attack_fractured.png"), get512("bg_skill_fractured.png"),
				get512("bg_power_fractured.png"), get512("card_fractured_orb.png"),
				get1024("bg_attack_fractured.png"), get1024("bg_skill_fractured.png"),
				get1024("bg_power_fractured.png"), get1024("card_fractured_orb.png"),
				get512("card_fractured_orb.png"));
	}

	public static String get512(String cardName) {
		return "spyr/images/cardui/512/" + cardName;
	}

	public static String get1024(String cardName) {
		return "spyr/images/cardui/1024/" + cardName;
	}

	// Used by SpireInitializer(), not really clear how though.
	public static void initialize() {
		new Spyr();
	}

	@Override
	public void receiveEditCards() {
		// Ironclad
		// ========

		// BaseMod.addCard(new Masochism());
		// BaseMod.addCard(new Revenge());
		// BaseMod.addCard(new StaunchBleeding());
		// Silent
		// ======

		// Defect
		// ======
		// BaseMod.addCard(new BeepBoop());

		// Fractured
		// =========
		BaseMod.addCard(new DefendFractured());
		BaseMod.addCard(new DualForm());
		BaseMod.addCard(new EnergyWave());
		BaseMod.addCard(new Invert());
		BaseMod.addCard(new Putrefy());
		BaseMod.addCard(new ShadowSlash());
		BaseMod.addCard(new StrikeFractured());

	}

	@Override
	public void receiveEditCharacters() {
		System.out.println("[SPYR] Editting Characters");
		BaseMod.addCharacter(new TheFractured(CardCrawlGame.playerName),
				TheFractured.BUTTON, TheFractured.POTRAIT, CharacterEnum.FRACTURED_CLASS);
	}

	@Override
	public void receiveEditRelics() {
		System.out.println("[SPYR] Editting Relics");
		SpyrRelics.addRelics();
	}

	@Override
	public void receiveEditStrings() {
		System.out.println("[SPYR] Editting Strings");
		String cardStrings = Gdx.files.internal("spyr/localization/spyr_cards.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

		String characterStrings = Gdx.files
				.internal("spyr/localization/spyr_characters.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(CharacterStrings.class, characterStrings);

		String powerStrings = Gdx.files
				.internal("spyr/localization/spyr_powers.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

		String relicStrings = Gdx.files
				.internal("spyr/localization/spyr_relics.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
	}

}
