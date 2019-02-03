package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.powers.BurnPower;
import spyr.utils.FormHelper;

/**
 * Applies burn and draws cards in light form. Switches to light form.
 */
public class EnflameMind extends FormAffectedCard {

	public static final String ID = "spyr:enflame_mind";
	public static final String NAME = "Enflame Mind";

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
		super(ID, NAME, COST, AbstractCard.CardType.ATTACK, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.damage = this.baseDamage = BURN;
		this.magicNumber = this.baseMagicNumber = CARD_DRAW;
		this.tags.add(SpyrTags.LIGHT);
	}

	@Override
	public String getLight() {
		return "Apply !D! Burn. Draw !M! cards.";
	}

	@Override
	public String getSuffix() {
		return "Switch to LightForm.";
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (FormHelper.lightFormIsActive(p)) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
					new BurnPower(m, p, this.damage), this.damage));
			AbstractDungeon.actionManager
					.addToBottom(new DrawCardAction(p, this.magicNumber));
		}
		FormHelper.maybeSwitchToLightForm(p);
	}

	@Override
	public void applyPowers() {
    super.applyPowers();
		// Our target type switches depending on form.
		if (FormHelper.lightFormIsActive(AbstractDungeon.player)) {
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
