package cz.mg.compiler.tasks.mg.builder.block.component.command;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedReturnCommand;
import cz.mg.language.entities.mg.unresolved.parts.expressions.MgUnresolvedClumpExpression;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildExpressionPartTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildReturnCommandTask extends MgBuildMultilineExpressionCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildExpressionPartTask.class,
        MgBuildReturnCommandTask.class,
        (source, destination) -> destination.command = new MgUnresolvedReturnCommand(source.getExpression())
    );

    @Output
    private MgUnresolvedReturnCommand command;

    public MgBuildReturnCommandTask(Block block) {
        super(block);
    }

    public MgUnresolvedReturnCommand getCommand() {
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
            command = new MgUnresolvedReturnCommand();
        }
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
