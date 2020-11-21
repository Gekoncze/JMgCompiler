package cz.mg.compiler.tasks.mg.builder.block.root.command;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalBlockCommand;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalCaseCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildExpressionPartTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildCaseCommandTask extends MgBuildBlockCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildExpressionPartTask.class,
        MgBuildCaseCommandTask.class,
        (source, destination) -> destination.command = new MgLogicalCaseCommand(source.getExpression())
    );

    @Output
    private MgLogicalCaseCommand command;

    public MgBuildCaseCommandTask(Block block) {
        super(block);
    }

    public MgLogicalCaseCommand getCommand() {
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
