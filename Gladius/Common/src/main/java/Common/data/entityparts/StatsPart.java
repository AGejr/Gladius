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

    public StatsPart(int attack, int defence) {
        this.attack = attack;
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
}
