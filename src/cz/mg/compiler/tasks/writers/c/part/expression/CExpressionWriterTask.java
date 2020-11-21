//package cz.mg.compiler.tasks.writers.c.part.expression;
//
//import cz.mg.language.LanguageException;
//import cz.mg.language.entities.c.logical.parts.expressions.CDeclaration;
//import cz.mg.language.entities.c.logical.parts.expressions.CExpression;
//import cz.mg.language.entities.c.logical.parts.expressions.calls.CCall;
//import cz.mg.language.entities.c.logical.parts.expressions.values.CValue;
//import cz.mg.compiler.tasks.writers.c.part.CPartWriterTask;
//import cz.mg.compiler.tasks.writers.c.part.expression.call.CCallWriterTask;
//import cz.mg.compiler.tasks.writers.c.part.expression.value.CValueWriterTask;
//
//
//public abstract class CExpressionWriterTask extends CPartWriterTask {
//    public static CExpressionWriterTask create(CExpression expression){
//        if(expression instanceof CValue) return CValueWriterTask.create((CValue)expression);
//        if(expression instanceof CCall) return CCallWriterTask.create((CCall)expression);
//        if(expression instanceof CDeclaration) return new CDeclarationWriterTask((CDeclaration)expression);
//        throw new LanguageException("Could not write expression: " + expression.getClass().getSimpleName() + " is not supported.");
//    }
//}
