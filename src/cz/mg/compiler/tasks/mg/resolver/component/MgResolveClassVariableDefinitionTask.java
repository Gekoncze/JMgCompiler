package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.component.VariableContext;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedVariable;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.language.entities.mg.runtime.components.types.classes.MgClass;
import cz.mg.language.entities.mg.runtime.components.variables.MgGlobalVariable;
import cz.mg.language.entities.mg.runtime.components.variables.MgInstanceVariable;
import cz.mg.language.entities.mg.runtime.components.variables.MgTypeVariable;
import cz.mg.language.entities.mg.runtime.components.variables.MgVariable;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class MgResolveClassVariableDefinitionTask extends MgResolveVariableDefinitionTask {
    @cz.mg.compiler.annotations.Input
    private final MgClass clazz;

    @Input
    private final MgUnresolvedVariable logicalVariable;

    @Output
    private MgVariable variable;

    public MgResolveClassVariableDefinitionTask(Context context, MgClass clazz, MgUnresolvedVariable logicalVariable) {
        super(new VariableContext(context), logicalVariable);
        this.clazz = clazz;
        this.logicalVariable = logicalVariable;
    }

    public MgVariable getVariable() {
        return variable;
    }

    @Override
    protected void onResolveComponent(List<MgStamp> stamps) {
        switch (getType(stamps, Type.GLOBAL)) {
            case GLOBAL:
                variable = new MgGlobalVariable(logicalVariable.getName());
                break;
            case TYPE:
                variable = new MgTypeVariable(logicalVariable.getName(), clazz);
                break;
            case INSTANCE:
                variable = new MgInstanceVariable(logicalVariable.getName(), clazz);
                break;
            default:
                throw new RuntimeException();
        }
        variable.getStamps().addCollectionLast(stamps);
        getContext().setVariable(variable);
        resolveDatatype(logicalVariable);
    }
}
