//package cz.mg.compiler.tasks.writers.c;
//
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.annotations.task.Subtask;
//import cz.mg.language.entities.c.logical.CSourceCode;
//import cz.mg.language.entities.c.logical.elements.directives.CInclude;
//import cz.mg.language.entities.c.logical.elements.statements.declarations.*;
//import cz.mg.language.entities.c.logical.elements.statements.definitions.*;
//import cz.mg.language.entities.text.linear.Line;
//import cz.mg.compiler.tasks.writers.LineWriterTask;
//import cz.mg.compiler.tasks.writers.c.element.CElementWriterTask;
//import cz.mg.compiler.tasks.writers.c.element.directive.CIncludeWriterTask;
//import cz.mg.compiler.tasks.writers.c.element.statement.declaration.*;
//import cz.mg.compiler.tasks.writers.c.element.statement.definition.*;
//
//
//public class CSourceCodeWriterTask extends CWriterTask implements LineWriterTask {
//    @Input
//    private final CSourceCode sourceCode;
//
//    @Output
//    private final List<Line> lines = new List<>();
//
//    @Subtask
//    private final List<CElementWriterTask> subtasks = new List<>();
//
//    public CSourceCodeWriterTask(CSourceCode sourceCode) {
//        this.sourceCode = sourceCode;
//    }
//
//    @Override
//    public List<Line> getLines() {
//        return lines;
//    }
//
//    @Override
//    protected void onRun() {
//        writeIncludes();
//        writeStructureForwardDeclarations();
//        writeStructureDefinitions();
//
//        writeFunctionForwardDeclarations();
//
//        writeFunctionDefinitions();
//    }
//
//    private void writeIncludes(){
//        for(CInclude include : sourceCode.getIncludes()){
//            runSubtask(CIncludeWriterTask.create(include));
//            addEmptyLine();
//        }
//
//        if(!sourceCode.getIncludes().isEmpty()){
//            addEmptyLine();
//        }
//    }
//
//    private void writeStructureForwardDeclarations(){
//        for(CStructureForwardDeclaration structureDeclaration : sourceCode.getStructureForwardDeclarations()){
//            runSubtask(new CStructureForwardDeclarationWriterTask(structureDeclaration));
//            addEmptyLine();
//        }
//
//        if(!sourceCode.getStructureForwardDeclarations().isEmpty()){
//            addEmptyLine();
//        }
//    }
//
//    private void writeStructureDefinitions(){
//        for(CStructureDefinition structureDefinition : sourceCode.getStructureDefinitions()){
//            runSubtask(new CStructureDefinitionWriterTask(structureDefinition));
//            addEmptyLine();
//            addEmptyLine();
//        }
//
//        if(!sourceCode.getStructureDefinitions().isEmpty()){
//            addEmptyLine();
//        }
//    }
//
//    private void writeFunctionForwardDeclarations(){
//        for(CFunctionForwardDeclaration functionDeclaration : sourceCode.getFunctionForwardDeclarations()){
//            runSubtask(new CFunctionForwardDeclarationWriterTask(functionDeclaration));
//            addEmptyLine();
//        }
//
//        if(!sourceCode.getFunctionForwardDeclarations().isEmpty()){
//            addEmptyLine();
//        }
//    }
//
//    private void writeFunctionDefinitions(){
//        for(CFunctionDefinition functionDefinition : sourceCode.getFunctionDefinitions()){
//            runSubtask(new CFunctionDefinitionWriterTask(functionDefinition));
//            addEmptyLine();
//            addEmptyLine();
//        }
//
//        if(!sourceCode.getFunctionDefinitions().isEmpty()){
//            addEmptyLine();
//        }
//    }
//
//    private void runSubtask(CElementWriterTask writerTask){
//        subtasks.addLast(writerTask);
//        subtasks.getLast().run();
//        lines.addCollectionLast(subtasks.getLast().getLines());
//    }
//
//    private void addEmptyLine(){
//        lines.addLast(Line.EMPTY_LINE);
//    }
//}
