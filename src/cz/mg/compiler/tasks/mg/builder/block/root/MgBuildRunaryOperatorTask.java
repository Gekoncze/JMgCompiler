package cz.mg.compiler.tasks.mg.builder.block.root;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.logical.components.MgLogicalRunaryOperator;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildOperatorNameTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildRunaryOperatorTask extends MgBuildOperatorTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildOperatorNameTask.class,
        MgBuildRunaryOperatorTask.class,
        (source, destination) -> destination.operator = new MgLogicalRunaryOperator(source.getName())
    );

    @Output
    private MgLogicalRunaryOperator operator;

    public MgBuildRunaryOperatorTask(Block block) {
        super(block);
    }

    @Override
    public MgLogicalRunaryOperator getOperator() {
        return operator;
    }

    @Override
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }
}
