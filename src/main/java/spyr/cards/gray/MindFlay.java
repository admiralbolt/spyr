
package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.actions.ExhaustCardsFromDeckAction;
import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;

/**
 * Attack card that exhausts cards from your deck in Shadow form. Switches you
 * to dark form.
 */
public class MindFlay extends SpyrCard {

	public static final String ID = "spyr:mind_flay";

	private static final int COST = 0;
	private static final int POWER = 5;
	private static final int UPGRADE_POWER = 2;

	public MindFlay() {
		super(ID, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.ENEMY);
		this.damage = this.baseDamage = POWER;
	}

	@Override
	public void applyPowers() {
		super.applyPowers();
		if (AbstractDungeon.player.hasPower(LightEcoPower.POWER_ID)) {
			this.costForTurn = this.cost + 1;
		} else {
			this.costForTurn = this.cost;
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
						AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToBottom(new ExhaustCardsFromDeckAction(1, /* random= */!this.upgraded));
		} else {
			if (p.hasPower(LightEcoPower.POWER_ID)) {
				AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, LightEcoPower.POWER_ID, 1));
			}
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DarkEcoPower(p, 1), 1));
		}

	}

	@Override
	public void doUpgrade() {
		this.upgradeDamage(UPGRADE_POWER);
		this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;
		this.initializeDescription();
	}

}
