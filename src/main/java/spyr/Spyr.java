package spyr;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.localization.RelicStrings;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import spyr.relics.AmericanSpirit;

@SpireInitializer
public class Spyr implements EditRelicsSubscriber, EditStringsSubscriber {

	private static final String MODNAME = "Spyr";
	private static final String AUTHOR = "Avi Knecht";
	private static final String DESCRIPTION = "Slay the spire mod.";

	public Spyr() {
		BaseMod.subscribe(this);
	}

	public static void initialize() {
		System.out.println("[INITIALIZE]");
		Spyr spyr = new Spyr();
	}

	@Override
	public void receiveEditRelics() {
		System.out.println("Editting relics...");
		BaseMod.addRelic(new AmericanSpirit(), RelicType.SHARED);
	}

	@Override
	public void receiveEditStrings() {
		System.out.println("STRINGS");
		String relicStrings = Gdx.files.internal("localization/spyr_relics.json")
				.readString(String.valueOf(StandardCharsets.UTF_8));
		System.out.println(relicStrings);
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
	}

}
