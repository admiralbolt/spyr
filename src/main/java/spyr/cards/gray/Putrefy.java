package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.utils.FormHelper;

/**
 * Applies vulnerable to an enemy. Also applies weak when in light form.
 */
public class Putrefy extends FormAffectedCard {

	public static final String ID = "spyr:putrefy";
	public static final String NAME = "Putrefy";

	private static final int COST = 1;
	private static final int DURATION = 2;
	private static final int UPGRADE_DURATION = 1;

	public Putrefy() {
		super(ID, NAME, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.magicNumber = this.baseMagicNumber = DURATION;
		this.tags.add(SpyrTags.LIGHT);
	}

	@Override
	public String getPrefix() {
		return "Apply !M! Vulnerable.";
	}

	@Override
	public String getLight() {
		return "Apply !M! Weak";
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
				new VulnerablePower(m, this.magicNumber, /* isSourceMonster= */false),
				this.magicNumber));
		if (FormHelper.lightFormIsActive(p)) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
					new WeakPower(m, this.magicNumber, /* isSourceMonster= */false),
					this.magicNumber));
		}
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_DURATION);
	}

}
