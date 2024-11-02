package edu.Kennesaw.ksumcspeedrun.Objects.Teams;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamSpawner {

    public static void spawnTeamsInCircle(World world, TeamManager tm, double radius) {
        // Calculate angle step based on the number of teams
        double angleStep = 2 * Math.PI / tm.getTeams().size();

        for (int i = 0; i < tm.getTeams().size(); i++) {
            double angle = i * angleStep;
            Location teamSpawnLocation = findSafeLocation(world, angle, radius);

            // Teleport each player to the team's spawn location and set their respawn point
            for (Player player : tm.getTeams().get(i).getPlayers()) {

                player.teleport(teamSpawnLocation);

                // Set the player's respawn location
                player.setRespawnLocation(teamSpawnLocation);
            }
        }
    }

    public static void spawnPlayersInCircle(World world, List<SoloTeam> participatingPlayers, double radius) {

        // Calculate angle step based on the number of teams
        double angleStep = 2 * Math.PI / participatingPlayers.size();

        for (Player p : participatingPlayers) {
            double angle = participatingPlayers.indexOf(p) * angleStep;
            Location teamSpawnLocation = findSafeLocation(world, angle, radius);

            // Teleport each player to the team's spawn location and set their respawn point
            p.teleport(teamSpawnLocation);

            // Set the player's respawn location
            p.setRespawnLocation(teamSpawnLocation);
        }
    }

    private static Location findSafeLocation(World world, double initialAngle, double radius) {
        double angle = initialAngle;

        for (int attempts = 0; attempts < 36; attempts++) { // Limit to 36 attempts (10° each) ***toggleable maybe?***
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);
            int highestY = world.getHighestBlockYAt((int) x, (int) z);
            Location potentialLocation = new Location(world, x, highestY + 1, z); // +1 to spawn above the block
            Block block = world.getBlockAt((int) x, highestY, (int) z);

            // Check if block below is solid and not water/lava
            if (block.getType().isSolid() && block.getType() != Material.LAVA && block.getType() != Material.WATER) {
                return potentialLocation; // Safe spot found
            }

            // Else increment the angle slightly and try a new location
            angle += Math.toRadians(10); // Move 10° around the circle
        }

        // Default to initial angle if no safe location is found within 36 attempts
        double x = radius * Math.cos(initialAngle);
        double z = radius * Math.sin(initialAngle);
        int highestY = world.getHighestBlockYAt((int) x, (int) z);
        return new Location(world, x, highestY + 1, z);
    }
}