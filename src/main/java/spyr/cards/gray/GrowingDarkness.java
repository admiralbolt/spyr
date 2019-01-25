package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.powers.GrowingDarknessPower;
import spyr.utils.FormHelper;

/**
 * Gain Strength at the start of each turn if in shadow form.
 */
public class GrowingDarkness extends SpyrCard {

	public static final String ID = "spyr:growing_darkness";
	public static final int COST = 2;
	public static final int BUFF_AMOUNT = 1;
	public static final int UPGRADE_BUFF_AMOUNT = 1;

	public GrowingDarkness() {
		super(ID, COST, AbstractCard.CardType.POWER, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = 1;
		// This is really a shadow card since the downside comes from being in light form.
		this.tags.add(SpyrTags.SHADOW);
	}

	@Override
	public void applyPowers() {
		super.applyPowers();
		if (FormHelper.lightFormIsActive(AbstractDungeon.player)) {
			this.costForTurn = this.cost + 3;
		} else {
			this.costForTurn = this.cost;
		}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(p, p, new GrowingDarknessPower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_BUFF_AMOUNT);
	}

}
