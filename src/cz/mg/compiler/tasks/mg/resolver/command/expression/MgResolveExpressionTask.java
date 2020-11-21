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
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.*;
import cz.mg.language.entities.mg.unresolved.parts.expressions.calls.operator.MgUnresolvedOperatorCallExpression;
import cz.mg.language.entities.mg.runtime.parts.expressions.MgExpression;
import cz.mg.compiler.tasks.mg.resolver.MgResolveTask;
import cz.mg.language.entities.mg.runtime.utilities.DeclarationHelper;


public abstract class MgResolveExpressionTask extends MgResolveTask {
    @Mandatory @Input
    protected final CommandContext context;

    public MgResolveExpressionTask(CommandContext context) {
        this.context = context;
    }

    public @Optional abstract MgExpression getExpression();

    @Override
    protected final void onRun() {
        DeclarationHelper.sink();

        onResolve();

        DeclarationHelper.raise();
    }

    protected abstract void onResolve();

    protected MgExpression resolveChild(MgUnresolvedCallExpression logicalExpression){
        MgResolveExpressionTask task = MgResolveExpressionTask.create(context, logicalExpression);
        task.run();
        return task.getExpression();
    }

    public static MgResolveExpressionTask create(
        CommandContext context,
        MgUnresolvedCallExpression logicalExpression
    ){
        if(logicalExpression instanceof MgUnresolvedNameCallExpression) {
            return MgResolveNameExpressionTask.create(context, (MgUnresolvedNameCallExpression) logicalExpression);
        }

        if(logicalExpression instanceof MgUnresolvedValueCallExpression){
            return new MgResolveValueExpressionTask(context, (MgUnresolvedValueCallExpression) logicalExpression);
        }

        if(logicalExpression instanceof MgUnresolvedOperatorCallExpression){
            return MgResolveOperatorExpressionTask.create(context, (MgUnresolvedOperatorCallExpression) logicalExpression);
        }

        if(logicalExpression instanceof MgUnresolvedGroupCallExpression){
            return new MgResolveGroupExpressionTask(context, (MgUnresolvedGroupCallExpression) logicalExpression);
        }

        if(logicalExpression instanceof MgUnresolvedMemberNameCallExpression){
            return MgResolveInstanceNameExpressionTask.create(context, (MgUnresolvedMemberNameCallExpression) logicalExpression);
        }

        throw new LanguageException("Unexpected expression " + logicalExpression.getClass().getSimpleName() + " for resolve.");
    }
}
