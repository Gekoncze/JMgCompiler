package cz.mg.compiler.tasks.mg.parser.utilities;

import cz.mg.collections.text.ReadableText;
import cz.mg.language.LanguageException;


public class CharReader extends ArrayReader<Character> {
    private final ReadableText text;

    public CharReader(ReadableText text) {
        this.text = text;
    }

    public ReadableText slice(){
        return text.slice(getMark(), getPosition());
    }

    public ReadableText slice(int deltaBegin, int deltaEnd){
        return text.slice(getMark() + deltaBegin, getPosition() + deltaEnd);
    }

    public ReadableText sliceChar(){
        return text.slice(getPosition()-1, getPosition());
    }

    @Override
    protected int count() {
        return text.count();
    }

    @Override
    protected Character get(int i) {
        return text.get(i);
    }

    public boolean hasNext(char ch) {
        if(!canRead()) return false;
        return text.get(getPosition() + 1) == ch;
    }

    @Override
    protected LanguageException outOfBoundsException() {
        if(getPosition() < 0) {
            return new LanguageException("Missing character at the start of line.");
        } else {
            return new LanguageException("Missing character at the end of line.");
        }
    }
}