package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.annotations.Utility;
import cz.mg.compiler.tasks.mg.resolver.context.component.structured.FunctionContext;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.compiler.tasks.mg.resolver.context.executable.FunctionBodyContext;
import cz.mg.language.entities.mg.logical.components.MgLogicalFunction;
import cz.mg.language.entities.mg.logical.components.MgLogicalVariable;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalCommand;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgFunction;
import cz.mg.compiler.tasks.mg.resolver.command.MgResolveCommandTask;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public abstract class MgResolveFunctionDefinitionTask extends MgResolveComponentDefinitionTask {
    @Input
    protected final MgLogicalFunction logicalFunction;

    @Output
    protected MgFunction function;

    @Utility
    private cz.mg.compiler.tasks.mg.resolver.context.executable.FunctionBodyContext functionBodyContext;

    public MgResolveFunctionDefinitionTask(Context context, MgLogicalFunction logicalFunction) {
        super(new cz.mg.compiler.tasks.mg.resolver.context.component.structured.FunctionContext(context), logicalFunction);
        this.logicalFunction = logicalFunction;
        this.functionBodyContext = new FunctionBodyContext(getContext());
    }

    @Override
    protected cz.mg.compiler.tasks.mg.resolver.context.component.structured.FunctionContext getContext() {
        return (FunctionContext) super.getContext();
    }

    public MgFunction getFunction() {
        return function;
    }

    protected void onResolveComponentChildren(){
        for(MgLogicalVariable logicalInput : logicalFunction.getInput()){
            postpone(cz.mg.compiler.tasks.mg.resolver.component.MgResolveFunctionVariableDefinitionTask.class, () -> {
                cz.mg.compiler.tasks.mg.resolver.component.MgResolveFunctionVariableDefinitionTask task = new cz.mg.compiler.tasks.mg.resolver.component.MgResolveFunctionVariableDefinitionTask(getContext(), function, logicalInput);
                task.run();
                function.getInputVariables().addLast(task.getVariable());
            });
        }

        for(MgLogicalVariable logicalOutput : logicalFunction.getOutput()){
            postpone(cz.mg.compiler.tasks.mg.resolver.component.MgResolveFunctionVariableDefinitionTask.class, () -> {
                cz.mg.compiler.tasks.mg.resolver.component.MgResolveFunctionVariableDefinitionTask task = new MgResolveFunctionVariableDefinitionTask(getContext(), function, logicalOutput);
                task.run();
                function.getOutputVariables().addLast(task.getVariable());
            });
        }

        for(MgLogicalCommand logicalCommand : logicalFunction.getCommands()){
            postpone(MgResolveCommandTask.class, () -> {
                MgResolveCommandTask task = MgResolveCommandTask.create(new CommandContext(functionBodyContext), logicalCommand);
                task.run();
                function.getCommands().addLast(task.getCommand());
            });
        }
    }
}
