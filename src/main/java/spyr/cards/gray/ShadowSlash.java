package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.utils.FormHelper;

/**
 * Attack card that deals more damage in dark form.
 */
public class ShadowSlash extends FormAffectedCard {

	public static final String ID = "spyr:shadow_slash";
	public static final String NAME = "Shadow Slash";

	private static final int COST = 1;
	private static final int POWER = 7;
	private static final int DARK_FORM_SCALING = 4;
	private static final int UPGRADE_POWER = 2;
	private static final int UPGRADE_SCALING = 3;

	public ShadowSlash() {
		super(ID, NAME, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ENEMY);
		this.damage = this.baseDamage = POWER;
		this.magicNumber = this.baseMagicNumber = DARK_FORM_SCALING;
		this.tags.add(SpyrTags.SHADOW);
	}

	@Override
	public String getPrefix() {
		return "Deal !D! damage.";
	}

	@Override
	public String getShadow() {
		return "Deal !M! additional damage.";
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

	}

	@Override
	public float calculateModifiedCardDamage(AbstractPlayer player,
			AbstractMonster mo, float tmp) {
		if (FormHelper.shadowFormIsActive(player)) {
			return tmp + this.magicNumber;
		}
		return tmp;
	}

	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		super.calculateCardDamage(mo);
		this.initializeDescription();
	}

	@Override
	public void doUpgrade() {
		this.upgradeDamage(UPGRADE_POWER);
		this.upgradeMagicNumber(UPGRADE_SCALING);
	}

}
