package cz.mg.compiler.tasks.mg.resolver.command.expression.name;

import cz.mg.compiler.Todo;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.MgUnresolvedNameCallExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.MgExpression;
import cz.mg.compiler.tasks.mg.resolver.command.expression.MgResolveExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public class MgResolveFunctionExpressionTask extends MgResolveExpressionTask {
    public MgResolveFunctionExpressionTask(
        CommandContext context,
        MgUnresolvedNameCallExpression logicalExpression,
        ExpectedParentInput parent
    ) {
        super(context, parent);
        new cz.mg.compiler.Todo();
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

//    protected void onResolve() {
//        createNode(createFilter().findOptional());
//
//        if(logicalExpression.getExpression() != null){
//            onResolveChild(logicalExpression.getExpression());
//        }
//
//        createNode(createFilter().find());
//    }
//
//    private NameExpressionFilter createFilter(){
//        return new NameExpressionFilter(
//            context,
//            logicalExpression.getName(),
//            getParentInputConnectors(),
//            getChildrenOutputConnectors()
//        );
//    }
}
