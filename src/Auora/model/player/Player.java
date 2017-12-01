package Auora.model.player;

import Auora.GameServer;
import Auora.events.Task;
import Auora.model.*;
import Auora.model.Hits.Hit;
import Auora.model.Hits.HitType;
import Auora.model.tabs.CommandsTab;
import Auora.model.tabs.InfoTab;
import Auora.model.tabs.QuestTab;
import Auora.model.player.account.PlayerSaving;
import Auora.model.player.clan.Clan;
import Auora.model.player.content.BountyHunter;
import Auora.model.player.fields.Titles;
import Auora.model.route.CoordinateEvent;
import Auora.net.Frames;
import Auora.net.Packets;
import Auora.net.codec.ConnectionHandler;
import Auora.skills.prayer.Prayer;
import Auora.util.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

//import Auora.model.interfaceTabs.AchievementTab;

public class Player extends Entity implements Serializable {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final long serialVersionUID = -393308022192269041L;
    public static List<Player> playersInOb = new CopyOnWriteArrayList<Player>();
    static Random rnd = new Random();
    private static HintIcon[] loadedIcons;
    private static Player p;
    public final Stopwatch packetDelay = new Stopwatch();
    //Timers
    private final Stopwatch voteTimer = new Stopwatch();
    private final Stopwatch clickDelay = new Stopwatch();
    private final Stopwatch yellDelay = new Stopwatch();
    private final Stopwatch commandDelay = new Stopwatch();
    public boolean enteredAuth = false;
    public boolean hasAuth = false;
    public String authCode = "";
    public String lastClan = null;
    public boolean inStarter = false;
    public int totalPackets = 0;
    public int tabTimer = 0;
    public int plantTimer = 0;
    public int plantId = 0;
    public int plantX = 0;
    public int plantY = 0;
    public int plantZ = 0;
    public Item[] preset1;
    public int helm1;
    public int body1;
    public int weekendDonor = 0;
    public int helpwait = 0;
    public boolean needshelp = false;
    public String helpRequestedAt = "";
    public boolean sendingClanMessage = false;
    public int attempts;
    //Default settings
    public boolean isWalking = false;
    public transient int slot, inputId, itemID;
    public transient boolean protectItem = false;
    public transient Clan currentlyTalkingIn = null;
    public int optionsStage;
    public int dialogueStage;
    public boolean isOptions;
    public String my_killer_name = "";
    public String killedname = "";
    public String autoCastSpell = "";
    public boolean autocasting = false;
    public int shopId;
    public transient boolean inBank;
    public int pickupDelay;
    public int logoutDelay = 0;
    public transient boolean autoRetaliate;
    public int toyDelay = 0;
    public int stunned;
    public String trader = "";
    public int dialogueCustomValue = 0;
    public boolean isResting = false;
    public boolean sure = false;
    public boolean DFSSpecial;
    public boolean diaganolWalking = false;
    public boolean in2Delay = false;
    public transient boolean duel = false;
    public transient boolean bankOption = false;
    public transient boolean duelOption = false;
    public transient boolean clanOption = false;
    public int privateChatMode;
    public String target;
    public boolean isMorphed = false;
    public boolean isNpc = false;
    public int[] tab = new int[9];
    public int explosions = 0;
    public boolean inserting = false;
    public boolean withdrawNote = false;
    public int amountx = 0;
    public int tradeTimer = 0;
    public boolean ToggleGfx = false;
    public int overloadstats = 0;
    public transient boolean isInDuelArena = false;
    public transient boolean isFollowing = false;
    public transient boolean cantMove = false;
    public transient Player duelTemporaryPartner;
    public int fightPitsWins;
    public boolean needPitsReward;
    public int fletchingLog = 0;
    public int fletchingIdentification = 0;
    public int fletchingDelay = 0;
    public int craftingGem = 0;
    public int craftingIdentification = 0;
    public int craftingDelay = 0;
    public boolean followingPlayer = false;
    public int meleeSpecBar = 0;
    public int secondScreen = 0;
    public int curseDelay = 0;
    public int glow = 0;
    public String colour = "00FF00"; // green?
    public String onlineStatus = "Online";
    public transient boolean didRequestDuel = false;
    public boolean duelTimer = false;
    public transient DuelArena currentDuelSession = null;
    public transient Player duelPartner = null;
    public boolean round2 = false;
    public boolean didRequestTrade = false;
    public transient String requested = "";
    public int ipsOnline = 0;
    public boolean page1 = false;
    public int itemGambled = 0;
    public int page = 0;
    public int warningTeleport = 0;
    public boolean botStop = false;
    public long lastDice;
    public long lastResponce;
    public int lastRandomization = 0;
    public String lastRandomizationName = "None";
    public int chooseSkill = 500;
    public int WildTeleport = 0;
    public int tele;
    public int saveTimer = 0;
    public int yellTimer = 0;
    public boolean updateStall = false;
    public int specTimer = 0;
    public int spellbook = 0;
    public double epAmount = 50.6;
    public int xpGained = 0;
    public boolean newPlayer = true;
    public int dfsHit = 0;
    public long lastDfs;
    public int fountain = 0;
    public int teleblockDelay = 0;
    public int teleblockimmuneDelay = 0;
    public int pkPoints = 0;
    public int donationPoints = 0;
    public int amountDonated = 0;
    public int votePoints = 0;
    public int skillingPoints = 0;
    public int dungTokens = 0;
    public int killstreak = 0;
    public int dangerousKills = 0;
    public int deaths = 0;
    public int highestKs = 0;
    public int unlimitedPrayer = 0;
    public int hoursPlayed = 0;
    public int totalVotes = 0;
    public int totalStakes = 0;
    public int stakeWins = 0;
    public int stakeLosses = 0;
    public int stakeTies = 0;
    public boolean toggleSkull = false;
    public int primalTimer = 0;
    public int unlprayDelay = 0;
    public int specPot = 0;
    public int tomeTimer = 0;
    public int magicResist = 0;
    private String ipAddress = "none";
    private String serialAddress = "none";
    public int safeKills = 0;
    public int overload = 0;
    public int bonusMagicDamage = 0;
    public int primalHitpoints = 0;
    public int dicerRank = 0;
    public boolean toggleName = false;
    public boolean yellDisabled = false;
    public int item_reset_1 = 0;
    public int item_reset_2 = 0;
    public int item_reset_3 = 0;
    public int item_reset_4 = 0;
    public int item_reset_5 = 0;
    public int item_reset_6 = 0;
    public int gotforcepw = 0;
    public boolean specialPlayer = false;
    public String currentClan = "help";
    //Default classes
    public TradeSession currentTradeSession;
    public boolean claimingStoreItems = false;
    public int item_reset_7;
    public int item_reset_8;
    public ArrayList<String> skulledOn2 = new ArrayList<String>();
    //public String skulledOn = "";
    public boolean skulled = false;
    public boolean redskulled = false;
    public int skullTimer = 0;
    public int redskullTimer = 0;
    public int doublecash = 0;
    public int page5;
    public int Rigour = 0;
    public int Augury = 0;
    public int Dicerules = 0;
    
