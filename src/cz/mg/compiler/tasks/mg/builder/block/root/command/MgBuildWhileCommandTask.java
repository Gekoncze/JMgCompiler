package cz.mg.compiler.tasks.mg.builder.block.root.command;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalBlockCommand;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalWhileCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildExpressionPartTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildWhileCommandTask extends MgBuildBlockCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildExpressionPartTask.class,
        MgBuildWhileCommandTask.class,
        (source, destination) -> destination.command = new MgLogicalWhileCommand(source.getExpression())
    );

    @Output
    private MgLogicalWhileCommand command;

    public MgBuildWhileCommandTask(Block block) {
        super(block);
    }

    public MgLogicalWhileCommand getCommand() {
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
