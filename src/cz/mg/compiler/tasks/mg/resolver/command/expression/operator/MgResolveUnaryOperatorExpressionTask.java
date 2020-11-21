package cz.mg.compiler.tasks.mg.resolver.command.expression.operator;

import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedLunaryOperatorCallExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedRunaryOperatorCallExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedUnaryOperatorCallExpression;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public abstract class MgResolveUnaryOperatorExpressionTask extends MgResolveOperatorExpressionTask {
    public MgResolveUnaryOperatorExpressionTask(CommandContext context) {
        super(context);
    }

    public static MgResolveOperatorExpressionTask create(
        CommandContext context,
        MgUnresolvedUnaryOperatorCallExpression logicalExpression
    ){
        if(logicalExpression instanceof MgUnresolvedLunaryOperatorCallExpression){
            return new MgResolveLunaryOperatorExpressionTask(context, (MgUnresolvedLunaryOperatorCallExpression) logicalExpression);
        }

        if(logicalExpression instanceof MgUnresolvedRunaryOperatorCallExpression){
            return new MgResolveRunaryOperatorExpressionTask(context, (MgUnresolvedRunaryOperatorCallExpression) logicalExpression);
        }

        throw new LanguageException("Unexpected operator expression " + logicalExpression.getClass().getSimpleName() + " for resolve.");
    }
}
