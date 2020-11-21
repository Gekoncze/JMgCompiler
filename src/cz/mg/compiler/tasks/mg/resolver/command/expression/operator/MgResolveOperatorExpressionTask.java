package cz.mg.compiler.tasks.mg.resolver.command.expression.operator;

import cz.mg.compiler.tasks.mg.resolver.command.expression.operator.assignment.MgResolveReferenceAssignmentExpressionTask;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.Operators;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedBinaryOperatorCallExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedOperatorCallExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedUnaryOperatorCallExpression;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public abstract class MgResolveOperatorExpressionTask extends MgResolveExpressionTask {
    public MgResolveOperatorExpressionTask(CommandContext context) {
        super(context);
    }

    public static MgResolveOperatorExpressionTask create(
        CommandContext context,
        MgUnresolvedOperatorCallExpression logicalExpression
    ){
        if(logicalExpression instanceof MgUnresolvedBinaryOperatorCallExpression){
            if(logicalExpression.getName().equals(Operators.ASSIGNMENT)){
                throw new LanguageException("Assignment using = operator is not supported yet. Use &= or $= instead.");
            } else if(logicalExpression.getName().equals(Operators.REFERENCE_ASSIGNMENT)){
                return new MgResolveReferenceAssignmentExpressionTask(context, (MgUnresolvedBinaryOperatorCallExpression) logicalExpression);
            } else if(logicalExpression.getName().equals(Operators.VALUE_ASSIGNMENT)){
                return new MgResolveValueAssignmentExpressionTask(context, (MgUnresolvedBinaryOperatorCallExpression) logicalExpression);
            } else {
                return new MgResolveBinaryOperatorExpression(context, (MgUnresolvedBinaryOperatorCallExpression)logicalExpression);
            }
        }

        if(logicalExpression instanceof MgUnresolvedUnaryOperatorCallExpression){
            return MgResolveUnaryOperatorExpressionTask.create(context, (MgUnresolvedUnaryOperatorCallExpression) logicalExpression);
        }

        throw new LanguageException("Unexpected operator expression " + logicalExpression.getClass().getSimpleName() + " for resolve.");
    }
}
