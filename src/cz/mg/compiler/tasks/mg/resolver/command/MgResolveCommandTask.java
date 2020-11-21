package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.entities.mg.unresolved.parts.commands.*;
import cz.mg.language.entities.mg.runtime.parts.commands.MgCommand;
import cz.mg.compiler.tasks.mg.resolver.MgResolveTask;


public abstract class MgResolveCommandTask extends MgResolveTask {
    public static MgResolveCommandTask create(CommandContext context, MgUnresolvedCommand logicalCommand){
        if(logicalCommand instanceof MgUnresolvedExpressionCommand){
            return new MgResolveExpressionCommandTask(context, (MgUnresolvedExpressionCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedIfCommand){
            return new MgResolveIfCommandTask(context, (MgUnresolvedIfCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedWhileCommand){
            return new MgResolveWhileCommandTask(context, (MgUnresolvedWhileCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedContinueCommand) {
            return new MgResolveContinueCommandTask(context, (MgUnresolvedContinueCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedBreakCommand){
            return new MgResolveBreakCommandTask(context, (MgUnresolvedBreakCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedReturnCommand){
            return new MgResolveReturnCommandTask(context, (MgUnresolvedReturnCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedRollbackCommand){
            return new MgResolveRollbackCommandTask(context, (MgUnresolvedRollbackCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedSwitchCommand){
            return new MgResolveSwitchCommandTask(context, (MgUnresolvedSwitchCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedElseCommand){
            return new MgResolveElseCommandTask(context, (MgUnresolvedElseCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedCaseCommand){
            return new MgResolveCaseCommandTask(context, (MgUnresolvedCaseCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedCheckpointCommand){
            return new MgResolveCheckpointCommandTask(context, (MgUnresolvedCheckpointCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedTryCommand){
            return new MgResolveTryCommandTask(context, (MgUnresolvedTryCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedCatchCommand){
            return new MgResolveCatchCommandTask(context, (MgUnresolvedCatchCommand) logicalCommand);
        }

        if(logicalCommand instanceof MgUnresolvedFinallyCommand){
            return new MgResolveFinallyCommandTask(context, (MgUnresolvedFinallyCommand) logicalCommand);
        }

        throw new RuntimeException("Unsupported command " + logicalCommand.getClass().getSimpleName() + " for resolve.");
    }

    public MgResolveCommandTask() {
    }

    public abstract MgCommand getCommand();
}
