package cz.mg.compiler.tasks.mg.builder.block.root;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildDeclarationsBlockTask;
import cz.mg.compiler.tasks.mg.builder.block.root.command.MgBuildCheckpointCommandTask;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.language.entities.mg.logical.components.MgLogicalFunction;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildNameTask;


public class MgBuildFunctionTask extends MgBuildBlockTask {
    private static final cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor PROCESSOR = new cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor<>(
        MgBuildNameTask.class,
        MgBuildFunctionTask.class,
        (source, destination) -> destination.function = new MgLogicalFunction(source.getName())
    );

    public static final List<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> PATTERNS = new List<>(
        // build input
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.SINGLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.part.MgBuildDeclarationsBlockTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getInput().addCollectionLast(source.getVariables())
            ),
            "INPUT"
        ),

        // build output
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.SINGLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildDeclarationsBlockTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getOutput().addCollectionLast(source.getVariables())
            ),
            "OUTPUT"
        ),

        // build expression command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.MANDATORY,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.command.MgBuildExpressionCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            )
        ),

        // build if command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.command.MgBuildIfCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "IF"
        ),

        // build switch command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.command.MgBuildSwitchCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "SWITCH"
        ),

        // build while command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.command.MgBuildWhileCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "WHILE"
        ),

        // build return command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.command.MgBuildReturnCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "RETURN"
        ),

        // build continue command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.command.MgBuildContinueCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "CONTINUE"
        ),

        // build break command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.command.MgBuildBreakCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "BREAK"
        ),

        // build rollback command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                cz.mg.compiler.tasks.mg.builder.block.root.command.MgBuildRollbackCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "ROLLBACK"
        ),

        // build checkpoint command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildCheckpointCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "CHECKPOINT"
        )
    );

    @Output
    private MgLogicalFunction function;

    public MgBuildFunctionTask(Block block) {
        super(block);
    }

    public MgLogicalFunction getFunction() {
        return function;
    }

    @Override
    protected Object getOutput() {
        return function;
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
