package edu.Kennesaw.ksumcspeedrun;

import edu.Kennesaw.ksumcspeedrun.Objects.Objective.Objective;
import edu.Kennesaw.ksumcspeedrun.Objects.Objective.ObjectiveManager;
import edu.Kennesaw.ksumcspeedrun.Objects.Teams.Team;
import edu.Kennesaw.ksumcspeedrun.Objects.Teams.TeamManager;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/* Speedrun Object Class, centerpoint of logic for speedrun events
   Contains various setters & getters for all speedrun attributes */
public class Speedrun {

    Main plugin;

    // Speedrun Attributes
    private long seed;
    private int border;
    private int timeLimit;
    private TimeUnit timeUnit;
    private int spawnRadius;
    private int playerLimit;

    // "isVerified" meaning objectives can be completed on world seed within world border constraints
    private boolean isVerified;

    // True if the Speedrun has started
    private boolean isStarted;

    // ObjectiveManager contains the list of all Objectives & all incomplete objectives, can be modified
    private final ObjectiveManager objectives;
    private final TeamManager teams;

    // GameRules set by admins will be located in this HashMap
    private final HashMap<GameRule<?>, Boolean> gameRules;

    public Map<UUID, Player> combatLog;
    public Map<UUID, ScheduledTask> combatTasks;

    public Map<Location, Player> bedLog;

    // Main Constructor with default attributes assigned
    public Speedrun(Main plugin) {

        this.plugin = plugin;

        Random rand = new Random();
        this.seed = rand.nextInt();
        this.border = 5000;
        this.timeLimit = 60;
        this.timeUnit = TimeUnit.MINUTES;
        this.spawnRadius = 300;

        objectives = new ObjectiveManager();
        teams = new TeamManager();
        gameRules = new HashMap<>();

        combatLog = new HashMap<UUID, Player>();
        combatTasks = new HashMap<UUID, ScheduledTask>();

        bedLog = new HashMap<Location, Player>();

    }

    // Setters & Getters below should be quite self-explanatory

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public long getSeed() {
        return seed;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    public int getBorder() {
        return border;
    }

    public void setTimeLimit(int time) {
        this.timeLimit = time;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setSpawnRadius(int spawnRadius) {
        this.spawnRadius = spawnRadius;
    }

    public int getSpawnRadius() {
        return spawnRadius;
    }

    public void addObjective(Objective objective) {
        if (objective != null) {
            objectives.addObjective(objective);
        }
    }

    public void remObjective(int objectiveNum) {
        objectives.removeObjective(objectiveNum);
    }

    public ObjectiveManager getObjectives() {
        return objectives;
    }

    public void generateDefaultTeams() {
        teams.addTeam(new Team(plugin, "white"));
        teams.addTeam(new Team(plugin, "lightGray"));
        teams.addTeam(new Team(plugin, "gray"));
        teams.addTeam(new Team(plugin, "black"));
        teams.addTeam(new Team(plugin, "brown"));
        teams.addTeam(new Team(plugin, "red"));
        teams.addTeam(new Team(plugin, "orange"));
        teams.addTeam(new Team(plugin, "yellow"));
        teams.addTeam(new Team(plugin, "lime"));
        teams.addTeam(new Team(plugin, "green"));
        teams.addTeam(new Team(plugin, "cyan"));
        teams.addTeam(new Team(plugin, "lightBlue"));
        teams.addTeam(new Team(plugin, "blue"));
        teams.addTeam(new Team(plugin, "purple"));
        teams.addTeam(new Team(plugin, "magenta"));
        teams.addTeam(new Team(plugin, "pink"));

    }

    public void addTeam(Team team) {
        if (team != null) {
            teams.addTeam(team);
        }
    }

    public void remTeam(Team team) {
        teams.removeTeam(team);
    }

    public TeamManager getTeams() {
        return teams;
    }

    public void setGameRule(GameRule<?> gameRule, boolean value) {
        gameRules.put(gameRule, value);
    }

    public boolean getGameRule(GameRule<?> gameRule) {
        return gameRules.getOrDefault(gameRule, false);
    }

    public void setPlayerLimit(int playerLimit) {
        this.playerLimit = playerLimit;
    }

    public int getPlayerLimit() {
        return playerLimit;
    }

    public void verifyMap() {
        // verify map
        isVerified = true;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setStarted() {
        isStarted = true;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void endGame() {
        isStarted = false;
    }

}
