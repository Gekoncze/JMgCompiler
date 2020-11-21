package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.compiler.tasks.mg.resolver.context.component.VariableContext;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedVariable;
import cz.mg.compiler.tasks.mg.resolver.link.MgResolveVariableDatatypeTask;


public abstract class MgResolveVariableDefinitionTask extends MgResolveComponentDefinitionTask {
    public MgResolveVariableDefinitionTask(VariableContext context, MgUnresolvedVariable logicalVariable) {
        super(context, logicalVariable);
    }

    @Override
    protected VariableContext getContext() {
        return (VariableContext) super.getContext();
    }

    protected void resolveDatatype(MgUnresolvedVariable logicalVariable){
        postpone(MgResolveVariableDatatypeTask.class, () -> {
            MgResolveVariableDatatypeTask task = new MgResolveVariableDatatypeTask(getContext(), logicalVariable.getDatatype());
            task.run();
            getContext().getVariable().setDatatype(task.getDatatype());
        });
    }
}
