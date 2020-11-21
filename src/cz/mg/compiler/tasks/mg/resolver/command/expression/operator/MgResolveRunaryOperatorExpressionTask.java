package cz.mg.compiler.tasks.mg.resolver.command.expression.operator;

import cz.mg.compiler.Todo;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedRunaryOperatorCallExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.MgExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.operator.MgRunaryOperatorExpression;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public class MgResolveRunaryOperatorExpressionTask extends MgResolveUnaryOperatorExpressionTask {
    @Input
    private final MgUnresolvedRunaryOperatorCallExpression logicalExpression;

    @Output
    private MgRunaryOperatorExpression expression;

    public MgResolveRunaryOperatorExpressionTask(
        CommandContext context,
        MgUnresolvedRunaryOperatorCallExpression logicalExpression,
        ExpectedParentInput parent
    ) {
        super(context, parent);
        this.logicalExpression = logicalExpression;
    }

    @Override
    protected void onResolve() {
        new Todo();
//        MgExpression leftChild = resolveChild(logicalExpression.getLeft(), getExpectedInput());
//        int replicationCount = leftChild.getOutputConnectors().count();
//
//        List<MgReplication> replications = new List<>();
//        for(int r = 0; r < replicationCount; r++){
//            replications.addLast(new MgReplication(new RunaryOperatorSearch(
//                context,
//                logicalExpression.getName(),
//                getParent(),
//                leftChild,
//                r
//            ).find()));
//        }
//
//        expression = new MgRunaryOperatorExpression(replications, leftChild);
    }
    
//    private ExpectedParentInput getExpectedInput(){
//        if(getParent() != null){
//            ExpectedParentInput expectedInput = new ExpectedParentInput();
//            for(int r = 0; r < getParent().getDatatypes().count(); r++){
//                MgRunaryOperator operator = new RunaryOperatorSearch(
//                    context,
//                    logicalExpression.getName(),
//                    getParent(),
//                    null,
//                    r
//                ).findOptional();
//                expectedInput.getDatatypes().addLast(
//                    operator != null
//                        ? operator.getInputVariables().getFirst().getDatatype()
//                        : null
//                );
//            }
//            return expectedInput;
//        } else {
//            return null;
//        }
//    }

    @Override
    public MgExpression getExpression() {
        return expression;
    }
}
