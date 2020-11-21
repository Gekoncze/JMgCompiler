package cz.mg.compiler.tasks.mg.resolver.command.expression.name;

import cz.mg.compiler.Todo;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.MgUnresolvedNameCallExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.MgExpression;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;


public class MgResolveVariableExpressionTask extends MgResolveNameExpressionTask {
    public MgResolveVariableExpressionTask(
        CommandContext context,
        MgUnresolvedNameCallExpression logicalExpression,
        ExpectedParentInput parent
    ) {
        super(context, logicalExpression, parent);
    }

    @Override
    protected void onResolve() {
        new Todo();
//        createNode(createFilter().findOptional());
//
//        if(logicalExpression.getExpression() != null){
//            onResolveChild(logicalExpression.getExpression());
//        }
//
//        createNode(createFilter().find());
    }

    @Override
    public MgExpression getExpression() {
        new Todo();
        return null;
    }

//    private VariableExpressionFilter createFilter(){
//        return new NameExpressionFilter(
//            context,
//            logicalExpression.getName(),
//            getParentInputConnectors(),
//            getChildrenOutputConnectors()
//        );
//    }
}
