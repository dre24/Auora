package Auora.rscache;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Auora.util.CacheConstants;

/**
 * @author dre
 */
public class ObjectDefinitions {

    private static HashMap<Integer, ObjectDefinitions> objDefs = new HashMap<Integer, ObjectDefinitions>();

    private short[] aShortArray3830;
    int[] anIntArray3831;
    static int anInt3832;
    int[] anIntArray3833 = null;
    private int anInt3834;
    int anInt3835;
    static int anInt3836;
    private byte aByte3837;
    int anInt3838 = -1;
    boolean aBoolean3839;
    private int anInt3840;
    private int anInt3841;
    static int anInt3842;
    static int anInt3843;
    int anInt3844;
    boolean aBoolean3845;
    static int anInt3846;
    private byte aByte3847;
    static int anInt3848;
    private byte aByte3849;
    int anInt3850;
    int anInt3851;
    boolean aBoolean3852;
    boolean aBoolean3853;
    static int anInt3854;
    int anInt3855;
    boolean ignoreClipOnAlternativeRoute;
    int anInt3857;
    private byte[] aByteArray3858;
    int[] anIntArray3859;
    int anInt3860;
    public String[] actions;
    static int anInt3862;
    private int anInt3863;
    private short[] aShortArray3864;
    int anInt3865;
    boolean aBoolean3866;
    boolean aBoolean3867;
    boolean projectileCliped;
    private int[] anIntArray3869;
    boolean aBoolean3870;
    int sizeY;
    boolean aBoolean3872;
    boolean aBoolean3873;
    int anInt3874;
    private int anInt3875;
    int anInt3876;
    private int anInt3877;
    private int anInt3878;
    int clipType;
    private int anInt3881;
    private int anInt3882;
    private int anInt3883;
    static int anInt3884;
    static int anInt3886;
    static int anInt3887;
    static int anInt3888;
    private int anInt3889;
    int sizeX;
    boolean aBoolean3891;
    int anInt3892;
    int anInt3893;
    boolean aBoolean3894;
    boolean aBoolean3895;
    private int anInt3897;
    static int anInt3898;
    private byte[] aByteArray3899;
    int anInt3900;
    public String name;
    private int anInt3902;
    static int anInt3903;
    int anInt3904;
    int anInt3905;
    boolean aBoolean3906;
    static int anInt3907;
    int[] anIntArray3908;
    static int anInt3910;
    static int anInt3911;
    private byte aByte3912;
    int anInt3913;
    private byte aByte3914;
    private int anInt3915;
    private int[][] anIntArrayArray3916;
    private int anInt3917;
    static int anInt3918;
    private short[] aShortArray3919;
    private short[] aShortArray3920;
    int anInt3921;
    private Map aClass194_3922;
    boolean aBoolean3923;
    boolean aBoolean3924;
    int cflag;
  
    public int id;


