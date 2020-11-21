//package cz.mg.compiler.tasks.writers.c.part;
//
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.annotations.task.Subtask;
//import cz.mg.language.entities.c.logical.elements.statements.declarations.CFunctionForwardDeclaration;
//import cz.mg.language.entities.c.logical.parts.CVariable;
//import cz.mg.language.entities.text.linear.tokens.c.CIdentifierToken;
//import cz.mg.language.entities.text.linear.Token;
//import cz.mg.language.entities.text.linear.tokens.c.CBracketToken;
//import cz.mg.language.entities.text.linear.tokens.c.CSeparatorToken;
//import cz.mg.language.entities.text.linear.tokens.c.CSpaceToken;
//
//
//public class CFunctionSignatureWriterTask extends CPartWriterTask {
//    @Input
//    private final CFunctionForwardDeclaration functionDeclaration;
//
//    @Output
//    private final List<Token> tokens = new List<>();
//
//    @Subtask
//    private final List<CVariableWriterTask> inputWriterTasks = new List<>();
//
//    @Subtask
//    private CTypeWriterTask outputWriterTask = null;
//
//    public CFunctionSignatureWriterTask(CFunctionForwardDeclaration functionDeclaration) {
//        this.functionDeclaration = functionDeclaration;
//    }
//
//    @Override
//    public List<Token> getTokens() {
//        return tokens;
//    }
//
//    @Override
//    protected void onRun() {
//        writeOutput();
//        tokens.addLast(new CSpaceToken());
//        writeName();
//        writeInput();
//    }
//
//    private void writeName(){
//        tokens.addLast(new CIdentifierToken(functionDeclaration.getName()));
//    }
//
//    private void writeOutput(){
//        outputWriterTask = new CTypeWriterTask(functionDeclaration.getOutput());
//        outputWriterTask.run();
//        tokens.addCollectionLast(outputWriterTask.getTokens());
//    }
//
//    private void writeInput(){
//        tokens.addLast(CBracketToken.ROUND_LEFT);
//
//        for(CVariable input : functionDeclaration.getInput()){
//            inputWriterTasks.addLast(new CVariableWriterTask(input));
//            inputWriterTasks.getLast().run();
//            tokens.addCollectionLast(inputWriterTasks.getLast().getTokens());
//            tokens.addLast(CSeparatorToken.COMMA);
//        }
//        if(!tokens.isEmpty()) if(tokens.getLast() == CSeparatorToken.COMMA) tokens.removeLast();
//
//        tokens.addLast(CBracketToken.ROUND_RIGHT);
//    }
//}
