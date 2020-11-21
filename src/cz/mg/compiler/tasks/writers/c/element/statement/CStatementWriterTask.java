//package cz.mg.compiler.tasks.writers.c.element.statement;
//
//import cz.mg.language.LanguageException;
//import cz.mg.language.entities.c.logical.elements.statements.CStatement;
//import cz.mg.language.entities.c.logical.elements.statements.declarations.CForwardDeclaration;
//import cz.mg.language.entities.c.logical.elements.statements.definitions.CDefinition;
//import cz.mg.compiler.tasks.writers.c.element.CElementWriterTask;
//import cz.mg.compiler.tasks.writers.c.element.statement.declaration.CForwardDeclarationWriterTask;
//import cz.mg.compiler.tasks.writers.c.element.statement.definition.CDefinitionWriterTask;
//
//
//public abstract class CStatementWriterTask extends CElementWriterTask {
//    public static CStatementWriterTask create(CStatement statement){
//        if(statement instanceof CForwardDeclaration) return CForwardDeclarationWriterTask.create((CForwardDeclaration) statement);
//        if(statement instanceof CDefinition) return CDefinitionWriterTask.create((CDefinition)statement);
//        else throw new LanguageException("Could not write statement: " + statement.getClass().getSimpleName() + " is not supported.");
//    }
//}
