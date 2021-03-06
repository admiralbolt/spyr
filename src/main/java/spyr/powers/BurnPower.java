package spyr.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

/**
 * Deals damage every turn and halves its power.
 *
 * The actual functionality is in patches/BurnPatch.java since it requires some
 * base code modifying.
 */
public class BurnPower extends SpyrPower {

	public static final String POWER_ID = "spyr:burn";

	public AbstractCreature source;

	public BurnPower(AbstractCreature owner, AbstractCreature source, int strAmt) {
		super(POWER_ID, owner, strAmt);
		this.source = source;
		this.type = AbstractPower.PowerType.DEBUFF;
	}

	@Override
	public void updateDescription() {
		this.description = this.powerStrings.DESCRIPTIONS[0] + this.amount + this.powerStrings.DESCRIPTIONS[1]
				+ getReductionAmount(this.amount) + this.powerStrings.DESCRIPTIONS[2];
	}

	/**
	 * Helper function to get burn reduction amount, which is half of the current
	 * burn amount rounded up.
	 */
	public static int getReductionAmount(int amount) {
		if (amount <= 0) {
			return 0;
		}
		return (int) Math.ceil(amount / 2.0);
	}

}
