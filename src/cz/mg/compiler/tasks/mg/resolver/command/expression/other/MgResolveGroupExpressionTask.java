package cz.mg.compiler.tasks.mg.resolver.command.expression.other;

import cz.mg.compiler.Todo;
import cz.mg.compiler.annotations.Input;
import cz.mg.language.entities.mg.logical.parts.expressions.calls.MgLogicalGroupCallExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.MgExpression;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public class MgResolveGroupExpressionTask extends MgResolveExpressionTask {
    @Input
    private final MgLogicalGroupCallExpression logicalExpression;

    public MgResolveGroupExpressionTask(
        CommandContext context,
        MgLogicalGroupCallExpression logicalExpression,
        ExpectedParentInput parent
    ) {
        super(context, parent);
        this.logicalExpression = logicalExpression;
    }

    @Override
    protected void onResolve() {
        new cz.mg.compiler.Todo();
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
//        for(MgLogicalCallExpression expression : logicalExpression.getExpressions()){
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
