package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedElseCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgElseCommand;


public class MgResolveElseCommandTask extends MgResolveCommandTask {
    @Input
    private final CommandContext context;

    @Input
    private final MgUnresolvedElseCommand logicalCommand;

    @Output
    private MgElseCommand command;

    public MgResolveElseCommandTask(CommandContext context, MgUnresolvedElseCommand logicalCommand) {
        this.context = context;
        this.logicalCommand = logicalCommand;
    }

    @Override
    public MgElseCommand getCommand() {
        return command;
    }

    @Override
    protected void onRun() {
        command = new MgElseCommand();
        context.setCommand(command);

        for(MgUnresolvedCommand logicalCommand : logicalCommand.getCommands()){
            MgResolveCommandTask resolveCommandTask = MgResolveCommandTask.create(context, logicalCommand);
            resolveCommandTask.run();
            command.getCommands().addLast(resolveCommandTask.getCommand());
        }
    }
}
