package cz.mg.compiler.tasks.mg.builder.block.root.command;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalExpressionCommand;
import cz.mg.language.entities.mg.logical.parts.expressions.MgLogicalClumpExpression;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildExpressionPartTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildExpressionCommandTask extends MgBuildMultilineExpressionCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildExpressionPartTask.class,
        MgBuildExpressionCommandTask.class,
        (source, destination) -> destination.command = new MgLogicalExpressionCommand(source.getExpression())
    );

    @Output
    private MgLogicalExpressionCommand command;

    public MgBuildExpressionCommandTask(Block block) {
        super(block);
    }

    public MgLogicalExpressionCommand getCommand() {
        return command;
    }

    @Override
    protected Object getOutput() {
        return command;
    }

    @Override
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }

    @Override
    public MgLogicalClumpExpression getExpression() {
        return command.getExpression();
    }

    @Override
    public void setExpression(MgLogicalClumpExpression expression) {
        command.setExpression(expression);
    }
}
