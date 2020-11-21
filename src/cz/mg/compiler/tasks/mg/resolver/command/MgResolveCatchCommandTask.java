package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.component.MgResolveFunctionVariableDefinitionTask;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCatchCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgCatchCommand;


public class MgResolveCatchCommandTask extends MgResolveCommandTask {
    @Input
    private final CommandContext context;

    @Input
    private final MgUnresolvedCatchCommand logicalCommand;

    @Output
    private MgCatchCommand command;

    public MgResolveCatchCommandTask(CommandContext context, MgUnresolvedCatchCommand logicalCommand) {
        this.context = context;
        this.logicalCommand = logicalCommand;
    }

    @Override
    public MgCatchCommand getCommand() {
        return command;
    }

    @Override
    protected void onRun() {
        MgResolveFunctionVariableDefinitionTask task = new MgResolveFunctionVariableDefinitionTask(
            context, context.getFunctionContext().getFunction(), logicalCommand.getVariable()
        );
        task.run();

        command = new MgCatchCommand(task.getVariable());
        context.setCommand(command);

        for(MgUnresolvedCommand logicalCommand : logicalCommand.getCommands()){
            MgResolveCommandTask resolveCommandTask = MgResolveCommandTask.create(context, logicalCommand);
            resolveCommandTask.run();
            command.getCommands().addLast(resolveCommandTask.getCommand());
        }
    }
}
