package cz.mg.compiler.tasks.mg.builder.block.component.command;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedBlockCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedFinallyCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildFinallyCommandTask extends MgBuildBlockCommandTask {
    @Output
    private MgUnresolvedFinallyCommand command;

    public MgBuildFinallyCommandTask(Block block) {
        super(block);
    }

    public MgUnresolvedFinallyCommand getCommand() {
        return command;
    }

    @Override
    protected MgUnresolvedBlockCommand getOutput() {
        return command;
    }

    @Override
    protected PartProcessor getProcessor() {
        return null;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        if(!parts.isEmpty()){
            throw new LanguageException("Unexpected part(s).");
        } else {
            command = new MgUnresolvedFinallyCommand();
        }
    }
}
