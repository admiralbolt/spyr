package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.BaseModCardTags;
import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;

/**
 * Basic defend card.
 */
public class DefendFractured extends SpyrCard {

	public static final String ID = "spyr:defend_fractured";
	
	private static final int COST = 1;
	private static final int BLOCK = 5;
	private static final int UPGRADE_BONUS = 3;

	public DefendFractured() {
		super(ID, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF);
		this.block = this.baseBlock = BLOCK;
		this.tags.add(BaseModCardTags.BASIC_DEFEND);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

	}
	
	@Override
	public void doUpgrade() {
		this.upgradeBlock(UPGRADE_BONUS);
	}

}
