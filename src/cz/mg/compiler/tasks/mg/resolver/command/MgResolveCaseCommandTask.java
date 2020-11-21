package cz.mg.compiler.tasks.mg.resolver.command;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTreeTask;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCaseCommand;
import cz.mg.language.entities.mg.unresolved.parts.commands.MgUnresolvedCommand;
import cz.mg.language.entities.mg.runtime.parts.commands.MgCaseCommand;


public class MgResolveCaseCommandTask extends MgResolveCommandTask {
    @cz.mg.compiler.annotations.Input
    private final cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext context;

    @Input
    private final MgUnresolvedCaseCommand logicalCommand;

    @Output
    private MgCaseCommand command;

    public MgResolveCaseCommandTask(CommandContext context, MgUnresolvedCaseCommand logicalCommand) {
        this.context = context;
        this.logicalCommand = logicalCommand;
    }

    @Override
    public MgCaseCommand getCommand() {
        return command;
    }

    @Override
    protected void onRun() {
        cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTreeTask resolveExpressionTreeTask = new MgResolveExpressionTreeTask(context, logicalCommand.getExpression());
        resolveExpressionTreeTask.run();

        cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTask resolveExpressionTask = MgResolveExpressionTask.create(
            context,
            resolveExpressionTreeTask.getLogicalCallExpression(),
            new ExpectedParentInput(MgCaseCommand.DATATYPE)
        );
        resolveExpressionTask.run();
        command = new MgCaseCommand(resolveExpressionTask.getExpression());
        context.setCommand(command);

        for(MgUnresolvedCommand logicalCommand : logicalCommand.getCommands()){
            MgResolveCommandTask resolveCommandTask = MgResolveCommandTask.create(context, logicalCommand);
            resolveCommandTask.run();
            command.getCommands().addLast(resolveCommandTask.getCommand());
        }
    }
}
