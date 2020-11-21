package cz.mg.compiler.tasks.mg.resolver.command.expression.operator.assignment;

import cz.mg.compiler.Todo;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.logical.parts.expressions.calls.operator.MgLogicalBinaryOperatorCallExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.MgExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.MgReferenceAssignmentExpression;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public class MgResolveReferenceAssignmentExpressionTask extends MgResolveAssignmentExpressionTask {
    @Input
    private final MgLogicalBinaryOperatorCallExpression logicalExpression;

    @Output
    private MgReferenceAssignmentExpression expression;

    public MgResolveReferenceAssignmentExpressionTask(
        CommandContext context,
        MgLogicalBinaryOperatorCallExpression logicalExpression,
        ExpectedParentInput parent
    ) {
        super(context, logicalExpression, parent);
        this.logicalExpression = logicalExpression;
    }

    @Override
    public MgExpression getExpression() {
        return expression;
    }

    @Override
    protected void onResolve() {
        new Todo();
//        MgExpression leftChild = resolveChild(logicalExpression.getLeft());
//        MgExpression rightChild = resolveChild(logicalExpression.getRight());
//
//
//
//        if(inputConnectors.count() != rightChild.getOutputConnectors().count()){
//            throw new LanguageException("Unbalanced reference assignment operator expression.");
//        }
//
//        expression = new MgReferenceAssignmentExpression();
//        expression.getExpressions().addLast(leftChild);
//        expression.getExpressions().addLast(rightChild);
//

    }
}