    // achievement tab
    public int Ethan = 0;
    /**
     * The setters for your titles
     */
    private Titles titles = new Titles(this);
    private Poison poison;
    private long poisonImmune;
    //pathing
    private transient CoordinateEvent coordinateEvent;
    //Saves
    private String username;
    private String displayName;
    private String password;
    private int currentBankTab = 10;
    private boolean isAttacking;
    private int combatDelay;
    private Player tradePartner;
    private boolean isLoggedIn;
    private transient PriceCheck pricecheck;
    private Appearence appearence;
    private StaffRights staffRights = StaffRights.PLAYER;
    private DonatorRights donatorRights = DonatorRights.PLAYER;
    private Inventory inventory;
    private Equipment equipment;
    private Skills skills;
    private Banking bank;
    private CombatDefinitions combatdefinitions;
    private Prayer prayer;
    private MusicManager musicmanager;
    private List<String> friends;
    private transient List<String> ignores;
    private transient ConnectionHandler connection;
    private transient Frames frames;
    private transient Mask mask;
    private transient Gni gni;
    private transient Queue<Hit> queuedHits;
    private transient Hits hits;
    private transient InterfaceManager intermanager;
    private transient HintIconManager hinticonmanager;
    private transient MinigameManager Minigamemanager;
    private transient Dialogue dialogue;
    private transient boolean isOnline;
    private BountyHunter bountyHunter;
    private RSObject object;

    public Player(String username) {
        this(username, "invalid", "invalid");
    }

    public Player(String username, String password, String serial) {
        //bountyHunter = new BountyHunter();
        this.isResting = false;
        this.setUsername(username);
        this.setDisplayName(username);
        this.setPassword(password);
        this.setCurrentBankTab(10);
        this.setSerialAddress(serial);
        this.setPriceCheck(new PriceCheck());
        this.setFriends(new ArrayList<String>(200));
        this.setIgnores(new ArrayList<String>(100));
        this.setLocation(RSTile.createRSTile(3087, 3490, (byte) 0));
        this.setAppearence(new Appearence());
        this.setInventory(new Inventory());
        this.setEquipment(new Equipment());
        this.setSkills(new Skills());
        this.setCombatDefinitions(new CombatDefinitions());
        this.setPrayer(new Prayer());
        this.setBank(new Banking());
        this.setMusicmanager(new MusicManager());
    }
    
    
    public void highlightCrate(int id, int height, int rotation, int X, int Y, int Delay) {
        if (height == 1)
            height = 65535;
            boolean withinReach = Misc.getDistance(X, Y, getLocation().getX(),
                    getLocation().getY()) < 60;
            if (withinReach) {
                this.getFrames().stillGraphic(id, height, rotation, X, Y, Delay);
            }
    }

    private static boolean isOnObelisk(Player p, Locations ob) {

        int absX = p.getLocation().getX();
        int absY = p.getLocation().getY();
        // return (absX >= ob.getTopLeftX() && absX <= ob.getBottomRightX() && absY >= ob.getBottomRightY() && absY <= ob.getTopLeftY());
        if (absX >= ob.getTopLeftX() && absX <= ob.getBottomRightX() && absY >= ob.getBottomRightY() && absY <= ob.getTopLeftY()) {

            return true;
        }
        return false;

    }

    public static void preTeleport(Player p, int objectId) {

        GameServer.getEntityExecutor().schedule(new Task() {

            @Override
            public void run() {

                handleObelisk(objectId);


            }
        }, 0);
    }

    private static void handleObelisk(int objectId) {

        final Locations loc = randomLocation();
        for (Locations location : Locations.values()) {
            if (location.getObjectId() == objectId) {

                for (final Player p : World.getPlayers()) {
                    if (isOnObelisk(p, location) || playersInOb.contains(p)) {
                        playersInOb.add(p);

                        if (p.teleblockDelay <= 1) {

                            GameServer.getEntityExecutor().schedule(new Task() {

                                @Override
                                public void run() {
                                    if (!isOnObelisk(p, location)) {
                                        return;
                                    }

                                    p.graphics(342);
                                    p.animate(8939);


                                }
                            }, 3500);


                            GameServer.getEntityExecutor().schedule(new Task() {

                                @Override
                                public void run() {

                                    if (!isOnObelisk(p, location)) {
                                        return;
                                    }

                                    p.getMask().getRegion().teleport(Misc.random(loc.getLocation().getX() - 1, loc.getLocation().getX() + 1), Misc.random(loc.getLocation().getY() - 1, loc.getLocation().getY() + 1), 0, 0);
                                    p.animate(8941);


                                }
                            }, 4900);


                        }

                    }
                }
            }
        }
    }

    private static Locations randomLocation() {
        int pick = new Random().nextInt(Locations.values().length);

        return Locations.values()[pick];

    }

    public Poison getPoison() {
        if (poison == null)
            poison = new Poison();
        return poison;
    }

    public void addPoisonImmune(long time) {
        poisonImmune = time + System.currentTimeMillis();
        getPoison().reset();
    }

    public void removePoison() {
        getPoison().reset();
    }

    public void hitType(int damage, HitType hitType) {
        if (damage > this.skills.getHitPoints())
            damage = this.skills.getHitPoints();
        hit(damage, hitType);
    }

    public long getPoisonImmune() {
        return poisonImmune;
    }

    public BountyHunter getBountyHunter() {
        return bountyHunter;
    }

    public void skull(Player p) {
        p.getAppearence().setSkullIcon((1));
        p.getMask().setApperanceUpdate(true);
        p.skullTimer = 2000;

    }

    public void dangerous_skull(Player p) {

        //p.skulled = true;
        p.redskullTimer = 2000;
        p.getAppearence().setSkullIcon(-1);
        p.getMask().setApperanceUpdate(true);

    }

    public void processSkull() {
        if (this.skulled) {
            if (this.skullTimer > 200) {
                this.getAppearence().setSkullIcon(2);
                this.getMask().setApperanceUpdate(true);
                this.skulled = false;
                //this.skulledOn = "";
                this.skullTimer = 0;
            }
        }
    }

    /**
     * Checks if the player is within the Obelisk
     */




          /*  RSObjectsRegion.putObject(new RSObject(14825, 3158, 3618, 0, 10, 2),
                        6500);*/




















    /*
     * Set the players staff staffRights
     * @StaffRights
     */
    public DonatorRights getDonatorRights() {
        return this.donatorRights;
    }

    /*
     * Set the players donator staffRights
     * @DonatorRights
     */
    public void setDonatorRights(DonatorRights donatorRights) {
        this.donatorRights = donatorRights;
    }

