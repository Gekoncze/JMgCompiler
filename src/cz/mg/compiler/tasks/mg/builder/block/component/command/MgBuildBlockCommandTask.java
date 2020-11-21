package cz.mg.compiler.tasks.mg.builder.block.component.command;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Count;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;
import cz.mg.compiler.tasks.mg.builder.pattern.Requirement;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedBlockCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildNameBlockTask;


public abstract class MgBuildBlockCommandTask extends MgBuildBlockTask {
    private static final List<Pattern> PATTERNS = new List<>(
        // build alias
        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildNameBlockTask.class,
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().setName(source.getName())
            ),
            "AS"
        ),

        // build expression command
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildExpressionCommandTask.class,
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
            )
        ),

        // build if command
        new Pattern(
            Order.RANDOM,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildIfCommandTask.class,
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
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
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
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
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
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
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
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
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
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
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
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
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
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
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
            ),
            "CHECKPOINT"
        )
    );

    public MgBuildBlockCommandTask(Block block) {
        super(block);
    }

    @Override
    protected abstract MgUnresolvedBlockCommand getOutput();

    @Override
    protected Clump<Pattern> getPatterns() {
        return PATTERNS;
    }
}
