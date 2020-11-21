package cz.mg.compiler.tasks.mg.builder.block.component.command;

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
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedSwitchCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.language.entities.text.structured.Part;


public class MgBuildSwitchCommandTask extends MgBuildCommandTask {
    private static final List<Pattern> PATTERNS = new List<>(
        // build case command
        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.MULTIPLE,
            new BlockProcessor<>(
                MgBuildCaseCommandTask.class,
                MgBuildSwitchCommandTask.class,
                (source, destination) -> destination.command.getCommands().addLast(source.getCommand())
            ),
            "CASE"
        ),

        // build else command
        new Pattern(
            Order.STRICT,
            Requirement.OPTIONAL,
            Count.SINGLE,
            new BlockProcessor<>(
                MgBuildElseCommandTask.class,
                MgBuildSwitchCommandTask.class,
                (source, destination) -> destination.command.getCommands().addLast(source.getCommand())
            ),
            "ELSE"
        )
    );

    @Output
    private MgUnresolvedSwitchCommand command;

    public MgBuildSwitchCommandTask(Block block) {
        super(block);
    }

    public MgUnresolvedSwitchCommand getCommand() {
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
            command = new MgUnresolvedSwitchCommand();
        }
    }
}
