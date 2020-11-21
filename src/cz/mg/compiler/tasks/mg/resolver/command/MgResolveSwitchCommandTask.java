package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCaseCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedElseCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedSwitchCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgSwitchCommand;


public class MgResolveSwitchCommandTask extends MgResolveCommandTask {
    @cz.mg.compiler.annotations.Input
    private final cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext context;

    @Input
    private final MgUnresolvedSwitchCommand logicalCommand;

    @Output
    private MgSwitchCommand command;

    public MgResolveSwitchCommandTask(CommandContext context, MgUnresolvedSwitchCommand logicalCommand) {
        this.context = context;
        this.logicalCommand = logicalCommand;
    }

    @Override
    public MgSwitchCommand getCommand() {
        return command;
    }

    @Override
    protected void onRun() {
        command = new MgSwitchCommand();
        context.setCommand(command);

        for(MgUnresolvedCaseCommand logicalCaseCommand : logicalCommand.getCommands()){
            if(logicalCaseCommand instanceof MgUnresolvedElseCommand){
                cz.mg.compiler.tasks.mg.resolver.command.MgResolveElseCommandTask task = new MgResolveElseCommandTask(context, (MgUnresolvedElseCommand) logicalCaseCommand);
                task.run();
                command.getCaseCommands().addLast(task.getCommand());
            } else {
                MgResolveCaseCommandTask task = new MgResolveCaseCommandTask(context, logicalCaseCommand);
                task.run();
                command.getCaseCommands().addLast(task.getCommand());
            }
        }
    }
}
