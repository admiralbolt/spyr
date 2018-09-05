package spyr;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import spyr.cards.blue.BeepBoop;
import spyr.relics.AmericanSpirit;

@SpireInitializer
public class Spyr implements EditCardsSubscriber, EditRelicsSubscriber,
		EditStringsSubscriber {

	private static final String MODNAME = "Spyr";
	private static final String AUTHOR = "Avi Knecht";
	private static final String DESCRIPTION = "Slay the spire mod.";

	public Spyr() {
		BaseMod.subscribe(this);
	}

	// Used by SpireInitializer(), not really clear how though.
	public static void initialize() {
		new Spyr();
	}

	@Override
	public void receiveEditRelics() {
		BaseMod.addRelic(new AmericanSpirit(), RelicType.SHARED);
	}

	@Override
	public void receiveEditCards() {
		BaseMod.addCard(new BeepBoop());
	}

	@Override
	public void receiveEditStrings() {
		String relicStrings = Gdx.files.internal("localization/spyr_relics.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);

		String cardStrings = Gdx.files.internal("localization/spyr_cards.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
	}

}
