package cz.mg.compiler.tasks.mg.builder.block.component.command;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedBreakCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildNameTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;


public class MgBuildBreakCommandTask extends MgBuildCommandTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildNameTask.class,
        MgBuildBreakCommandTask.class,
        (source, destination) -> destination.command = new MgUnresolvedBreakCommand(source.getName())
    );

    @Output
    private MgUnresolvedBreakCommand command;

    public MgBuildBreakCommandTask(Block block) {
        super(block);
    }

    public MgUnresolvedBreakCommand getCommand() {
        return command;
    }

    @Override
    protected Object getOutput() {
        return command;
    }

    @Override
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }

    @Override
    protected Clump<Pattern> getPatterns() {
        return null;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        if(!parts.isEmpty()){
            super.buildParts(parts);
        } else {
            command = new MgUnresolvedBreakCommand();
        }
    }
}
