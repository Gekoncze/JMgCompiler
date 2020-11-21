package cz.mg.compiler.tasks.mg.resolver.context.component;

import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Link;
import cz.mg.language.entities.mg.runtime.components.MgComponent;
import cz.mg.language.entities.mg.runtime.components.variables.MgVariable;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class VariableContext extends ComponentContext {
    @Optional @Link
    private MgVariable variable;

    public VariableContext(Context outerContext) {
        super(outerContext);
    }

    public MgVariable getVariable() {
        return variable;
    }

    public void setVariable(MgVariable variable) {
        this.variable = variable;
    }

    @Override
    public MgComponent getComponent() {
        return variable;
    }
}
