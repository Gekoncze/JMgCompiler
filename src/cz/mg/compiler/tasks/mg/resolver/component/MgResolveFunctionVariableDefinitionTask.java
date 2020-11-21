package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.component.VariableContext;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedVariable;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgFunction;
import cz.mg.language.entities.mg.runtime.components.variables.MgInstanceVariable;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class MgResolveFunctionVariableDefinitionTask extends MgResolveVariableDefinitionTask {
    @cz.mg.compiler.annotations.Input
    private final MgFunction function;

    @Input
    private final MgUnresolvedVariable logicalVariable;

    @Output
    private MgInstanceVariable variable;

    public MgResolveFunctionVariableDefinitionTask(Context context, MgFunction function, MgUnresolvedVariable logicalVariable) {
        super(new VariableContext(context), logicalVariable);
        this.function = function;
        this.logicalVariable = logicalVariable;
    }

    public MgInstanceVariable getVariable() {
        return variable;
    }

    @Override
    protected void onResolveComponent(List<MgStamp> stamps) {
        variable = new MgInstanceVariable(logicalVariable.getName(), function);
        variable.getStamps().addCollectionLast(instanceStampOnly(stamps));
        getContext().setVariable(variable);
        resolveDatatype(logicalVariable);
    }
}
