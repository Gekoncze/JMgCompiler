package cz.mg.compiler.tasks.mg.resolver.command.expression.operator;

import cz.mg.compiler.Todo;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedLunaryOperatorCallExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.MgExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.operator.MgLunaryOperatorExpression;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public class MgResolveLunaryOperatorExpressionTask extends MgResolveUnaryOperatorExpressionTask {
    @Input
    private final MgUnresolvedLunaryOperatorCallExpression logicalExpression;

    @Output
    private MgLunaryOperatorExpression expression;

    public MgResolveLunaryOperatorExpressionTask(
        CommandContext context,
        MgUnresolvedLunaryOperatorCallExpression logicalExpression
    ) {
        super(context);
        this.logicalExpression = logicalExpression;
    }

    @Override
    protected void onResolve() {
        new Todo();
//        MgExpression rightChild = resolveChild(logicalExpression.getRight(), getExpectedInput());
//        int replicationCount = rightChild.getOutputConnectors().count();
//
//        List<MgReplication> replications = new List<>();
//        for(int r = 0; r < replicationCount; r++){
//            replications.addLast(new MgReplication(new LunaryOperatorSearch(
//                context,
//                logicalExpression.getName(),
//                getParent(),
//                rightChild,
//                r
//            ).find()));
//        }
//
//        expression = new MgLunaryOperatorExpression(replications, rightChild);
    }

//    private ExpectedParentInput getExpectedInput(){
//        if(getParent() != null){
//            ExpectedParentInput expectedInput = new ExpectedParentInput();
//            for(int r = 0; r < getParent().getDatatypes().count(); r++){
//                MgLunaryOperator operator = new LunaryOperatorSearch(
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
