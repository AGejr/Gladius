package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;

public class StatsPart implements EntityPart {
    /**
     * @param attack Damage modifier for entity
     * @param defence Protects makes an attack deal less damage
     */

    private int attack;
    private int spikeAttack;
    private int explosiveAttack;
    private int explosionRadius;
    private int defence;
    private int balance;

    public StatsPart(int attack, int spikeAttack, int explosiveAttack, int explosionRadius, int defence, int balance) {
        this.attack = attack;
        this.spikeAttack = spikeAttack;
        this.explosiveAttack = explosiveAttack;
        this.explosionRadius = explosionRadius;
        this.defence = defence;
        this.balance = balance;
    }

    public StatsPart(int attack, int defence, int balance) {
        this(attack, 0, 0, 0, defence, balance)
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public int getExplosiveAttack() {
        return explosiveAttack;
    }

    public void setExplosiveAttack(int explosiveAttack) {
        this.explosiveAttack = explosiveAttack;
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }

    public int getSpikeAttack() {
        return spikeAttack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void withdrawBalance(int amount) {
        balance -= amount;
    }

    public void depositBalance(int amount) {
        balance += amount;
    }

}
