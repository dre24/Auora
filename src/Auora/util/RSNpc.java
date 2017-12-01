package Auora.util;

public class RSNpc {

    private short id;
    private RSTile location;

    public RSNpc(int id, RSTile location) {
        this.setId(id);
        this.setLocation(location);
    }

    public short getId() {
        return id;
    }

    public void setId(int id) {
        this.id = (short) id;
    }

    public RSTile getLocation() {
        return location;
    }

    public void setLocation(RSTile location) {
        this.location = location;
    }
}
