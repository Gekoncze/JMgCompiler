//package cz.mg.compiler.tasks.writers.c.part;
//
//import cz.mg.collections.Collection;
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.entities.c.logical.parts.CModifier;
//import cz.mg.language.entities.c.logical.parts.CPointer;
//import cz.mg.language.entities.c.logical.parts.CType;
//import cz.mg.language.entities.text.linear.tokens.c.CIdentifierToken;
//import cz.mg.language.entities.text.linear.tokens.c.CSpecialModifierToken;
//import cz.mg.language.entities.text.linear.Token;
//import cz.mg.language.entities.text.linear.tokens.c.CTypeModifierToken;
//
//
//public class CTypeWriterTask extends CPartWriterTask {
//    @Input
//    private final CType type;
//
//    @Output
//    private final List<Token> tokens = new List<>();
//
//    public CTypeWriterTask(CType type) {
//        this.type = type;
//    }
//
//    @Override
//    public List<Token> getTokens() {
//        return tokens;
//    }
//
//    @Override
//    protected void onRun() {
//        writeModifiers(type.getModifiers());
//        tokens.addLast(new CIdentifierToken(type.getName()));
//        writeTypePointers();
//    }
//
//    private void writeModifiers(Collection<CModifier> modifiers){
//        for(CModifier modifier : modifiers){
//            tokens.addLast(new CSpecialModifierToken(modifier.getName()));
//        }
//    }
//
//    private void writeTypePointers(){
//        for(CPointer pointer : type.getPointers()){
//            tokens.addLast(CTypeModifierToken.POINTER);
//            if(pointer.getModifers().count() > 0){
//                writeModifiers(pointer.getModifers());
//            }
//        }
//    }
//}
