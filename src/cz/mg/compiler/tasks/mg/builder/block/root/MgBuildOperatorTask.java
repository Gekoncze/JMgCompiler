package cz.mg.compiler.tasks.mg.builder.block.root;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.language.entities.mg.logical.components.MgLogicalFunction;
import cz.mg.language.entities.mg.logical.components.MgLogicalOperator;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildPriorityBlockTask;


public abstract class MgBuildOperatorTask extends MgBuildFunctionTask {
    private static final List<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> PATTERNS = new List<>(
        // build priority
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.SINGLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildPriorityBlockTask.class,
                MgBuildOperatorTask.class,
                (source, destination) -> {
                    destination.getOperator().setPriority(source.getPriority());
                }
            ),
            "PRIORITY"
        )
    );

    static {
        PATTERNS.addCollectionFirst(MgBuildFunctionTask.PATTERNS);
    }

    public MgBuildOperatorTask(Block block) {
        super(block);
    }

    public abstract MgLogicalOperator getOperator();

    @Override
    public MgLogicalFunction getFunction() {
        return getOperator();
    }

    @Override
    protected Object getOutput() {
        return getOperator();
    }

    @Override
    protected cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor getProcessor() {
        throw new RuntimeException();
    }

    @Override
    protected Clump<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> getPatterns() {
        return PATTERNS;
    }
}
