package cz.mg.compiler.tasks.writers.c;

import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadonlyText;
import cz.mg.collections.text.Text;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.text.plain.Line;
import cz.mg.language.entities.text.plain.tokens.WhitespaceToken;


public class Utilities {
    private static final char SINGLE_QUOTE = '\'';
    private static final char DOUBLE_QUOTE = '"';
    private static final char ESCAPE = '\\';

    private static ReadableText quote(ReadableText input, char quote){
        Text output = new Text();
        boolean escaped = false;
        for(Character ch : input){
            if(ch == '\n'){
                output.append("\\n");
            } else if(ch == '\r'){
                output.append("\\r");
            } else {
                if(escaped){
                    output.append(ch);
                } else {
                    if(ch == quote) output.append(ESCAPE);
                    escaped = ch == ESCAPE;
                    output.append(ch);
                }
            }
        }
        output.prepend(quote);
        output.append(quote);
        return output;
    }

    public static ReadableText singleQuote(ReadableText input){
        return quote(input, SINGLE_QUOTE);
    }

    public static ReadableText doubleQuote(ReadableText input){
        return quote(input, DOUBLE_QUOTE);
    }

    public static List<Line> indent(List<Line> lines){
        for(Line line : lines){
            line.getTokens().addFirst(new WhitespaceToken(new ReadonlyText("\t")));
        }
        return lines;
    }
}
