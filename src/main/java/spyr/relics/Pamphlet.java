package spyr.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;

/**
 * Whenever you switch forms, gain 6 block.
 * 
 * No real functionality in this class, see the form switching logic in
 * FormHelper.java.
 */
public class Pamphlet extends SpyrRelic {

	public static final String ID = "spyr:pamphlet";
	public static final int BLOCK = 6;

	public Pamphlet() {
		super(ID, RelicTier.SHOP, AbstractRelic.LandingSound.FLAT);
	}

}
