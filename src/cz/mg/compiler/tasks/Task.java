package cz.mg.compiler.tasks;

import cz.mg.language.LanguageException;


public abstract class Task {
    public Task() {
    }

    protected abstract void onRun();

    public void run(){
        try {
            onRun();
        } catch (LanguageException e){
            consume(e);
            throw e;
        }
    }

    public void tryToRun(){
        try {
            onRun();
        } catch (LanguageException e){
            consume(e);
        }
    }

    private void consume(LanguageException e){
        if(!e.isConsumed()){
            e.setConsumed(true);
            ErrorLogger.errorLogger.log(e);
        }
    }
}