    private void readValues(int i, RSInputStream stream, int opcode) throws IOException {
		if (opcode != 1 && opcode != 5) {
			if (opcode != 2) {
				if (opcode != 14) {
					if (opcode != 15) {
						if (opcode == 17) {
							projectileCliped = false;
							clipType = 0;
						} else if (opcode != 18) {
							if (opcode == 19)
								anInt3893 = stream.readUnsignedByte();
							else if (opcode == 21)
								aByte3912 = (byte) 1;
							else if (opcode != 22) {
								if (opcode != 23) {
									if (opcode != 24) {
										if (opcode == 27)
											clipType = 1;
										else if (opcode == 28)
											anInt3892 = (stream
													.readUnsignedByte() << 2);
										else if (opcode != 29) {
											if (opcode != 39) {
												if (opcode < 30 || opcode >= 35) {
													if (opcode == 40) {
														int i_53_ = (stream.readUnsignedByte());
														aShortArray3830 = new short[i_53_];
														aShortArray3864 = new short[i_53_];
														for (int i_54_ = 0; i_53_ > i_54_; i_54_++) {
															aShortArray3830[i_54_] = (short) (stream
																	.readUnsignedShort());
															aShortArray3864[i_54_] = (short) (stream
																	.readUnsignedShort());
														}
													} else if (opcode != 41) {
														if (opcode != 42) {
															if (opcode != 62) {
																if (opcode != 64) {
																	if (opcode == 65)
																		anInt3902 = stream.readUnsignedShort();
																	else if (opcode != 66) {
																		if (opcode != 67) {
																			if (opcode == 69)
																				cflag = stream
																						.readUnsignedByte();
																			else if (opcode != 70) {
																				if (opcode == 71)
																					anInt3889 = stream
																							.readShort() << 2;
																				else if (opcode != 72) {
																					if (opcode == 73)
																						aBoolean3852 = true;
																					else if (opcode == 74)
																						ignoreClipOnAlternativeRoute = true;
																					else if (opcode != 75) {
																						if (opcode != 77
																								&& opcode != 92) {
																							if (opcode == 78) {
																								anInt3860 = stream
																										.readUnsignedShort();
																								anInt3904 = stream
																										.readUnsignedByte();
																							} else if (opcode != 79) {
																								if (opcode == 81) {
																									aByte3912 = (byte) 2;
																									anInt3882 = 256
																											* stream
																													.readUnsignedByte();
																								} else if (opcode != 82) {
																									if (opcode == 88)
																										aBoolean3853 = false;
																									else if (opcode != 89) {
																										if (opcode == 90)
																											aBoolean3870 = true;
																										else if (opcode != 91) {
																											if (opcode != 93) {
																												if (opcode == 94)
																													aByte3912 = (byte) 4;
																												else if (opcode != 95) {
																													if (opcode != 96) {
																														if (opcode == 97)
																															aBoolean3866 = true;
																														else if (opcode == 98)
																															aBoolean3923 = true;
																														else if (opcode == 99) {
																															anInt3857 = stream
																																	.readUnsignedByte();
																															anInt3835 = stream
																																	.readUnsignedShort();
																														} else if (opcode == 100) {
																															anInt3844 = stream
																																	.readUnsignedByte();
																															anInt3913 = stream
																																	.readUnsignedShort();
																														} else if (opcode != 101) {
																															if (opcode == 102)
																																anInt3838 = stream
																																		.readUnsignedShort();
																															else if (opcode == 103)
																																anInt3874 = 0;
																															else if (opcode != 104) {
																																if (opcode == 105)
																																	aBoolean3906 = true;
																																else if (opcode == 106) {
																																	int i_55_ = stream
																																			.readUnsignedByte();
																																	anIntArray3869 = new int[i_55_];
																																	anIntArray3833 = new int[i_55_];
																																	for (int i_56_ = 0; i_56_ < i_55_; i_56_++) {
																																		anIntArray3833[i_56_] = stream
																																				.readUnsignedShort();
																																		int i_57_ = stream
																																				.readUnsignedByte();
																																		anIntArray3869[i_56_] = i_57_;
																																		anInt3881 += i_57_;
																																	}
																																} else if (opcode == 107)
																																	anInt3851 = stream
																																			.readUnsignedShort();
																																else if (opcode >= 150
																																		&& opcode < 155) {
																																	actions[opcode
																																			+ -150] = stream
																																					.readCString();
																																/*	if (!((Class84) aClass84_3885).aBoolean1157)
																																		actions[opcode
																																				+ -150] = null;*/
																																} else if (opcode != 160) {
																																	if (opcode == 162) {
																																		aByte3912 = (byte) 3;
																																		anInt3882 = stream
																																				.readInt();
																																	} else if (opcode == 163) {
																																		aByte3847 = stream
																																				.readByte();
																																		aByte3849 = stream
																																				.readByte();
																																		aByte3837 = stream
																																				.readByte();
																																		aByte3914 = stream
																																				.readByte();
																																	} else if (opcode != 164) {
																																		if (opcode != 165) {
																																			if (opcode != 166) {
																																				if (opcode == 167)
																																					anInt3921 = stream
																																							.readUnsignedShort();
																																				else if (opcode != 168) {
																																					if (opcode == 169)
																																						aBoolean3845 = true;
																																					else if (opcode == 249) {
																																						int i_58_ = stream
																																								.readUnsignedByte();
																																						if (aClass194_3922 == null) {
																																							
																																							aClass194_3922 = new HashMap<Integer, Object>(
																																									opcode);
																																						}
																																						for (int i_60_ = 0; i_60_ < i_58_; i_60_++) {
																																							boolean bool = stream
																																									.readUnsignedByte() == 1;
																																							int i_61_ = stream
																																									.read24BitInt();
																																							
																																							Object class279;
																																							if (!bool)
																																								class279 = new Integer(
																																										stream
																																												.readInt());
																																							else
																																								class279 = new String(
																																										stream
																																												.readCString());
																																							aClass194_3922
																																									.put(i_61_,
																																											class279);
																																						}
																																					}
																																				} else
																																					aBoolean3894 = true;
																																			} else
																																				anInt3877 = stream
																																						.readShort();
																																		} else
																																			anInt3875 = stream
																																					.readShort();
																																	} else
																																		anInt3834 = stream
																																				.readShort();
																																} else {
																																	int i_62_ = stream
																																			.readUnsignedByte();
																																	anIntArray3908 = new int[i_62_];
																																	for (int i_63_ = 0; i_62_ > i_63_; i_63_++)
																																		anIntArray3908[i_63_] = stream
																																				.readUnsignedShort();
																																}
																															} else
																																anInt3865 = stream
																																		.readUnsignedByte();
																														} else
																															anInt3850 = stream
																																	.readUnsignedByte();
																													} else
																														aBoolean3924 = true;
																												} else {
																													aByte3912 = (byte) 5;
																													anInt3882 = stream
																															.readShort();
																												}
																											} else {
																												aByte3912 = (byte) 3;
																												anInt3882 = stream
																														.readUnsignedShort();
																											}
																										} else
																											aBoolean3873 = true;
																									} else
																										aBoolean3895 = false;
																								} else
																									aBoolean3891 = true;
																							} else {
																								anInt3900 = stream
																										.readUnsignedShort();
																								anInt3905 = stream
																										.readUnsignedShort();
																								anInt3904 = stream
																										.readUnsignedByte();
																								int i_64_ = stream
																										.readUnsignedByte();
																								anIntArray3859 = new int[i_64_];
																								for (int i_65_ = 0; i_65_ < i_64_; i_65_++)
																									anIntArray3859[i_65_] = stream
																											.readUnsignedShort();
																							}
																						} else {
																							anInt3863 = stream
																									.readUnsignedShort();
																							if (anInt3863 == 65535)
																								anInt3863 = -1;
																							anInt3897 = stream
																									.readUnsignedShort();
																							if (anInt3897 == 65535)
																								anInt3897 = -1;
																							int i_66_ = -1;
																							if (opcode == 92) {
																								i_66_ = stream
																										.readUnsignedShort();
																								if (i_66_ == 65535)
																									i_66_ = -1;
																							}
																							int i_67_ = stream
																									.readUnsignedByte();
																							anIntArray3831 = new int[i_67_
																									- -2];
																							for (int i_68_ = 0; i_67_ >= i_68_; i_68_++) {
																								anIntArray3831[i_68_] = stream
																										.readUnsignedShort();
																								if (anIntArray3831[i_68_] == 65535)
																									anIntArray3831[i_68_] = -1;
																							}
																							anIntArray3831[i_67_
																									+ 1] = i_66_;
																						}
																					} else
																						anInt3855 = stream
																								.readUnsignedByte();
																				} else
																					anInt3915 = stream
																							.readShort() << 2;
																			} else
																				anInt3883 = stream
																						.readShort() << 2;
																		} else
																			anInt3917 = stream
																					.readUnsignedShort();
																	} else
																		anInt3841 = stream.readUnsignedShort();
																} else
																	aBoolean3872 = false;
															} else
																aBoolean3839 = true;
														} else {
															int i_69_ = (stream.readUnsignedByte());
															aByteArray3858 = (new byte[i_69_]);
															for (int i_70_ = 0; i_70_ < i_69_; i_70_++)
																aByteArray3858[i_70_] = (stream.readByte());
														}
													} else {
														int i_71_ = (stream.readUnsignedByte());
														aShortArray3920 = new short[i_71_];
														aShortArray3919 = new short[i_71_];
														for (int i_72_ = 0; i_71_ > i_72_; i_72_++) {
															aShortArray3920[i_72_] = (short) (stream
																	.readUnsignedShort());
															aShortArray3919[i_72_] = (short) (stream
																	.readUnsignedShort());
														}
													}
												} else
													actions[-30
															+ opcode] = (stream.readCString());
											} else
												anInt3840 = (stream.readByte() * 5);
										} else
											anInt3878 = stream.readByte();
									} else {
										anInt3876 = stream.readUnsignedShort();
										if (anInt3876 == 65535)
											anInt3876 = -1;
									}
								} else
									anInt3874 = 1;
							} else
								aBoolean3867 = true;
						} else
							projectileCliped = false;
					} else
						sizeY = stream.readUnsignedByte();
				} else
					sizeX = stream.readUnsignedByte();
			} else
				name = stream.readCString();
		} else {
			/*if (opcode == 5 && ((Class84) aClass84_3885).aBoolean1162)
				method3297(stream, i + -3727);*/
			int i_73_ = stream.readUnsignedByte();
			anIntArrayArray3916 = new int[i_73_][];
			aByteArray3899 = new byte[i_73_];
			for (int i_74_ = 0; i_74_ < i_73_; i_74_++) {
				aByteArray3899[i_74_] = stream.readByte();
				int i_75_ = stream.readUnsignedByte();
				anIntArrayArray3916[i_74_] = new int[i_75_];
				for (int i_76_ = 0; i_75_ > i_76_; i_76_++)
					anIntArrayArray3916[i_74_][i_76_] = stream.readUnsignedShort();
			}
			if (opcode == 5/* && !((Class84) aClass84_3885).aBoolean1162*/)
				method3297(stream, 2);
		}
    }
    
