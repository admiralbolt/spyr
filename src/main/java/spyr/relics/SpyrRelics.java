package spyr.relics;

import java.util.ArrayList;

import org.javatuples.Triplet;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import spyr.patches.CardEnum;

public class SpyrRelics {

	private static Triplet<CustomRelic, RelicType, Boolean> createTriplet(
			CustomRelic relic, RelicType type, Boolean enabled) {
		return new Triplet<CustomRelic, RelicType, Boolean>(relic, type, enabled);
	}

	// A list of all mod relics. They are created in triplets of (relic, type, boolean),
	// where type represents which class they apply to, and the boolean represents whether
	// or not the relic is enabled.
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
		
		// Starter relics for custom classes.
		BaseMod.addRelicToCustomPool(new Eco(), CardEnum.FRACTURED);
	}

}
