package cz.mg.compiler.tasks.mg.builder.block.root.command;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalBlockCommand;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalElseCommand;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;


public class MgBuildElseCommandTask extends MgBuildBlockCommandTask {
    @Output
    private MgLogicalElseCommand command;

    public MgBuildElseCommandTask(Block block) {
        super(block);
    }

    public MgLogicalElseCommand getCommand() {
        return command;
    }

    @Override
    protected MgLogicalBlockCommand getOutput() {
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
            command = new MgLogicalElseCommand();
        }
    }
}
