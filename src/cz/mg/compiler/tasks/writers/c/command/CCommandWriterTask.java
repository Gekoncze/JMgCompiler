//package cz.mg.compiler.tasks.writers.c.command;
//
//import cz.mg.language.LanguageException;
//import cz.mg.language.entities.c.logical.commands.*;
//import cz.mg.compiler.tasks.writers.LineWriterTask;
//import cz.mg.compiler.tasks.writers.c.CWriterTask;
//
//
//public abstract class CCommandWriterTask extends CWriterTask implements LineWriterTask {
//    public static CCommandWriterTask create(CCommand command){
//        if(command instanceof CIfCommand) return new CIfCommandWriterTask((CIfCommand) command);
//        if(command instanceof CElseIfCommand) return new CElseIfCommandWriterTask((CElseIfCommand) command);
//        if(command instanceof CElseCommand) return new CElseCommandWriterTask((CElseCommand) command);
//        if(command instanceof CWhileCommand) return new CWhileCommandWriterTask((CWhileCommand) command);
//        if(command instanceof CReturnCommand) return new CReturnCommandWriterTask((CReturnCommand) command);
//        if(command instanceof CContinueCommand) return new CContinueCommandWriterTask((CContinueCommand) command);
//        if(command instanceof CBreakCommand) return new CBreakCommandWriterTask((CBreakCommand) command);
//        if(command instanceof CForCommand) return new CForCommandWriterTask((CForCommand) command);
//        if(command instanceof CExpressionCommand) return new CExpressionCommandWriterTask((CExpressionCommand) command);
//        else throw new LanguageException("Cannot write command: " + command.getClass().getSimpleName() + " is not supported.");
//    }
//}
