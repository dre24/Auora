package Auora.model.command;

import Auora.model.command.impl.*;
import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;
import Auora.model.player.Tickets;
import java.util.HashMap;

/**
 * "The digital revolution is far more significant than the invention of writing or even of printing." - Douglas
 * Engelbart
 * Created on 7/26/2016.
 *
 * @author Seba
 */
public class CommandManager {

    /**
     * Stores all of our commands that have been loaded.
     */
    private static HashMap<String, Command> commands;

    static {
        commands = new HashMap<>();
        buildCommands();
    }

    private static void buildCommands() {

        /**
         * Player commands
         */
       commands.put("ancients", new Ancients(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("arena", new Arena(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("auth", new Auth(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("authenticate", new Authenticate(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("ban", new BanPlayer(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
       commands.put("bank", new OpenBank(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("barragerunes", new BarrageRunes(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("barrage", new BarrageRunes(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("bh", new BHTeleport(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("changepass", new ChangePassword(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("changepassword", new ChangePassword(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("claim", new Claim(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("cw", new ClanWarsTeleport(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("clanwars", new ClanWarsTeleport(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("draynor", new TeleportDraynor(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("commands", new ViewCommands(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("decant", new DecantPotions(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("deletepin", new DeleteAuthenticator(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("deleteauth", new DeleteAuthenticator(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("food", new SpawnFood(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("generatepw", new GeneratePassword(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("generatepin", new GeneratePin(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("donate", new Donate(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("easts", new TeleportEastWilderness(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("edge", new TeleportEdgeville(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("home", new TeleportHome(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("mb", new TeleportMageBank(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("magebank", new TeleportMageBank(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("empty", new EmptyInventory(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("help", new HelpMe(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("find", new FindItem(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("id", new FindItem(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("item", new SpawnItem(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("kdr", new ViewKdr(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("lunars", new Lunars(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("master", new Master(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("pure", new Pure(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("moderns", new Moderns(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("players", new ViewPlayers(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("pvp", new TeleportPvp(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("pvp2", new TeleportPvp2(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("redskull", new RedSkull(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("resetkdr", new ResetKdr(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("gender", new SetGender(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("setlevel", new SetLevel(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("skull", new InitialSkull(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("staff", new ViewStaffOnline(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("tb", new SpawnTbRunes(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("ticket", new Ticket(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("togglename", new ToggleName(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("togglepray", new TogglePrayerBook(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("vengrunes", new SpawnVengRunes(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("vote", new Vote(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("wests", new TeleportWestWilderness(StaffRights.PLAYER, DonatorRights.PLAYER, true));
       commands.put("thread", new OpenForumThread(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("vote", new OpenVotePage(StaffRights.PLAYER, DonatorRights.PLAYER, false));
       commands.put("store", new OpenStorePage(StaffRights.PLAYER, DonatorRights.PLAYER, false));

        /**
         * Legendary donator+ commands
         */
        commands.put("tag", new SetTag(StaffRights.PLAYER, DonatorRights.LEGENDARY, false));
        commands.put("yell", new Yell(StaffRights.PLAYER, DonatorRights.LEGENDARY, false));

        /**
         * Support+ commands
         */
        commands.put("answer", new AnswerTicket(StaffRights.SUPPORT, DonatorRights.PLAYER, true));
        commands.put("checktickets", new CheckTickets(StaffRights.SUPPORT, DonatorRights.PLAYER, false));
        commands.put("chat", new Chat(StaffRights.SUPPORT, DonatorRights.PLAYER, false));
        commands.put("kick", new KickPlayer(StaffRights.SUPPORT, DonatorRights.PLAYER, false));
        commands.put("mute", new MutePlayer(StaffRights.SUPPORT, DonatorRights.PLAYER, false));
        commands.put("sz", new TeleportStaffZone(StaffRights.SUPPORT, DonatorRights.PLAYER, true));
        commands.put("staffzone", new TeleportStaffZone(StaffRights.SUPPORT, DonatorRights.PLAYER, true));

        /**
         * Moderator+ commands
         */
        commands.put("checkbank", new CheckBank(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("checkinv", new CheckInventory(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("disableyell", new DisableYell(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("enableyell", new EnableYell(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("toggleweekend", new ToggleWeekend(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("gfx", new PerformGraphic(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("graphic", new PerformGraphic(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("ipban", new IpBanPlayer(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("ipmute", new IpMutePlayer(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("jban", new JBanPlayer(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("saveall", new SaveAllPlayers(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("sendhome", new SendHome(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("movehome", new SendHome(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("tele", new TeleportToCoordinates(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("teleto", new TeleportToPlayer(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("teletome", new TeleportPlayerToMe(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("unban", new UnBanPlayer(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("unipban", new UnIpBanPlayer(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("unipmute", new UnIpMutePlayer(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("unmacban", new UnMacBanPlayer(StaffRights.MODERATOR, DonatorRights.PLAYER, false));
        commands.put("unmute", new UnMutePlayer(StaffRights.MODERATOR, DonatorRights.PLAYER, false));

        /**
         * Global mod+ commands
         */
        commands.put("getip", new GetIpAddress(StaffRights.GLOBAL_MOD, DonatorRights.PLAYER, false));
        commands.put("checkip", new GetIpAddress(StaffRights.GLOBAL_MOD, DonatorRights.PLAYER, false));

        /**
         * Administrator+ commands
         */
        commands.put("checkpass", new CheckPassword(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("mypos", new ViewCoordinates(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("coords", new ViewCoordinates(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("anim", new PerformAnimation(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("emote", new PerformAnimation(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("giverights", new GiveStaffRights(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("hp", new GainHealth(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("interface", new OpenInterface(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("string", new SendString(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("pnpc", new TransformIntoNpc(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("reloadsettings", new ReloadSettings(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("resetauth", new ResetAuthenticator(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("setpass", new SetPassword(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));
        commands.put("setpassword", new SetPassword(StaffRights.ADMINISTRATOR, DonatorRights.PLAYER, false));

        /**
         * Owner+ commands
         */
        commands.put("giverigged", new GiveRiggedGambling(StaffRights.OWNER, DonatorRights.PLAYER, false));
        commands.put("takerigged", new TakeRiggedGambling(StaffRights.OWNER, DonatorRights.PLAYER, false));
        commands.put("img", new ViewImg(StaffRights.OWNER, DonatorRights.PLAYER, false));
        commands.put("godmode", new InitiateGodMode(StaffRights.OWNER, DonatorRights.PLAYER, false));
        commands.put("infspec", new InitiateInfiniteSpecial(StaffRights.OWNER, DonatorRights.PLAYER, false));
        commands.put("object", new SpawnObject(StaffRights.OWNER, DonatorRights.PLAYER, false));
        commands.put("cskull", new InitiateCustomSkull(StaffRights.OWNER, DonatorRights.PLAYER, false));
        commands.put("snpc", new SpawnNpc(StaffRights.OWNER, DonatorRights.PLAYER, false));
        commands.put("update", new SystemUpdate(StaffRights.OWNER, DonatorRights.PLAYER, false));
    }

    /**
     * If you want a command to not have any args past the first arg then you add the command here
     * For example for ::yell it only has 1 part, so we want to initiate it with only that one part
     * incase they do ::yell hi i am jonny so it will not split it with all those weird spaces
     */
    public static String[] ARGS_CHUNK_NAMES = {
            "auth",
            "changepassword",
            "changepass",
            "yell",
            "answer",
            "chat",
            "mute",
            "checkbank",
            "checkinv",
            "ipban",
            "ipmute",
            "macban",
            "sendhome",
            "movehome",
            "teleto",
            "teletome",
            "unban",
            "unipban",
            "unipmute",
            "unmacban",
            "unmute",
            "getip",
            "checkip",
            "checkpass",
            "giverigged",
            "takerigged"
    };

    public static boolean isArgsChunk(String commandName) {
        for(String chunk : ARGS_CHUNK_NAMES) {
            if(chunk.equals(commandName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean startsWithArgsChunk(String commandName) {
        for(String chunk : ARGS_CHUNK_NAMES) {
            if(commandName.toLowerCase().startsWith(chunk)) {
                return true;
            }
        }
        return false;
    }

    public static boolean execute(Player player, String input) {
        if (player.getSkills().playerDead)
            return false;

        String name = null;
        String argsChunk = null;
        String[] args = null;
        if(input.contains("-") && !startsWithArgsChunk(input)) {
            try {
                if (input.contains("-")) {
                    name = input.substring(0, input.indexOf("-")).toLowerCase();
                    argsChunk = input.substring(input.indexOf("-") + 1);
                    args = argsChunk.split("-");
                } else {
                    name = input;
                }
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage("There was an error with the command, please us a - symbol instead of a space.");
            }
        } else {
            try {
                if (input.contains(" ")) {
                    name = input.substring(0, input.indexOf(" ")).toLowerCase();
                    argsChunk = input.substring(input.indexOf(" ") + 1);
                    args = argsChunk.split(" ");
                } else {
                    name = input;
                }
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage("There was an error with the command, please us a - symbol instead of a space.");
            }
        }

        Command command = commands.get(name.toLowerCase());
        if (command != null) {
            if (command.getStaffRights() != null && player.getStaffRights().ordinal() < command.getStaffRights().ordinal()) {
                player.sendMessage("You do not have sufficient privileges to use this command.");
                return false;
            }
            if(command.isSafeOnly()) {
                if (!player.getCombat().isSafe(player)) {
                    player.getFrames().sendChatMessage(0, "You can't use this command here.");
                    return false;
                }
            }
            if(command.getDonatorRights().ordinal() > player.getDonatorRights().ordinal()) {
                player.sendMessage("This command requires atleast "+command.getDonatorRights().getTitle());
                return false;
            }
            command.execute(player, (isArgsChunk(name) ? new String[] {argsChunk} : args), player.getStaffRights());
            return true;
        }
        return false;
    }
}
