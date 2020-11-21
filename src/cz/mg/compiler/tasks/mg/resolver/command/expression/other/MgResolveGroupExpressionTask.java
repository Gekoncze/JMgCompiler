package cz.mg.compiler.tasks.mg.resolver.command.expression.other;

import cz.mg.compiler.Todo;
import cz.mg.compiler.annotations.Input;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.MgUnresolvedGroupCallExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.MgExpression;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public class MgResolveGroupExpressionTask extends MgResolveExpressionTask {
    @Input
    private final MgUnresolvedGroupCallExpression logicalExpression;

    public MgResolveGroupExpressionTask(
        CommandContext context,
        MgUnresolvedGroupCallExpression logicalExpression
    ) {
        super(context);
        this.logicalExpression = logicalExpression;
    }

    @Override
    protected void onResolve() {
        new Todo();
    }

    @Override
    public MgExpression getExpression() {
        new Todo();
        return null;
    }

//    @Override
//    protected Node onResolveEnter() {
//        if(getParentInputConnectors() != null){
//            return new GroupNode(getParentInputConnectors());
//        }
//        return null;
//    }
//
//    @Override
//    protected void onResolveChildren() {
//        for(MgUnresolvedCallExpression expression : logicalExpression.getExpressions()){
//            onResolveChild(expression);
//        }
//    }
//
//    @Override
//    protected Node onResolveLeave() {
//        return new GroupNode(getChildrenOutputConnectors());
//    }
//
//    @Override
//    public cz.mg.language.entities.mg.runtime.parts.expressions.MgExpression onCreateExpression() {
//        return new MgGroupExpression(createExpressions());
//    }
//
//    private List<MgExpression> createExpressions(){
//        List<MgExpression> expressions = new List<>();
//        for(MgResolveExpressionTask child : getChildren()){
//            expressions.addLast(child.createExpression());
//        }
//        return expressions;
//    }
}
