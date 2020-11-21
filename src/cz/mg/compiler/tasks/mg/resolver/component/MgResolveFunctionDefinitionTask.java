package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.annotations.Utility;
import cz.mg.compiler.tasks.mg.resolver.context.component.structured.FunctionContext;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.compiler.tasks.mg.resolver.context.executable.FunctionBodyContext;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedFunction;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedVariable;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCommand;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgFunction;
import cz.mg.compiler.tasks.mg.resolver.command.MgResolveCommandTask;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public abstract class MgResolveFunctionDefinitionTask extends MgResolveComponentDefinitionTask {
    @Input
    protected final MgUnresolvedFunction logicalFunction;

    @Output
    protected MgFunction function;

    @Utility
    private FunctionBodyContext functionBodyContext;

    public MgResolveFunctionDefinitionTask(Context context, MgUnresolvedFunction logicalFunction) {
        super(new FunctionContext(context), logicalFunction);
        this.logicalFunction = logicalFunction;
        this.functionBodyContext = new FunctionBodyContext(getContext());
    }

    @Override
    protected FunctionContext getContext() {
        return (FunctionContext) super.getContext();
    }

    public MgFunction getFunction() {
        return function;
    }

    protected void onResolveComponentChildren(){
        for(MgUnresolvedVariable logicalInput : logicalFunction.getInput()){
            postpone(MgResolveFunctionVariableDefinitionTask.class, () -> {
                MgResolveFunctionVariableDefinitionTask task = new MgResolveFunctionVariableDefinitionTask(getContext(), function, logicalInput);
                task.run();
                function.getInputVariables().addLast(task.getVariable());
            });
        }

        for(MgUnresolvedVariable logicalOutput : logicalFunction.getOutput()){
            postpone(MgResolveFunctionVariableDefinitionTask.class, () -> {
                MgResolveFunctionVariableDefinitionTask task = new MgResolveFunctionVariableDefinitionTask(getContext(), function, logicalOutput);
                task.run();
                function.getOutputVariables().addLast(task.getVariable());
            });
        }

        for(MgUnresolvedCommand logicalCommand : logicalFunction.getCommands()){
            postpone(MgResolveCommandTask.class, () -> {
                MgResolveCommandTask task = MgResolveCommandTask.create(new CommandContext(functionBodyContext), logicalCommand);
                task.run();
                function.getCommands().addLast(task.getCommand());
            });
        }
    }
}
