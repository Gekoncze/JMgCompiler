//package cz.mg.compiler.tasks.writers.c.part.expression.call;
//
//import cz.mg.language.LanguageException;
//import cz.mg.language.entities.c.logical.parts.expressions.calls.*;
//import cz.mg.compiler.tasks.writers.c.part.expression.CExpressionWriterTask;
//
//
//public abstract class CCallWriterTask extends CExpressionWriterTask {
//    public static CCallWriterTask create(CCall call){
//        if(call instanceof CFunctionCall) return new CFunctionCallWriterTask((CFunctionCall) call);
//        if(call instanceof CBinaryOperatorCall) return new CBinaryOperatorCallWriterTask((CBinaryOperatorCall) call);
//        if(call instanceof CUnaryLeftOperatorCall) return new CUnaryLeftOperatorCallWriterTask((CUnaryLeftOperatorCall) call);
//        if(call instanceof CUnaryRightOperatorCall) return new CUnaryRightOperatorCallWriterTask((CUnaryRightOperatorCall) call);
//        throw new LanguageException("Could not write call: " + call.getClass().getSimpleName() + " is not supported.");
//    }
//}
