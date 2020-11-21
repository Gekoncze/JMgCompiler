//package cz.mg.compiler.tasks.writers.c.command;
//
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.annotations.task.Subtask;
//import cz.mg.language.entities.c.logical.commands.CReturnCommand;
//import cz.mg.language.entities.text.linear.Line;
//import cz.mg.language.entities.text.linear.tokens.c.CKeywordToken;
//import cz.mg.language.entities.text.linear.tokens.c.CSeparatorToken;
//import cz.mg.compiler.tasks.writers.c.part.expression.CExpressionWriterTask;
//
//
//public class CReturnCommandWriterTask extends CCommandWriterTask {
//    @Input
//    private final CReturnCommand command;
//
//    @Output
//    private final List<Line> lines = new List<>();
//
//    @Subtask
//    private CExpressionWriterTask expressionWriterTask = null;
//
//    public CReturnCommandWriterTask(CReturnCommand command) {
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
//        Line line = new Line();
//        line.getTokens().addLast(CKeywordToken.RETURN);
//        if(command.getExpression() != null){
//            expressionWriterTask = CExpressionWriterTask.create(command.getExpression());
//            expressionWriterTask.run();
//            line.getTokens().addCollectionLast(expressionWriterTask.getTokens());
//        }
//        line.getTokens().addLast(CSeparatorToken.SEMICOLON);
//        lines.addLast(line);
//    }
//}
