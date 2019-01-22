package spyr.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FlickerPower extends SpyrPower {

	public static final String POWER_ID = "spyr:flicker";

	public FlickerPower(AbstractCreature owner, int strAmt) {
		super(POWER_ID, owner, strAmt);
	}

	@Override
	public void reducePower(int reduceAmount) {
		this.amount -= reduceAmount;
    this.flash();
		if (this.amount == 0) {
			AbstractDungeon.actionManager.addToTop(
					new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
		}
	}

  @Override
  public void atEndOfTurn(boolean isPlayer) {
    if (!isPlayer) {
      return;
    }
    this.reducePower(1);
  }

  // If the player already has a form, consume the form and reduce the number
  // of stacks. In general the player will only have 1 form, but in the case
  // where dual form is active we'll remove shadow form first. ShadowForm first
  // was chosen fairly by coin toss.
  @Override
  public void onInitialApplication() {
    int totalReduction = 0;
    if (this.owner.hasPower(DarkEcoPower.POWER_ID)) {
      AbstractPower darkEco = this.owner.getPower(DarkEcoPower.POWER_ID);
      darkEco.reducePower(1);
      totalReduction++;
    }
    // We only want to remove light as well *if* we have enough flicker for it.
    if (this.owner.hasPower(LightEcoPower.POWER_ID) && this.amount > totalReduction) {
      AbstractPower lightEco = this.owner.getPower(LightEcoPower.POWER_ID);
      lightEco.reducePower(1);
      totalReduction++;
    }
    this.reducePower(totalReduction);
  }


}
