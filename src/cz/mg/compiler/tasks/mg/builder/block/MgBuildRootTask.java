package cz.mg.compiler.tasks.mg.builder.block;

import cz.mg.collections.ReadableCollection;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Cache;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.block.root.MgBuildRunaryOperatorTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.logical.components.MgLogicalComponent;
import cz.mg.language.entities.mg.logical.parts.MgLogicalContext;
import cz.mg.language.entities.mg.logical.parts.MgLogicalUsage;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.language.entities.text.structured.Part;


public class MgBuildRootTask extends MgBuildBlockTask {
    private static final ReadableCollection<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> PATTERNS = new List<>(
        // build usages
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.MgBuildUsageTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.context.getUsages().addLast(source.getUsage()),
                (block, destination) -> new cz.mg.compiler.tasks.mg.builder.block.root.MgBuildUsageTask(block, MgLogicalUsage.Filter.ALL)
            ),
            "USING"
        ),

        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.MgBuildUsageTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.context.getUsages().addLast(source.getUsage()),
                (block, destination) -> new cz.mg.compiler.tasks.mg.builder.block.root.MgBuildUsageTask(block, MgLogicalUsage.Filter.CLASS)
            ),
            "USING", "CLASS"
        ),

        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.MgBuildUsageTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.context.getUsages().addLast(source.getUsage()),
                (block, destination) -> new cz.mg.compiler.tasks.mg.builder.block.root.MgBuildUsageTask(block, MgLogicalUsage.Filter.FUNCTION)
            ),
            "USING", "FUNCTION"
        ),

        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.MgBuildUsageTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.context.getUsages().addLast(source.getUsage()),
                (block, destination) -> new cz.mg.compiler.tasks.mg.builder.block.root.MgBuildUsageTask(block, MgLogicalUsage.Filter.OPERATOR)
            ),
            "USING", "OPERATOR"
        ),

        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.MgBuildUsageTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.context.getUsages().addLast(source.getUsage()),
                (block, destination) -> new cz.mg.compiler.tasks.mg.builder.block.root.MgBuildUsageTask(block, MgLogicalUsage.Filter.VARIABLE)
            ),
            "USING", "VARIABLE"
        ),

        // build class
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.SINGLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.MgBuildClassTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.setComponent(source.getClazz())
            ),
            "CLASS"
        ),

        // build function
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.SINGLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.MgBuildFunctionTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.setComponent(source.getFunction())
            ),
            "FUNCTION"
        ),

        // build binary operator
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.SINGLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.MgBuildBinaryOperatorTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.setComponent(source.getOperator())
            ),
            "BINARY", "OPERATOR"
        ),

        // build lunary operator
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.SINGLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.MgBuildLunaryOperatorTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.setComponent(source.getOperator())
            ),
            "LUNARY", "OPERATOR"
        ),

        // build runary operator
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.SINGLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildRunaryOperatorTask.class,
                MgBuildRootTask.class,
                (source, destination) -> destination.setComponent(source.getOperator())
            ),
            "RUNARY", "OPERATOR"
        )
    );

    @Output
    private MgLogicalComponent component;

    @Cache
    private MgLogicalContext context;

    public MgBuildRootTask(Block block) {
        super(block);
    }

    @Override
    protected void onRun() {
        context = new MgLogicalContext();
        super.onRun();
        if(component == null) throw new LanguageException("Missing component.");
        component.setContext(context);
    }

    public MgLogicalComponent getComponent() {
        return component;
    }

    private void setComponent(MgLogicalComponent component) {
        if(this.component != null) throw new LanguageException("Expected only one component at root level.");
        this.component = component;
    }

    @Override
    protected Object getOutput() {
        return component;
    }

    @Override
    public ReadableCollection<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> getPatterns() {
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
