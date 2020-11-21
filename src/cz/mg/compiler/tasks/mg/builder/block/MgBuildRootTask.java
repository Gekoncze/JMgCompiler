package cz.mg.compiler.tasks.mg.builder.block;

import cz.mg.collections.ReadableCollection;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Cache;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.block.root.MgBuildBinaryOperatorTask;
import cz.mg.compiler.tasks.mg.builder.block.root.MgBuildClassTask;
import cz.mg.compiler.tasks.mg.builder.block.root.MgBuildFunctionTask;
import cz.mg.compiler.tasks.mg.builder.block.root.MgBuildLunaryOperatorTask;
import cz.mg.compiler.tasks.mg.builder.block.root.MgBuildRunaryOperatorTask;
import cz.mg.compiler.tasks.mg.builder.block.root.MgBuildUsageTask;
import cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Count;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;
import cz.mg.compiler.tasks.mg.builder.pattern.Requirement;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedComponent;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedWorkspace;
import cz.mg.language.entities.mg.unresolved.parts.MgUnresolvedUsage;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.language.entities.text.structured.Part;


public class MgBuildRootTask extends MgBuildBlockTask {
    private static final ReadableCollection<Pattern> PATTERNS = new List<>(
        // build usages
        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildUsageTask.class,
                MgBuildRootTask.class,
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
                MgBuildRootTask.class,
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
                MgBuildRootTask.class,
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
                MgBuildRootTask.class,
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
                MgBuildRootTask.class,
                (source, destination) -> destination.workspace.getUsages().addLast(source.getUsage()),
                (block, destination) -> new MgBuildUsageTask(block, MgUnresolvedUsage.Filter.VARIABLE)
            ),
            "USING", "VARIABLE"
        ),

        // build class
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildClassTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.setComponent(source.getClazz())
            ),
            "CLASS"
        ),

        // build function
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildFunctionTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.setComponent(source.getFunction())
            ),
            "FUNCTION"
        ),

        // build binary operator
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildBinaryOperatorTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.setComponent(source.getOperator())
            ),
            "BINARY", "OPERATOR"
        ),

        // build lunary operator
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildLunaryOperatorTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.setComponent(source.getOperator())
            ),
            "LUNARY", "OPERATOR"
        ),

        // build runary operator
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildRunaryOperatorTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.setComponent(source.getOperator())
            ),
            "RUNARY", "OPERATOR"
        )
    );

    @Output
    private MgUnresolvedComponent component;

    @Cache
    private MgUnresolvedWorkspace workspace;

    public MgBuildRootTask(Block block) {
        super(block);
    }

    @Override
    protected void onRun() {
        workspace = new MgUnresolvedWorkspace();
        super.onRun();
        if(component == null) throw new LanguageException("Missing component.");
        component.setWorkspace(workspace);
    }

    public MgUnresolvedComponent getComponent() {
        return component;
    }

    private void setComponent(MgUnresolvedComponent component) {
        if(this.component != null) throw new LanguageException("Expected only one component at root level.");
        this.component = component;
    }

    @Override
    protected Object getOutput() {
        return component;
    }

    @Override
    public ReadableCollection<Pattern> getPatterns() {
        return PATTERNS;
    }

    @Override
    public PartProcessor getProcessor() {
        return null;
    }

    @Override
    protected void buildParts(List<Part> parts){
        // nothing to do
    }
}
