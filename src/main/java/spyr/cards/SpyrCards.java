package spyr.cards;

import java.util.ArrayList;

import org.javatuples.Pair;

import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import spyr.cards.blue.BeepBoop;
import spyr.cards.gray.StrikeFractured;
import spyr.cards.red.Masochism;
import spyr.cards.red.Revenge;
import spyr.cards.red.StaunchBleeding;

public class SpyrCards {

	private static Pair<CustomCard, Boolean> createPair(CustomCard card,
			Boolean bool) {
		return new Pair<CustomCard, Boolean>(card, bool);
	}

	// A list of all modded cards. They are created in pairs of card -> boolean,
	// where the boolean represents if they are enabled or not.
	@SuppressWarnings("serial")
	public static ArrayList<Pair<CustomCard, Boolean>> CARDS = new ArrayList<Pair<CustomCard, Boolean>>() {
		{
			// Keep these sorted so they are readable.
			add(createPair(new BeepBoop(), false));
			add(createPair(new Masochism(), true));
			add(createPair(new Revenge(), true));
			add(createPair(new StaunchBleeding(), true));
			add(createPair(new StrikeFractured(), true));
		}
	};

	public static void addCards() {
		for (Pair<CustomCard, Boolean> cardEnabledPair : CARDS) {
			BaseMod.addCard(cardEnabledPair.getValue0());
			if (cardEnabledPair.getValue1()) {
				UnlockTracker.unlockCard(cardEnabledPair.getValue0().cardID);
				BaseMod.addCard(cardEnabledPair.getValue0());
			}
		}
	}

}
