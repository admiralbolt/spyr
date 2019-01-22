
package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.actions.ExhaustCardsFromDeckAction;
import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.powers.DarkEcoPower;
import spyr.powers.LightEcoPower;
import spyr.utils.FormHelper;

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
		this.tags.add(SpyrTags.SHADOW);
	}

	@Override
	public void applyPowers() {
		super.applyPowers();
		if (FormHelper.lightFormIsActive(AbstractDungeon.player)) {
			this.costForTurn = this.cost + 1;
		} else {
			this.costForTurn = this.cost;
		}
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
						AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		if (FormHelper.shadowFormIsActive(p)) {
			AbstractDungeon.actionManager.addToBottom(new ExhaustCardsFromDeckAction(1, /* random= */!this.upgraded));
		}
		FormHelper.maybeSwitchToShadowForm(p);
	}

	@Override
	public void doUpgrade() {
		this.upgradeDamage(UPGRADE_POWER);
		this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;
		this.initializeDescription();
	}

}
