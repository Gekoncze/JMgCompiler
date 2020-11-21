//package cz.mg.compiler.tasks.writers.c;
//
//import cz.mg.collections.Collection;
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.annotations.task.Subtask;
//import cz.mg.language.entities.c.logical.parts.CVariable;
//import cz.mg.language.entities.text.linear.Line;
//import cz.mg.language.entities.text.linear.tokens.c.CSeparatorToken;
//import cz.mg.compiler.tasks.writers.LineWriterTask;
//import cz.mg.compiler.tasks.writers.c.part.CVariableWriterTask;
//
//
//public class CVariableBlockWriterTask extends CWriterTask implements LineWriterTask {
//    @Input
//    private final Collection<CVariable> variables;
//
//    @Output
//    private final List<Line> lines = new List<>();
//
//    @Subtask
//    protected List<CVariableWriterTask> variableWriterTasks = new List<>();
//
//    public CVariableBlockWriterTask(Collection<CVariable> variables) {
//        this.variables = variables;
//    }
//
//    @Override
//    public List<Line> getLines() {
//        return lines;
//    }
//
//    @Override
//    protected void onRun() {
//        for(CVariable variable : variables){
//            Line line = new Line();
//            variableWriterTasks.addLast(new CVariableWriterTask(variable));
//            variableWriterTasks.getLast().run();
//            line.getTokens().addCollectionLast(variableWriterTasks.getLast().getTokens());
//            line.getTokens().addLast(CSeparatorToken.SEMICOLON);
//            lines.addLast(line);
//        }
//    }
//}
