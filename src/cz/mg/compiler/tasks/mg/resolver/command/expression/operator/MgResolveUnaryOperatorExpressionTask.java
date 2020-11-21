package cz.mg.compiler.tasks.mg.resolver.command.expression.operator;

import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedLunaryOperatorCallExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedRunaryOperatorCallExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedUnaryOperatorCallExpression;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public abstract class MgResolveUnaryOperatorExpressionTask extends MgResolveOperatorExpressionTask {
    public MgResolveUnaryOperatorExpressionTask(
        CommandContext context,
        ExpectedParentInput parent
    ) {
        super(context, parent);
    }

    public static MgResolveOperatorExpressionTask create(
        CommandContext context,
        MgUnresolvedUnaryOperatorCallExpression logicalExpression,
        ExpectedParentInput parent
    ){
        if(logicalExpression instanceof MgUnresolvedLunaryOperatorCallExpression){
            return new MgResolveLunaryOperatorExpressionTask(context, (MgUnresolvedLunaryOperatorCallExpression) logicalExpression, parent);
        }

        if(logicalExpression instanceof MgUnresolvedRunaryOperatorCallExpression){
            return new MgResolveRunaryOperatorExpressionTask(context, (MgUnresolvedRunaryOperatorCallExpression) logicalExpression, parent);
        }

        throw new LanguageException("Unexpected operator expression " + logicalExpression.getClass().getSimpleName() + " for resolve.");
    }
}
