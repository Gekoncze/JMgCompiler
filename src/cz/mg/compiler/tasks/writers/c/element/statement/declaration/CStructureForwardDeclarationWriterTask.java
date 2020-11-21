//package cz.mg.compiler.tasks.writers.c.element.statement.declaration;
//
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.entities.c.logical.elements.statements.declarations.CStructureForwardDeclaration;
//import cz.mg.language.entities.text.linear.tokens.c.CIdentifierToken;
//import cz.mg.language.entities.text.linear.Line;
//import cz.mg.language.entities.text.linear.tokens.c.CKeywordToken;
//import cz.mg.language.entities.text.linear.tokens.c.CSeparatorToken;
//import cz.mg.language.entities.text.linear.tokens.c.CSpaceToken;
//
//
//public class CStructureForwardDeclarationWriterTask extends CForwardDeclarationWriterTask {
//    @Input
//    private final CStructureForwardDeclaration structureDeclaration;
//
//    @Output
//    private final List<Line> lines = new List<>();
//
//    public CStructureForwardDeclarationWriterTask(CStructureForwardDeclaration structureDeclaration) {
//        this.structureDeclaration = structureDeclaration;
//    }
//
//    @Override
//    public List<Line> getLines() {
//        return lines;
//    }
//
//    @Override
//    protected void onRun() {
//        Line line = new Line();
//        line.getTokens().addLast(CKeywordToken.STRUCT);
//        line.getTokens().addLast(new CSpaceToken());
//        line.getTokens().addLast(new CIdentifierToken(structureDeclaration.getName()));
//        line.getTokens().addLast(CSeparatorToken.SEMICOLON);
//        lines.addLast(line);
//    }
//}
