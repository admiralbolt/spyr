package spyr.cards.gray;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import spyr.patches.CardEnum;
import spyr.utils.FormHelper;

/**
 * A block card that either defends a large amount one time in dark form, or a
 * small amount several times in light form.
 */
public class IronSkin extends FormAffectedCard {

	public static final String ID = "spyr:iron_skin";
	public static final String NAME = "Iron Skin";

	private static final int COST = 1;
	private static final int BLOCK = 4;
	private static final int BLOCK_TIMES = 2;
	private static final int UPGRADE_BLOCK_TIMES = 1;

	/**
	 * Damage is used to encode what the block is for dark form. We are switching
	 * between block once and block X times which is difficult to do by itself.
	 * Since Dual Form is a card, we need to be able to support the case where
	 * both modes are run, which means we cannot just modify block directly. This
	 * also means we manually apply powers to the values of the block.
	 */
	public IronSkin() {
		super(ID, NAME, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
		this.block = this.baseBlock = BLOCK;
		this.magicNumber = this.baseMagicNumber = BLOCK_TIMES;
		this.damage = this.baseDamage = this.block * this.magicNumber;
	}

	@Override
	public String getShadow() {
		return "Gain !D! Block.";
	}

	@Override
	public String getLight() {
		return "Gain !B! Block !M! times.";
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		// Need to decide if we want to double the intraction with dual form or not.
		if (FormHelper.shadowFormIsActive(p)) {
			AbstractDungeon.actionManager
					.addToBottom(new GainBlockAction(p, p, this.damage));
		}
		if (FormHelper.lightFormIsActive(p)) {
			for (int i = 0; i < this.magicNumber; i++) {
				AbstractDungeon.actionManager
						.addToBottom(new GainBlockAction(p, p, this.block));
			}
		}
	}

	@Override
	public void applyPowers() {
		this.damage = applyBlock(this.baseDamage);
		if (this.damage != this.baseDamage) {
			// I hate this, but the text won't show up as modified unless this is set.
			this.isDamageModified = true;
		}
		this.block = applyBlock(this.baseBlock);
		if (this.block != this.baseBlock) {
			this.isBlockModified = true;
		}
		this.loadCardDescription();
	}

	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		// Leaving this blank. No need to adjust card damage, because it doesn't
		// deal damage. Another part of the hack to get this card to work as
		// intended.
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_BLOCK_TIMES);
	}

	/**
	 * Helper function that applies powers to block values. This is a duplicate of
	 * existing functionality, but since we are encoding a block value into the
	 * damage attribute we need to explicitly apply block powers to it.
	 */
	public static int applyBlock(float baseBlock) {
		float tmp = baseBlock;
		for (AbstractPower p : AbstractDungeon.player.powers) {
			tmp = p.modifyBlock(tmp);
		}
		if (tmp < 0.0f) {
			tmp = 0.0f;
		}
		return MathUtils.floor(tmp);
	}

}
