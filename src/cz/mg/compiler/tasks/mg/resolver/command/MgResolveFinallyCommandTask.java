package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedFinallyCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgFinallyCommand;


public class MgResolveFinallyCommandTask extends MgResolveCommandTask {
    @Input
    private final CommandContext context;

    @Input
    private final MgUnresolvedFinallyCommand logicalCommand;

    @Output
    private MgFinallyCommand command;

    public MgResolveFinallyCommandTask(CommandContext context, MgUnresolvedFinallyCommand logicalCommand) {
        this.context = context;
        this.logicalCommand = logicalCommand;
    }

    @Override
    public MgFinallyCommand getCommand() {
        return command;
    }

    @Override
    protected void onRun() {
        command = new MgFinallyCommand();
        context.setCommand(command);

        for(MgUnresolvedCommand logicalCommand : logicalCommand.getCommands()){
            MgResolveCommandTask resolveCommandTask = MgResolveCommandTask.create(context, logicalCommand);
            resolveCommandTask.run();
            command.getCommands().addLast(resolveCommandTask.getCommand());
        }
    }
}
