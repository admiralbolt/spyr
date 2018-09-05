package spyr.cards.blue;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

import basemod.abstracts.CustomCard;
import spyr.cards.SpyrCards;

public class BeepBoop extends CustomCard {

	public static final String ID = "BeepBoop";
	public static final String IMG = "images/cards/blue/attack/beep_boop.png";

	private static final int COST = 3;
	private static final int ATTACK_DMG = 14;
	private static final int STAT_SCALING = 3;

	public BeepBoop() {
		super("BeepBoop", SpyrCards.BEEP_BOOP.NAME, IMG, COST,
				SpyrCards.BEEP_BOOP.DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCard.CardColor.BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.ALL_ENEMY);

		this.damage = this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = 3;
		this.isMultiDamage = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
						this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
	}

	@Override
	public AbstractCard makeCopy() {
		return new BeepBoop();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(STAT_SCALING);
		}
	}

	@Override
	public void applyPowers() {
		// First we apply the normal strength bonus.
		super.applyPowers();
		// Then, we iterate through the modified damage and update it with the focus
		// bonus.
		float damage = this.damage;
		AbstractPlayer player = AbstractDungeon.player;
		for (AbstractPower p : player.powers) {
			if (p instanceof FocusPower) {
				damage += this.magicNumber * p.amount;
			}
		}
		// With our new damage calculated, update the multi damage & damage
		// attributes.
		for (int i = 0; i < this.multiDamage.length; i++) {
			this.multiDamage[i] = MathUtils.floor(Math.max(damage, 0));
		}
		this.damage = this.multiDamage[0];
	}

	// I'm not entirely sure why, but it seems like the same damage calculation is
	// happening in two places... Need to investigate this further to understand
	// what's going on here / what applyPowers() is really doing.
	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		// First we apply the normal strength bonus.
		super.calculateCardDamage(mo);
		// Then, we iterate through the modified damage and update it with the focus
		// bonus.
		float damage = this.damage;
		AbstractPlayer player = AbstractDungeon.player;
		for (AbstractPower p : player.powers) {
			if (p instanceof FocusPower) {
				damage += this.magicNumber * p.amount;
			}
		}
		// With our new damage calculated, update the multi damage & damage
		// attributes.
		for (int i = 0; i < this.multiDamage.length; i++) {
			this.multiDamage[i] = MathUtils.floor(Math.max(damage, 0));
		}
		this.damage = this.multiDamage[0];
	}

}
