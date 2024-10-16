package edu.Kennesaw.ksumcspeedrun.Objects.Objective;

import edu.Kennesaw.ksumcspeedrun.Main;
import org.bukkit.Material;

// Same as other Objective Subclasses, this time a block is a target
public class MineObjective extends Objective {

    private final Material blockTarget;

    public MineObjective(Material blockTarget, int weight, Main plugin) {
        super(ObjectiveType.MINE, weight, plugin);
        this.blockTarget = blockTarget;
        setTargetName(blockTarget.name());
    }

    public MineObjective(Material blockTarget, Main plugin) {
        super(ObjectiveType.MINE, plugin);
        this.blockTarget = blockTarget;
        setTargetName(blockTarget.name());
    }

    public Material getBlockTarget() {
        return blockTarget;
    }

}
