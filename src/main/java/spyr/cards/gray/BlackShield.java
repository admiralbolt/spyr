package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import spyr.cards.SpyrCard;
import spyr.patches.CardEnum;
import spyr.patches.SpyrTags;
import spyr.utils.FormHelper;

/**
 * Blocks for more than a block. If in ShadowForm gain an artifact.
 */
public class BlackShield extends SpyrCard {

	public static final String ID = "spyr:black_shield";

	private static final int COST = 1;
	private static final int BLOCK = 7;
	private static final int NUMBER_OF_ARTIFACTS = 1;

	private static final int UPGRADE_BONUS = 3;

	public BlackShield() {
		super(ID, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);
		this.block = this.baseBlock = BLOCK;
		this.magicNumber = this.baseMagicNumber = NUMBER_OF_ARTIFACTS;
		this.tags.add(SpyrTags.SHADOW);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		if (FormHelper.shadowFormIsActive(p)) {
			AbstractDungeon.actionManager
					.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));
		}
	}

	@Override
	public void doUpgrade() {
		this.upgradeBlock(UPGRADE_BONUS);
	}

}
