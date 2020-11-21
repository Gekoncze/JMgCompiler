package cz.mg.compiler.tasks.mg.builder.block.component;

import cz.mg.collections.Clump;
import cz.mg.collections.ReadableCollection;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Count;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;
import cz.mg.compiler.tasks.mg.builder.pattern.Requirement;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedClass;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildDeclarationBlockTask;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildNamesBlockTask;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildNameTask;


public class MgBuildClassTask extends MgBuildBlockTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildNameTask.class,
        MgBuildClassTask.class,
        (source, destination) -> destination.clazz = new MgUnresolvedClass(source.getName())
    );

    private static final ReadableCollection<Pattern> PATTERNS = new List<>(
        // build base class names
        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildNamesBlockTask.class,
                MgBuildClassTask.class,
                (source, destination) -> destination.clazz.getBaseClasses().addCollectionLast(source.getNames())
            ),
            "IS"
        ),

        // build variables
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildDeclarationBlockTask.class,
                MgBuildClassTask.class,
                (source, destination) -> destination.clazz.getVariables().addLast(source.getDeclaration())
            )
        ),

        // build functions
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildFunctionTask.class,
                MgBuildClassTask.class,
                (source, destination) -> destination.clazz.getFunctions().addLast(source.getFunction())
            ),
            "FUNCTION"
        ),

        // build binary operator
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildBinaryOperatorTask.class,
                MgBuildClassTask.class,
                (source, destination) -> destination.clazz.getFunctions().addLast(source.getOperator())
            ),
            "BINARY", "OPERATOR"
        ),

        // build lunary operator
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildLunaryOperatorTask.class,
                MgBuildClassTask.class,
                (source, destination) -> destination.clazz.getFunctions().addLast(source.getOperator())
            ),
            "LUNARY", "OPERATOR"
        ),

        // build runary operator
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildRunaryOperatorTask.class,
                MgBuildClassTask.class,
                (source, destination) -> destination.clazz.getFunctions().addLast(source.getOperator())
            ),
            "RUNARY", "OPERATOR"
        )
    );

    @Output
    private MgUnresolvedClass clazz;

    public MgBuildClassTask(Block block) {
        super(block);
    }

    public MgUnresolvedClass getClazz() {
        return clazz;
    }

    @Override
    protected Object getOutput() {
        return clazz;
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
