//package cz.mg.compiler.tasks.writers.c.element.statement.definition;
//
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.annotations.task.Subtask;
//import cz.mg.language.entities.c.logical.elements.statements.definitions.CStructureDefinition;
//import cz.mg.language.entities.c.logical.parts.CModifier;
//import cz.mg.language.entities.text.linear.Line;
//import cz.mg.language.entities.text.linear.tokens.c.*;
//import cz.mg.compiler.tasks.writers.c.CVariableBlockWriterTask;
//
//import static cz.mg.compiler.tasks.writers.c.Utilities.indent;
//
//
//public class CStructureDefinitionWriterTask extends CDefinitionWriterTask {
//    @Input
//    private final CStructureDefinition structureDefinition;
//
//    @Output
//    private final List<Line> lines = new List<>();
//
//    @Subtask
//    private CVariableBlockWriterTask variableBlockWriterTask = null;
//
//    public CStructureDefinitionWriterTask(CStructureDefinition structureDefinition) {
//        this.structureDefinition = structureDefinition;
//    }
//
//    @Override
//    public List<Line> getLines() {
//        return lines;
//    }
//
//    @Override
//    protected void onRun() {
//        writeHeader();
//        writeBody();
//        writeFooter();
//    }
//
//    private void writeHeader(){
//        Line line = new Line();
//        line.getTokens().addLast(CKeywordToken.STRUCT);
//        line.getTokens().addLast(new CSpaceToken());
//        line.getTokens().addLast(new CIdentifierToken(structureDefinition.getName()));
//        line.getTokens().addLast(CBracketToken.CURLY_LEFT);
//        lines.addLast(line);
//    }
//
//    private void writeBody(){
//        variableBlockWriterTask = new CVariableBlockWriterTask(structureDefinition.getVariables());
//        variableBlockWriterTask.run();
//        lines.addCollectionLast(indent(variableBlockWriterTask.getLines()));
//    }
//
//    private void writeFooter(){
//        Line line = new Line();
//        line.getTokens().addLast(CBracketToken.CURLY_RIGHT);
//        for(CModifier modifier : structureDefinition.getModifiers()){
//            line.getTokens().addLast(new CSpecialModifierToken(modifier.getName()));
//        }
//        line.getTokens().addLast(CSeparatorToken.SEMICOLON);
//        lines.addLast(line);
//    }
//}
