package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.powers.DualFormPower;
import spyr.utils.FormHelper;

/**
 * Adds both DarkEco & LightEco. This will trigger both effects on cards that
 * gain bonuses from being in one form.
 */
public class DualForm extends SpyrCard {

	public static final String ID = "spyr:dual_form";
	public static final String NAME = "Dual Form";
	public static final String DESCRIPTION = "Ethereal. NL Activate both ShadowForm and LightForm.";
	public static final String UPGRADE_DESCRIPTION = "Activate both ShadowForm and LightForm.";

	private static final int COST = 3;

	public DualForm() {
		super(ID, NAME, DESCRIPTION, COST, AbstractCard.CardType.POWER, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		this.isEthereal = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DualFormPower(p, 1), 1));
		AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
			@Override
			public void update() {
				FormHelper.applyLightStacks(p);
				FormHelper.applyShadowStacks(p);
        this.isDone = true;
			}
		});
	}

	@Override
	public void doUpgrade() {
		this.isEthereal = false;
		this.rawDescription = UPGRADE_DESCRIPTION;
		this.initializeDescription();
	}

}