    private final void method3297(RSInputStream streamBuffer, int i) throws IOException {
        int i_47_ = streamBuffer.readUnsignedByte();
        for (int i_48_ = 0; i_48_ < i_47_; i_48_++) {
        	streamBuffer.skip(1);
            int i_49_ = streamBuffer.readUnsignedByte();
            streamBuffer.skip(i_49_*2);
        }
    }

    private void readValueLoop(RSInputStream stream) throws IOException {
        for (; ;) {
            int opcode = stream.readUnsignedByte();
            if (opcode == 0)
                break;
            readValues(-2129513334, stream, opcode);
        }

    }


    private ObjectDefinitions() {
        anInt3835 = -1;
        anInt3860 = -1;
        anInt3863 = -1;
        aBoolean3866 = false;
        anInt3851 = -1;
        anInt3865 = 255;
        aBoolean3845 = false;
        aBoolean3867 = false;
        anInt3850 = 0;
        anInt3844 = -1;
        anInt3881 = 0;
        anInt3857 = -1;
        aBoolean3872 = true;
        anInt3882 = -1;
        anInt3834 = 0;
        actions = new String[5];
        anInt3875 = 0;
        aBoolean3839 = false;
        anIntArray3869 = null;
        sizeY = 1;
        anInt3874 = -1;
        projectileCliped = true;
        anInt3883 = 0;
        aBoolean3895 = true;
        anInt3840 = 0;
        aBoolean3870 = false;
        anInt3889 = 0;
        aBoolean3853 = true;
        aBoolean3852 = false;
        clipType = 2;
        anInt3855 = -1;
        anInt3878 = 0;
        anInt3904 = 0;
        sizeX = 1;
        anInt3876 = -1;
        ignoreClipOnAlternativeRoute = false;
        aBoolean3891 = false;
        anInt3905 = 0;
        name = "null";
        anInt3913 = -1;
        aBoolean3906 = false;
        aBoolean3873 = false;
        aByte3914 = (byte) 0;
        anInt3915 = 0;
        anInt3900 = 0;
        anInt3893 = -1;
        aBoolean3894 = false;
        aByte3912 = (byte) 0;
        anInt3921 = 0;
        anInt3902 = 128;
        anInt3897 = -1;
        anInt3877 = 0;
        cflag = 0;
        anInt3892 = 64;
        aBoolean3923 = false;
        aBoolean3924 = false;
        anInt3841 = 128;
        anInt3917 = 128;
    }

