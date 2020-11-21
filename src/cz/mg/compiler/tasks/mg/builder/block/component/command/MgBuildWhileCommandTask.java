package cz.mg.compiler.tasks.mg.builder.block.component.command;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedBlockCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedWhileCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildExpressionPartTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildWhileCommandTask extends MgBuildBlockCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildExpressionPartTask.class,
        MgBuildWhileCommandTask.class,
        (source, destination) -> destination.command = new MgUnresolvedWhileCommand(source.getExpression())
    );

    @Output
    private MgUnresolvedWhileCommand command;

    public MgBuildWhileCommandTask(Block block) {
        super(block);
    }

    public MgUnresolvedWhileCommand getCommand() {
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
