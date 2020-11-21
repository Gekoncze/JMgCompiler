//package cz.mg.compiler.tasks.writers.c.part.expression.call;
//
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.annotations.task.Subtask;
//import cz.mg.language.entities.c.logical.parts.expressions.CExpression;
//import cz.mg.language.entities.c.logical.parts.expressions.calls.CFunctionCall;
//import cz.mg.language.entities.text.linear.tokens.c.CIdentifierToken;
//import cz.mg.language.entities.text.linear.Token;
//import cz.mg.language.entities.text.linear.tokens.c.CBracketToken;
//import cz.mg.language.entities.text.linear.tokens.c.CSeparatorToken;
//import cz.mg.compiler.tasks.writers.c.part.expression.CExpressionWriterTask;
//
//
//public class CFunctionCallWriterTask extends CCallWriterTask {
//    @Input
//    private final CFunctionCall functionCall;
//
//    @Output
//    private final List<Token> tokens = new List<>();
//
//    @Subtask
//    private final List<CExpressionWriterTask> expressionWriterTasks = new List<>();
//
//    public CFunctionCallWriterTask(CFunctionCall functionCall) {
//        this.functionCall = functionCall;
//    }
//
//    @Override
//    public List<Token> getTokens() {
//        return tokens;
//    }
//
//    @Override
//    protected void onRun() {
//        tokens.addLast(new CIdentifierToken(functionCall.getName()));
//        tokens.addLast(CBracketToken.ROUND_LEFT);
//        writeInput();
//        tokens.addLast(CBracketToken.ROUND_RIGHT);
//    }
//
//    private void writeInput(){
//        for(CExpression expression : functionCall.getInput()){
//            expressionWriterTasks.addLast(CExpressionWriterTask.create(expression));
//            expressionWriterTasks.getLast().run();
//            tokens.addCollectionLast(expressionWriterTasks.getLast().getTokens());
//            tokens.addLast(CSeparatorToken.COMMA);
//        }
//        if(tokens.count() > 0) if(tokens.getLast() == CSeparatorToken.COMMA) tokens.removeLast();
//    }
//}
