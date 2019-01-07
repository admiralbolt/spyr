package spyr.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SpyrPower extends AbstractPower {

	public final PowerStrings powerStrings;

	public SpyrPower(String id, AbstractCreature owner, int strAmt) {
		this.powerStrings = CardCrawlGame.languagePack.getPowerStrings(id);
		this.name = this.powerStrings.NAME;
		this.ID = id;
		this.owner = owner;
		this.amount = strAmt;
		this.updateDescription();
		this.img = ImageMaster.loadImage(
				String.format("spyr/images/powers/%s.png", id.split(":")[1]));
	}

	@Override
	public void updateDescription() {
		this.description = this.powerStrings.DESCRIPTIONS[0];
	}

}
