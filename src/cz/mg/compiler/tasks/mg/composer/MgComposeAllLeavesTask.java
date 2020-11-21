package cz.mg.compiler.tasks.mg.composer;

import cz.mg.collections.list.List;
import cz.mg.collections.list.ReadableList;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.text.plain.Token;
import cz.mg.language.entities.text.plain.tokens.*;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.language.entities.text.structured.parts.leaves.Operator;
import cz.mg.language.entities.text.structured.parts.leaves.Bracket;
import cz.mg.language.entities.text.structured.parts.leaves.Value;
import cz.mg.language.entities.text.structured.parts.leaves.names.ObjectName;
import cz.mg.language.entities.text.structured.parts.leaves.names.TypeName;


public class MgComposeAllLeavesTask extends MgComposeTask {
    @Input
    private final ReadableList<Token> tokens;

    @Output
    private List<Part> parts = null;

    public MgComposeAllLeavesTask(ReadableList<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Part> getParts() {
        return parts;
    }

    @Override
    protected void onRun() {
        parts = new List<>();
        for(Token token : tokens){
            if(token instanceof TypeNameToken) parts.addLast(new TypeName(token.getText()));
            else if(token instanceof ObjectNameToken) parts.addLast(new ObjectName(token.getText()));
            else if(token instanceof ValueToken) parts.addLast(new Value(token.getText()));
            else if(token instanceof OperatorToken) parts.addLast(new Operator(token.getText()));
            else if(token instanceof BracketToken) parts.addLast(new Bracket(token.getText()));
            else if(token instanceof CommentToken) continue;
            else if(token instanceof WhitespaceToken) continue;
            else throw new LanguageException("Unsupported token of type " + token.getClass().getSimpleName() + ".");
        }
    }
}
