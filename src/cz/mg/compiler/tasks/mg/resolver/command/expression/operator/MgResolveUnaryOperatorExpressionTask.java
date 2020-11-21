package cz.mg.compiler.tasks.mg.resolver.command.expression.operator;

import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.logical.parts.expressions.calls.operator.MgLogicalLunaryOperatorCallExpression;
import cz.mg.language.entities.mg.logical.parts.expressions.calls.operator.MgLogicalRunaryOperatorCallExpression;
import cz.mg.language.entities.mg.logical.parts.expressions.calls.operator.MgLogicalUnaryOperatorCallExpression;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public abstract class MgResolveUnaryOperatorExpressionTask extends cz.mg.compiler.tasks.mg.resolver.command.expression.operator.MgResolveOperatorExpressionTask {
    public MgResolveUnaryOperatorExpressionTask(
        CommandContext context,
        ExpectedParentInput parent
    ) {
        super(context, parent);
    }

    public static MgResolveOperatorExpressionTask create(
        CommandContext context,
        MgLogicalUnaryOperatorCallExpression logicalExpression,
        ExpectedParentInput parent
    ){
        if(logicalExpression instanceof MgLogicalLunaryOperatorCallExpression){
            return new MgResolveLunaryOperatorExpressionTask(context, (MgLogicalLunaryOperatorCallExpression) logicalExpression, parent);
        }

        if(logicalExpression instanceof MgLogicalRunaryOperatorCallExpression){
            return new MgResolveRunaryOperatorExpressionTask(context, (MgLogicalRunaryOperatorCallExpression) logicalExpression, parent);
        }

        throw new LanguageException("Unexpected operator expression " + logicalExpression.getClass().getSimpleName() + " for resolve.");
    }
}
