package cz.mg.compiler.tasks.mg.builder.block.root.command;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedRollbackCommand;
import cz.mg.language.entities.mg.unresolved.parts.expressions.MgUnresolvedClumpExpression;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildExpressionPartTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildRollbackCommandTask extends MgBuildMultilineExpressionCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildExpressionPartTask.class,
        MgBuildRollbackCommandTask.class,
        (source, destination) -> destination.command = new MgUnresolvedRollbackCommand(source.getExpression())
    );

    @Output
    private MgUnresolvedRollbackCommand command;

    public MgBuildRollbackCommandTask(Block block) {
        super(block);
    }

    public MgUnresolvedRollbackCommand getCommand() {
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
        if(parts.count() > 0){
            super.buildParts(parts);
        } else {
            command = new MgUnresolvedRollbackCommand();
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
