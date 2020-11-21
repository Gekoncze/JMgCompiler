package cz.mg.compiler.tasks.mg.builder.block.root;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.part.chain.path.MgBuildNamePathTask;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.language.entities.mg.unresolved.parts.MgUnresolvedUsage;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildNameBlockTask;


public class MgBuildUsageTask extends MgBuildBlockTask {
    private static final cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor PROCESSOR = new cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor<>(
        MgBuildNamePathTask.class,
        MgBuildUsageTask.class,
        (source, destination) -> destination.usage = new MgUnresolvedUsage(destination.filter, source.getNames())
    );

    private static final List<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> PATTERNS = new List<>(
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.SINGLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildNameBlockTask.class,
                MgBuildUsageTask.class,
                (source, destination) -> destination.usage.setAlias(source.getName())
            ),
            "AS"
        )
    );

    @Input
    private final MgUnresolvedUsage.Filter filter;

    @Output
    private MgUnresolvedUsage usage;

    public MgBuildUsageTask(Block block, MgUnresolvedUsage.Filter filter) {
        super(block);
        this.filter = filter;
    }

    public MgUnresolvedUsage getUsage() {
        return usage;
    }

    @Override
    protected Object getOutput() {
        return usage;
    }

    @Override
    protected cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor getProcessor() {
        return PROCESSOR;
    }

    @Override
    protected Clump<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> getPatterns() {
        return PATTERNS;
    }
}
