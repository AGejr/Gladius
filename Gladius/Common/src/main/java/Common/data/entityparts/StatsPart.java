package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;

public class StatsPart implements EntityPart {
    /***
     @param attack Damage modifier for entity
     @param defence Protects makes an attack deal less damage
     */

    private int attack;
    private int defence;
    private int balance;

    public StatsPart(int attack, int defence, int balance) {
        this.attack = attack;
        this.defence = defence;
        this.balance = balance;
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
