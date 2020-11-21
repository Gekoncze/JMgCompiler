package cz.mg.compiler.tasks.mg.builder.block.component.command;

import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;


public abstract class MgBuildCommandTask extends MgBuildBlockTask {
    public MgBuildCommandTask(Block block) {
        super(block);
    }
}
