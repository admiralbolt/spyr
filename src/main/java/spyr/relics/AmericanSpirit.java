package spyr.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.helpers.RelicType;

public class AmericanSpirit extends SpyrRelic {

	public static final String ID = "spyr:american_spirit";

	private static final int HP_PER_EXHAUST = 1;
	private static final int STR_PER_EXHAUST = 1;

	public RelicType type = RelicType.SHARED;

	public AmericanSpirit() {
		super(ID, RelicTier.COMMON, AbstractRelic.LandingSound.HEAVY);
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

}
