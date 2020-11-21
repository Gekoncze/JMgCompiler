package cz.mg.compiler.tasks.mg.builder.block.component;

import cz.mg.collections.Clump;
import cz.mg.collections.ReadableCollection;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildNameBlockTask;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildNameTask;
import cz.mg.compiler.tasks.mg.builder.pattern.*;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedAlias;
import cz.mg.language.entities.text.structured.Block;


public class MgBuildAliasTask extends MgBuildBlockTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildNameTask.class,
        MgBuildAliasTask.class,
        (source, destination) -> destination.alias = new MgUnresolvedAlias(source.getName())
    );

    private static final ReadableCollection<Pattern> PATTERNS = new List<>(
        new Pattern(
            Order.STRICT,
            Requirement.MANDATORY,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildNameBlockTask.class,
                MgBuildAliasTask.class,
                (source, destination) -> destination.alias.setOriginal(source.getName())
            ),
            "AS"
        )
    );

    @Output
    private MgUnresolvedAlias alias;

    public MgBuildAliasTask(Block block) {
        super(block);
    }

    public MgUnresolvedAlias getAlias() {
        return alias;
    }

    @Override
    protected MgUnresolvedAlias getOutput() {
        return alias;
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
