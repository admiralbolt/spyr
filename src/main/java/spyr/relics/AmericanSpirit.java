package spyr.relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;

public class AmericanSpirit extends CustomRelic {

	public static final String ID = "AmericanSpirit";
	private static final String IMG = "images/relics/american_spirit.png";
	private static final String OUTLINE = "images/relics/outlines/american_spirit.png";

	private static final int HP_PER_EXHAUST = 1;
	private static final int STR_PER_EXHAUST = 1;

	public AmericanSpirit() {
		super(ID, new Texture(Gdx.files.internal(IMG)),
				new Texture(Gdx.files.internal(OUTLINE)), RelicTier.COMMON,
				LandingSound.HEAVY);
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + HP_PER_EXHAUST + DESCRIPTIONS[1] + STR_PER_EXHAUST
				+ DESCRIPTIONS[2];
	}

	@Override
	public void onExhaust(AbstractCard card) {
		this.flash();
		AbstractDungeon.player.increaseMaxHp(HP_PER_EXHAUST, true);
		AbstractDungeon.actionManager.addToTop(
				new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
						new StrengthPower(AbstractDungeon.player, 1), 1));
	}

	@Override
	public AbstractRelic makeCopy() {
		return new AmericanSpirit();
	}

}
