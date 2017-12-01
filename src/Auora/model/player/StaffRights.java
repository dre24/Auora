package Auora.model.player;

public enum StaffRights {

    PLAYER("Player", "", "", -1, 0),
    HIDDEN("Trusted", "<col=cc00ff>", "<shad=0>", -1, 0),
    SUPPORT("Server Support", "<col=2554C7>", "<shad=000000>", 3, 8),
    MODERATOR("Moderator", "<col=d6aeea>", "<shad=000000>", 0, 1),
    FORUM_MOD("Forum Moderator", "<col=b670d9>", "<shad=000000>", 0, 1),
    GLOBAL_MOD("Global Mod", "<col=882fb5>", "<shad=000000>", 0, 1),
    FORUM_ADMIN("Forum Admin", "<col=690909>", "<shad=000000>", 1, 2),
    ADMINISTRATOR("Administrator", "<col=EE5C42>", "<shad=000000>", 1, 2),
    GLOBAL_ADMIN("Global Admin", "<col=1138cc>", "<shad=000000>", 1, 2),
    DEVELOPER("Developer", "<col=484192>", "<shad=000000>", 1, 2),
    WEBSITE_DEVELOPER("Website Developer", "<col=A0A0A0>", "<shad=000000>", 1, 2),
    COMMUNITY_MANAGER("Community Manager", "<col=FF8C00>", "<shad=000000>", 1, 2),
    STAFF_MANAGER("Staff Manager", "<col=793e5b>", "<shad=000000>", 1, 2),
    OWNER("Owner", "<col=FF9933>", "<shad=000000>", 1, 2);

    private String title;
    private String color;
    private String shad;
    private int crownId;
    private int clientCrownId;

    StaffRights(String title, String color, String shad, int crownId, int clientCrownId) {
        this.title = title;
        this.color = color;
        this.shad = shad;
        this.crownId = crownId;
        this.clientCrownId = clientCrownId;
    }

    /**
     * Gets the rank for a certain id.
     *
     * @param id The id (ordinal()) of the rank.
     * @return rights.
     */
    public static StaffRights forId(int id) {
        for (StaffRights rights : StaffRights.values()) {
            if (rights.ordinal() == id) {
                return rights;
            }
        }
        return null;
    }

    /**
     * Gets the crown ID for a certain rank
     *
     * @return
     */
    public int getCrownId() {
        return this.crownId;
    }

    /**
     * Gets the crown client ID for a certain rank
     *
     * @return
     */
    public int getClientCrownId() {
        return this.clientCrownId;
    }

    /**
     * Gets the color code for a certain rank
     *
     * @return
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Gets the color shad code for a certain rank
     *
     * @return
     */
    public String getShad() {
        return this.shad;
    }

    /**
     * Gets the rank title for a certain rank
     *
     * @return
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Tells you if your opponent is a higher rank than you
     *
     * @return
     */
    public boolean isHigherRank(StaffRights otherRights) {
        if (this.ordinal() < otherRights.ordinal()) {
            return true;
        }
        return false;
    }
}
