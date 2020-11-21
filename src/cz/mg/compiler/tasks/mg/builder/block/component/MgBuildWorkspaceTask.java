package cz.mg.compiler.tasks.mg.builder.block.component;

import cz.mg.collections.Clump;
import cz.mg.collections.ReadableCollection;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildNameTask;
import cz.mg.compiler.tasks.mg.builder.pattern.*;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedWorkspace;
import cz.mg.language.entities.mg.unresolved.parts.MgUnresolvedUsage;
import cz.mg.language.entities.text.structured.Block;


public class MgBuildWorkspaceTask extends MgBuildBlockTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildNameTask.class,
        MgBuildWorkspaceTask.class,
        (source, destination) -> destination.workspace = new MgUnresolvedWorkspace(source.getName())
    );

    private static final ReadableCollection<Pattern> PATTERNS = new List<>(
        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildUsageTask.class,
                MgBuildWorkspaceTask.class,
                (source, destination) -> destination.workspace.getUsages().addLast(source.getUsage()),
                (block, destination) -> new MgBuildUsageTask(block, MgUnresolvedUsage.Filter.ALL)
            ),
            "USING"
        ),

        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildUsageTask.class,
                MgBuildWorkspaceTask.class,
                (source, destination) -> destination.workspace.getUsages().addLast(source.getUsage()),
                (block, destination) -> new MgBuildUsageTask(block, MgUnresolvedUsage.Filter.CLASS)
            ),
            "USING", "CLASS"
        ),

        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildUsageTask.class,
                MgBuildWorkspaceTask.class,
                (source, destination) -> destination.workspace.getUsages().addLast(source.getUsage()),
                (block, destination) -> new MgBuildUsageTask(block, MgUnresolvedUsage.Filter.FUNCTION)
            ),
            "USING", "FUNCTION"
        ),

        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildUsageTask.class,
                MgBuildWorkspaceTask.class,
                (source, destination) -> destination.workspace.getUsages().addLast(source.getUsage()),
                (block, destination) -> new MgBuildUsageTask(block, MgUnresolvedUsage.Filter.OPERATOR)
            ),
            "USING", "OPERATOR"
        ),

        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildUsageTask.class,
                MgBuildWorkspaceTask.class,
                (source, destination) -> destination.workspace.getUsages().addLast(source.getUsage()),
                (block, destination) -> new MgBuildUsageTask(block, MgUnresolvedUsage.Filter.VARIABLE)
            ),
            "USING", "VARIABLE"
        ),

        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildUsageTask.class,
                MgBuildWorkspaceTask.class,
                (source, destination) -> destination.workspace.getUsages().addLast(source.getUsage()),
                (block, destination) -> new MgBuildUsageTask(block, MgUnresolvedUsage.Filter.WORKSPACE)
            ),
            "USING", "WORKSPACE"
        )
    );

    @Output
    private MgUnresolvedWorkspace workspace;

    public MgBuildWorkspaceTask(Block block) {
        super(block);
    }

    public MgUnresolvedWorkspace getWorkspace() {
        return workspace;
    }

    @Override
    protected Object getOutput() {
        return workspace;
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
