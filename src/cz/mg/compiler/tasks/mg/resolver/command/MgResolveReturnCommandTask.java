package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTreeTask;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedReturnCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgReturnCommand;


public class MgResolveReturnCommandTask extends MgResolveCommandTask {
    @Input
    private final CommandContext context;

    @Input
    private final MgUnresolvedReturnCommand logicalCommand;

    @Output
    private MgReturnCommand command;

    public MgResolveReturnCommandTask(CommandContext context, MgUnresolvedReturnCommand logicalCommand) {
        this.context = context;
        this.logicalCommand = logicalCommand;
    }

    @Override
    public MgReturnCommand getCommand() {
        return command;
    }

    @Override
    protected void onRun() {
        MgResolveExpressionTreeTask resolveExpressionTreeTask = new MgResolveExpressionTreeTask(context, logicalCommand.getExpression());
        resolveExpressionTreeTask.run();

        MgResolveExpressionTask resolveExpressionTask = MgResolveExpressionTask.create(
            context,
            resolveExpressionTreeTask.getLogicalCallExpression(),
            new ExpectedParentInput()
        );
        resolveExpressionTask.run();
        command = new MgReturnCommand(resolveExpressionTask.getExpression());
        context.setCommand(command);
    }
}
