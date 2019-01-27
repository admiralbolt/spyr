package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.powers.DrowsyPower;
import spyr.utils.FormHelper;

/**
 * Attack card that applies Drowsy when in dark form.
 */
public class DreamTendril extends FormAffectedCard {

	public static final String ID = "spyr:dream_tendril";
	public static final String NAME = "Dream Tendril";

	private static final int COST = 1;
	private static final int POWER = 8;
	private static final int DROWSY_DURATION = 3;
	private static final int UPGRADE_POWER = 1;
	private static final int UPGRADE_DROWSY_DURATION = -1;

	public DreamTendril() {
		super(ID, NAME, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.damage = this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = DROWSY_DURATION;
		this.tags.add(SpyrTags.SHADOW);
	}

	@Override
	public String getPrefix() {
		return "Deal !D! damage.";
	}

	@Override
	public String getShadow() {
		return "Apply !M! Drowsy.";
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		if (FormHelper.shadowFormIsActive(p)) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
					new DrowsyPower(m, this.magicNumber), this.magicNumber));
		}
	}

	@Override
	public void doUpgrade() {
		this.upgradeDamage(UPGRADE_POWER);
		this.upgradeMagicNumber(UPGRADE_DROWSY_DURATION);
	}

}
