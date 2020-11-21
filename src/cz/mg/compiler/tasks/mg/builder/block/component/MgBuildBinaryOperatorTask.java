package cz.mg.compiler.tasks.mg.builder.block.component;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedBinaryOperator;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildOperatorNameTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildBinaryOperatorTask extends MgBuildOperatorTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildOperatorNameTask.class,
        MgBuildBinaryOperatorTask.class,
        (source, destination) -> destination.operator = new MgUnresolvedBinaryOperator(source.getName())
    );

    @Output
    private MgUnresolvedBinaryOperator operator;

    public MgBuildBinaryOperatorTask(Block block) {
        super(block);
    }

    @Override
    public MgUnresolvedBinaryOperator getOperator() {
        return operator;
    }

    @Override
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }
}
