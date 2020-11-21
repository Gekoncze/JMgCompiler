package cz.mg.compiler.tasks.mg.builder.block.component;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Count;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;
import cz.mg.compiler.tasks.mg.builder.pattern.Requirement;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedFunction;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedOperator;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildPriorityBlockTask;


public abstract class MgBuildOperatorTask extends MgBuildFunctionTask {
    private static final List<Pattern> PATTERNS = new List<>(
        // build priority
        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
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

    public abstract MgUnresolvedOperator getOperator();

    @Override
    public MgUnresolvedFunction getFunction() {
        return getOperator();
    }

    @Override
    protected Object getOutput() {
        return getOperator();
    }

    @Override
    protected PartProcessor getProcessor() {
        throw new RuntimeException();
    }

    @Override
    protected Clump<Pattern> getPatterns() {
        return PATTERNS;
    }
}
