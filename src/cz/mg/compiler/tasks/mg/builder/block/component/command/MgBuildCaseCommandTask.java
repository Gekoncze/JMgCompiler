package cz.mg.compiler.tasks.mg.builder.block.component.command;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedBlockCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCaseCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildExpressionPartTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildCaseCommandTask extends MgBuildBlockCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildExpressionPartTask.class,
        MgBuildCaseCommandTask.class,
        (source, destination) -> destination.command = new MgUnresolvedCaseCommand(source.getExpression())
    );

    @Output
    private MgUnresolvedCaseCommand command;

    public MgBuildCaseCommandTask(Block block) {
        super(block);
    }

    public MgUnresolvedCaseCommand getCommand() {
        return command;
    }

    @Override
    protected MgUnresolvedBlockCommand getOutput() {
        return command;
    }

    @Override
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }
}
