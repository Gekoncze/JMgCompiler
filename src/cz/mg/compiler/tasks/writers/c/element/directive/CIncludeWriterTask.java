//package cz.mg.compiler.tasks.writers.c.element.directive;
//
//import cz.mg.collections.list.List;
//import cz.mg.language.annotations.task.Input;
//import cz.mg.language.annotations.task.Output;
//import cz.mg.language.entities.c.logical.elements.directives.CInclude;
//import cz.mg.language.entities.text.linear.Line;
//import cz.mg.language.entities.text.linear.tokens.c.CSpaceToken;
//import cz.mg.language.entities.text.linear.tokens.c.preprocessor.CDirective;
//import cz.mg.language.entities.text.linear.tokens.c.preprocessor.CDirectiveSign;
//import cz.mg.language.entities.text.linear.tokens.c.preprocessor.CPathToken;
//
//
//public class CIncludeWriterTask extends CDirectiveWriterTask {
//    @Input
//    private final CInclude include;
//
//    @Output
//    private final List<Line> lines = new List<>();
//
//    public CIncludeWriterTask(CInclude include) {
//        this.include = include;
//    }
//
//    @Override
//    public List<Line> getLines() {
//        return lines;
//    }
//
//    @Override
//    protected void onRun() {
//        Line line = new Line();
//        line.getTokens().addLast(CDirectiveSign.DIRECTIVE);
//        line.getTokens().addLast(CDirective.INCLUDE);
//        line.getTokens().addLast(new CSpaceToken());
//
//        if(include.isLocal()){
//            line.getTokens().addLast(CDirectiveSign.INCLUDE_LOCAL_LEFT);
//        } else {
//            line.getTokens().addLast(CDirectiveSign.INCLUDE_STANDARD_LEFT);
//        }
//
//        line.getTokens().addLast(new CPathToken(include.getPath()));
//
//        if(include.isLocal()){
//            line.getTokens().addLast(CDirectiveSign.INCLUDE_LOCAL_RIGHT);
//        } else {
//            line.getTokens().addLast(CDirectiveSign.INCLUDE_STANDARD_RIGHT);
//        }
//
//        lines.addLast(line);
//    }
//}
