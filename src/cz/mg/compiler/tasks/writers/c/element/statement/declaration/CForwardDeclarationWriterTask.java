//package cz.mg.compiler.tasks.writers.c.element.statement.declaration;
//
//import cz.mg.language.LanguageException;
//import cz.mg.language.entities.c.logical.elements.statements.declarations.CForwardDeclaration;
//import cz.mg.language.entities.c.logical.elements.statements.declarations.CFunctionForwardDeclaration;
//import cz.mg.language.entities.c.logical.elements.statements.declarations.CStructureForwardDeclaration;
//import cz.mg.compiler.tasks.writers.c.element.statement.CStatementWriterTask;
//
//
//public abstract class CForwardDeclarationWriterTask extends CStatementWriterTask {
//    public static CForwardDeclarationWriterTask create(CForwardDeclaration declaration){
//        if(declaration instanceof CFunctionForwardDeclaration) return new CFunctionForwardDeclarationWriterTask((CFunctionForwardDeclaration) declaration);
//        if(declaration instanceof CStructureForwardDeclaration) return new CStructureForwardDeclarationWriterTask((CStructureForwardDeclaration) declaration);
//        throw new LanguageException("Could not write declaration: " + declaration.getClass().getSimpleName() + " is not supported.");
//    }
//}
