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
import com.megacrit.cardcrawl.localization.UIStrings;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import spyr.cards.gray.Amnesia;
import spyr.cards.gray.AnnihilatorBeam;
import spyr.cards.gray.BitterRelease;
import spyr.cards.gray.BlackShield;
import spyr.cards.gray.ChannelPower;
import spyr.cards.gray.ChargeEnergy;
import spyr.cards.gray.Cleanse;
import spyr.cards.gray.DeepDream;
import spyr.cards.gray.DefendFractured;
import spyr.cards.gray.DreamTendril;
import spyr.cards.gray.DualForm;
import spyr.cards.gray.EnergyWave;
import spyr.cards.gray.Enlighten;
import spyr.cards.gray.Entrance;
import spyr.cards.gray.EnvelopingShadow;
import spyr.cards.gray.GrowingDarkness;
import spyr.cards.gray.Invert;
import spyr.cards.gray.IronSkin;
import spyr.cards.gray.LightRay;
import spyr.cards.gray.Meditate;
import spyr.cards.gray.MindFlay;
import spyr.cards.gray.NeverEndingBlaze;
import spyr.cards.gray.Putrefy;
import spyr.cards.gray.QuickChange;
import spyr.cards.gray.Radiate;
import spyr.cards.gray.ShadowSlash;
import spyr.cards.gray.StrikeFractured;
import spyr.cards.gray.UnleashPower;
import spyr.characters.TheFractured;
import spyr.patches.CardEnum;
import spyr.patches.CharacterEnum;
import spyr.relics.SpyrRelics;

@SpireInitializer
public class Spyr implements EditCardsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber,
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

		BaseMod.addColor(CardEnum.FRACTURED_GRAY, Color.LIGHT_GRAY, get512("bg_attack_fractured.png"),
				get512("bg_skill_fractured.png"), get512("bg_power_fractured.png"), get512("card_fractured_orb.png"),
				get1024("bg_attack_fractured.png"), get1024("bg_skill_fractured.png"),
				get1024("bg_power_fractured.png"), get1024("card_fractured_orb.png"), get512("card_fractured_orb.png"));
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
		BaseMod.addCard(new Amnesia());
		BaseMod.addCard(new AnnihilatorBeam());
		BaseMod.addCard(new BitterRelease());
		BaseMod.addCard(new BlackShield());
		BaseMod.addCard(new Cleanse());
		BaseMod.addCard(new ChannelPower());
		BaseMod.addCard(new ChargeEnergy());
		BaseMod.addCard(new DeepDream());
		BaseMod.addCard(new DefendFractured());
		BaseMod.addCard(new DreamTendril());
		BaseMod.addCard(new DualForm());
		BaseMod.addCard(new EnergyWave());
		BaseMod.addCard(new Enlighten());
		BaseMod.addCard(new Entrance());
		BaseMod.addCard(new EnvelopingShadow());
		BaseMod.addCard(new GrowingDarkness());
		BaseMod.addCard(new Invert());
		BaseMod.addCard(new IronSkin());
		BaseMod.addCard(new LightRay());
		BaseMod.addCard(new Meditate());
		BaseMod.addCard(new MindFlay());
		BaseMod.addCard(new NeverEndingBlaze());
		BaseMod.addCard(new Putrefy());
		BaseMod.addCard(new QuickChange());
		BaseMod.addCard(new Radiate());
		BaseMod.addCard(new ShadowSlash());
		BaseMod.addCard(new StrikeFractured());
		BaseMod.addCard(new UnleashPower());
	}

	@Override
	public void receiveEditCharacters() {
		System.out.println("[SPYR] Editting Characters");
		BaseMod.addCharacter(new TheFractured(CardCrawlGame.playerName), TheFractured.BUTTON, TheFractured.POTRAIT,
				CharacterEnum.FRACTURED_CLASS);
	}

	@Override
	public void receiveEditKeywords() {
		System.out.println("[SPYR] Editting Keywords.");
		String[] shadow = { "shadowform" };
		String[] light = { "lightform" };
		String[] burn = { "burn" };
		BaseMod.addKeyword(shadow, "Grants additional effects to some cards.");
		BaseMod.addKeyword(light, "Grants additional effects to some cards.");
		BaseMod.addKeyword(burn,
				"Creatures on fire take damage at the start of their turn. Each turn, Burn is reduced by half.");
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

		String characterStrings = Gdx.files.internal("spyr/localization/spyr_characters.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(CharacterStrings.class, characterStrings);

		String powerStrings = Gdx.files.internal("spyr/localization/spyr_powers.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

		String relicStrings = Gdx.files.internal("spyr/localization/spyr_relics.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);

		String uiStrings = Gdx.files.internal("spyr/localization/spyr_ui.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
	}

}
