package cz.mg.compiler.tasks.mg.builder.block.component.command;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedExpressionCommand;
import cz.mg.language.entities.mg.unresolved.parts.expressions.MgUnresolvedClumpExpression;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildExpressionPartTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildExpressionCommandTask extends MgBuildMultilineExpressionCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildExpressionPartTask.class,
        MgBuildExpressionCommandTask.class,
        (source, destination) -> destination.command = new MgUnresolvedExpressionCommand(source.getExpression())
    );

    @Output
    private MgUnresolvedExpressionCommand command;

    public MgBuildExpressionCommandTask(Block block) {
        super(block);
    }

    public MgUnresolvedExpressionCommand getCommand() {
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
    public MgUnresolvedClumpExpression getExpression() {
        return command.getExpression();
    }

    @Override
    public void setExpression(MgUnresolvedClumpExpression expression) {
        command.setExpression(expression);
    }
}
