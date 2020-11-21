package cz.mg.compiler.tasks.mg.resolver.context.executable;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Link;
import cz.mg.compiler.tasks.mg.resolver.context.component.structured.FunctionContext;
import cz.mg.language.entities.mg.runtime.parts.commands.MgCommand;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.OperatorCache;


public class CommandContext extends cz.mg.compiler.tasks.mg.resolver.context.executable.ExecutableContext {
    @Optional @Link
    private MgCommand command;

    public CommandContext(@Mandatory cz.mg.compiler.tasks.mg.resolver.context.executable.ExecutableContext outerContext) {
        super(outerContext);
    }

    @Override
    public @Optional cz.mg.compiler.tasks.mg.resolver.context.executable.ExecutableContext getOuterContext() {
        return (ExecutableContext) super.getOuterContext();
    }

    public MgCommand getCommand() {
        return command;
    }

    public void setCommand(MgCommand command) {
        this.command = command;
    }

    public cz.mg.compiler.tasks.mg.resolver.context.component.structured.FunctionContext getFunctionContext(){
        if(getOuterContext() instanceof CommandContext){
            return ((CommandContext) getOuterContext()).getFunctionContext();
        } else if(getOuterContext() instanceof FunctionBodyContext) {
            return (FunctionContext) getOuterContext().getOuterContext();
        } else {
            throw new RuntimeException();
        }
    }

    public OperatorCache getOperatorCache() {
        return getFunctionContext().getOperatorCache();
    }
}
