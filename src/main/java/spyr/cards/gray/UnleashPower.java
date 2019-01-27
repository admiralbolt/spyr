package spyr.cards.gray;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.SlowPower;

import spyr.patches.CardEnum;
import spyr.powers.DarkEcoPower;
import spyr.powers.HolyFirePower;
import spyr.powers.LightEcoPower;

/**
 * Gives a large amount of block in shadow form. Aplies statuses to all enemies
 * in light form.
 */
public class UnleashPower extends FormAffectedCard {

	public static final String ID = "spyr:unleash_power";
	public static final String NAME = "Unleash Power";

	private static final int COST = 3;
	private static final int BLOCK = 20;
	private static final int UPGRADE_BLOCK = 10;
	private static final int FIRE_AMOUNT = 1;

	public UnleashPower() {
		super(ID, NAME, COST, AbstractCard.CardType.SKILL, CardEnum.FRACTURED_GRAY,
				AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		this.block = this.baseBlock = BLOCK;
		this.magicNumber = this.baseMagicNumber = FIRE_AMOUNT;
		this.exhaust = true;
	}

	@Override
	public String getShadow() {
		return "Gain !B! Block. Block is not removed at the start of your next turn.";
	}

	@Override
	public String getLight() {
		if (this.upgraded) {
			return "Apply !M! HolyFire and Slow to ALL enemies.";
		}
		return "Apply !M! HolyFire to All enemies.";
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (p.hasPower(DarkEcoPower.POWER_ID)) {
			AbstractDungeon.actionManager
					.addToBottom(new GainBlockAction(p, p, this.block));
			AbstractDungeon.actionManager
					.addToBottom(new ApplyPowerAction(p, p, new BlurPower(p, 1), 1));
		}
		if (p.hasPower(LightEcoPower.POWER_ID)) {
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
				if (monster.isDead || monster.isDying)
					continue;
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster,
						p, new HolyFirePower(monster, p, this.magicNumber),
						this.magicNumber));
				if (this.upgraded) {
					AbstractDungeon.actionManager.addToBottom(
							new ApplyPowerAction(monster, p, new SlowPower(monster, 0)));
				}
			}
		}
	}

	@Override
	public void doUpgrade() {
		this.upgradeBlock(UPGRADE_BLOCK);
	}

}
