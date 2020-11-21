package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalBreakCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.Breakable;
import cz.mg.language.entities.mg.runtime.parts.commands.MgBreakCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgCommand;


public class MgResolveBreakCommandTask extends MgResolveCommandTask {
    @cz.mg.compiler.annotations.Input
    private final cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext context;

    @Input
    private final MgLogicalBreakCommand logicalCommand;

    @Output
    private MgBreakCommand command;

    public MgResolveBreakCommandTask(cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext context, MgLogicalBreakCommand logicalCommand) {
        this.context = new cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext(context);
        this.logicalCommand = logicalCommand;
    }

    @Override
    public MgBreakCommand getCommand() {
        return command;
    }

    @Override
    protected void onRun() {
        command = new MgBreakCommand(findTargetCommand(logicalCommand.getTarget()));
        context.setCommand(command);
    }

    private MgCommand findTargetCommand(ReadableText name) {
        Context current = context;
        while(current != null){
            if(current instanceof cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext){
                MgCommand command = ((CommandContext) current).getCommand();
                if(command instanceof Breakable){
                    if(name != null){
                        if(name.equals(((Breakable) command).getName())){
                            return command;
                        }
                    } else {
                        return command;
                    }
                }
            }
            current = current.getOuterContext();
        }

        throw new LanguageException("Could not find command to break.");
    }
}
