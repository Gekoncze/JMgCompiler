package cz.mg.compiler.tasks.mg.resolver.command.expression.operator;

import cz.mg.compiler.tasks.mg.resolver.command.expression.operator.assignment.MgResolveReferenceAssignmentExpressionTask;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.Operators;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedBinaryOperatorCallExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedOperatorCallExpression;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedUnaryOperatorCallExpression;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public abstract class MgResolveOperatorExpressionTask extends MgResolveExpressionTask {
    public MgResolveOperatorExpressionTask(CommandContext context, ExpectedParentInput parent) {
        super(context, parent);
    }

    public static MgResolveOperatorExpressionTask create(
        CommandContext context,
        MgUnresolvedOperatorCallExpression logicalExpression,
        ExpectedParentInput parent
    ){
        if(logicalExpression instanceof MgUnresolvedBinaryOperatorCallExpression){
            if(logicalExpression.getName().equals(Operators.ASSIGNMENT)){
                throw new LanguageException("Assignment using = operator is not supported yet. Use &= or $= instead.");
            } else if(logicalExpression.getName().equals(Operators.REFERENCE_ASSIGNMENT)){
                return new MgResolveReferenceAssignmentExpressionTask(context, (MgUnresolvedBinaryOperatorCallExpression) logicalExpression, parent);
            } else if(logicalExpression.getName().equals(Operators.VALUE_ASSIGNMENT)){
                return new MgResolveValueAssignmentExpressionTask(context, (MgUnresolvedBinaryOperatorCallExpression) logicalExpression, parent);
            } else {
                return new MgResolveBinaryOperatorExpression(context, (MgUnresolvedBinaryOperatorCallExpression)logicalExpression, parent);
            }
        }

        if(logicalExpression instanceof MgUnresolvedUnaryOperatorCallExpression){
            return MgResolveUnaryOperatorExpressionTask.create(context, (MgUnresolvedUnaryOperatorCallExpression) logicalExpression, parent);
        }

        throw new LanguageException("Unexpected operator expression " + logicalExpression.getClass().getSimpleName() + " for resolve.");
    }
}
