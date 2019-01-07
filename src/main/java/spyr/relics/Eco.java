package spyr.relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;

public class Eco extends CustomRelic {

	public static final String ID = "Eco";
	private static final String IMG = "images/relics/eco.png";
	private static final String OUTLINE = "images/relics/outlines/eco.png";

	public static final RelicTier TIER = RelicTier.STARTER;

	public Eco() {
		super(ID, new Texture(Gdx.files.internal(IMG)),
				new Texture(Gdx.files.internal(OUTLINE)), RelicTier.STARTER,
				LandingSound.HEAVY);
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new Eco();
	}

}
