package cz.mg.compiler.tasks.mg.resolver.command.expression;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.tasks.mg.resolver.command.expression.name.MgResolveNameExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.command.expression.name.instance.MgResolveInstanceNameExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.command.expression.operator.MgResolveOperatorExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.command.expression.other.MgResolveGroupExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.command.expression.other.MgResolveValueExpressionTask;
import cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.logical.parts.expressions.calls.*;
import cz.mg.language.entities.mg.logical.parts.expressions.calls.operator.MgLogicalOperatorCallExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.MgExpression;
import cz.mg.compiler.tasks.mg.resolver.MgResolveTask;
import cz.mg.language.entities.mg.runtime.utilities.DeclarationHelper;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.ExpectedParentInput;


public abstract class MgResolveExpressionTask extends MgResolveTask {
    @Mandatory @cz.mg.compiler.annotations.Input
    protected final cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext context;

    @Optional @Input
    private final ExpectedParentInput parent;

    public MgResolveExpressionTask(cz.mg.compiler.tasks.mg.resolver.context.executable.CommandContext context, ExpectedParentInput parent) {
        this.context = context;
        this.parent = parent;
    }

    public ExpectedParentInput getParent() {
        return parent;
    }

    public @Optional abstract MgExpression getExpression();

    @Override
    protected final void onRun() {
        DeclarationHelper.sink();

        onResolve();

        updateRemainingParentSlots();

        DeclarationHelper.raise();
    }

    protected abstract void onResolve();

    private void updateRemainingParentSlots(){
        if(getParent() != null){
            for(int i = 0; i < getExpression().getOutputConnectors().count(); i++){
                getParent().getDatatypes().removeFirst();
            }
        }
    }

    protected MgExpression resolveChild(MgLogicalCallExpression logicalExpression, ExpectedParentInput parent){
        MgResolveExpressionTask task = MgResolveExpressionTask.create(context, logicalExpression, parent);
        task.run();
        return task.getExpression();
    }

    public static MgResolveExpressionTask create(
        CommandContext context,
        MgLogicalCallExpression logicalExpression,
        ExpectedParentInput parent
    ){
        if(logicalExpression instanceof MgLogicalNameCallExpression) {
            return MgResolveNameExpressionTask.create(context, (MgLogicalNameCallExpression) logicalExpression, parent);
        }

        if(logicalExpression instanceof MgLogicalValueCallExpression){
            return new MgResolveValueExpressionTask(context, (MgLogicalValueCallExpression) logicalExpression, parent);
        }

        if(logicalExpression instanceof MgLogicalOperatorCallExpression){
            return MgResolveOperatorExpressionTask.create(context, (MgLogicalOperatorCallExpression) logicalExpression, parent);
        }

        if(logicalExpression instanceof MgLogicalGroupCallExpression){
            return new MgResolveGroupExpressionTask(context, (MgLogicalGroupCallExpression) logicalExpression, parent);
        }

        if(logicalExpression instanceof MgLogicalMemberNameCallExpression){
            return MgResolveInstanceNameExpressionTask.create(context, (MgLogicalMemberNameCallExpression) logicalExpression, parent);
        }

        throw new LanguageException("Unexpected expression " + logicalExpression.getClass().getSimpleName() + " for resolve.");
    }
}
