//package cz.mg.compiler.tasks.writers.c.part.expression.value;
//
//import cz.mg.language.LanguageException;
//import cz.mg.language.entities.c.logical.parts.expressions.values.CLiteral;
//import cz.mg.language.entities.c.logical.parts.expressions.values.CName;
//import cz.mg.language.entities.c.logical.parts.expressions.values.CValue;
//import cz.mg.compiler.tasks.writers.c.part.expression.CExpressionWriterTask;
//
//
//public abstract class CValueWriterTask extends CExpressionWriterTask {
//    public static CValueWriterTask create(CValue value){
//        if(value instanceof CLiteral) return new CLiteralWriterTask((CLiteral) value);
//        if(value instanceof CName) return new CNameWriterTask((CName) value);
//        throw new LanguageException("Could not write value: " + value.getClass().getSimpleName() + " is not supported.");
//    }
//}
