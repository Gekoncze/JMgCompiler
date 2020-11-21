//package cz.mg.compiler.tasks.writers.c.part;
//
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.annotations.task.Subtask;
//import cz.mg.language.entities.c.logical.parts.CVariable;
//import cz.mg.language.entities.text.linear.tokens.c.CIdentifierToken;
//import cz.mg.language.entities.text.linear.Token;
//import cz.mg.language.entities.text.linear.tokens.c.CSpaceToken;
//
//
//public class CVariableWriterTask extends CPartWriterTask {
//    @Input
//    private final CVariable variable;
//
//    @Output
//    private final List<Token> tokens = new List<>();
//
//    @Subtask
//    private CTypeWriterTask typeWriterTask = null;
//
//    public CVariableWriterTask(CVariable variable) {
//        this.variable = variable;
//    }
//
//    @Override
//    public List<Token> getTokens() {
//        return tokens;
//    }
//
//    @Override
//    protected void onRun() {
//        writeType();
//        tokens.addLast(new CSpaceToken());
//        writeName();
//    }
//
//    private void writeType(){
//        typeWriterTask = new CTypeWriterTask(variable.getType());
//        typeWriterTask.run();
//        tokens.addCollectionLast(typeWriterTask.getTokens());
//    }
//
//    private void writeName(){
//        tokens.addLast(new CIdentifierToken(variable.getName()));
//    }
//}
