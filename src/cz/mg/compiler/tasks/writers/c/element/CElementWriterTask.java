//package cz.mg.compiler.tasks.writers.c.element;
//
//import cz.mg.language.LanguageException;
//import cz.mg.language.entities.c.logical.elements.CElement;
//import cz.mg.language.entities.c.logical.elements.directives.CDirective;
//import cz.mg.language.entities.c.logical.elements.statements.CStatement;
//import cz.mg.compiler.tasks.writers.LineWriterTask;
//import cz.mg.compiler.tasks.writers.c.CWriterTask;
//import cz.mg.compiler.tasks.writers.c.element.directive.CDirectiveWriterTask;
//import cz.mg.compiler.tasks.writers.c.element.statement.CStatementWriterTask;
//
//
//public abstract class CElementWriterTask extends CWriterTask implements LineWriterTask {
//    public static CElementWriterTask create(CElement element){
//        if(element instanceof CDirective) return CDirectiveWriterTask.create((CDirective) element);
//        if(element instanceof CStatement) return CStatementWriterTask.create((CStatement) element);
//        throw new LanguageException("Could not write element: " + element.getClass().getSimpleName() + " is not supported.");
//    }
//}
