package spyr.relics;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;

public abstract class SpyrRelic extends CustomRelic {

	public SpyrRelic(String id, RelicTier tier) {
		this(id, tier, LandingSound.HEAVY);
	}

	public SpyrRelic(String id, RelicTier tier,
			AbstractRelic.LandingSound sound) {
		super(id,
				new Texture(Gdx.files.internal(
						String.format("spyr/images/relics/%s.png", id.split(":")[1]))),
				new Texture(Gdx.files.internal(String
						.format("spyr/images/relics/outlines/%s.png", id.split(":")[1]))),
				tier, sound);
	}

	/**
	 * Dynamically constructs an instance of the current relic.
	 */
	@Override
	public AbstractRelic makeCopy() {
		try {
			return this.getClass().getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

}
