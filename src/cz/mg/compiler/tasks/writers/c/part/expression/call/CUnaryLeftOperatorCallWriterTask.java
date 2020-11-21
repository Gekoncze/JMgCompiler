//package cz.mg.compiler.tasks.writers.c.part.expression.call;
//
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.annotations.task.Subtask;
//import cz.mg.language.entities.c.logical.parts.expressions.CExpression;
//import cz.mg.language.entities.c.logical.parts.expressions.calls.CUnaryLeftOperatorCall;
//import cz.mg.language.entities.text.linear.Token;
//import cz.mg.language.entities.text.linear.tokens.c.COperatorToken;
//import cz.mg.compiler.tasks.writers.c.part.expression.CExpressionWriterTask;
//
//
//public class CUnaryLeftOperatorCallWriterTask extends COperatorWriterTask {
//    @Input
//    private final CUnaryLeftOperatorCall unaryLeftOperatorCall;
//
//    @Output
//    private final List<Token> tokens = new List<>();
//
//    @Subtask
//    private CExpressionWriterTask operandWriterTask = null;
//
//    public CUnaryLeftOperatorCallWriterTask(CUnaryLeftOperatorCall unaryLeftOperatorCall) {
//        this.unaryLeftOperatorCall = unaryLeftOperatorCall;
//    }
//
//    @Override
//    public List<Token> getTokens() {
//        return tokens;
//    }
//
//    @Override
//    protected void onRun() {
//        writeOperator();
//        writeOperand();
//    }
//
//    private void writeOperator() {
//        tokens.addLast(COperatorToken.Unary.get(unaryLeftOperatorCall.getOperator()));
//    }
//
//    private void writeOperand(){
//        CExpression operand = unaryLeftOperatorCall.getLeft();
//        writeLeftBracket(operand);
//        operandWriterTask = CExpressionWriterTask.create(operand);
//        operandWriterTask.run();
//        tokens.addCollectionLast(operandWriterTask.getTokens());
//        writeRightBracket(operand);
//    }
//}
