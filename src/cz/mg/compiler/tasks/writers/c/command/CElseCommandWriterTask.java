//package cz.mg.compiler.tasks.writers.c.command;
//
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.annotations.task.Subtask;
//import cz.mg.language.entities.c.logical.commands.CElseCommand;
//import cz.mg.language.entities.text.linear.Line;
//import cz.mg.language.entities.text.linear.tokens.c.CBracketToken;
//import cz.mg.language.entities.text.linear.tokens.c.CKeywordToken;
//import cz.mg.compiler.tasks.writers.c.CCommandBlockWriterTask;
//
//import static cz.mg.compiler.tasks.writers.c.Utilities.indent;
//
//
//public class CElseCommandWriterTask extends CCommandWriterTask {
//    @Input
//    private final CElseCommand command;
//
//    @Output
//    private final List<Line> lines = new List<>();
//
//    @Subtask
//    private CCommandBlockWriterTask commandBlockWriterTask = null;
//
//    public CElseCommandWriterTask(CElseCommand command) {
//        this.command = command;
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
//        line.getTokens().addLast(CKeywordToken.ELSE);
//        line.getTokens().addLast(CBracketToken.CURLY_LEFT);
//        lines.addLast(line);
//    }
//
//    private void writeCommands(){
//        commandBlockWriterTask = new CCommandBlockWriterTask(command.getCommands());
//        commandBlockWriterTask.run();
//        lines.addCollectionLast(indent(commandBlockWriterTask.getLines()));
//    }
//
//    private void writeFooter() {
//        Line line = new Line();
//        line.getTokens().addLast(CBracketToken.CURLY_RIGHT);
//        lines.addLast(line);
//    }
//}
