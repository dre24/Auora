package Auora.model.player;

import Auora.util.Misc;

import java.io.Serializable;

public class Appearence implements Serializable {

    private static final long serialVersionUID = 5497272754883262586L;

    private short npcType;
    private byte gender;
    private byte[] look;
    private byte[] colour;
    private boolean male;
    private int skullIcon = -1;
    private transient Player p;


    public Appearence() {
        if (gender < 0 && gender > 1) {
            gender = 1;
        }
        this.setMale(true);
        this.setNpcType((short) -1);
        this.resetAppearence();
    }

    public void resetAppearence() {
        if (gender < 0 && gender > 1) {
            gender = 1;
        }
        this.setLook(new byte[7]);
        this.setColour(new byte[5]);
        this.setMale(true);
        this.setLook(new byte[7]);
        this.setColour(new byte[5]);
        this.setMale(true);
        look[0] = 3; // Hair
        look[1] = (byte) (10 + Misc.random(7)); // Beard 10-17
        look[2] = (byte) (18 + Misc.random(7)); // Torso
        look[3] = (byte) (105 + Misc.random(5)); // Arms110 105-
        look[4] = 34; // Bracelets
        look[5] = (byte) (85 + Misc.random(4)); // Legs 89 85
        look[6] = 42; // Shoes
        colour[2] = 16;
        colour[1] = 16;
        gender = 0;
        for (int i = 0; i < 5; i++) {
            colour[4] = (byte) Misc.random(6);//skin colour 1 - white
            colour[2] = (byte) Misc.random(29);//3 - 29 max
            colour[1] = (byte) Misc.random(29);//14
            colour[0] = (byte) Misc.random(25);
        }
    }

    public void female() {
        if (gender < 0 && gender > 1) {
            gender = 1;
        }
        this.setLook(new byte[7]);
        this.setColour(new byte[5]);
        this.setMale(false);
        look[0] = 48; // Hair
        look[1] = 57; // Beard
        look[2] = 57; // Torso
        look[3] = 65; // Arms
        look[4] = 68; // Bracelets
        look[5] = 77; // Legs
        look[6] = 80; // Shoes
        colour[2] = 16;
        colour[1] = 16;
        for (int i = 0; i < 5; i++) {
            colour[2] = 16;
            colour[1] = 16;
            colour[0] = 3;
            gender = 1;
        }
    }

    public short getNpcType() {
        return npcType;
    }

    public void setNpcType(short npcType) {
        this.npcType = npcType;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public byte[] getLook() {
        return look;
    }

    public void setLook(byte[] look) {
        this.look = look;
    }

    public byte[] getColour() {
        return colour;
    }

    public void setColour(byte[] colour) {
        this.colour = colour;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    /**
     * @return the skullIcon
     */
    public int getSkullIcon() {
        return skullIcon;
    }

    /**
     * @param skullIcon the skullIcon to set
     */
    public void setSkullIcon(int skullIcon) {
        this.skullIcon = skullIcon;
    }

}