    private static int GetContainerId(int i_0_) {
        return i_0_ >>> 8;
    }

    public static ObjectDefinitions forID(int objectID) {
        ObjectDefinitions objectDef = objDefs.get(objectID);
        if (objectDef != null)
            return objectDef;
        byte[] is = new byte[0];
        try {
            is = (CacheManager.getData(CacheConstants.OBJECTDEF_IDX_ID, GetContainerId(objectID), 0xff & objectID));
        } catch (Exception e) {
            System.out.println("Could not grab object " + objectID);
        }
        objectDef = new ObjectDefinitions();
        objectDef.id = objectID;
        if (is != null) {
            try {
                objectDef.readValueLoop(new RSInputStream(new ByteArrayInputStream(is)));
            } catch (IOException e) {
            	//e.printStackTrace();
               // System.out.println("Could not load object " + objectID);
            }
        }

        
        objectDef.method3287(0);
        
        if (objectDef.ignoreClipOnAlternativeRoute) {
        	objectDef.projectileCliped = false;
        	objectDef.clipType = 0;
        }
        
       /* if (!objectDef.aBoolean3873 && aBoolean1162) {
        	objectDef.anIntArray3908 = null;
        	objectDef.actions = null;
        }*/
        
   
	    
	     if(objectDef.name.equalsIgnoreCase("bank booth") || objectDef.name.equalsIgnoreCase("counter")) {
	    	 objectDef.ignoreClipOnAlternativeRoute = false;
	    	 objectDef.projectileCliped = true;
	    	 objectDef.clipType = 1;
	 	}
	     
        
        objDefs.put(objectID, objectDef);
        return objectDef;
    }
    
