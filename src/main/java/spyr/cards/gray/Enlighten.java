package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.utils.FormHelper;

/**
 * Draw cards if already in light form. Switch to light form.
 */
public class Enlighten extends FormAffectedCard {

	public static final String ID = "spyr:enlighten";
	public static final String NAME = "Enlighten";

	private static final int COST = 1;
	private static final int NUM_CARDS = 3;
	private static final int UPGRADED_COST = 0;

	public Enlighten() {
		super(ID, NAME, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = NUM_CARDS;
		this.tags.add(SpyrTags.LIGHT);
	}

	@Override
	public String getLight() {
		return "Draw !M! cards.";
	}

	@Override
	public String getSuffix() {
		return "Switch to LightForm.";
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (FormHelper.lightFormIsActive(p)) {
			AbstractDungeon.actionManager
					.addToBottom(new DrawCardAction(p, this.magicNumber));
		}
		FormHelper.maybeSwitchToLightForm(p);
	}

	@Override
	public void doUpgrade() {
		this.upgradeBaseCost(UPGRADED_COST);
	}

}