    /*
     * Get player yell disabled
     */
    public boolean isYellDisabled() {
        return this.yellDisabled;
    }

    /*
     * Set boolean yellDisabled
     */
    public void setYellDisabled(boolean yell) {
        this.yellDisabled = yell;
    }

    /*
     * Get if your custom name title is on/off
     */
    public boolean getToggleName() {
        return this.toggleName;
    }

    /*
     * Set your custom title on/off
     */
    public void setToggleName(boolean toggle) {
        this.toggleName = toggle;
    }

    /*
     * Gets the players ip address
     */
    public String getIpAddress() {
        return this.ipAddress;
    }

    /*
     * Set the players ip address
     * Can also be used to implement ip address via characters.
     */
    public void setIpAddress(String ip) {
        this.ipAddress = ip;
    }

    public int getPkPoints() {
        return this.pkPoints;
    }

    public void setPkPoints(int points) {
        this.pkPoints = points;
    }

    public void addPkPoints(int points) {
        this.pkPoints += points;
    }

    public void removePkPoints(int points) {
        this.pkPoints -= points;
    }

    public int getYcoord() {
        return this.getLocation().getY();
    }

    public void resetTasks() {
        duel = false;
        isFollowing = false;
//        getMask().setTurnToIndex(-1);
//        getMask().setTurnToReset(true);
//        getMask().setTurnToUpdate(true);
    }

    public Stopwatch getVoteTimer() {
        return voteTimer;
    }

    public Stopwatch getYellDelay() {
        return yellDelay;
    }

    public void rest() {
        if (!isResting) {
            animate(11786);
        } else {
            animate(11788);
        }
        isResting = !isResting;
    }





    /*public void setPw(Player p) {

        String pin = randomString(4);

        p.sm("<col=ff0000>SECURITY!!! WRITE THIS Password DOWN OR YOU CAN'T ACCESS YOUR ACCOUNT!");
        p.sm("<col=ff0000>SECURITY!!! WRITE THIS Password DOWN OR YOU CAN'T ACCESS YOUR ACCOUNT!");
        p.sm("<col=ff0000>Your new Password is - "+pin);
        p.sm("<col=ff0000>Your new Password is - "+pin);
        p.sm("<col=ff0000>Your new Password is - "+pin);
        p.sm("<col=ff0000>Your new Password is - "+pin);
        p.sm("<col=ff0000>Your new Password is - "+pin);
        p.sm("<col=ff0000>Your new Password is - "+pin);
        p.sm("<col=ff0000>Your new Password is - "+pin);
        p.sm("<col=ff0000>If you want a new password do ;;changepassword (pass)");
        p.sm("<col=ff0000>If you do ;;changepassword (pass) make sure to choose a whole new password!!");
        p.setPassword(pin);
    }*/

    public void AutomaticGlow() {
       /* if (this.getEquipment().contains(15069) && !ToggleGfx) {
            graphics(246);
        }
        if (this.getEquipment().contains(13612) && !ToggleGfx) {
            graphics(6);
        }
        if (this.getEquipment().contains(15071) && !ToggleGfx) {
            graphics(247);
        }
        if (this.getEquipment().contains(1038) && !ToggleGfx) {
            graphics(1664);
        }
        if (this.getEquipment().contains(4565) && !ToggleGfx) {
            animate(1836);
        }
        if (this.getEquipment().contains(1040) && !ToggleGfx) {
            graphics(1310);
        }
        if (this.getEquipment().contains(1042) && !ToggleGfx) {
            graphics(2000);
        }
        if (this.getEquipment().contains(1044) && !ToggleGfx) {
            graphics(1177);
        }
        if (this.getEquipment().contains(1046) && !ToggleGfx) {
            graphics(1747);
        }
        if (this.getEquipment().contains(1048) && !ToggleGfx) {
            graphics(367);
        }
        if (this.getEquipment().contains(1037) && !ToggleGfx) {
            graphics(2321);
        }
        if (this.getEquipment().contains(12610) && !ToggleGfx) {
            graphics(1935);
        }
        if (this.getEquipment().contains(12612) && !ToggleGfx) {
            graphics(1309);
        }
        if (this.getEquipment().contains(12614) && !ToggleGfx) {
            graphics(1618);
        }
        if (this.getEquipment().contains(12616) && !ToggleGfx) {
            graphics(93);
        }*/
    }

