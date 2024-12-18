package edu.Kennesaw.ksumcspeedrun.Objects.Objective;

import edu.Kennesaw.ksumcspeedrun.Objects.Teams.SoloTeam;
import edu.Kennesaw.ksumcspeedrun.Objects.Teams.TrueTeam;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages all objectives and holds them in a single list which can be adjusted using the provided methods.
 * It keeps track of the objectives and their total weight.
 */
public class ObjectiveManager {

    // An instance of ObjectiveManager holds a list of Objectives
    private List<Objective> objectives;

    private int totalWeight;

    // Class is initialized with an empty constructor, an empty list of Objectives is made
    public ObjectiveManager() {
        this.objectives = new ArrayList<>();
        totalWeight = 0;
    }

    // Add an objective to the objective list
    public void addObjective(Objective objective) {
        objectives.add(objective);
        totalWeight += objective.getWeight();
    }

    // Returns the list of objectives
    @SuppressWarnings("unused")
    public List<Objective> getObjectives() {
        return objectives;
    }

    // Returns the list of incomplete objectives
    public List<Objective> getIncompleteObjectives(TrueTeam trueTeam) {
        List<Objective> incompleteObjectives = new ArrayList<>();
        for (Objective o : objectives) {
            if (o.isIncomplete(trueTeam)) {
                incompleteObjectives.add(o);
            }
        }
        return incompleteObjectives;
    }

    // Returns the list of incomplete objectives
    public List<Objective> getIncompleteObjectives(SoloTeam player) {
        List<Objective> incompleteObjectives = new ArrayList<>();
        for (Objective o : objectives) {
            if (o.isIncomplete(player)) {
                incompleteObjectives.add(o);
            }
        }
        return incompleteObjectives;
    }

    // Returns the length of the list of objectives
    @SuppressWarnings("unused")
    public int getLength() {
        return objectives.size();
    }

    /* Remove objective by the number that it appears in the admin objective book
       Adjust total weight to not include weight of removed objective */
    public void removeObjective(int number) {
        totalWeight-=objectives.get(number).getWeight();
        objectives.remove(number);
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void clearObjectives() {
        this.objectives = new ArrayList<>();
    }

    public Objective getObjective(int number) {
        return objectives.get(number);
    }
}
