package Auora.model;

/**
 * Handles player hits.
 *
 * @author Graham
 */
public class Hits {

    private Hit hit1;
    private Hit hit2;

    public Hits() {
        hit1 = null;
        hit2 = null;
    }

    public void setHit1(Hit hit) {
        this.hit1 = hit;
    }

    public void setHit2(Hit hit) {
        this.hit2 = hit;
    }

    public int getHitDamage1() {
        if (hit1 == null) {
            return 0;
        }
        return hit1.damage;
    }

    public int getHitDamage2() {
        if (hit2 == null) {
            return 0;
        }
        return hit2.damage;
    }

    public int getHitType1() {
        if (hit1 == null) {
            return HitType.NO_DAMAGE.getType();
        }
        return hit1.type.getType();
    }

    public int getHitType2() {
        if (hit2 == null) {
            return HitType.NO_DAMAGE.getType();
        }
        return hit2.type.getType();
    }

    public void clear() {
        hit1 = null;
        hit2 = null;
    }

    public enum HitType {
        NO_DAMAGE(1),            // blue
        NORMAL_DAMAGE(2),    //red
        NORMAL_BIG_DAMAGE(3),    // red
        POISON_DAMAGE(4),        // green
        DISEASE_DAMAGE(5),    // orange
        DUNGEON_DAMAGE(6);    // orange

        private final int type;

        HitType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }
    }

    public static class Hit {

        private HitType type;
        private int damage;
        private int delay;

        public Hit(int damage, HitType type) {
            this.type = type;
            this.damage = damage;
        }

        public HitType getType() {
            return type;
        }

        public int getDamage() {
            return damage;
        }
    }

    public static class Heal {

        private HitType type;
        private int damage;
        private int delay;

        public Heal(int damage, HitType type) {
            this.type = type;
            this.damage = damage;
        }

        public HitType getType() {
            return type;
        }

        public int getDamage() {
            return damage;
        }
    }

}
