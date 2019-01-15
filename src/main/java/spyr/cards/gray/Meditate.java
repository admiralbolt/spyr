package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.actions.MeditateAction;
import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;

/**
 * Block a small amount. Look at top 3 cards of deck. Draw 1 and discard the
 * rest.
 */
public class Meditate extends SpyrCard {

	public static final String ID = "spyr:meditate";
	private static final int COST = 1;
	private static final int BLOCK = 5;
	private static final int NUM_CARDS_FROM_DECK = 3;
	private static final int NUM_CARDS_TO_KEEP = 1;

	private static final int UPGRADE_BONUS = 3;

	public Meditate() {
		super(ID, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.SELF);
		this.block = this.baseBlock = BLOCK;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		AbstractDungeon.actionManager.addToBottom(new MeditateAction(NUM_CARDS_FROM_DECK, NUM_CARDS_TO_KEEP));
	}

	@Override
	public void doUpgrade() {
		this.upgradeBlock(UPGRADE_BONUS);
	}

}
