package spyr.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

/**
 * Deals damage every turn and doubles in power.
 * 
 * Actual functionality is in patches/BurnPatch.java since it requires some base
 * code modifying.
 */
public class HolyFirePower extends SpyrPower {

	public static final String POWER_ID = "spyr:holy_fire";

	public AbstractCreature source;

	public HolyFirePower(AbstractCreature owner, AbstractCreature source,
			int strAmt) {
		super(POWER_ID, owner, strAmt);
		this.source = source;

	}

	@Override
	public void updateDescription() {
		this.description = this.powerStrings.DESCRIPTIONS[0] + this.amount
				+ this.powerStrings.DESCRIPTIONS[1] + this.amount
				+ this.powerStrings.DESCRIPTIONS[2];
	}

}
