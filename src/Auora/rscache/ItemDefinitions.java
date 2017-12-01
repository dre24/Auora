package Auora.rscache;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Auora.util.CacheConstants;

public class ItemDefinitions {
    public static final int SIZE = 30000;
    private static final ItemDefinitions[] definitions = new ItemDefinitions[SIZE];
    static int anInt2061;
    static int anInt2065;
    private static HashMap<Integer, ItemDefinitions> itemsDefs = new HashMap<Integer, ItemDefinitions>();
    public int equipId = -1;
    public String name;
    public int lendTemplateID;
    public int renderEmote;
    public int questId;
    public List<Integer> skillRequirimentId;
    public List<Integer> skillRequirimentLvl;
    public int[] bonus; // added
    int maleEquip2;
    int certID;
    String[] groundActions;
    int anInt2017;
    int[] anIntArray2018;
    int anInt2024;
    int anInt2026;
    String[] actions;
    int anInt2030;
    int modelZoom;
    int modelOffset2;
    int[] stackAmounts;
    int[] stackIDs;
    int modelOffset1;
    int lendID;
    int modelRotation1;
    int maleEquip1;
    int anInt2049;
    boolean membersObject;
    int anInt2054;
    int anInt2055;
    int stackable;
    int anInt2059;
    int anInt2063;
    int value;
    int modelRotation2;
    int anInt2077;
    int certTemplateID;
    int team;
    Class aClass14_2082;
    Class aClass82_2083;
    boolean aBoolean2086;
    int anInt2089;
    int[] opcodes2;
    Object[] values2;
    private int id;
    private boolean noted;
    private int anInt2008;
    private int anInt2009;
    private int anInt2011 = -1;
    private int anInt2014;
    private short[] originalModelColors;
    private int anInt2016;
    private int modelID;
    private int anInt2025;
    private int femaleEquip1;
    private int anInt2028 = -1;
    private int anInt2031;
    private int anInt2035;
    private int anInt2043;
    private int femaleEquip2;
    private byte[] aByteArray2047;
    private int anInt2050;
    private int anInt2051;
    private int anInt2056;
    private short[] aShortArray2060;
    private int anInt2067;
    private int anInt2071;
    private short[] aShortArray2075;
    private short[] modifiedModelColors;
    private int anInt2080;
    private int anInt2084;
    private int price;

    private ItemDefinitions() {
        bonus = new int[15];
        anInt2025 = -1;
        anInt2043 = -1;
        anInt2026 = 0;
        maleEquip1 = -1;
        modelOffset2 = 0;
        anInt2030 = -1;
        femaleEquip1 = -1;
        anInt2031 = 0;
        modelZoom = 2000;
        lendID = -1;
        anInt2008 = -1;
        anInt2009 = 128;
        anInt2055 = -1;
        anInt2051 = 0;
        name = "null";
        modelOffset1 = 0;
        modelRotation1 = 0;
        anInt2049 = -1;
        anInt2056 = 128;
        stackable = 0;
        membersObject = false;
        anInt2054 = -1;
        value = 1;
        maleEquip2 = -1;
        anInt2050 = 128;
        femaleEquip2 = -1;
        anInt2035 = 0;
        anInt2063 = -1;
        certID = -1;
        lendTemplateID = -1;
        anInt2059 = -1;
        anInt2014 = 0;
        anInt2017 = -1;
        anInt2077 = -1;
        anInt2016 = 0;
        anInt2080 = 0;
        certTemplateID = -1;
        modelRotation2 = 0;
        anInt2084 = 0;
        aBoolean2086 = false;
        anInt2067 = -1;
        anInt2071 = 0;
        team = 0;
        anInt2089 = 0;
    }

    public static ItemDefinitions forName(String name) {
        for (ItemDefinitions definition : definitions) {
            if (definition.name.equalsIgnoreCase(name)) {
                return definition;
            }
        }
        return null;
    }

    private static int getContainerId(int id) {
        return id >>> 8;
    }

