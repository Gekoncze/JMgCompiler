package cz.mg.compiler.tasks.mg.builder.block.root;

import cz.mg.collections.Clump;
import cz.mg.collections.ReadableCollection;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.language.entities.mg.logical.components.MgLogicalClass;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildDeclarationBlockTask;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildNamesBlockTask;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildNameTask;


public class MgBuildClassTask extends MgBuildBlockTask {
    private static final cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor PROCESSOR = new cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor<>(
        MgBuildNameTask.class,
        MgBuildClassTask.class,
        (source, destination) -> destination.clazz = new MgLogicalClass(source.getName())
    );

    private static final ReadableCollection<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> PATTERNS = new List<>(
        // build base class names
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.SINGLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildNamesBlockTask.class,
                MgBuildClassTask.class,
                (source, destination) -> destination.clazz.getBaseClasses().addCollectionLast(source.getNames())
            ),
            "IS"
        ),

        // build variables
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildDeclarationBlockTask.class,
                MgBuildClassTask.class,
                (source, destination) -> destination.clazz.getVariables().addLast(source.getDeclaration())
            )
        ),

        // build functions
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildFunctionTask.class,
                MgBuildClassTask.class,
                (source, destination) -> destination.clazz.getFunctions().addLast(source.getFunction())
            ),
            "FUNCTION"
        ),

        // build binary operator
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildBinaryOperatorTask.class,
                MgBuildClassTask.class,
                (source, destination) -> destination.clazz.getFunctions().addLast(source.getOperator())
            ),
            "BINARY", "OPERATOR"
        ),

        // build lunary operator
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildLunaryOperatorTask.class,
                MgBuildClassTask.class,
                (source, destination) -> destination.clazz.getFunctions().addLast(source.getOperator())
            ),
            "LUNARY", "OPERATOR"
        ),

        // build runary operator
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildRunaryOperatorTask.class,
                MgBuildClassTask.class,
                (source, destination) -> destination.clazz.getFunctions().addLast(source.getOperator())
            ),
            "RUNARY", "OPERATOR"
        )
    );

    @Output
    private MgLogicalClass clazz;

    public MgBuildClassTask(Block block) {
        super(block);
    }

    public MgLogicalClass getClazz() {
        return clazz;
    }

    @Override
    protected Object getOutput() {
        return clazz;
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
