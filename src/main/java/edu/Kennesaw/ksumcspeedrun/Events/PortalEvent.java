package edu.Kennesaw.ksumcspeedrun.Events;

import edu.Kennesaw.ksumcspeedrun.Main;
import edu.Kennesaw.ksumcspeedrun.Objects.Objective.EnterObjective;
import edu.Kennesaw.ksumcspeedrun.Objects.Objective.Objective;
import edu.Kennesaw.ksumcspeedrun.Objects.Teams.SoloTeam;
import edu.Kennesaw.ksumcspeedrun.Objects.Teams.Team;
import edu.Kennesaw.ksumcspeedrun.Objects.Teams.TeamManager;
import edu.Kennesaw.ksumcspeedrun.Speedrun;
import edu.Kennesaw.ksumcspeedrun.Structures.Portal;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PortalEvent implements Listener {

    Main plugin;
    private final TeamManager tm;
    private final Speedrun speedrun;

    public PortalEvent(Main plugin) {
        this.plugin = plugin;
        this.speedrun = plugin.getSpeedrun();
        this.tm = speedrun.getTeams();
    }

    @EventHandler
    public void onPlayerPortalMove(PlayerPortalEvent e) {

        System.out.println("Portal Event Triggered");

        Player p = e.getPlayer();

        World.Environment from = e.getFrom().getWorld().getEnvironment();
        World.Environment to = e.getTo().getWorld().getEnvironment();

        Team team = tm.getTeam(p);
        SoloTeam st = null;

        if (team == null) {

            if (!speedrun.getTeamsEnabled()) {

                if (speedrun.soloPlayersContain(p)) {

                    SoloTeam soloPlayer = speedrun.getSoloPlayer(p);

                    for (Objective o : soloPlayer.getIncompleteObjectives()) {

                        if (o.getType().equals(Objective.ObjectiveType.ENTER)) {

                            EnterObjective eo = (EnterObjective) o;

                            System.out.println("Enter objective matched!");

                            if (eo.getTarget() instanceof Portal portal) {

                                if (from.equals(portal.getFrom()) && to.equals(portal.getTo())) {

                                    eo.setComplete(soloPlayer);
                                    return;

                                }

                            }

                        }

                    }

                }
            }
            return;
        }

        for (Objective o : team.getIncompleteObjectives()) {

            if (o.getType().equals(Objective.ObjectiveType.ENTER)) {

                EnterObjective eo = (EnterObjective) o;

                System.out.println("Enter objective matched!");

                if (eo.getTarget() instanceof Portal portal) {

                    if (from.equals(portal.getFrom()) && to.equals(portal.getTo())) {

                        eo.setComplete(team);
                        break;

                    }

                }

            }

        }

    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {

        Player p = e.getPlayer();
        World.Environment from = e.getFrom().getWorld().getEnvironment();
        World.Environment to = e.getTo().getWorld().getEnvironment();

        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_GATEWAY)
                || e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {

            if (from.equals(World.Environment.THE_END) && to.equals(World.Environment.THE_END)) {

                Team team = tm.getTeam(p);
                SoloTeam st = null;

                if (team == null) {

                    if (!speedrun.getTeamsEnabled()) {

                        if (speedrun.soloPlayersContain(p)) {

                            st = speedrun.getSoloPlayer(p);

                            for (Objective o : st.getIncompleteObjectives()) {

                                if (o.getType().equals(Objective.ObjectiveType.ENTER)) {

                                    EnterObjective eo = (EnterObjective) o;

                                    if (eo.getTarget() instanceof Portal portal) {

                                        if (portal.portalType().equals(Portal.PortalType.END_TO_END)
                                                && e.getTo().distance(e.getFrom()) >= 800) {

                                            eo.setComplete(st);
                                            return;

                                        }

                                    }

                                }

                            }

                            return;

                        }

                        return;

                    }

                    return;

                }

                for (Objective o : team.getIncompleteObjectives()) {

                    if (o.getType().equals(Objective.ObjectiveType.ENTER)) {

                        EnterObjective eo = (EnterObjective) o;

                        if (eo.getTarget() instanceof Portal portal) {

                            if (portal.portalType().equals(Portal.PortalType.END_TO_END)
                                    && e.getTo().distance(e.getFrom()) >= 800) {

                                eo.setComplete(team);
                                return;

                            }

                        }

                    }

                }

            }

        } else if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.UNKNOWN)
                    || e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) {

            Team team = tm.getTeam(p);
            SoloTeam st = null;

            if (from.equals(World.Environment.THE_END) && to.equals(World.Environment.NORMAL)) {

                if (team == null) {

                    if (!speedrun.getTeamsEnabled()) {

                        if (speedrun.soloPlayersContain(p)) {

                            SoloTeam soloPlayer = speedrun.getSoloPlayer(p);

                            for (Objective o : soloPlayer.getIncompleteObjectives()) {

                                if (o.getType().equals(Objective.ObjectiveType.ENTER)) {

                                    EnterObjective eo = (EnterObjective) o;

                                    if (eo.getTarget() instanceof Portal portal) {

                                        if (portal.portalType().equals(Portal.PortalType.END_TO_WORLD)) {

                                            eo.setComplete(team);
                                            return;

                                        }

                                    }

                                }

                            }

                        }

                    }

                    return;

                }

                for (Objective o : team.getIncompleteObjectives()) {

                    if (o.getType().equals(Objective.ObjectiveType.ENTER)) {

                        EnterObjective eo = (EnterObjective) o;

                        if (eo.getTarget() instanceof Portal portal) {

                            if (portal.portalType().equals(Portal.PortalType.END_TO_WORLD)) {

                                eo.setComplete(team);
                                break;

                            }

                        }

                    }

                }

            }

        }

    }

}