    final void method3287(int i) {
        if (anInt3893 == -1) {
            anInt3893 = 0;
            if (aByteArray3899 != null && aByteArray3899.length == 1 && aByteArray3899[0] == 10)
                anInt3893 = 1;
            for (int i_13_ = 0; i_13_ < 5; i_13_++) {
                if (actions[i_13_] != null) {
                    anInt3893 = 1;
                    break;
                }
            }
        }
        if (anInt3855 == -1)
            anInt3855 = clipType != 0 ? 1 : 0;
    }
    
    public boolean hasActions() {
    	if(actions == null)
    		return false;
    	for(int i = 0; i < actions.length; i++) {
    		if(actions[i] != null && !actions[i].equals("null") && !actions[i].equals(""))
    			return true;
    	}
		return false;
    }
    
    public int getNumberOfActions() {
    	if(actions == null)
    		return 0;
    	int amt = 0;
    	for(int i = 0; i < actions.length; i++) {
    		if(actions[i] != null && !actions[i].equals("null") && !actions[i].equals(""))
    			amt++;
    	}
    	return amt;
    }

	public int getClipType() {
		return clipType;
	}
	
    public boolean isProjectileCliped() {
	return projectileCliped;
    }
    
    public boolean isIgnoreClipOnAlternativeRoute() {
    	return ignoreClipOnAlternativeRoute;
    }
    //they were switched, dont forget  to correct
    public int getSizeX() {
	return sizeX;
    }

    public int getSizeY() {
	return sizeY;
    }
    
    public int getAccessBlockFlag() {
	return cflag;
    }
}

