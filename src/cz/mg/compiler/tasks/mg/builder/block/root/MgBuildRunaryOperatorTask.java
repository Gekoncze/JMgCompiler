package cz.mg.compiler.tasks.mg.builder.block.root;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedRunaryOperator;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildOperatorNameTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildRunaryOperatorTask extends MgBuildOperatorTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildOperatorNameTask.class,
        MgBuildRunaryOperatorTask.class,
        (source, destination) -> destination.operator = new MgUnresolvedRunaryOperator(source.getName())
    );

    @Output
    private MgUnresolvedRunaryOperator operator;

    public MgBuildRunaryOperatorTask(Block block) {
        super(block);
    }

    @Override
    public MgUnresolvedRunaryOperator getOperator() {
        return operator;
    }

    @Override
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }
}
