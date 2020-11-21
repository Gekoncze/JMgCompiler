package cz.mg.compiler.tasks.mg.parser.utilities;

import cz.mg.language.LanguageException;


public abstract class ArrayReader<T> {
    private int position = 0;
    private int mark;

    public T read() {
        if(!canRead()) throw outOfBoundsException();
        return get(position++);
    }

    public T readOptional() {
        if(!canRead()) return null;
        return get(position++);
    }

    public void back() {
        position--;
    }

    public boolean canRead(){
        return position >= 0 && position < count();
    }

    private boolean canRead(int position){
        return position >= 0 && position < count();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMark() {
        return mark;
    }

    public void setMark() {
        mark = getPosition();
    }

    protected abstract int count();
    protected abstract T get(int i);
    protected abstract LanguageException outOfBoundsException();
}