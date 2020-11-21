package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedContinueCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.Continuable;
import cz.mg.language.entities.mg.runtime.parts.commands.MgCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgContinueCommand;


public class MgResolveContinueCommandTask extends MgResolveCommandTask {
    @cz.mg.compiler.annotations.Input
    private final cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext context;

    @Input
    private final MgUnresolvedContinueCommand logicalCommand;

    @Output
    private MgContinueCommand command;

    public MgResolveContinueCommandTask(cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext context, MgUnresolvedContinueCommand logicalCommand) {
        this.context = new cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext(context);
        this.logicalCommand = logicalCommand;
    }

    @Override
    public MgContinueCommand getCommand() {
        return command;
    }

    @Override
    protected void onRun() {
        command = new MgContinueCommand(findTargetCommand(logicalCommand.getTarget()));
        context.setCommand(command);
    }
    
    private MgCommand findTargetCommand(ReadableText name) {
        Context current = context;
        while(current != null){
            if(current instanceof cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext){
                MgCommand command = ((CommandContext) current).getCommand();
                if(command instanceof Continuable){
                    if(name != null){
                        if(name.equals(((Continuable) command).getName())){
                            return command;
                        }
                    } else {
                        return command;
                    }
                }
            }
            current = current.getOuterContext();
        }

        throw new LanguageException("Could not find command to continue.");
    }
}