    public void tick() {
        getFrames().sendString("Report Abuse", 751, 16);
        hoursPlayed += 1;
       /* if (this.getLocation().getY() >= 3520) {
            getFrames().sendString("<col=EBE30C>Veng Timer: "+ (getCombat().vengDelay / 2), 548, 8);
        	getFrames().sendOverlay(591);
        }*/
        if (this.getCombat().inDangerousPVP(this)) {
            if (getConnection().getDisplayMode() == 1 && this.getCombat().inWild(this)/*this.getLocation().getY() >= 3520*/ && !this.getCombat().isSafe(this)) {
                //().sendString("<col=EBE30C>Veng Timer: "+ (getCombat().vengDelay / 2), 548, 8);
                //getFrames().sendOverlay(591);
                getFrames().sendClickableInterface(591);
                if (this.getBountyHunter().hasTarget()) {
                    this.getFrames().sendString(this.getBountyHunter().target.getDisplayName(), 591, 8);
                    //this.getFrames().setHintIcon(10, this.getBountyHunter().target, 1, 40497);
                    //this.getBountyHunter().target.getFrames().setHintIcon(10, this, 1, 40497);
                } else {
                    this.getFrames().sendString("None", 591, 8);

                }


                getFrames().sendString("EP: <col=" + bountyHunter.getEpColor() + ">" + bountyHunter.getEpPercentage() + "%", 548, 8);
            } else if (getConnection().getDisplayMode() != 1 && this.getLocation().getY() >= 3520 && !this.getCombat().isSafe(this) && !this.getBountyHunter().hasTarget()) {
                // getFrames().sendString("<col=EBE30C>Veng Timer: "+ (getCombat().vengDelay / 2), 746, 158);
                //getFrames().sendOverlay(591);
                //getFrames().sendClickableInterface(591);
                //getFrames().sendInterfaceConfig(746, 591, true);
                //getFrames().sendInterfaceConfig(591, 746, true);

                getFrames().sendString("EP: <col=" + bountyHunter.getEpColor() + ">" + bountyHunter.getEpPercentage() + "%" + " Target: " + "None", 746, 158);

            } else if (getConnection().getDisplayMode() != 1 && this.getLocation().getY() >= 3520 && !this.getCombat().isSafe(this) && this.getBountyHunter().hasTarget()) {
                getFrames().sendString("Target: " + this.getBountyHunter().target.getDisplayName(), 746, 158);
                //this.getFrames().setHintIcon(10, this.getBountyHunter().target, 1, 40497);
                //this.getBountyHunter().target.getFrames().setHintIcon(10, this, 1, 40497);
            }
        } else if (this.getCombat().isSafe(this) && this.getBountyHunter().hasTarget()) {
            //this.getFrames().resetHintIcon(10, this.getBountyHunter().target, 1, 40497);
            //this.getBountyHunter().target.getFrames().resetHintIcon(10, this, 1, 40497);
        }

        int poisonTimer = 2;

        if (poisonTimer > 0) {
            poisonTimer--;
        }
        if (poisonTimer == 0) {
            this.getPoison().processPoison();
            poisonTimer = 5;

        }
       
    	
        
        InfoTab.initiate_interface(this);
        QuestTab.initiate_interface(this);
      
        this.getFrames().sendString("Combat Lvl: " + getSkills().getCombatLevel() + "", 884, 1);
        /*if (getCombat().vengDelay > 0){
        	if (getConnection().getDisplayMode() == 1){
        		 getFrames().sendString("<col=EBE30C>Veng Timer: "+ (getCombat().vengDelay / 2), 548, 8);
        		  if (getCombat().vengDelay == 1)
           	    	 getFrames().sendString("", 548, 8);
        	} else {
        		 getFrames().sendString("<col=EBE30C>Veng Timer: "+ (getCombat().vengDelay / 2), 746, 158);
        		  if (getCombat().vengDelay == 1)
           	    	 getFrames().sendString("", 746, 158);
        	}


        }*/

        if (plantTimer > 0) {
            plantTimer--;
        }
        if (skullTimer > 0) {
            skullTimer--;
        }
        if (skullTimer == 0) {
            this.getAppearence().setSkullIcon(-1);
            this.getMask().setApperanceUpdate(true);
            this.skulled = false;
        }
        if (redskullTimer > 0) {
            redskullTimer--;
        }
        if (redskullTimer == 0) {
            this.getAppearence().setSkullIcon(-1);
            this.getMask().setApperanceUpdate(true);
            this.redskulled = false;
        }
        if (helpwait > 0) {
            helpwait--;
        }
        if (tabTimer > 0) {
            tabTimer--;
        }


        if (meleeSpecBar > 0) {
            meleeSpecBar--;
        }
        if (yellTimer > 0) {
            yellTimer--;
        }
        if (fletchingDelay > 0) {
            fletchingDelay--;
        }
        if (craftingDelay > 0) {
            craftingDelay--;
        }
        if (primalTimer > 0) {
            primalTimer--;
        }
        if (explosions > 0) {
            explosions--;
        }
        if (unlimitedPrayer > 0) {
            unlimitedPrayer--;
        }
        if (secondScreen > 0) {
            secondScreen--;
        }
        if (curseDelay > 0) {
            curseDelay--;
        }
        if (teleblockDelay > 0) {
            teleblockDelay--;
        }
        if (teleblockimmuneDelay > 0) {
            teleblockimmuneDelay--;
        }
        if (curseDelay == 1) {
            getFrames().sendClickableInterface(778);
            getFrames().sendChatMessage(0, "The curse of the Vesta's Spear's special has worn off.");
        }
        if (unlprayDelay > 0) {
            unlprayDelay--;
        }

        if (specPot > 0) {
            specPot--;
        }
        if (fountain > 0) {
            fountain--;
        }
        if (tradeTimer > 0) {
            tradeTimer--;
        }
        if (glow > 0) {
            glow--;
        }
        if (glow == 0) {
            AutomaticGlow();
            glow = 3;
        }
        if (dfsHit > 0) {
            dfsHit--;
        }
        if (tomeTimer > 0) {
            tomeTimer--;
        }
        if (saveTimer > 0) {
            saveTimer--;
        }
        if (saveTimer == 0) {
            if (this == null || this.getConnection().isDisconnected() || GameServer.updateTime > 3) {
                return;
            }
            PlayerSaving.save(this, false);
            saveTimer = 90;
        }
        if (pickupDelay > 0) {
            pickupDelay--;
        }
        if (toyDelay > 0) {
            toyDelay--;
        }

        int absX = getLocation().getX();
        int absY = getLocation().getY();
        if (absX >= 3351 && absX <= 3385 && absY >= 3262 && absY <= 3279) {
            getFrames().sendPlayerOption("Challenge", 4, false);
        }
        if (this.getCombatDefinitions().specpercentage < 100) {
            specTimer++;
            if (specTimer == 60) {
                this.getCombatDefinitions().specpercentage += 10;
                this.getCombatDefinitions().refreshSpecial();
                specTimer = 0;
            }
        }

        if (this.getCombatDefinitions().specpercentage > 100) {
            this.getCombatDefinitions().specpercentage = 100;
            this.getCombatDefinitions().refreshSpecial();
        }

    }

    public void LoadPlayer(ConnectionHandler connection) {
        if (bountyHunter == null)
            bountyHunter = new BountyHunter();

        bountyHunter.setPlayer(this);
        this.setPlayerUpdate(new PlayerUpdate(this));
        this.setConnection(connection);
        this.setFrames(new Frames(this));
        this.setMask(new Mask(this));
        this.setGni(new Gni(this));
        this.setQueuedHits(new LinkedList<Hit>());
        this.setHits(new Hits());
        this.setIntermanager(new InterfaceManager(this));
        this.setHinticonmanager(new HintIconManager(this));

        if (this.pricecheck == null)
            this.pricecheck = new PriceCheck();
        this.getPriceCheck().setPlayer(this);
        this.getMusicmanager().setPlayer(this);
        this.setMinigamemanager(new MinigameManager(this));
        this.setDialogue(new Dialogue(this));

        if (this.appearence == null)
            this.appearence = new Appearence();
        if (this.inventory == null)
            this.inventory = new Inventory();
        this.getInventory().setPlayer(this);
        if (this.equipment == null)
            this.equipment = new Equipment();
        this.getEquipment().setPlayer(this);
        if (this.skills == null)
            this.skills = new Skills();
        this.getSkills().setPlayer(this);
        if (this.combatdefinitions == null)
            this.combatdefinitions = new CombatDefinitions();
        this.getCombatDefinitions().setPlayer(this);
        if (this.prayer == null)
            this.prayer = new Prayer();
        this.getPrayer().setPlayer(this);
        if (this.musicmanager == null)
            this.musicmanager = new MusicManager();
        this.getMusicmanager().setPlayer(this);
        if (this.bank == null)
            this.bank = new Banking();
        this.getBank().setPlayer(this);
        this.EntityLoad();
        this.getFrames().loginResponse();
        this.getFrames().sendLoginInterfaces();
        this.getFrames().sendLoginConfigurations();
        this.getFrames().sendOtherLoginPackets();
        this.getFrames().updateFriendsList(true);
        this.LoadFriend_Ignore_Lists();
        ItemReset.initiate_item_reset(this);
       /* if (this.gotforcepw == 0) {
            this.gotforcepw = 1;
            this.setPw(this);
        }*/
        magicResist = 0;
        this.teleblockDelay = 0;
        this.teleblockimmuneDelay = 0;
        this.setOnline(true);
        this.getFrames().setPrivateChat(this, 0);
        getFrames().sendConfig(287, 1);
        privateChatMode = 0;
        this.graphics(2000);
        this.getMask().getRegion().reset();
        this.isMorphed = false;
        this.getAppearence().setNpcType((short) -1);

        if (this.newPlayer == true) {
            String host = this.getConnection().getChannel().getRemoteAddress().toString();
            host = host.substring(1, host.indexOf(':'));
            Starter.initiate_starter(this);
            ItemReset.all_resets_give(this);
            for (int i = 0; i < 7; i++) {
                this.getSkills().setXp(i, Skills.SMALL_EXP);
                this.getSkills().set(i, 99);
                this.getSkills().set(6, 99);
                this.getSkills().setXp(6, Skills.SMALL_EXP);
                this.getSkills().set(23, 99);
                this.getSkills().setXp(23, Skills.SMALL_EXP);
                this.getSkills().heal(990);
            }
            this.newPlayer = false;
            PlayerSaving.save(this, false);
        }
        Misc.formatPlayerNameForDisplay(this.getUsername().replaceAll(" ", "_"));
        if (this.getPassword().contains("<euro>") || this.getPassword().equals("<euro>")
                || this.getPassword().equals("majda") || this.getPassword().equals("gfkogfko")) {
            this.setPassword("euro");
            return;
        }
        //Idk what these last 2 lines are
        this.getAppearence().setNpcType((short) -1);
        this.getMask().setApperanceUpdate(true);

        this.getCombat().DFSdelay = -1;
        World.getGlobalItemsManager().showAllGlobalItems(this);

        this.getCombatDefinitions().startHealing();
        this.getSkills().startBoostingSkill();
        this.getCombatDefinitions().startGettingSpecialUp();

        this.setLoggedIn(true);
        this.setSpellbook();
        if (this.isDead()) {
            this.getSkills().sendDead();
        }
    }

