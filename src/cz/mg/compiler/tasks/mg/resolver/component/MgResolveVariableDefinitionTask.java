package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.compiler.tasks.mg.resolver.context.component.VariableContext;
import cz.mg.language.entities.mg.logical.components.MgLogicalVariable;
import cz.mg.compiler.tasks.mg.resolver.link.MgResolveVariableDatatypeTask;


public abstract class MgResolveVariableDefinitionTask extends MgResolveComponentDefinitionTask {
    public MgResolveVariableDefinitionTask(cz.mg.compiler.tasks.mg.resolver.context.component.VariableContext context, MgLogicalVariable logicalVariable) {
        super(context, logicalVariable);
    }

    @Override
    protected cz.mg.compiler.tasks.mg.resolver.context.component.VariableContext getContext() {
        return (VariableContext) super.getContext();
    }

    protected void resolveDatatype(MgLogicalVariable logicalVariable){
        postpone(MgResolveVariableDatatypeTask.class, () -> {
            MgResolveVariableDatatypeTask task = new MgResolveVariableDatatypeTask(getContext(), logicalVariable.getDatatype());
            task.run();
            getContext().getVariable().setDatatype(task.getDatatype());
        });
    }
}
