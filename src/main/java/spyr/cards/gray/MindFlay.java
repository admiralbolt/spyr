
package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.actions.ExhaustCardsFromDeckAction;
import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.utils.FormHelper;

/**
 * Attack card that exhausts cards from your deck in Shadow form. Switches you
 * to dark form.
 */
public class MindFlay extends FormAffectedCard {

	public static final String ID = "spyr:mind_flay";
	public static final String NAME = "Mind Flay";

	private static final int COST = 0;
	private static final int POWER = 5;
	private static final int UPGRADE_POWER = 2;

	public MindFlay() {
		super(ID, NAME, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
		this.damage = this.baseDamage = POWER;
		this.tags.add(SpyrTags.SHADOW);
	}

	@Override
	public String getPrefix() {
		return "Deal !D! damage.";
	}

	@Override
	public String getShadow() {
		if (this.upgraded) {
			return "Exhaust a card from your deck.";
		}
		return "Exhaust a random card from your deck.";
	}

	@Override
	public String getLight() {
		return "Costs [E] more.";
	}

	@Override
	public String getSuffix() {
		return "Gain 1 ShadowEnergy.";
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

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		if (FormHelper.shadowFormIsActive(p)) {
			AbstractDungeon.actionManager.addToBottom(
					new ExhaustCardsFromDeckAction(1, /* random= */!this.upgraded));
		}
		FormHelper.applyShadowStacks(p);
	}

	@Override
	public void doUpgrade() {
		this.upgradeDamage(UPGRADE_POWER);
		this.initializeFormDescriptions();
	}

}
