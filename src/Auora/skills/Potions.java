package Auora.skills;

public enum Potions {

    OVERLOAD(15332, 15333, 15334, 15335),
    SUPER_ATTACK(2436, 145, 147, 149),
    SUPER_STRENGTH(2440, 157, 159, 161),
    SUPER_DEFENCE(2442, 163, 165, 167),
    RECOVER_SPECIAL(15300, 15301, 15302, 15303),
    SUPER_RESTORE(3024, 3026, 3028, 3030);

    private int quarterId, halfId, threeQuartersId, fullId;

    Potions(int fullId, int threeQuartersId, int halfId, int quarterId) {
        this.quarterId = quarterId;
        this.halfId = halfId;
        this.threeQuartersId = threeQuartersId;
        this.fullId = fullId;
    }

    public int getQuarterId() {
        return this.quarterId;
    }

    public int getHalfId() {
        return this.halfId;
    }

    public int getThreeQuartersId() {
        return this.threeQuartersId;
    }

    public int getFullId() {
        return this.fullId;
    }


}