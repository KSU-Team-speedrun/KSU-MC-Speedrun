package edu.Kennesaw.ksumcspeedrun.Objects.Objective;

import edu.Kennesaw.ksumcspeedrun.Main;
import org.bukkit.Material;

/* Same as other objective subclasses, Material is target
   For ObtainObjective, there is an additional possible flag: amount
   The number of items needed to be obtained can be specified */

public class ObtainObjective extends Objective {

    private Material item;
    private int amount;

    Main plugin;

    public ObtainObjective(Material item, Main plugin) {
        super(ObjectiveType.OBTAIN, plugin);
        this.item = item;
        setTargetName(item.name());
    }

    public ObtainObjective(Material item, int weight, Main plugin) {
        super(ObjectiveType.OBTAIN, weight, plugin);
        this.item = item;
        setTargetName(item.name());
    }

    public ObtainObjective(Material item, int weight, int amount, Main plugin) {
        super(ObjectiveType.OBTAIN, weight, plugin);
        this.item = item;
        this.amount = amount;
        setTargetName(item.name());
    }
}
