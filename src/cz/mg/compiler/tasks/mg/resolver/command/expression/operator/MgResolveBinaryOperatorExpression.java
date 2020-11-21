package cz.mg.compiler.tasks.mg.resolver.command.expression.operator;

import cz.mg.compiler.Todo;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedBinaryOperatorCallExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.operator.MgBinaryOperatorExpression;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public class MgResolveBinaryOperatorExpression extends MgResolveOperatorExpressionTask {
    @Input
    private final MgUnresolvedBinaryOperatorCallExpression logicalExpression;

    @Output
    private MgBinaryOperatorExpression expression;

    public MgResolveBinaryOperatorExpression(
        CommandContext context,
        MgUnresolvedBinaryOperatorCallExpression logicalExpression
    ) {
        super(context);
        this.logicalExpression = logicalExpression;
    }

    @Override
    public MgBinaryOperatorExpression getExpression() {
        return expression;
    }

//    private ExpectedParentInput[] preResolve(){
//        if(getParent() != null){
//            ExpectedParentInput expectedLeftInput = new ExpectedParentInput();
//            ExpectedParentInput expectedRightInput = new ExpectedParentInput();
//            for(int r = 0; r < getParent().getDatatypes().count(); r++){
//                MgBinaryOperator operator = new BinaryOperatorExpressionSearch(
//                    context,
//                    logicalExpression.getName(),
//                    getParent(),
//                    null,
//                    null,
//                    r
//                ).findOptional();
//
//                if(operator != null){
//                    expectedLeftInput.getDatatypes().addLast(operator.getInputVariables().getFirst().getDatatype());
//                    expectedRightInput.getDatatypes().addLast(operator.getInputVariables().getLast().getDatatype());
//                } else {
//                    break;
//                }
//            }
//            return new ExpectedParentInput[]{
//                expectedLeftInput,
//                expectedRightInput
//            };
//        } else {
//            return new ExpectedParentInput[]{
//                null, null
//            };
//        }
//    }

    @Override
    protected void onResolve() {
        new Todo();
//        ExpectedParentInput[] expectedInput = preResolve();
//        MgExpression leftChild = resolveChild(logicalExpression.getLeft(), expectedInput[0]);
//        MgExpression rightChild = resolveChild(logicalExpression.getRight(), expectedInput[1]);
//
//        if(leftChild.getOutputConnectors().count() != rightChild.getOutputConnectors().count()){
//            throw new LanguageException("Unbalanced binary operator expression.");
//        }
//
//        int count = leftChild.getOutputConnectors().count();
//
//        if(getParent() != null){
//            if(getParent().)
//        }
//
//        List<MgBinaryOperator> operators = new List<>();
//        for(int r = 0; r < ){
//
//        }
//        expression = new MgBinaryOperatorExpression(operators);
//        expression.getExpressions().addLast(leftChild);
//        expression.getExpressions().addLast(rightChild);
//
//        for(int r = 0; r < expression.getReplications().count(); r++){
//            connect(
//                expression.getInputConnectors().get(r * 2),
//                leftChild.getOutputConnectors().get(r)
//            );
//            connect(
//                expression.getInputConnectors().get(r * 2 + 1),
//                rightChild.getOutputConnectors().get(r)
//            );
//        }
    }

//    private List<@Optional MgReplication> createReplications(
//        @Optional MgExpression parent,
//        @Optional MgExpression leftChild,
//        @Optional MgExpression rightChild,
//        @Mandatory boolean optional
//    ){
//        // todo - problem - list of replications has replication optional
//        // todo - what does it mean for user of this as parent input?
//        // todo - he cannot resolve it or what???
//
//        int replicationCount = guessReplicationCount(parent, leftChild, rightChild);
//        List<MgReplication> replications = new List<>();
//        for(int r = 0; r < replicationCount; r++){
//            replications.addLast(createReplication(parent, leftChild, rightChild, r, optional));
//        }
//        return replications;
//    }
//
//    private MgReplication createReplication(
//        @Optional MgExpression parent,
//        @Optional MgExpression leftChild,
//        @Optional MgExpression rightChild,
//        @Mandatory int replication,
//        @Mandatory boolean optional
//    ){
//        MgBinaryOperator operator = new BinaryOperatorExpressionFilter(
//            context,
//            logicalExpression.getName(),
//            parent,
//            leftChild,
//            rightChild,
//            replication
//        ).find(optional);
//
//        if(operator != null){
//            return new MgReplication(operator);
//        } else {
//            return null;
//        }
//    }
//
//    private int guessReplicationCount(
//        @Optional MgExpression parent,
//        @Optional MgExpression leftChild,
//        @Optional MgExpression rightChild
//    ){
//        if(leftChild != null && rightChild != null){
//            return leftChild.getOutputConnectors().count();
//        } else if(parent != null){
//            return ExpressionFilter.destinationInterface(parent).count();
//        } else {
//            throw new RuntimeException();
//        }
//    }
//
//    private static class MgLeftExpression extends MgExpression {
//        public MgLeftExpression(List<MgReplication> replications) {
//            super(
//                new Array<>(new List<>(input(replications))),
//                new Array<>()
//            );
//        }
//
//        private static Clump<MgInputConnector> input(List<MgReplication> replications){
//            return new PartCollection<>(
//                replications,
//                replication -> replication.getInputConnectors().getFirst()
//            );
//        }
//
//        @Override
//        protected void onRun(MgFunctionInstance functionInstance) {
//            throw new RuntimeException();
//        }
//    }
//
//    private static class MgRightExpression extends MgExpression {
//        public MgRightExpression(List<MgReplication> replications) {
//            super(
//                new Array<>(new List<>(input(replications))),
//                new Array<>()
//            );
//        }
//
//        private static Clump<MgInputConnector> input(List<MgReplication> replications){
//            return new PartCollection<>(
//                replications,
//                replication -> replication.getInputConnectors().getLast()
//            );
//        }
//
//        @Override
//        protected void onRun(MgFunctionInstance functionInstance) {
//            throw new RuntimeException();
//        }
//    }
}
