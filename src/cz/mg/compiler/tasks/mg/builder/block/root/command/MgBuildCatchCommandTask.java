package cz.mg.compiler.tasks.mg.builder.block.root.command;

import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedBlockCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCatchCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildDeclarationTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildCatchCommandTask extends MgBuildBlockCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildDeclarationTask.class,
        MgBuildCatchCommandTask.class,
        (source, destination) -> destination.command = new MgUnresolvedCatchCommand(source.getVariable())
    );

    @Output
    private MgUnresolvedCatchCommand command;

    public MgBuildCatchCommandTask(Block block) {
        super(block);
    }

    public MgUnresolvedCatchCommand getCommand() {
        return command;
    }

    @Override
    protected MgUnresolvedBlockCommand getOutput() {
        return command;
    }

    @Override
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }
}
