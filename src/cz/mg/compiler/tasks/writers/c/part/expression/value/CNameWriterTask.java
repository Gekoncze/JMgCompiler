//package cz.mg.compiler.tasks.writers.c.part.expression.value;
//
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.entities.c.logical.parts.expressions.values.CName;
//import cz.mg.language.entities.text.linear.tokens.c.CIdentifierToken;
//import cz.mg.language.entities.text.linear.Token;
//
//
//public class CNameWriterTask extends CValueWriterTask {
//    @Input
//    private final CName name;
//
//    @Output
//    private final List<Token> tokens = new List<>();
//
//    public CNameWriterTask(CName name) {
//        this.name = name;
//    }
//
//    @Override
//    public List<Token> getTokens() {
//        return tokens;
//    }
//
//    @Override
//    protected void onRun() {
//        tokens.addLast(new CIdentifierToken(name.getName()));
//    }
//}
