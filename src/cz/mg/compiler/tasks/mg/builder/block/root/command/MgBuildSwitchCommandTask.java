package cz.mg.compiler.tasks.mg.builder.block.root.command;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalSwitchCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.language.entities.text.structured.Part;


public class MgBuildSwitchCommandTask extends MgBuildCommandTask {
    private static final List<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> PATTERNS = new List<>(
        // build case command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            cz.mg.compiler.tasks.mg.builder.pattern.Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildCaseCommandTask.class,
                MgBuildSwitchCommandTask.class,
                (source, destination) -> destination.command.getCommands().addLast(source.getCommand())
            ),
            "CASE"
        ),

        // build else command
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            Order.STRICT,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.OPTIONAL,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.SINGLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildElseCommandTask.class,
                MgBuildSwitchCommandTask.class,
                (source, destination) -> destination.command.getCommands().addLast(source.getCommand())
            ),
            "ELSE"
        )
    );

    @Output
    private MgLogicalSwitchCommand command;

    public MgBuildSwitchCommandTask(Block block) {
        super(block);
    }

    public MgLogicalSwitchCommand getCommand() {
        return command;
    }

    @Override
    protected Object getOutput() {
        return command;
    }

    @Override
    protected cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor getProcessor() {
        return null;
    }

    @Override
    protected Clump<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> getPatterns() {
        return PATTERNS;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        if(!parts.isEmpty()){
            throw new LanguageException("Unexpected part(s).");
        } else {
            command = new MgLogicalSwitchCommand();
        }
    }
}
