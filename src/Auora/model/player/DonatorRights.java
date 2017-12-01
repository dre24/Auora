package Auora.model.player;

public enum DonatorRights {

    PLAYER("Player", "<col=ffffff>", "<shad=0>", 50000000, 0),
    WEEKENDDONATOR("Premium", "<col=C61515>", "<shad=0>", 40000000, 0),
    PREMIUM("Premium", "<col=C61515>", "<shad=0>", 40000000, 5),
    EXTREME("Extreme", "<col=09A9F8>", "<shad=0>", 30000000, 6),
    LEGENDARY("Legendary", "<col=12b203>", "<shad=0>", 15000000, 7),
    SUPER("Extreme", "<col=12b203>", "<shad=0>", 15000000, 0),
    GODLIKE("Godlike", "<col=ffd700>", "<shad=0>", 0, 0),
    //SPONSOR("$ Sponsor $", "<col=ff751a>", "<shad=0>", 50000000, 0),
    //VIP("#1VIP", "<col=40ff00>", "<shad=208000>", 50000000, 0),
    YOUTUBER("YouTuber", "<col=EE0101>", "<shad=0>", 50000000, 0),
    TRUSTED("Trusted", "<col=cc00ff>", "<shad=0>", 50000000, 0);


    private String title;
    private String color;
    private String shad;
    private int titlePrice;
    private int clientCrownId;

    DonatorRights(String title, String color, String shad, int titlePrice, int clientCrownId) {
        this.title = title;
        this.color = color;
        this.shad = shad;
        this.titlePrice = titlePrice;
        this.clientCrownId = clientCrownId;
    }

    /**
     * Gets the rank for a certain id.
     *
     * @param id The id (ordinal()) of the rank.
     * @return rights.
     */
    public static DonatorRights forId(int id) {
        for (DonatorRights rights : DonatorRights.values()) {
            if (rights.ordinal() == id) {
                return rights;
            }
        }
        return null;
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

    public int getTitlePrice() {
        return titlePrice;
    }

    public int getClientCrownId() {
        return clientCrownId;
    }
}
