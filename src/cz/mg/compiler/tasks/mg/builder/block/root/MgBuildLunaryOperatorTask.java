package cz.mg.compiler.tasks.mg.builder.block.root;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedLunaryOperator;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildOperatorNameTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildLunaryOperatorTask extends MgBuildOperatorTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildOperatorNameTask.class,
        MgBuildLunaryOperatorTask.class,
        (source, destination) -> destination.operator = new MgUnresolvedLunaryOperator(source.getName())
    );

    @Output
    private MgUnresolvedLunaryOperator operator;

    public MgBuildLunaryOperatorTask(Block block) {
        super(block);
    }

    @Override
    public MgUnresolvedLunaryOperator getOperator() {
        return operator;
    }

    @Override
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }
}
