package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.BurnPower;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

/**
 * Deals damage to all in dark form. Applies poison to all in light form.
 */
public class EnflameMind extends SpyrCard {

	public static final String ID = "spyr:enflame_mind";

	private static final int COST = 1;
	private static final int CARD_DRAW = 1;
	private static final int UPGRADE_CARD_DRAW = 2;
	private static final int BURN = 8;
	private static final int UPGRADE_BURN = 4;

	/**
	 * This is another situation where we will use damage to encode something
	 * besides damage. In this case damage will be the burn applied. I guess
	 * thinking about it now there aren't any cards that apply statuses and draw
	 * cards, or apply multiple separate durations of statuses.
	 */
	public EnflameMind() {
		super(ID, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ENEMY);
		this.damage = this.baseDamage = BURN;
		this.magicNumber = this.baseMagicNumber = CARD_DRAW;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager
					.addToBottom(new ApplyPowerAction(m, p, new BurnPower(m, p, this.damage), this.damage));
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
		} else {
			if (p.hasPower(DarkEcoPower.POWER_ID)) {
				AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, DarkEcoPower.POWER_ID, 1));
			}
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightEcoPower(p, 1), 1));
		}
	}

	@Override
	public void applyPowers() {
		// Our target type switches depending on form.
		if (AbstractDungeon.player.hasPower(LightEcoPower.POWER_ID)) {
			this.target = AbstractCard.CardTarget.ENEMY;
		} else {
			this.target = AbstractCard.CardTarget.SELF;
		}
	}

	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		// Leaving this blank. No need to adjust card damage, because it doesn't
		// deal damage. Just another part of the hack to get this to work as
		// intended.
	}

	@Override
	public void doUpgrade() {
		this.upgradeDamage(UPGRADE_BURN);
		this.upgradeMagicNumber(UPGRADE_CARD_DRAW);
	}

}
