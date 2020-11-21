package cz.mg.compiler.tasks.mg.parser;

import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.annotations.Utility;
import cz.mg.compiler.tasks.Task;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.text.plain.Line;
import cz.mg.language.entities.text.plain.tokens.*;
import cz.mg.compiler.tasks.mg.parser.utilities.CharReader;


public class MgParseLineTask extends Task {
    @Input
    private final ReadableText text;

    @Output
    private Line line = null;

    @Utility
    private CharReader reader = null;

    public MgParseLineTask(ReadableText text) {
        this.text = text;
    }

    public Line getLine() {
        return line;
    }

    private static boolean isCommentSign(char ch){
        return ch == '#';
    }

    private static boolean isValueSign(char ch){
        return ch == '"' || ch == '\'';
    }

    private static boolean isStampSign(char ch){
        return ch == '@';
    }

    private static boolean isNumberCharacter(char ch){
        return ch >= '0' && ch <= '9';
    }

    private static boolean isLowerCharacter(char ch){
        return ch >= 'a' && ch <= 'z';
    }

    private static boolean isUpperCharacter(char ch){
        return ch >= 'A' && ch <= 'Z';
    }

    private static boolean isWordCharacter(char ch){
        return isLowerCharacter(ch) || isUpperCharacter(ch) || isNumberCharacter(ch);
    }

    private static boolean isAllowedLiteralCharacter(char ch){
        return ch >= ' ' && ch <= '~';
    }

    private static boolean isAllowedCommentCharacter(char ch){
        return ch >= ' ' && ch <= '~';
    }

    private static boolean isOperator(char ch){
        switch (ch){
            case '+': return true;
            case '-': return true;
            case '*': return true;
            case '/': return true;
            case '\\': return true;
            case '%': return true;
            case '^': return true;
            case '!': return true;
            case '=': return true;
            case '$': return true;
            case '&': return true;
            case ',': return true;
            case '.': return true;
            case '?': return true;
            default: return false;
        }
    }

    private static boolean isBracket(char ch){
        switch (ch){
            case '(': return true;
            case ')': return true;
            case '[': return true;
            case ']': return true;
            case '{': return true;
            case '}': return true;
            case ':': return true;
            default: return false;
        }
    }

    @Override
    protected void onRun() {
        line = new Line();
        reader = new CharReader(text);

        while(reader.canRead()){
            reader.setMark();
            char ch = reader.read();
            if(ch == ' '){
                line.getTokens().addLast(new SpaceToken(reader.slice()));
            } else if(isCommentSign(ch)){
                line.getTokens().addLast(parseComment());
            } else if(isValueSign(ch)){
                line.getTokens().addLast(parseValue(ch));
            } else if(isWordCharacter(ch)){
                line.getTokens().addLast(parseWord());
            } else if(isStampSign(ch)){
                line.getTokens().addLast(parseStamp());
            } else if(isOperator(ch)){
                line.getTokens().addLast(parseOperator());
            } else if(isBracket(ch)) {
                line.getTokens().addLast(parseBracket());
            } else {
                throw new LanguageException("Illegal character " + reader.sliceChar() + " (" + (int)reader.sliceChar().get(0) + ").");
            }
        }
    }

    private CommentToken parseComment(){
        while(reader.canRead()){
            char ch = reader.read();
            if(!isAllowedCommentCharacter(ch)) throw new LanguageException("Illegal character '" + reader.sliceChar() + "' (" + (int)ch + ") in comment.");
        }
        return new CommentToken(reader.slice(1, 0).trim());
    }

    private ValueToken parseValue(char boundary){
        while(reader.canRead()){
            char ch = reader.read();
            if(ch == boundary){
                return new ValueToken(reader.slice(1, -1));
            } else if(isAllowedLiteralCharacter(ch)) {
                continue;
            } else {
                throw new LanguageException("Illegal character " + reader.sliceChar() + " (" + (int)ch + ") in value.");
            }
        }
        throw new LanguageException("Missing closing character " + boundary + " for value.");
    }

    private WordToken parseWord(){
        reader.back();

        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasNumber = false;

        while(reader.canRead()){
            char ch = reader.read();
            if(isLowerCharacter(ch)){
                hasLower = true;
            } else if(isUpperCharacter(ch)){
                hasUpper = true;
            } else if(isNumberCharacter(ch)){
                hasNumber = true;
            } else {
                reader.back();
                break;
            }
        }

        if(hasLower){
            ReadableText name = reader.slice();
            if(isUpperCharacter(name.getFirst())){
                return new TypeNameToken(name);
            } else {
                return new ObjectNameToken(name);
            }
        }

        if(hasUpper) return new KeywordToken(reader.slice());
        if(hasNumber) return new NumberToken(reader.slice());

        throw new LanguageException("Unsupported word '" + reader.slice() + "'.");
    }

    private StampToken parseStamp(){
        while(reader.canRead()){
            char ch = reader.read();
            if(!isLowerCharacter(ch)){
                reader.back();
                break;
            }
        }
        return new StampToken(reader.slice());
    }

    private OperatorToken parseOperator(){
        reader.back();

        while(reader.canRead()){
            char ch = reader.read();
            if(!isOperator(ch)){
                reader.back();
                break;
            }
        }

        return new OperatorToken(reader.slice());
    }

    private BracketToken parseBracket() {
        return new BracketToken(reader.slice());
    }
}