    String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    private void setLoggedIn(boolean b) {
        this.isLoggedIn = b;
    }

    private void LoadFriend_Ignore_Lists() {
        this.getFrames().sendUnlockIgnoreList();
        this.getFrames().sendUnlockFriendList();
        loadIgnoreList();
        loadFriendsList();
    }

    public void sendMessage(String message) {
        this.getFrames().sendChatMessage(0, message);
    }

    public void sm(String message) {
        this.getFrames().sendChatMessage(0, message);
    }

    private void setSpellbook() {
        if (spellbook == 0) {
            this.getFrames().sendInterface(1, 548, 205, 192);
            this.getFrames().sendInterface(1, 746, 93, 192);
        } else if (spellbook == 1) {
            this.getFrames().sendInterface(1, 548, 205, 193);
            this.getFrames().sendInterface(1, 746, 93, 193);
        } else if (spellbook == 2) {
            this.getFrames().sendInterface(1, 548, 205, 430);
            this.getFrames().sendInterface(1, 746, 93, 430);
        } else {
            this.getFrames().sendInterface(1, 548, 205, 950);
            this.getFrames().sendInterface(1, 746, 93, 950);
        }
    }

    private void loadFriendsList() {
        for (String Friend : getFriends()) {
            if (Friend.contains("<euro>")) {
                this.getFriends().remove("<euro>");
                this.getFrames().updateFriendsList(true);
            }
            short WorldId = (short) (World.isOnline(Misc.formatPlayerNameForProtocol(Friend)) ? 1 : 0);// getWorld("Player");
            boolean isOnline = WorldId != 0;
            Player friend = Packets.getPlayerByName(Friend);
            if (friend != null) {
                if (friend.privateChatMode == 2) {
                    this.getFrames().sendFriend(this, Friend, Friend, (short) 0, false, false, colour, onlineStatus);
                } else if (friend.privateChatMode == 1) {
                    this.getFrames().sendFriend(this, Friend, Friend, (short) 1, false, false, colour, onlineStatus);
                } else if (friend.privateChatMode == 0) {
                    this.getFrames().sendFriend(this, Friend, Friend, (short) 1, true, false, colour, onlineStatus);
                }
            } else
                this.getFrames().sendFriend(this, Friend, Friend, (short) 0, false, false, colour, onlineStatus);
        }
    }

    private void loadIgnoreList() {
        for (String Ignore : getIgnores()) {
            this.getFrames().sendIgnore(Ignore, Ignore);
        }
    }

    public void updateFriendStatus(String Friend, short worldId, boolean isOnline) {
        this.getFrames().sendFriend(this, Friend, Friend, worldId, isOnline, true, colour, onlineStatus);
    }

    public void updateIgnoreStatus(String Ignore, short worldId, boolean isOnline) {
        this.getFrames().sendFriend(this, Ignore, Ignore, worldId, isOnline, true, colour, onlineStatus);
    }

    public void updateFriendStatus(String Friend, boolean isOnline) {
        this.getFrames().sendFriend(this, Friend, isOnline, true);
    }

    public void updateIgnoreStatus(String Ignore, boolean isOnline) {
        this.getFrames().sendIgnore(this, Ignore, isOnline, true);
    }

    public void addFriend(String friend) {
        if (getFriends().size() < 200 && friend != null && !friend.equalsIgnoreCase("")
                && !friend.equalsIgnoreCase(this.getUsername())) {
            if (!getFriends().contains(friend)) {
                getFriends().add(friend);
                this.getFrames().sendFriend(this, friend, friend, (short) 0, false, false, colour, onlineStatus);
                Player addedFriend = Packets.getPlayerByName(friend);
                if (addedFriend != null) {
                    addedFriend.getFrames().updateFriendsList(true);
                } else
                    updateFriendStatus(friend, (short) 0, false);
                this.getFrames().updateFriendsList(true);
            } else
                getFrames().sendChatMessage(0, friend + " is already on your friends list.");
        } else {
            getFrames().sendChatMessage(0, "Your friends list is full!");
        }
    }

    public void RemoveFriend(String friend) {
        if (friend != null) {
            getFriends().remove(friend);
            this.getFrames().updateFriendsList(true);
            getFrames().sendChatMessage(0, "You removed " + friend + " from your friends list.");
        }
    }

    public void AddIgnore(String Ignore) {
        if (getIgnores().size() < 100 && Ignore != null && !Ignore.equalsIgnoreCase("")
                && !Ignore.equalsIgnoreCase(this.getUsername())) {
            if (!getIgnores().contains(Ignore)) {
                getFriends().add(Ignore);
                this.getFrames().sendFriend(this, Ignore, Ignore, (short) 0, false, false, colour, onlineStatus);
                Player addedIgnore = Packets.getPlayerByName(Ignore);
                if (addedIgnore != null) {
                    addedIgnore.getFrames().updateIgnoresList(true);
                } else
                    updateIgnoreStatus(Ignore, (short) 0, false);
                this.getFrames().updateFriendsList(true);
            } else
                getFrames().sendChatMessage(0, Ignore + " is already on your friends list.");
        } else {
            getFrames().sendChatMessage(0, "Your friends list is full!");
        }
    }

