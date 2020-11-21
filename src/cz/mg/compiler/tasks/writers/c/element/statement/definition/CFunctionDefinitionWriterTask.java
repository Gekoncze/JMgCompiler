//package cz.mg.compiler.tasks.writers.c.element.statement.definition;
//
//import cz.mg.collections.list.List;
//import cz.mg.collections.text.ReadonlyText;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.annotations.task.Subtask;
//import cz.mg.language.entities.c.logical.elements.statements.definitions.CFunctionDefinition;
//import cz.mg.language.entities.text.linear.Line;
//import cz.mg.language.entities.text.linear.tokens.BracketToken;
//import cz.mg.compiler.tasks.writers.c.CCommandBlockWriterTask;
//import cz.mg.compiler.tasks.writers.c.part.CFunctionSignatureWriterTask;
//
//import static cz.mg.compiler.tasks.writers.c.Utilities.indent;
//
//
//public class CFunctionDefinitionWriterTask extends CDefinitionWriterTask {
//    @Input
//    private final CFunctionDefinition functionDefinition;
//
//    @Output
//    private final List<Line> lines = new List<>();
//
//    @Subtask
//    private CFunctionSignatureWriterTask functionSignatureWriterTask = null;
//
//    @Subtask
//    private CCommandBlockWriterTask commandBlockWriterTask = null;
//
//    public CFunctionDefinitionWriterTask(CFunctionDefinition functionDefinition) {
//        this.functionDefinition = functionDefinition;
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
//        writeCommands();
//        writeFooter();
//    }
//
//    private void writeHeader(){
//        Line line = new Line();
//        functionSignatureWriterTask = new CFunctionSignatureWriterTask(functionDefinition.getDeclaration());
//        functionSignatureWriterTask.run();
//        line.getTokens().addCollectionLast(functionSignatureWriterTask.getTokens());
//        line.getTokens().addLast(new BracketToken(new ReadonlyText("{")));
//        lines.addLast(line);
//    }
//
//    private void writeCommands(){
//        commandBlockWriterTask = new CCommandBlockWriterTask(functionDefinition.getCommands());
//        commandBlockWriterTask.run();
//        lines.addCollectionLast(indent(commandBlockWriterTask.getLines()));
//    }
//
//    private void writeFooter() {
//        Line line = new Line();
//        line.getTokens().addLast(new BracketToken(new ReadonlyText("}")));
//        lines.addLast(line);
//    }
//}
