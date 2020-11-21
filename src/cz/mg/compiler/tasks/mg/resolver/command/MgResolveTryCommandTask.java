package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedTryCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgTryCommand;


public class MgResolveTryCommandTask extends MgResolveCommandTask {
    @Input
    private final CommandContext context;

    @Input
    private final MgUnresolvedTryCommand logicalCommand;

    @Output
    private MgTryCommand command;

    public MgResolveTryCommandTask(CommandContext context, MgUnresolvedTryCommand logicalCommand) {
        this.context = context;
        this.logicalCommand = logicalCommand;
    }

    @Override
    public MgTryCommand getCommand() {
        return command;
    }

    @Override
    protected void onRun() {
        command = new MgTryCommand();
        context.setCommand(command);

        for(MgUnresolvedCommand logicalCommand : logicalCommand.getCommands()){
            MgResolveCommandTask resolveCommandTask = MgResolveCommandTask.create(context, logicalCommand);
            resolveCommandTask.run();
            command.getCommands().addLast(resolveCommandTask.getCommand());
        }
    }
}
