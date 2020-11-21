package cz.mg.compiler.tasks.mg.builder.block.root.command;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Count;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;
import cz.mg.compiler.tasks.mg.builder.pattern.Requirement;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCheckpointCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.language.entities.text.structured.Part;


public class MgBuildCheckpointCommandTask extends MgBuildCommandTask {
    private static final List<Pattern> PATTERNS = new List<>(
        // build try command
        new Pattern(
            Order.STRICT,
            Requirement.MANDATORY,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildTryCommandTask.class,
                MgBuildCheckpointCommandTask.class,
                (source, destination) -> destination.command.setTryCommand(source.getCommand())
            ),
            "TRY"
        ),

        // build catch command
        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildCatchCommandTask.class,
                MgBuildCheckpointCommandTask.class,
                (source, destination) -> destination.command.getCatchCommands().addLast(source.getCommand())
            ),
            "CATCH"
        ),

        // build finally command
        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildFinallyCommandTask.class,
                MgBuildCheckpointCommandTask.class,
                (source, destination) -> destination.command.setFinallyCommand(source.getCommand())
            ),
            "FINALLY"
        )
    );

    @Output
    private MgUnresolvedCheckpointCommand command;

    public MgBuildCheckpointCommandTask(Block block) {
        super(block);
    }

    public MgUnresolvedCheckpointCommand getCommand() {
        return command;
    }

    @Override
    protected Object getOutput() {
        return command;
    }

    @Override
    protected PartProcessor getProcessor() {
        return null;
    }

    @Override
    protected Clump<Pattern> getPatterns() {
        return PATTERNS;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        if(!parts.isEmpty()){
            throw new LanguageException("Unexpected part(s).");
        } else {
            command = new MgUnresolvedCheckpointCommand();
        }
    }
}
