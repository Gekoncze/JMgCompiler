package cz.mg.compiler.tasks.mg.builder.block.root;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.part.chain.path.MgBuildNamePathTask;
import cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Count;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;
import cz.mg.compiler.tasks.mg.builder.pattern.Requirement;
import cz.mg.language.entities.mg.unresolved.parts.MgUnresolvedUsage;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildNameBlockTask;


public class MgBuildUsageTask extends MgBuildBlockTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildNamePathTask.class,
        MgBuildUsageTask.class,
        (source, destination) -> destination.usage = new MgUnresolvedUsage(destination.filter, source.getNames())
    );

    private static final List<Pattern> PATTERNS = new List<>(
        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
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
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }

    @Override
    protected Clump<Pattern> getPatterns() {
        return PATTERNS;
    }
}
