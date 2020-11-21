package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.component.VariableContext;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedVariable;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.language.entities.mg.runtime.components.variables.MgGlobalVariable;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class MgResolveLocationVariableDefinitionTask extends MgResolveVariableDefinitionTask {
    @Input
    private final MgUnresolvedVariable logicalVariable;

    @Output
    private MgGlobalVariable variable;

    public MgResolveLocationVariableDefinitionTask(Context context, MgUnresolvedVariable logicalVariable) {
        super(new VariableContext(context), logicalVariable);
        this.logicalVariable = logicalVariable;
    }

    public MgGlobalVariable getVariable() {
        return variable;
    }

    @Override
    protected void onResolveComponent(List<MgStamp> stamps) {
        variable = new MgGlobalVariable(logicalVariable.getName());
        variable.getStamps().addCollectionLast(globalStampOnly(stamps));
        getContext().setVariable(variable);
        resolveDatatype(logicalVariable);
    }
}
