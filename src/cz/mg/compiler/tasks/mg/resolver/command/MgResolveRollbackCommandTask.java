package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTreeTask;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedRollbackCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgRollbackCommand;


public class MgResolveRollbackCommandTask extends MgResolveCommandTask {
    @Input
    private final CommandContext context;

    @Input
    private final MgUnresolvedRollbackCommand logicalCommand;

    @Output
    private MgRollbackCommand command;

    public MgResolveRollbackCommandTask(CommandContext context, MgUnresolvedRollbackCommand logicalCommand) {
        this.context = context;
        this.logicalCommand = logicalCommand;
    }

    @Override
    public MgRollbackCommand getCommand() {
        return command;
    }

    @Override
    protected void onRun() {
        MgResolveExpressionTreeTask resolveExpressionTreeTask = new MgResolveExpressionTreeTask(context, logicalCommand.getExpression());
        resolveExpressionTreeTask.run();

        MgResolveExpressionTask resolveExpressionTask = MgResolveExpressionTask.create(
            context,
            resolveExpressionTreeTask.getLogicalCallExpression()
        );
        resolveExpressionTask.run();
        command = new MgRollbackCommand(resolveExpressionTask.getExpression());
        context.setCommand(command);
    }
}
