package edu.Kennesaw.ksumcspeedrun.Commands;

import edu.Kennesaw.ksumcspeedrun.Main;
import edu.Kennesaw.ksumcspeedrun.Objects.Teams.Team;
import edu.Kennesaw.ksumcspeedrun.Objects.Teams.TeamManager;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandTeam implements BasicCommand {

    Main plugin;
    TeamManager tm;

    public CommandTeam(Main plugin) {
        this.plugin = plugin;
        this.tm = plugin.getSpeedrun().getTeams();
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, @NotNull String @NotNull [] args) {

        if (commandSourceStack.getSender() instanceof Player p) {

            List<Team> teams = tm.getTeams();

            if (args.length != 1) {

                p.sendMessage(plugin.getMessages().getTeamHelp());

            } else {

                if (plugin.getSpeedrun().teamCooldown.contains(p)) {
                    p.sendMessage(plugin.getMessages().getTeamCooldownMessage());
                    return;
                }

                for (Team team : teams) {

                    if (args[0].equalsIgnoreCase(team.getStrippedName().replace(' ', '_'))) {

                        Team oldTeam = tm.getTeam(p);

                        if (oldTeam != null) {

                            if (oldTeam.equals(team)) {
                                p.sendMessage(plugin.getMessages().getAlreadyOnTeam());
                                return;
                            }

                            oldTeam.removePlayer(p);
                            tm.getTeamInventory().updateTeamInventory(oldTeam);
                        }

                        team.addPlayer(p);
                        tm.getTeamInventory().updateTeamInventory(team);

                        plugin.getSpeedrun().teamCooldown.add(p);

                        Bukkit.getAsyncScheduler().runDelayed(plugin, scheduledTask ->
                                        plugin.getSpeedrun().teamCooldown.remove(p),
                                plugin.getConfig().getInt("teams.inventory.cooldown"), TimeUnit.SECONDS);
                        return;

                    }

                }

                p.sendMessage(plugin.getMessages().getTeamNotFound(args[0]));

            }

        }

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 0) {
            for (Team team : tm.getTeams()) {
                suggestions.add(team.getStrippedName().replace(' ', '_'));
            }
        } else if (args.length == 1) {
            addMatchingSuggestions(suggestions, args[0], tm.getStrippedTeamNames(true));
        }
        return suggestions;
    }

    // This method makes it easier to add multiple possible suggestions to a list suggestion list & match them
    private void addMatchingSuggestions(List<String> suggestions, String arg, List<String> teamNames) {
        for (String suggestion : teamNames) {
            if (suggestion.toLowerCase().startsWith(arg.toLowerCase())) {
                suggestions.add(suggestion);
            }
        }
    }

    @Override
    public @Nullable String permission() {
        return "ksu.speedrun.user";
    }
}
