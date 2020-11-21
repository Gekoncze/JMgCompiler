package cz.mg.compiler.tasks.mg.builder.block.root;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.logical.components.MgLogicalBinaryOperator;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildOperatorNameTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildBinaryOperatorTask extends MgBuildOperatorTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildOperatorNameTask.class,
        MgBuildBinaryOperatorTask.class,
        (source, destination) -> destination.operator = new MgLogicalBinaryOperator(source.getName())
    );

    @Output
    private MgLogicalBinaryOperator operator;

    public MgBuildBinaryOperatorTask(Block block) {
        super(block);
    }

    @Override
    public MgLogicalBinaryOperator getOperator() {
        return operator;
    }

    @Override
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }
}
