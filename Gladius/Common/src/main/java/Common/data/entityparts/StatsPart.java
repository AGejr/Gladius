package Common.data.entityparts;

import Common.data.Entity;
import Common.data.GameData;

public class StatsPart implements EntityPart {

    /***
        @param attack Damage modifier for entity
        @param defence Protects makes an attack deal less damage
     */

    private int attack;
    private int spikeAttack;
    private int explosiveAttack;
    private int explosionRadius;
    private int defence;

    public StatsPart(int attack, int spikeAttack, int explosiveAttack, int explosionRadius, int defence) {
        this.attack = attack;
        this.spikeAttack = spikeAttack;
        this.explosiveAttack = explosiveAttack;
        this.explosionRadius = explosionRadius;
        this.defence = defence;
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
}
