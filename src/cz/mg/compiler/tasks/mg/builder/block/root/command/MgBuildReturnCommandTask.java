package cz.mg.compiler.tasks.mg.builder.block.root.command;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalReturnCommand;
import cz.mg.language.entities.mg.logical.parts.expressions.MgLogicalClumpExpression;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildExpressionPartTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildReturnCommandTask extends MgBuildMultilineExpressionCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildExpressionPartTask.class,
        MgBuildReturnCommandTask.class,
        (source, destination) -> destination.command = new MgLogicalReturnCommand(source.getExpression())
    );

    @Output
    private MgLogicalReturnCommand command;

    public MgBuildReturnCommandTask(Block block) {
        super(block);
    }

    public MgLogicalReturnCommand getCommand() {
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
    protected void buildParts(List<Part> parts) {
        if(!parts.isEmpty()){
            super.buildParts(parts);
        } else {
            command = new MgLogicalReturnCommand();
        }
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
