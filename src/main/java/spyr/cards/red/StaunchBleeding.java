package spyr.cards.red;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import spyr.powers.StaunchBleedingPower;

public class StaunchBleeding extends CustomCard {

	public static final String ID = "StaunchBleeding";
	public static final String IMG = "images/cards/red/power/staunch_bleeding.png";
	public static CardStrings STAUNCH_BLEEDING = CardCrawlGame.languagePack
			.getCardStrings(ID);

	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;

	private int regenAmount = 1;

	public StaunchBleeding() {

		super(ID, STAUNCH_BLEEDING.NAME, IMG, COST, STAUNCH_BLEEDING.DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCard.CardColor.RED,
				AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);

		this.damage = this.baseDamage = 0;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new StaunchBleedingPower(p, this.regenAmount), this.regenAmount));
	}

	@Override
	public AbstractCard makeCopy() {
		return new StaunchBleeding();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADED_COST);
		}
	}

}