    public static ItemDefinitions forID(int id) {
    	if (id == -1){
    		return null;
    	}
        ItemDefinitions itemDef = itemsDefs.get(id);
        if (itemDef != null)
            return itemDef;
        byte[] is = null;
        try {
            is = (CacheManager.getData(CacheConstants.ITEMDEF_IDX_ID,
                    getContainerId(id), 0xff & id));
        } catch (Exception e) {
            System.out.println("Could not grab item " + id);
            return null;
        }
        itemDef = new ItemDefinitions();
        itemDef.groundActions = new String[]{null, null, "take", null, null};
        itemDef.actions = new String[]{null, null, null, null, "drop"};
        itemDef.id = id;
        if (is != null) {
            try {
                itemDef.getOpCodeLoop(new RSInputStream(
                        new ByteArrayInputStream(is)), is.length);
            } catch (IOException e) {
                System.out.println("Could not load item " + id);
                return null;
            }
        }
        if (itemDef.certTemplateID != -1)
            itemDef.toNote(forID(itemDef.certTemplateID), forID(itemDef.certID));
        if (itemDef.lendTemplateID != -1)
            itemDef.toLend(forID(itemDef.lendTemplateID), forID(itemDef.lendID));
        itemDef.loadItemPart2();
        try {
            itemDef.loadExtraInformation();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        itemsDefs.put(id, itemDef);
        return itemDef;

    }

    public static void packItemPart2() {
        for (int i = 0; i < Cache.getAmountOfItems(); i++) {
            try {
                final File item = new File("./Data/Items/bonus/" + i + ".txt");
                if (!item.exists()) {
                    if (i == 18353) {
                        System.out.println("Something bad fucked up.");
                    }
                    continue;
                } else {
                    BufferedReader in = new BufferedReader(new FileReader(item));
                    Cache.getItemDefinitionsinitionFile2().seek(31 * i);
                    for (int i2 = 0; i2 < 15; i2++) { // writes bonus and speed
                        try {
                            Cache.getItemDefinitionsinitionFile2().writeShort(
                                    Integer.parseInt(in.readLine()));
                        } catch (NumberFormatException e) {
                            System.out.println("Problem in id "+i);
                            e.printStackTrace();
                        }
                    }
                    try {
                        Cache.getItemDefinitionsinitionFile2().writeByte(
                                Integer.parseInt(in.readLine()));
                    } catch (NumberFormatException e) {
                        System.out.println("Problem in id "+i);
                        e.printStackTrace();
                    }
                }
            } catch(IOException e){
                System.out.println("Problem in id "+i);
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isStackable() {
        return stackable == 1;
    }

    public boolean isNoted() {
        return noted;
    }

    public void loadExtraInformation() throws NumberFormatException, IOException {
        if (values2 == null || opcodes2 == null)
            return;
        int renderEmote = 0;
        int questId = 0;
        List<Integer> skillRequirimentLevelNoId = null;
        List<Integer> skillRequirimentId = null;
        List<Integer> skillRequirimentLvl = null;
        boolean isLvl = false;
        for (int index = 0; index < opcodes2.length; index++) {
            if (values2[index] instanceof Integer) {
                int opcode = opcodes2[index];
                int value = (Integer) values2[index];
                if (opcode == 23) {
                    if (skillRequirimentLevelNoId == null)
                        skillRequirimentLevelNoId = new ArrayList<Integer>();
                    skillRequirimentLevelNoId.add(value);
                }
                if (opcode == 644)
                    renderEmote = value;
                else if (opcode == 743)
                    questId = value;
                else if (opcode >= 749) {
                    if (skillRequirimentId == null) {
                        skillRequirimentId = new ArrayList<Integer>();
                        skillRequirimentLvl = new ArrayList<Integer>();
                    }
                    if (isLvl)
                        skillRequirimentLvl.add(value);
                    else
                        skillRequirimentId.add(value);
                    isLvl = !isLvl;
                }
            }
        }
        this.renderEmote = renderEmote;
        this.questId = questId;
        this.skillRequirimentId = skillRequirimentId;
        this.skillRequirimentLvl = skillRequirimentLvl;
        opcodes2 = null;
        values2 = null;
    }

    public int retrievePrice(int itemId) throws NumberFormatException, IOException {
        BufferedReader reader = null;
        String string;

        reader = new BufferedReader(new FileReader("data/ge/Prices.txt"));
        while ((string = reader.readLine()) != null) {
            String[] data = string.split(":");
            itemId = Integer.parseInt(data[0]);
            int price = Integer.parseInt(data[1]);
            return price;
        }
        reader.close();
        return 0;
    }

    private void setPrice(int price) {
        this.price = price;

    }

    public boolean isWearItem() {
        if (actions == null) return false;
        for (String action : actions) {
            if (action == null) continue;
            if (action.equals("Wield") || action.equals("Wear") || action.equals("Ride")) return true;
        }
        return false;
    }

    final void toNote(ItemDefinitions class138_6_, ItemDefinitions class138_7_) {
        aShortArray2075 = class138_6_.aShortArray2075;
        (this).modelRotation2 = (class138_6_).modelRotation2;
        (this).modelOffset2 = (class138_6_).modelOffset2;
        (this).modelOffset1 = (class138_6_).modelOffset1;
        (this).membersObject = (class138_7_).membersObject;
        (this).modelZoom = (class138_6_).modelZoom;
        modifiedModelColors = class138_6_.modifiedModelColors;
        (this).value = (class138_7_).value;
        aShortArray2060 = class138_6_.aShortArray2060;
        modelID = class138_6_.modelID;
        (this).name = (class138_7_).name;
        (this).stackable = 1;
        (this).anInt2026 = (class138_6_).anInt2026;
        aByteArray2047 = class138_6_.aByteArray2047;
        (this).modelRotation1 = (class138_6_).modelRotation1;
        originalModelColors = class138_6_.originalModelColors;
        noted = true;
    }

    final void toLend(ItemDefinitions class138_32_, ItemDefinitions class138_31_) {
        anInt2067 = class138_31_.anInt2067;
        originalModelColors = class138_31_.originalModelColors;
        anInt2011 = class138_31_.anInt2011;
        anInt2080 = class138_31_.anInt2080;
        (this).maleEquip2 = (class138_31_).maleEquip2;
        (this).aClass82_2083 = (class138_31_).aClass82_2083;
        anInt2025 = class138_31_.anInt2025;
        (this).actions = new String[5];
        anInt2043 = class138_31_.anInt2043;
        (this).modelOffset1 = (class138_32_).modelOffset1;
        (this).modelRotation2 = (class138_32_).modelRotation2;
        anInt2031 = class138_31_.anInt2031;
        (this).groundActions = (class138_31_).groundActions;
        anInt2016 = class138_31_.anInt2016;
        (this).maleEquip1 = (class138_31_).maleEquip1;
        (this).modelOffset2 = (class138_32_).modelOffset2;
        (this).name = (class138_31_).name;
        anInt2071 = class138_31_.anInt2071;
        (this).modelZoom = (class138_32_).modelZoom;
        aShortArray2075 = class138_31_.aShortArray2075;
        modelID = class138_32_.modelID;
        aByteArray2047 = class138_31_.aByteArray2047;
        femaleEquip1 = class138_31_.femaleEquip1;
        (this).value = 0;
        anInt2035 = class138_31_.anInt2035;
        aShortArray2060 = class138_31_.aShortArray2060;
        (this).membersObject = (class138_31_).membersObject;
        anInt2028 = class138_31_.anInt2028;
        (this).modelRotation1 = (class138_32_).modelRotation1;
        (this).anInt2026 = (class138_32_).anInt2026;
        femaleEquip2 = class138_31_.femaleEquip2;
        anInt2008 = class138_31_.anInt2008;
        modifiedModelColors = class138_31_.modifiedModelColors;
        (this).team = (class138_31_).team;
        anInt2084 = class138_31_.anInt2084;
        if ((class138_31_).actions != null) {
            for (int i_33_ = 0; i_33_ < 4; i_33_++)
                (this).actions[i_33_] = (class138_31_).actions[i_33_];
        }
        (this).actions[4] = "Discard";
    }

    final void getOpCodeLoop(RSInputStream stream, int length)
            throws IOException {
        for (; ; ) {
            int opcode = stream.readUnsignedByte();
            if (opcode == 0)
                break;
            readValues(stream, opcode);
        }
    }

    private final void readValues(RSInputStream stream, int opcode)
            throws IOException {
        if (opcode != 1) {
            if (opcode != 2) {
                if (opcode == 4)
                    modelZoom = stream.readUnsignedShort();
                else if (opcode != 5) {
                    if (opcode != 6) {
                        if (opcode == 7) {
                            modelOffset1 = stream.readUnsignedShort();
                            if (modelOffset1 > 32767)
                                modelOffset1 -= 65536;
                            modelOffset1 <<= 0;
                        } else if (opcode != 8) {
                            if (opcode == 11)
                                stackable = 1;
                            else if (opcode == 12)
                                value = stream.readInt();
                            else if (opcode == 16)
                                membersObject = true;
                            else if (opcode == 23)
                                maleEquip1 = stream.readUnsignedShort();
                            else if (opcode != 24) {
                                if (opcode == 25)
                                    maleEquip2 = stream.readUnsignedShort();
                                else if (opcode != 26) {
                                    if (opcode < 30 || opcode >= 35) {
                                        if (opcode < 35 || opcode >= 40) {
                                            if (opcode == 40) {
                                                int j = (stream
                                                        .readUnsignedByte());
                                                originalModelColors = new short[j];
                                                modifiedModelColors = new short[j];
                                                for (int k = 0; j > k; k++) {
                                                    originalModelColors[k] = (short) (stream
                                                            .readUnsignedShort());
                                                    modifiedModelColors[k] = (short) (stream
                                                            .readUnsignedShort());
                                                }
                                            } else if (opcode == 41) {
                                                int i_57_ = (stream
                                                        .readUnsignedByte());
                                                aShortArray2060 = new short[i_57_];
                                                aShortArray2075 = new short[i_57_];
                                                for (int i_58_ = 0; i_57_ > i_58_; i_58_++) {
                                                    aShortArray2060[i_58_] = (short) (stream
                                                            .readUnsignedShort());
                                                    aShortArray2075[i_58_] = (short) (stream
                                                            .readUnsignedShort());
                                                }
                                            } else if (opcode == 42) {
                                                int i_59_ = (stream
                                                        .readUnsignedByte());
                                                aByteArray2047 = new byte[i_59_];
                                                for (int i_60_ = 0; i_60_ < i_59_; i_60_++)
                                                    aByteArray2047[i_60_] = stream
                                                            .readByte();
                                            } else if (opcode == 65)
                                                aBoolean2086 = true;
                                            else if (opcode != 78) {
                                                if (opcode != 79) {
                                                    if (opcode == 90)
                                                        anInt2043 = (stream
                                                                .readUnsignedShort());
                                                    else if (opcode != 91) {
                                                        if (opcode == 92)
                                                            anInt2067 = (stream
                                                                    .readUnsignedShort());
                                                        else if (opcode == 93)
                                                            anInt2025 = (stream
                                                                    .readUnsignedShort());
                                                        else if (opcode != 95) {
                                                            if (opcode != 96) {
                                                                if (opcode != 97) {
                                                                    if (opcode != 98) {
                                                                        if ((opcode >= 100)
                                                                                && opcode < 110) {
                                                                            if (stackIDs == null) {
                                                                                stackAmounts = new int[10];
                                                                                stackIDs = new int[10];
                                                                            }
                                                                            stackIDs[-100
                                                                                    + opcode] = stream
                                                                                    .readUnsignedShort();
                                                                            stackAmounts[-100
                                                                                    + opcode] = stream
                                                                                    .readUnsignedShort();
                                                                        } else if (opcode != 110) {
                                                                            if (opcode != 111) {
                                                                                if (opcode != 112) {
                                                                                    if (opcode != 113) {
                                                                                        if (opcode != 114) {
                                                                                            if (opcode != 115) {
                                                                                                if (opcode == 121)
                                                                                                    lendID = stream
                                                                                                            .readUnsignedShort();
                                                                                                else if (opcode == 122)
                                                                                                    lendTemplateID = stream
                                                                                                            .readUnsignedShort();
                                                                                                else if (opcode == 125) {
                                                                                                    anInt2084 = stream
                                                                                                            .readByte() << 0;
                                                                                                    anInt2031 = stream
                                                                                                            .readByte() << 0;
                                                                                                    anInt2035 = stream
                                                                                                            .readByte() << 0;
                                                                                                } else if (opcode != 126) {
                                                                                                    if (opcode == 127) {
                                                                                                        anInt2054 = stream
                                                                                                                .readUnsignedByte();
                                                                                                        anInt2030 = stream
                                                                                                                .readUnsignedShort();
                                                                                                    } else if (opcode == 128) {
                                                                                                        anInt2077 = stream
                                                                                                                .readUnsignedByte();
                                                                                                        anInt2017 = stream
                                                                                                                .readUnsignedShort();
                                                                                                    } else if (opcode == 129) {
                                                                                                        anInt2063 = stream
                                                                                                                .readUnsignedByte();
                                                                                                        anInt2059 = stream
                                                                                                                .readUnsignedShort();
                                                                                                    } else if (opcode == 130) {
                                                                                                        anInt2055 = stream
                                                                                                                .readUnsignedByte();
                                                                                                        anInt2049 = stream
                                                                                                                .readUnsignedShort();
                                                                                                    } else if (opcode != 132) {
                                                                                                        if (opcode == 249) {
                                                                                                            int length = stream
                                                                                                                    .readUnsignedByte();
                                                                                                            opcodes2 = new int[length];
                                                                                                            values2 = new Object[length];
                                                                                                            for (int index = 0; index < length; index++) {
                                                                                                                boolean bool = stream
                                                                                                                        .readUnsignedByte() == 1;
                                                                                                                opcodes2[index] = stream
                                                                                                                        .read24BitInt();
                                                                                                                if (bool) {
                                                                                                                    values2[index] = stream
                                                                                                                            .readCString();
                                                                                                                } else {
                                                                                                                    values2[index] = stream
                                                                                                                            .readInt();
                                                                                                                }

                                                                                                            }
                                                                                                            /*
                                                                                                             * int
																											 * i_61_
																											 * =
																											 * stream
																											 * .
																											 * readUnsignedByte
																											 * (
																											 * )
																											 * ;
																											 * if
																											 * (
																											 * aClass82_2083
																											 * ==
																											 * null
																											 * )
																											 * {
																											 * int
																											 * i_62_
																											 * =
																											 * 0
																											 * ;
																											 * /
																											 * /
																											 * Class237
																											 * .
																											 * method2639
																											 * (
																											 * (
																											 * byte
																											 * )
																											 * /
																											 * /
																											 * 66
																											 * ,
																											 * /
																											 * /
																											 * i_61_
																											 * )
																											 * ;
																											 * aClass82_2083
																											 * =
																											 * null
																											 * ;
																											 * /
																											 * /
																											 * =
																											 * /
																											 * /
																											 * new
																											 * /
																											 * /
																											 * Class82
																											 * (
																											 * i_62_
																											 * )
																											 * ;
																											 * }
																											 * for
																											 * (
																											 * int
																											 * i_63_
																											 * =
																											 * 0
																											 * ;
																											 * i_63_
																											 * <
																											 * i_61_
																											 * ;
																											 * i_63_
																											 * ++
																											 * )
																											 * {
																											 * boolean
																											 * bool
																											 * =
																											 * stream
																											 * .
																											 * readUnsignedByte
																											 * (
																											 * )
																											 * ==
																											 * 1
																											 * ;
																											 * int
																											 * i_64_
																											 * =
																											 * stream
																											 * .
																											 * read24BitInt
																											 * (
																											 * )
																											 * ;
																											 * /
																											 * *
																											 * if
																											 * (
																											 * id
																											 * ==
																											 * 11694
																											 * )
																											 * {
																											 * System
																											 * .
																											 * out
																											 * .
																											 * println
																											 * (
																											 * i_64_
																											 * )
																											 * ;
																											 * }
																											 */
                                                                                                            // Class
                                                                                                            // class28;
																											/*
																											 * if
																											 * (
																											 * bool
																											 * )
																											 * {
																											 * class28
																											 * =
																											 * null
																											 * ;
																											 * /
																											 * *
																											 * =
																											 * new
																											 * Class28_Sub7
																											 * (
																											 * stream
																											 * .
																											 * readString
																											 * (
																											 * (
																											 * byte
																											 * )
																											 * 28
																											 * )
																											 * )
																											 * ;
																											 */
																											/*
																											 * stream
																											 * .
																											 * readCString
																											 * (
																											 * )
																											 * ;
																											 */
																											/*
																											 * }
																											 * else
																											 * {
																											 * class28
																											 * =
																											 * null
																											 * ;
																											 * /
																											 * *
																											 * =
																											 * new
																											 * Class28_Sub42
																											 * (
																											 * stream
																											 * .
																											 * readInt
																											 * (
																											 * 116
																											 * )
																											 * )
																											 * ;
																											 */
																											/*
																											 * stream
																											 * .
																											 * readInt
																											 * (
																											 * )
																											 * ;
																											 */
																											/*
																											 * }
																											 * /
																											 * *
																											 * aClass82_2083
																											 * .
																											 * method1313
																											 * (
																											 * (
																											 * long
																											 * )
																											 * i_64_
																											 * ,
																											 * class28
																											 * ,
																											 * (
																											 * byte
																											 * )
																											 * -
																											 * 126
																											 * )
																											 * ;
																											 */
																											/* } */
                                                                                                        }
                                                                                                    } else {
                                                                                                        int i_65_ = stream
                                                                                                                .readUnsignedByte();
                                                                                                        anIntArray2018 = new int[i_65_];
                                                                                                        for (int i_66_ = 0; i_66_ < i_65_; i_66_++)
                                                                                                            anIntArray2018[i_66_] = stream
                                                                                                                    .readUnsignedShort();
                                                                                                    }
                                                                                                } else {
                                                                                                    anInt2071 = stream
                                                                                                            .readByte() << 0;
                                                                                                    anInt2016 = stream
                                                                                                            .readByte() << 0;
                                                                                                    anInt2080 = stream
                                                                                                            .readByte() << 0;
                                                                                                }
                                                                                            } else
                                                                                                team = stream
                                                                                                        .readUnsignedByte();
                                                                                        } else
                                                                                            anInt2014 = stream
                                                                                                    .readByte() * 5;
                                                                                    } else
                                                                                        anInt2051 = stream
                                                                                                .readByte();
                                                                                } else
                                                                                    anInt2009 = stream
                                                                                            .readUnsignedShort();
                                                                            } else
                                                                                anInt2050 = stream
                                                                                        .readUnsignedShort();
                                                                        } else
                                                                            anInt2056 = stream
                                                                                    .readUnsignedShort();
                                                                    } else
                                                                        certTemplateID = stream
                                                                                .readUnsignedShort();
                                                                } else
                                                                    certID = (stream
                                                                            .readUnsignedShort());
                                                            } else
                                                                anInt2089 = (stream
                                                                        .readUnsignedByte());
                                                        } else
                                                            anInt2026 = (stream
                                                                    .readUnsignedShort());
                                                    } else
                                                        anInt2028 = (stream
                                                                .readUnsignedShort());
                                                } else
                                                    anInt2008 = stream
                                                            .readUnsignedShort();
                                            } else
                                                anInt2011 = stream
                                                        .readUnsignedShort();
                                        } else
                                            actions[opcode - 35] = stream
                                                    .readCString();
                                    } else
                                        groundActions[-30 + opcode] = stream
                                                .readCString();
                                } else
                                    femaleEquip2 = stream.readUnsignedShort();
                            } else
                                femaleEquip1 = stream.readUnsignedShort();
                        } else {
                            modelOffset2 = stream.readUnsignedShort();
                            if (modelOffset2 > 32767)
                                modelOffset2 -= 65536;
                            modelOffset2 <<= 0;
                        }
                    } else
                        modelRotation2 = stream.readUnsignedShort();
                } else
                    modelRotation1 = stream.readUnsignedShort();
            } else
                name = stream.readCString();
            if (id == 12610 || id == 12611) {
                name = "Black partyhat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4315 || id == 4316) {
                name = "Attack master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4317 || id == 4318) {
                name = "Defence master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4319 || id == 4320) {
                name = "Strength master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4321 || id == 4322) {
                name = "Const. master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4323 || id == 4324) {
                name = "Ranging master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4325 || id == 4326) {
                name = "Prayer master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4327 || id == 4328) {
                name = "Magic master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4329 || id == 4330) {
                name = "Cooking master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4331 || id == 4332) {
                name = "Woodcut. master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4333 || id == 4334) {
                name = "Fletching master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4335 || id == 4336) {
                name = "Fishing master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4337 || id == 4338) {
                name = "Firemaking master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4339 || id == 4340) {
                name = "Crafting master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4341 || id == 4342) {
                name = "Smithing master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4343 || id == 4344) {
                name = "Mining master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4345 || id == 4346) {
                name = "Herblore master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4347 || id == 4348) {
                name = "Agility master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4349 || id == 4350) {
                name = "Thieving master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4351 || id == 4352) {
                name = "Slayer master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4353 || id == 4354) {
                name = "Farming master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4355 || id == 4356) {
                name = "Runecraft. master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4357 || id == 4358) {
                name = "Constr. master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4358 || id == 4359) {
                name = "Hunter master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 4360 || id == 4361) {
                name = "Summoning master cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 12183) {
                name = "Gold shards";
            }
            if (id == 5680 || id == 5681) {
                name = "Abyssal dagger";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17035) {
                name = "Abyssal bludgeon";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 15441) {
                name = "Abyssal tentacle";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 1191 || id == 1192) {
                name = "Bloodbone spirit shield";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 12612 || id == 12613) {
                name = "Pink partyhat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 16347 || id == 16348) {
                name = "Steadfast boots";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17317 || id == 17318) {
                name = "Ragefire boots";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 16353 || id == 16354) {
                name = "Glaiven boots";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 12620 || id == 12621) {
                name = "White santa hat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 12618 || id == 12619) {
                name = "Black santa hat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 10944) {
                name = "Vote Token";
            }
            if (id == 7629) {
                name = "Dicer Token";
            }
            if (id == 7774) {
                name = "Godlike Token";
            }
            if (id == 7775) {
                name = "Regular Token";
            }
            if (id == 7776) {
                name = "Super Token";
            }
			if (id == 10943) {
                name = "Extreme Token";
            }
            if (id == 7142) {
                name = "Admin Rapier";
            }
            if (id == 12616 || id == 12617) {
                name = "Aqua partyhat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if(id == 16174) {
                name = "Elder maul";
                actions = new String[5];
                actions[1] = "Wield";
                actions[4] = "Drop";
            }
            if(id == 16873) {
                name = "Twisted bow";
                actions = new String[5];
                actions[1] = "Wield";
                actions[4] = "Drop";
            }
            if(id == 10156) {
                name = "Dragon hunter crossbow";
                actions = new String[5];
                actions[1] = "Wield";
                actions[4] = "Drop";
            }
            if(id == 16753) {
                name = "Ancestral hat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if(id == 16754) {
                name = "Ancestral hat";
            }
            if(id == 17235) {
                name = "Ancestral robe top";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if(id == 17236) {
                name = "Ancestral robe top";
            }
            if(id == 16863) {
                name = "Ancestral robe bottom";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if(id == 16864) {
                name = "Ancestral robe bottom";
            }
            if(id == 6910) {
                name = "Kodai wand";
                actions = new String[5];
                actions[1] = "Wield";
                actions[4] = "Drop";
            }
            if(id == 14596) {
                name = "Amulet of torture";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if(id == 17349) {
                name = "Dragon kiteshield";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if(id == 18691) {
                name = "Twisted buckler";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 16691 || id == 16692 || id == 16693 || id == 16694 || id == 16695 || id == 16696) {
                name = "Partyhat & specs";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 12614 || id == 12615) {
                name = "Lava partyhat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17161 || id == 17162) {
                name = "Lime santa hat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17163 || id == 17164) {
                name = "Pink santa hat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17165 || id == 17166) {
                name = "Lava santa hat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17167 || id == 17168) {
                name = "Aqua santa hat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17169 || id == 17170) {
                name = "Purple santa hat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17171 || id == 17172) {
                name = "Yellow santa hat";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17173 || id == 17174) {
                name = "Yellow h'ween mask";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17175 || id == 17176) {
                name = "Purple h'ween mask";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17177 || id == 17178) {
                name = "Aqua h'ween mask";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17179 || id == 17180) {
                name = "Lava h'ween mask";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17181 || id == 17182) {
                name = "Pink h'ween mask";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17183 || id == 17184) {
                name = "Lime h'ween mask";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 17185 || id == 17186) {
                name = "White h'ween mask";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";

            }
            if (id == 17187 || id == 17188) {
                name = "Black h'ween mask";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 9177 || id == 9178) {
                name = "Armadyl crossbow";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 10663) {
                name = "Max cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[2] = "Customize";
                actions[4] = "Drop";
            }
            if (id == 10664) {
                name = "Fire max cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 10665) {
                name = "Ava's max cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 10666) {
                name = "Saradomin max cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 10667) {
                name = "Guthix max cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 10668) {
                name = "Zamorak max cape";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 10669) {
                name = "Max hood";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 10670) {
                name = "Fire max hood";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 10671) {
                name = "Ava's max hood";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 10672) {
                name = "Saradomin max hood";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 10673) {
                name = "Guthix max hood";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 10674) {
                name = "Zamorak max hood";
                actions = new String[5];
                actions[1] = "Wear";
                actions[4] = "Drop";
            }
            if (id == 18768) {
                name = "Random PartyHat Box";
            }
            if (id == 6183) {
                name = "Random SantaHat Box";
            }
            if (id == 14664) {
                name = "Random Ween Box";
            } 
        } else
            modelID = stream.readUnsignedShort();
    }

    public void loadItemPart2() {
        try {
            if (Cache.getItemDefinitionsinitionFile2().length() <= (31 * id)) {
                return;
            }
            Cache.getItemDefinitionsinitionFile2().seek(31 * id);
            for (int i2 = 0; i2 < 15; i2++) {
                bonus[i2] = Cache.getItemDefinitionsinitionFile2().readShort();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    public int getPrice(int itemId) {
        try (BufferedReader buf = new BufferedReader(new FileReader("./Data/shop_prices.txt"))) {
            String line;
            while ((line = buf.readLine()) != null) {

                String[] regex = line.split(",");
                if (line.startsWith(String.valueOf("ItemID: " + itemId + ","))) {
                    int itemValue = Integer.valueOf(regex[1].replace(" Value: ", ""));
                    return itemValue;
                }
            }
            buf.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}
