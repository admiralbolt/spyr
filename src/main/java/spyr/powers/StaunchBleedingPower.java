package spyr.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;

public class StaunchBleedingPower
extends AbstractPower {
    public static final String POWER_ID = "spyr:staunch_bleeding";
    private static final PowerStrings STAUNCH_BLEEDING = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public StaunchBleedingPower(AbstractCreature owner, int strAmt) {
        this.name = STAUNCH_BLEEDING.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = strAmt;
        this.updateDescription();
        this.isPostActionPower = true;
        this.img = ImageMaster.loadImage("spyr/images/powers/staunch_bleeding.png");
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.owner == this.owner) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new RegenPower(this.owner, this.amount), this.amount));
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = STAUNCH_BLEEDING.DESCRIPTIONS[0] + this.amount + STAUNCH_BLEEDING.DESCRIPTIONS[1];
    }
}


