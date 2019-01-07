package spyr.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import spyr.cards.SpyrCard;

public class Masochism extends SpyrCard {

	public static final String ID = "spyr:masochism";

	private static final int COST = 1;
	private static final int ATTACK_DMG = 5;
	private static final int UPGRADE_ATTACK = 2;
	private static final int HP_LOSS_SCALING = 2;
	private static final int UPGRADE_SCALING = 1;

	public Masochism() {

		super(ID, COST, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.RED,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);

		this.damage = this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = HP_LOSS_SCALING;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new DamageAction((AbstractCreature) m,
						new DamageInfo(p, this.damage, this.damageTypeForTurn),
						AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
	}

	@Override
	public void tookDamage() {
		AbstractDungeon.actionManager
				.addToBottom(new ModifyDamageAction(this.uuid, this.magicNumber));
	}

	@Override
	public void doUpgrade() {
		this.upgradeMagicNumber(UPGRADE_SCALING);
		this.upgradeDamage(UPGRADE_ATTACK);
	}

}
