package spyr.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

public class Masochism extends CustomCard {

	public static final String ID = "Masochism";
	public static final String IMG = "images/cards/red/attack/masochism.png";
	public static CardStrings MASOCHISM = CardCrawlGame.languagePack
			.getCardStrings(ID);

	private static final int COST = 1;
	private static final int ATTACK_DMG = 5;
	private static final int UPGRADE_ATTACK = 2;
	private static final int HP_LOSS_SCALING = 2;
	private static final int UPGRADE_SCALING = 1;

	public Masochism() {

		super(ID, MASOCHISM.NAME, IMG, COST, MASOCHISM.DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCard.CardColor.RED,
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
	public AbstractCard makeCopy() {
		return new Masochism();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_SCALING);
			this.upgradeDamage(UPGRADE_ATTACK);
		}
	}

	@Override
	public void tookDamage() {
		AbstractDungeon.actionManager
				.addToBottom(new ModifyDamageAction(this, this.magicNumber));
	}

}
