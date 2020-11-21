package cz.mg.compiler.tasks.mg.builder.block.root.command;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalBlockCommand;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalCatchCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildDeclarationTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildCatchCommandTask extends MgBuildBlockCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildDeclarationTask.class,
        MgBuildCatchCommandTask.class,
        (source, destination) -> destination.command = new MgLogicalCatchCommand(source.getVariable())
    );

    @Output
    private MgLogicalCatchCommand command;

    public MgBuildCatchCommandTask(Block block) {
        super(block);
    }

    public MgLogicalCatchCommand getCommand() {
        return command;
    }

    @Override
    protected MgLogicalBlockCommand getOutput() {
        return command;
    }

    @Override
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }
}
