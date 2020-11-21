package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.component.MgResolveFunctionVariableDefinitionTask;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalCatchCommand;
import cz.mg.language.entities.mg.logical.parts.commands.MgLogicalCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgCatchCommand;


public class MgResolveCatchCommandTask extends MgResolveCommandTask {
    @cz.mg.compiler.annotations.Input
    private final cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext context;

    @Input
    private final MgLogicalCatchCommand logicalCommand;

    @Output
    private MgCatchCommand command;

    public MgResolveCatchCommandTask(CommandContext context, MgLogicalCatchCommand logicalCommand) {
        this.context = context;
        this.logicalCommand = logicalCommand;
    }

    @Override
    public MgCatchCommand getCommand() {
        return command;
    }

    @Override
    protected void onRun() {
        cz.mg.compiler.tasks.mg.resolver.component.MgResolveFunctionVariableDefinitionTask task = new MgResolveFunctionVariableDefinitionTask(
            context, context.getFunctionContext().getFunction(), logicalCommand.getVariable()
        );
        task.run();

        command = new MgCatchCommand(task.getVariable());
        context.setCommand(command);

        for(MgLogicalCommand logicalCommand : logicalCommand.getCommands()){
            MgResolveCommandTask resolveCommandTask = MgResolveCommandTask.create(context, logicalCommand);
            resolveCommandTask.run();
            command.getCommands().addLast(resolveCommandTask.getCommand());
        }
    }
}
