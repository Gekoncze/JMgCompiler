package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.entities.mg.logical.parts.commands.*;
import cz.mg.language.entities.mg.runtime.parts.commands.MgCommand;
import cz.mg.compiler.tasks.mg.resolver.MgResolveTask;


public abstract class MgResolveCommandTask extends MgResolveTask {
    public static MgResolveCommandTask create(CommandContext context, MgLogicalCommand logicalCommand){
        if(logicalCommand instanceof MgLogicalExpressionCommand){
            return new MgResolveExpressionCommandTask(context, (MgLogicalExpressionCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalIfCommand){
            return new MgResolveIfCommandTask(context, (MgLogicalIfCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalWhileCommand){
            return new MgResolveWhileCommandTask(context, (MgLogicalWhileCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalContinueCommand) {
            return new MgResolveContinueCommandTask(context, (MgLogicalContinueCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalBreakCommand){
            return new MgResolveBreakCommandTask(context, (MgLogicalBreakCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalReturnCommand){
            return new MgResolveReturnCommandTask(context, (MgLogicalReturnCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalRollbackCommand){
            return new MgResolveRollbackCommandTask(context, (MgLogicalRollbackCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalSwitchCommand){
            return new MgResolveSwitchCommandTask(context, (MgLogicalSwitchCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalElseCommand){
            return new MgResolveElseCommandTask(context, (MgLogicalElseCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalCaseCommand){
            return new MgResolveCaseCommandTask(context, (MgLogicalCaseCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalCheckpointCommand){
            return new MgResolveCheckpointCommandTask(context, (MgLogicalCheckpointCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalTryCommand){
            return new MgResolveTryCommandTask(context, (MgLogicalTryCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalCatchCommand){
            return new MgResolveCatchCommandTask(context, (MgLogicalCatchCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgLogicalFinallyCommand){
            return new MgResolveFinallyCommandTask(context, (MgLogicalFinallyCommand) logicalCommand);
        }

        throw new RuntimeException("Unsupported command " + logicalCommand.getClass().getSimpleName() + " for resolve.");
    }

    public MgResolveCommandTask() {
    }

    public abstract MgCommand getCommand();
}
