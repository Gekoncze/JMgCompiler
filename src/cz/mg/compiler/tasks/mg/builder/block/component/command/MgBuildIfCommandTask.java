package cz.mg.compiler.tasks.mg.builder.block.component.command;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedBlockCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedIfCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildExpressionPartTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildIfCommandTask extends MgBuildBlockCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildExpressionPartTask.class,
        MgBuildIfCommandTask.class,
        (source, destination) -> destination.command = new MgUnresolvedIfCommand(source.getExpression())
    );

    @Output
    private MgUnresolvedIfCommand command;

    public MgBuildIfCommandTask(Block block) {
        super(block);
    }

    public MgUnresolvedIfCommand getCommand() {
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
