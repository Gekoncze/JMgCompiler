package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCatchCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCheckpointCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgCatchCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgCheckpointCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgFinallyCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgTryCommand;


public class MgResolveCheckpointCommandTask extends MgResolveCommandTask {
    @Input
    private final CommandContext context;

    @Input
    private final MgUnresolvedCheckpointCommand logicalCommand;

    @Output
    private MgCheckpointCommand command;

    public MgResolveCheckpointCommandTask(CommandContext context, MgUnresolvedCheckpointCommand logicalCommand) {
        this.context = context;
        this.logicalCommand = logicalCommand;
    }

    @Override
    public MgCheckpointCommand getCommand() {
        return command;
    }

    @Override
    protected void onRun() {
        MgTryCommand tryCommand = null;
        if(logicalCommand.getTryCommand() != null){
            MgResolveTryCommandTask resolveTryCommandTask = new MgResolveTryCommandTask(context, logicalCommand.getTryCommand());
            resolveTryCommandTask.run();
            tryCommand = resolveTryCommandTask.getCommand();
        }

        List<MgCatchCommand> catchCommands = null;
        if(logicalCommand.getCatchCommands() != null){
            catchCommands = new List<>();
            for(MgUnresolvedCatchCommand logicalCatchCommand : logicalCommand.getCatchCommands()){
                MgResolveCatchCommandTask resolveCatchCommandTask = new MgResolveCatchCommandTask(context, logicalCatchCommand);
                resolveCatchCommandTask.run();
                catchCommands.addLast(resolveCatchCommandTask.getCommand());
            }
        }

        MgFinallyCommand finallyCommand = null;
        if(logicalCommand.getFinallyCommand() != null){
            MgResolveFinallyCommandTask resolveFinallyCommandTask = new MgResolveFinallyCommandTask(context, logicalCommand.getFinallyCommand());
            resolveFinallyCommandTask.run();
            finallyCommand = resolveFinallyCommandTask.getCommand();
        }

        command = new MgCheckpointCommand(tryCommand, catchCommands, finallyCommand);
        context.setCommand(command);
    }
}
