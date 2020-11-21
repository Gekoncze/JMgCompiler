package cz.mg.compiler.tasks.mg.resolver.command.expression.operator.assignment;

import cz.mg.compiler.annotations.Input;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedBinaryOperatorCallExpression;
import cz.mg.compiler.tasks.mg.resolver.command.expression.operator.MgResolveOperatorExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public abstract class MgResolveAssignmentExpressionTask extends MgResolveOperatorExpressionTask {
    @Input
    protected final MgUnresolvedBinaryOperatorCallExpression logicalExpression;

    public MgResolveAssignmentExpressionTask(
        CommandContext context,
        MgUnresolvedBinaryOperatorCallExpression logicalExpression,
        ExpectedParentInput parent
    ) {
        super(context, parent);
        this.logicalExpression = logicalExpression;
    }
}
