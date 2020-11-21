package cz.mg.compiler.tasks.mg.builder.block.component;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildDeclarationsBlockTask;
import cz.mg.compiler.tasks.mg.builder.block.component.command.MgBuildBreakCommandTask;
import cz.mg.compiler.tasks.mg.builder.block.component.command.MgBuildCheckpointCommandTask;
import cz.mg.compiler.tasks.mg.builder.block.component.command.MgBuildContinueCommandTask;
import cz.mg.compiler.tasks.mg.builder.block.component.command.MgBuildExpressionCommandTask;
import cz.mg.compiler.tasks.mg.builder.block.component.command.MgBuildIfCommandTask;
import cz.mg.compiler.tasks.mg.builder.block.component.command.MgBuildReturnCommandTask;
import cz.mg.compiler.tasks.mg.builder.block.component.command.MgBuildRollbackCommandTask;
import cz.mg.compiler.tasks.mg.builder.block.component.command.MgBuildSwitchCommandTask;
import cz.mg.compiler.tasks.mg.builder.block.component.command.MgBuildWhileCommandTask;
import cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Count;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;
import cz.mg.compiler.tasks.mg.builder.pattern.Requirement;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedFunction;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildNameTask;


public class MgBuildFunctionTask extends MgBuildBlockTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildNameTask.class,
        MgBuildFunctionTask.class,
        (source, destination) -> destination.function = new MgUnresolvedFunction(source.getName())
    );

    public static final List<Pattern> PATTERNS = new List<>(
        // build input
        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildDeclarationsBlockTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getInput().addCollectionLast(source.getVariables())
            ),
            "INPUT"
        ),

        // build output
        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildDeclarationsBlockTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getOutput().addCollectionLast(source.getVariables())
            ),
            "OUTPUT"
        ),

        // build expression command
        new Pattern(
            Order.RANDOM,
            Requirement.MANDATORY,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildExpressionCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            )
        ),

        // build if command
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildIfCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "IF"
        ),

        // build switch command
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildSwitchCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "SWITCH"
        ),

        // build while command
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildWhileCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "WHILE"
        ),

        // build return command
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildReturnCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "RETURN"
        ),

        // build continue command
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildContinueCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "CONTINUE"
        ),

        // build break command
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildBreakCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "BREAK"
        ),

        // build rollback command
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildRollbackCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "ROLLBACK"
        ),

        // build checkpoint command
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildCheckpointCommandTask.class,
                MgBuildFunctionTask.class,
                (source, destination) -> destination.function.getCommands().addLast(source.getCommand())
            ),
            "CHECKPOINT"
        )
    );

    @Output
    private MgUnresolvedFunction function;

    public MgBuildFunctionTask(Block block) {
        super(block);
    }

    public MgUnresolvedFunction getFunction() {
        return function;
    }

    @Override
    protected Object getOutput() {
        return function;
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
