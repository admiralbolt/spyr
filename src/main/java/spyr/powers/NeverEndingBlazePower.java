package spyr.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

/**
 * Whilst in light form, burn stacks don't decrease. Again this power doesn't
 * actually have any functionality, the interesting stuff is in the
 * BurnDamageAction class.
 */
public class NeverEndingBlazePower extends SpyrPower {

	public static final String POWER_ID = "spyr:never_ending_blaze";

	public NeverEndingBlazePower(AbstractCreature owner, int strAmt) {
		super(POWER_ID, owner, strAmt);
	}

	@Override
	public void updateDescription() {
		this.description = this.powerStrings.DESCRIPTIONS[0];
	}

}