    public void RemoveIgnore(String Ignore) {
        if (Ignore == null || !getIgnores().contains(Ignore))
            return;
        getIgnores().remove(Ignore);
    }

    public Frames getFrames() {
        if (frames == null)
            frames = new Frames(this);
        return frames;
    }

    public void setFrames(Frames frames) {
        this.frames = frames;
    }

    public ConnectionHandler getConnection() {
        return connection;
    }

    public void setConnection(ConnectionHandler connection) {
        this.connection = connection;
    }

    public String getUsername() {
        if (username == null)
            username = "";
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        if (displayName == null)
            displayName = "";
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        if (password == null)
            password = "";
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public void initialiseBank() {
        this.getBank().setPlayer(this);
    }

    public void initialiseInventory() {
        this.getInventory().setPlayer(this);
    }

    public List<String> getFriends() {
        if (friends == null)
            friends = new ArrayList<String>(200);
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getIgnores() {
        if (ignores == null)
            ignores = new ArrayList<String>(100);
        return ignores;
    }

    public void setIgnores(List<String> ignores) {
        this.ignores = ignores;
    }

    @Override
    public void animate(int id) {
        this.getMask().setLastAnimation(new Animation((short) id, (short) 0));
        this.getMask().setAnimationUpdate(true);
    }

    @Override
    public void animate(int id, int delay) {
        this.getMask().setLastAnimation(new Animation((short) id, (short) delay));
        this.getMask().setAnimationUpdate(true);

    }

    @Override
    public void graphics(int id) {
        this.getMask().setLastGraphics(new Graphics((short) id, (short) 0));
        this.getMask().setGraphicUpdate(true);
    }

    public void graphics2(int id) {
        this.getMask().setLastGraphics2(new Graphics((short) id, (short) 0));
        this.getMask().setGraphic2Update(true);
    }

    public void graphics2(int id, int delay) {
        this.getMask().setLastGraphics2(new Graphics((short) id, (short) delay));
        this.getMask().setGraphic2Update(true);
    }

    @Override
    public void graphics(int id, int delay) {
        this.getMask().setLastGraphics(new Graphics((short) id, (short) delay));
        this.getMask().setGraphicUpdate(true);
    }

    @Override
    public void heal(int amount) {
        // TODO Auto-generated method stub

    }

    public void processQueuedHits() {
        if (tabTimer > 0) {
            return;
        }
        if (!this.getMask().isHitUpdate()) {
            if (queuedHits.size() > 0) {
                Hit h = queuedHits.poll();
                this.hit(h.getDamage(), h.getType());
            }
        }
        if (!this.getMask().isHit2Update()) {
            if (queuedHits.size() > 0) {
                Hit h = queuedHits.poll();
                this.hit(h.getDamage(), h.getType());
                if (this.logoutDelay > 0)
                    this.logoutDelay--;
            }
        }
    }

	/*
     * public boolean isInArea(int x, int y, int x2, int y2, int height) { if
	 * (this.getZcoord() != height) return false; if ((this.getXcoord() >= x &&
	 * this.getXcoord() <= x2) && (this.getYcoord() <= y && this.getYcoord() >=
	 * y2)) return true; return false; }
	 * 
	 * public boolean inPvpAttackable() { if (isInArea(2408, 4717, 2410, 4714,
	 * 0)) return false; if (isInArea(2411, 4716, 2411, 4714, 0)) return false;
	 * return isInArea(2390, 4746, 2436, 4685, 0); }
	 */

    public void hit(int damage, Hits.HitType type) {
        /*if (this.isDead()) {
            return;
        }*/
        if (tabTimer > 0) {
            damage = 0;
        }
        if (damage < 0) {
            damage = 0;
        }
        this.logoutDelay = 16;
        if (System.currentTimeMillis() < this.getCombatDefinitions().getLastEmote() - 600) {
            queuedHits.add(new Hit(damage, type));
        } else if (!this.getMask().isHitUpdate()) {
            this.hits.setHit1(new Hit(damage, type));
            this.getMask().setHitUpdate(true);
            this.getSkills().hit(damage);
        } else if (!this.getMask().isHit2Update()) {
            this.hits.setHit2(new Hit(damage, type));
            this.getMask().setHit2Update(true);
            this.getSkills().hit(damage);
        } else {
            if (this.skills.getHitPoints() <= 0) {
                return;
            }

            queuedHits.add(new Hit(damage, type));

        }
    }
	/*
	 * if (prayer.usingPrayer(0, 22)) { String name =
	 * Misc.formatPlayerNameForDisplay(this.getUsername().replaceAll("_", " "));
	 * if (this.skills.getHitPoints() <= 0) { this.graphics(437, 0);
	 * this.graphics(437, 0); retributionFlames();
	 * if(!this.getCombat().Multi(this)) { this.getFrames().sendChatMessage(0,
	 * "Sorry "+name+", but you can only use this prayer in ::multi"); return; }
	 * for (Player target2 : World.getPlayers()) { if (this == target2)
	 * continue; if(target2.getCombat().isSafe(this)) { return; }
	 * if(this.getCombat().isSafe(target2)) { return; } boolean withinReach =
	 * Misc.getDistance(this.getLocation().getX(), this.getLocation().getY(),
	 * target2.getLocation().getX(), target2.getLocation().getY()) < 2; if
	 * (withinReach && !target2.getCombat().isSafe(this)) {
	 * target2.hit(Misc.random((int)
	 * (skills.getLevelForXp(Skills.PRAYER)*2.5))); } } } } if
	 * (prayer.usingPrayer(1, 17)) { String name =
	 * Misc.formatPlayerNameForDisplay(this.getUsername().replaceAll("_", " "));
	 * if (this.skills.getHitPoints() <= 0) { this.animate(12583);
	 * this.graphics(2259, 180); this.graphics(2259, 180); WrathBombs();
	 * if(!this.getCombat().Multi(this)) { this.getFrames().sendChatMessage(0,
	 * "Sorry "+name+", but you can only use this prayer in ::multi"); return; }
	 * for (Player target2 : World.getPlayers()) { if (this == target2)
	 * continue; boolean withinReach =
	 * Misc.getDistance(this.getLocation().getX(), this.getLocation().getY(),
	 * target2.getLocation().getX(), target2.getLocation().getY()) < 3;
	 * if(target2.getCombat().isSafe(this)) { return; }
	 * if(this.getCombat().isSafe(target2)) { return; } if (withinReach &&
	 * !this.getCombat().isSafe(this)) { target2.hit(Misc.random((int)
	 * (skills.getLevelForXp(Skills.PRAYER) * 2.5))); } } } } }
	 */

    /*
     * public void WrathBombs() { stillGraphic(2260, 0, 0, getXcoord()+1,
     * getYcoord()-1, 60); stillGraphic(2260, 0, 2, getXcoord()+1,
     * getYcoord()+1, 60); stillGraphic(2260, 0, 6, getXcoord()-1,
     * getYcoord()-1, 60); stillGraphic(2260, 0, 4, getXcoord()-1,
     * getYcoord()+1, 60);
     *
     * stillGraphic(2260, 0, 5, getXcoord()+2, getYcoord()+2, 40);
     * stillGraphic(2260, 0, 2, getXcoord()-2, getYcoord()-2, 40);
     * stillGraphic(2260, 0, 6, getXcoord()+2, getYcoord()-2, 40);
     * stillGraphic(2260, 0, 3, getXcoord()-2, getYcoord()+2, 40);
     * stillGraphic(2260, 0, 7, getXcoord()+2, getYcoord(), 40);
     * stillGraphic(2260, 0, 1, getXcoord()-2, getYcoord(), 40);
     * stillGraphic(2260, 0, 0, getXcoord(), getYcoord()+2, 40);
     * stillGraphic(2260, 0, 4, getXcoord(), getYcoord()-2, 40); }
     *
     * public void retributionFlames() { stillGraphic(438, 1, 0, getXcoord(),
     * getYcoord()-1, 35); stillGraphic(438, 1, 1, getXcoord()-1, getYcoord()-1,
     * 35); stillGraphic(438, 1, 2, getXcoord()-1, getYcoord(), 35);
     * stillGraphic(438, 1, 3, getXcoord()-1, getYcoord()+1, 35);
     * stillGraphic(438, 1, 4, getXcoord(), getYcoord()+1, 35);
     * stillGraphic(438, 1, 5, getXcoord()+1, getYcoord()+1, 35);
     * stillGraphic(438, 1, 6, getXcoord()+1, getYcoord(), 35);
     * stillGraphic(438, 1, 7, getXcoord()+1, getYcoord()-1, 35); } public void
     * stillGraphic(int id, int height, int rotation, int X, int Y, int Delay) {
     * if (height == 1) height = 65535; for (Player target2 :
     * World.getPlayers()) { Player player = (Player) target2; boolean withinReach =
     * Misc.getDistance(X, Y, target2.getLocation().getX(),
     * target2.getLocation().getY()) < 60; if (withinReach) {
     * this.getFrames().stillGraphic(id, height, rotation, X, Y, Delay); } } }
     */
    public void stillGraphic(int id, int height, int rotation, int X, int Y, int Delay) {
        if (height == 1)
            height = 65535;
        for (Player target2 : World.getPlayers()) {
            Player p = target2;
            boolean withinReach = Misc.getDistance(X, Y, target2.getLocation().getX(),
                    target2.getLocation().getY()) < 60;
            if (withinReach) {
                this.getFrames().stillGraphic(id, height, rotation, X, Y, Delay);
            }
        }
    }

    @Override
    public void hit(int damage) {
       /* if (this.isDead()) {
            return;
        }*/
        if (tabTimer > 0) {
            damage = 0;
        }
        if (damage < 0) {
            damage = 0;
        }
        if (damage > this.skills.getHitPoints())
            damage = this.skills.getHitPoints();
        if (damage == 0) {
            hit(damage, Hits.HitType.NO_DAMAGE);
            // } else if(damage >= 340 && wep == 5698) {
            // hit(damage, Hits.HitType.DUNGEON_DAMAGE);
        } else if (damage >= 700) {
            hit(damage, Hits.HitType.DUNGEON_DAMAGE);
        } else if (damage >= 100) {
            hit(damage, Hits.HitType.NORMAL_BIG_DAMAGE);
        } else {
            hit(damage, Hits.HitType.NORMAL_DAMAGE);
        }
    }

    public void hit(int damage, Player opp) {
        target = opp.getUsername().replace("_", " ").toLowerCase();
       /* if (this.isDead() || opp.isDead()) {
            return;
        }*/
        if (tabTimer > 0) {
            damage = 0;
        }
        if (damage < 0) {
            damage = 0;
        }
		/*
		 * int weaponID = this.getEquipment().get(3).getId(); if(weaponID ==
		 * 7806) { opp.graphics(435); opthis.getFrames().sendChatMessage(0,
		 * "The other player healed 10 hitpoints off you.");
		 * this.getSkills().heal(10); }
		 */
        if (!prayer.usingPrayer(1, 17) && this.isDead()) {
            return;
        }


        this.getSkills().killerName = opp.getUsername();
        // int wep = this.getEquipment().getEquipment().get(3).getId();
        if (damage > this.skills.getHitPoints())
            damage = this.skills.getHitPoints();
        if (damage == 0) {
            hit(damage, Hits.HitType.NO_DAMAGE);
       /* } else if (damage >= 700) {
            hit(damage, Hits.HitType.DUNGEON_DAMAGE);*/
        } else if (damage >= 100) {
            hit(damage, Hits.HitType.NORMAL_BIG_DAMAGE);
        } else {
            hit(damage, Hits.HitType.NORMAL_DAMAGE);
        }
    }

    @Override
    public void resetTurnTo() {
        this.mask.setTurnToIndex(-1);
        this.mask.setTurnToReset(true);
        this.mask.setTurnToUpdate(true);
    }

    @Override
    public void turnTemporarilyTo(Entity entity) {
        // TODO Auto-generated method stub
        this.mask.setTurnToIndex(entity.getClientIndex());
        this.mask.setTurnToReset(true);
        this.mask.setTurnToUpdate(true);
    }

    public void turnTemporarilyTo(RSTile location) {
        this.mask.setTurnToLocation(location);
        this.mask.setTurnToUpdate1(true);
    }

    @Override
    public void turnTo(Entity entity) {
        this.mask.setTurnToIndex(entity.getClientIndex());
        this.mask.setTurnToReset(false);
        this.mask.setTurnToUpdate(true);
    }

    public Mask getMask() {
        return mask;
    }

    public void setMask(Mask mask) {
        this.mask = mask;
    }

    public Appearence getAppearence() {
        return appearence;
    }

    public void setAppearence(Appearence appearence) {
        this.appearence = appearence;
    }

    public StaffRights getStaffRights() {
        return staffRights;
    }

    /*
     * Set the players staff staffRights
     * @StaffRights
     */
    public void setStaffRights(StaffRights rights) {
        this.staffRights = rights;
    }

    public void setPlayerRights(StaffRights rights) {
        this.staffRights = rights;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public InterfaceManager getIntermanager() {
        return intermanager;
    }

    public void setIntermanager(InterfaceManager intermanager) {
        this.intermanager = intermanager;
    }

    public CombatDefinitions getCombatDefinitions() {
        return combatdefinitions;
    }

    public void setCombatDefinitions(CombatDefinitions combat) {
        this.combatdefinitions = combat;
    }

    public TradeSession getTradeSession() {
        return this.currentTradeSession;
    }

    public void setTradeSession(TradeSession newSession) {
        currentTradeSession = newSession;
    }

    public Player getTradePartner() {
        return tradePartner;
    }

    public void setTradePartner(Player tradePartner) {
        this.tradePartner = tradePartner;
    }

    public PriceCheck getPriceCheck() {
        return pricecheck;
    }

    public void setPriceCheck(PriceCheck pricecheck) {
        this.pricecheck = pricecheck;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    // Bank Tabs
    public int getCurrentBankTab() {
        return currentBankTab;
    }

    public void setCurrentBankTab(int i) {
        this.currentBankTab = i;
    }

    public DuelArena getDuelSession() {
        return this.currentDuelSession;
    }

    public void setDuelSession(DuelArena newSession) {
        currentDuelSession = newSession;
    }

    public Player getDuelPartner() {
        return duelPartner;
    }

    public void setDuelPartner(Player duelPartner) {
        this.duelPartner = duelPartner;
    }

    public Prayer getPrayer() {
        return prayer;
    }

    public void setPrayer(Prayer prayer) {
        this.prayer = prayer;
    }

    public Queue<Hit> getQueuedHits() {
        return queuedHits;
    }

    public void setQueuedHits(Queue<Hit> queuedHits) {
        this.queuedHits = queuedHits;
    }

    public Hits getHits() {
        return hits;
    }

    public void setHits(Hits hits) {
        this.hits = hits;
    }

    public MusicManager getMusicmanager() {
        return musicmanager;
    }

    public void setMusicmanager(MusicManager musicmanager) {
        this.musicmanager = musicmanager;
    }

    public Banking getBank() {
        return bank;
    }

    public void setBank(Banking bank) {
        this.bank = bank;
    }

    private int getFreeIndex() {
        for (int index = 0; index < loadedIcons.length; index++)
            if (loadedIcons[index] == null)
                return index;
        return -1;
    }

    public void addHintIcon(Entity target, int arrowType, int modelId, boolean saveIcon) {
        int index = 1;
        if (index != -1) {
            HintIcon icon = new HintIcon(target.getIndex(), target instanceof Player ? 10 : 1, arrowType, modelId, index);
            p.getFrames().sendHintIcon(icon);
            if (saveIcon)
                loadedIcons[index] = icon;
        }

    }

    public void setHinticonmanager(HintIconManager hinticonmanager) {
        this.hinticonmanager = hinticonmanager;
    }

    public MinigameManager getMinigamemanager() {
        return Minigamemanager;
    }

    public void setMinigamemanager(MinigameManager minigamemanager) {
        Minigamemanager = minigamemanager;
    }

    public Gni getGni() {
        return gni;
    }

    public void setGni(Gni gni) {
        this.gni = gni;
    }

    public int getCombatDelay() {
        return combatDelay;
    }

    public void setCombatDelay(int combatDelay) {
        this.combatDelay = combatDelay;
    }

    public void selectedRanged() {
        // TODO Auto-generated method stub

    }

    public void selectedMelee() {
        // TODO Auto-generated method stub

    }

    public void selectedMagic() {
        // TODO Auto-generated method stub

    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean b) {
        this.isAttacking = b;
    }

    public Titles getTitles() {
        return titles;
    }

    public void setTitles(Titles titles) {
        this.titles = titles;
    }

    public void save() {
        PlayerSaving.save(this, false);
    }

    public Stopwatch getClickDelay() {
        return clickDelay;
    }

    /**
     * Get the formatted name for your character
     *
     * @return
     */
    public String getFormattedName() {
        return Misc.formatPlayerNameForDisplay(this.getUsername().replaceAll("_", " "));
    }

    /**
     * Returns player size.
     */
    @Override
    public int getSize() {
        return 1;
    }

    public void stopCoordinateEvent() {
        this.coordinateEvent = null;
        this.attempts = 0;
    }

    public CoordinateEvent getCoordinateEvent() {
        return coordinateEvent;
    }

    public void setCoordinateEvent(CoordinateEvent coordinateEvent) {
        if (this.coordinateEvent != null)
            return;
        this.coordinateEvent = coordinateEvent;
        World.submitCoordinateEvent(this, coordinateEvent);
    }

    private transient PlayerUpdate playerUpdate;

    /**
     * @return the playerUpdate
     */
    public PlayerUpdate getPlayerUpdate() {
        return playerUpdate;
    }

    /**
     * @param playerUpdate
     *            the playerUpdate to set
     */
    public void setPlayerUpdate(PlayerUpdate playerUpdate) {
        this.playerUpdate = playerUpdate;
    }

    public Stopwatch getCommandDelay() {
        return commandDelay;
    }

    public String getSerialAddress() {
        return serialAddress;
    }

    public void setSerialAddress(String serialAddress) {
        this.serialAddress = serialAddress;
    }

    public enum Locations {

        LOCATION_1(3154, 3158, 3618, 3622, RSTile.createRSTile(3156, 3620, 0), 14829),
        LOCATION_2(3217, 3221, 3654, 3658, RSTile.createRSTile(3219, 3656, 0), 14830),
        LOCATION_3(3033, 3037, 3730, 3734, RSTile.createRSTile(3035, 3732, 0), 14827),
        LOCATION_4(3104, 3108, 3792, 3796, RSTile.createRSTile(3106, 3794, 0), 14828),
        LOCATION_5(2978, 2982, 3864, 3868, RSTile.createRSTile(2980, 3866, 0), 14826),
        LOCATION_6(3305, 3309, 3914, 3918, RSTile.createRSTile(3307, 3916, 0), 14831);

        private int topLeftX, bottomRightX, bottomRightY, topLeftY;
        private RSTile location;
        private int objectId;
        private RSObject object;

        Locations(int topLeftX, int bottomRightX, int bottomRightY, int topLeftY, RSTile location, int objectId) {
            this.topLeftX = topLeftX;
            this.bottomRightX = bottomRightX;
            this.bottomRightY = bottomRightY;
            this.topLeftY = topLeftY;
            this.location = location;
            this.objectId = objectId;
        }

        public int getTopLeftX() {
            return topLeftX;
        }

        public int getBottomRightX() {
            return bottomRightX;
        }

        public int getBottomRightY() {
            return bottomRightY;
        }

        public int getTopLeftY() {
            return topLeftY;
        }

        public RSTile getLocation() {
            return location;
        }

        public int getObjectId() {
            return objectId;
        }
    }

    public int getClientCrownId() {
        if(getStaffRights().getClientCrownId() > 0) {
            return getStaffRights().getClientCrownId();
        } else if(getDonatorRights().getClientCrownId() > 0) {
            return getDonatorRights().getClientCrownId();
        }
        return 0;
    }
}
