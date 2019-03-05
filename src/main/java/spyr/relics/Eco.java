package spyr.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import spyr.utils.FormHelper;

/**
 * At the beginning of each combat choose between starting in LightForm and
 * ShadowForm.
 */
public class Eco extends SpyrRelic {

	public static final String ID = "spyr:eco";

	public Eco() {
		super(ID, RelicTier.STARTER, AbstractRelic.LandingSound.MAGICAL);
	}

	@Override
	public void atBattleStart() {
		this.flash();
		FormHelper.chooseForm(AbstractDungeon.player);
	}

}
