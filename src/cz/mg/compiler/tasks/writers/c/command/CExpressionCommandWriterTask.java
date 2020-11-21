//package cz.mg.compiler.tasks.writers.c.command;
//
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.annotations.task.Subtask;
//import cz.mg.language.entities.c.logical.commands.CExpressionCommand;
//import cz.mg.language.entities.text.linear.Line;
//import cz.mg.language.entities.text.linear.tokens.c.CSeparatorToken;
//import cz.mg.compiler.tasks.writers.c.part.expression.CExpressionWriterTask;
//
//
//public class CExpressionCommandWriterTask extends CCommandWriterTask {
//    @Input
//    private final CExpressionCommand command;
//
//    @Output
//    private final List<Line> lines = new List<>();
//
//    @Subtask
//    private CExpressionWriterTask expressionWriterTask = null;
//
//    public CExpressionCommandWriterTask(CExpressionCommand command) {
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
//        expressionWriterTask = CExpressionWriterTask.create(command.getExpression());
//        expressionWriterTask.run();
//        lines.addLast(new Line(expressionWriterTask.getTokens()));
//        lines.getLast().getTokens().addLast(CSeparatorToken.SEMICOLON);
//    }
//}
