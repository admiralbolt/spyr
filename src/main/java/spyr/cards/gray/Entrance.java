package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;

import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.utils.FormHelper;

/**
 * Applies slow.
 */
public class Entrance extends FormAffectedCard {

	public static final String ID = "spyr:entrance";
	public static final String NAME = "Entrance";

	private static final int COST = 2;

	public Entrance() {
		super(ID, NAME, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.tags.add(SpyrTags.LIGHT);
	}

	@Override
	public String getLight() {
		if (this.upgraded) {
			return "Apply Slow to ALL enemies.";
		}
		return "Apply Slow to an enemy.";
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (FormHelper.lightFormIsActive(p)) {
			if (this.upgraded) {
				for (AbstractMonster monster : AbstractDungeon
						.getCurrRoom().monsters.monsters) {
					AbstractDungeon.actionManager.addToBottom(
							new ApplyPowerAction(monster, p, new SlowPower(monster, 1), 1));
				}
			} else {
				AbstractDungeon.actionManager
						.addToBottom(new ApplyPowerAction(m, p, new SlowPower(m, 1), 1));
			}
		}
	}

	@Override
	public void doUpgrade() {
		this.target = AbstractCard.CardTarget.ALL_ENEMY;
		this.initializeDescription();
	}

}
