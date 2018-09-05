package spyr.relics;

import java.util.ArrayList;

import org.javatuples.Triplet;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;

public class SpyrRelics {

	private static Triplet<CustomRelic, RelicType, Boolean> createTriplet(
			CustomRelic relic, RelicType type, Boolean enabled) {
		return new Triplet<CustomRelic, RelicType, Boolean>(relic, type, enabled);
	}

	// A list of all modded cards. They are created in pairs of card -> boolean,
	// where the boolean represents if they are enabled or not.
	@SuppressWarnings("serial")
	public static ArrayList<Triplet<CustomRelic, RelicType, Boolean>> RELICS = new ArrayList<Triplet<CustomRelic, RelicType, Boolean>>() {
		{
			// Keep these sorted so they are readable.

			// Disabled cards.
			add(createTriplet(new AmericanSpirit(), RelicType.SHARED, false));
		}
	};

	public static void addRelics() {
		for (Triplet<CustomRelic, RelicType, Boolean> relicTriplet : RELICS) {
			if (relicTriplet.getValue2()) {
				BaseMod.addRelic(relicTriplet.getValue0(), relicTriplet.getValue1());
			}
		}
	}

}
