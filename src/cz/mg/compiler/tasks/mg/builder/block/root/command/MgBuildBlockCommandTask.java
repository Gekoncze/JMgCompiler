package cz.mg.compiler.tasks.mg.builder.block.root.command;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedBlockCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.block.part.MgBuildNameBlockTask;


public abstract class MgBuildBlockCommandTask extends MgBuildBlockTask {
    private static final List<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> PATTERNS = new List<>(
        // build alias
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.SINGLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildNameBlockTask.class,
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().setName(source.getName())
            ),
            "AS"
        ),

        // build expression command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildExpressionCommandTask.class,
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
            )
        ),

        // build if command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildIfCommandTask.class,
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
            ),
            "IF"
        ),

        // build switch command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildSwitchCommandTask.class,
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
            ),
            "SWITCH"
        ),

        // build while command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildWhileCommandTask.class,
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
            ),
            "WHILE"
        ),

        // build return command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildReturnCommandTask.class,
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
            ),
            "RETURN"
        ),

        // build continue command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildContinueCommandTask.class,
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
            ),
            "CONTINUE"
        ),

        // build break command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildBreakCommandTask.class,
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
            ),
            "BREAK"
        ),

        // build rollback command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildRollbackCommandTask.class,
                MgBuildBlockCommandTask.class,
                (source, destination) -> destination.getOutput().getCommands().addLast(source.getCommand())
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
    protected Clump<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> getPatterns() {
        return PATTERNS;
    }
}
