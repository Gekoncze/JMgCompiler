//package cz.mg.compiler.tasks.writers.c.part.expression.call;
//
//import cz.mg.language.entities.c.logical.parts.expressions.CDeclaration;
//import cz.mg.language.entities.c.logical.parts.expressions.CExpression;
//import cz.mg.language.entities.c.logical.parts.expressions.calls.CFunctionCall;
//import cz.mg.language.entities.c.logical.parts.expressions.values.CValue;
//import cz.mg.language.entities.text.linear.tokens.c.CBracketToken;
//
//
//public abstract class COperatorWriterTask extends CCallWriterTask {
//    protected void writeLeftBracket(CExpression operand){
//        if(skip(operand)) return;
//        getTokens().addLast(CBracketToken.ROUND_LEFT);
//    }
//
//    protected void writeRightBracket(CExpression operand){
//        if(skip(operand)) return;
//        getTokens().addLast(CBracketToken.ROUND_RIGHT);
//    }
//
//    private boolean skip(CExpression operand){
//        if(operand instanceof CValue) return true;
//        if(operand instanceof CFunctionCall) return true;
//        if(operand instanceof CDeclaration) return true;
//        return false;
//    }
//}
