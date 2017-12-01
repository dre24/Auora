package Auora.model.command;

import Auora.model.player.DonatorRights;
import Auora.model.player.Player;
import Auora.model.player.StaffRights;

/**
 * "The digital revolution is far more significant than the invention of writing or even of printing." - Douglas
 * Engelbart
 * Created on 7/26/2016.
 *
 * @author Seba
 */
public abstract class Command {

    /**
     * Constructs our command
     * @param staffRights
     */
    public Command(StaffRights staffRights, DonatorRights donatorRights, boolean safeOnly) {
        this.staffRights = staffRights;
        this.donatorRights = donatorRights;
        this.safeOnly = safeOnly;
    }

    /**
     * Executes the command's actions
     * @param player The player executing the command
     * @param args The args that the player passed threw
     * @param privilege The rights of the player.
     */
    public abstract void execute(Player player, String[] args, StaffRights privilege);

    /**
     * Returns the staff rights needed to execute the command.
     * @return
     */
    public StaffRights getStaffRights() {
        return staffRights;
    }

    /**
     * The player rights needed to execute this command
     */
    private final StaffRights staffRights;

    /**
     * The lowest donator rights possible to use this command
     */
    private final DonatorRights donatorRights;

    /**
     * If the command can't be used in the wilderness set this to true for each command
     */
    private final boolean safeOnly;

    /**
     * Returns if the command is allowed only in safe areas
     * @return
     */
    public boolean isSafeOnly() {
        return safeOnly;
    }

    /**
     * Returns the donator rights for this command
     * @return
     */
    public DonatorRights getDonatorRights() {
        return donatorRights;
    }
}
