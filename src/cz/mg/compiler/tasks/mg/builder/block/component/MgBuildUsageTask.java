package cz.mg.compiler.tasks.mg.builder.block.component;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildNameBlockTask;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildUsagePathTask;
import cz.mg.compiler.tasks.mg.builder.pattern.*;
import cz.mg.language.entities.mg.unresolved.parts.MgUnresolvedUsage;
import cz.mg.language.entities.text.structured.Block;


public class MgBuildUsageTask extends MgBuildBlockTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildUsagePathTask.class,
        MgBuildUsageTask.class,
        (source, destination) -> destination.usage = new MgUnresolvedUsage(destination.filter, source.getPath())
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
