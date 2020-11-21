package cz.mg.compiler.tasks.mg.resolver.context.component;

import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Link;
import cz.mg.language.entities.mg.runtime.components.MgComponent;
import cz.mg.language.entities.mg.runtime.components.variables.MgGlobalVariable;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class GlobalVariableContext extends ComponentContext {
    @Optional @Link
    private MgGlobalVariable globalVariable;

    public GlobalVariableContext(Context outerContext) {
        super(outerContext);
    }

    @Override
    public MgComponent getComponent() {
        return globalVariable;
    }

    public MgGlobalVariable getGlobalVariable() {
        return globalVariable;
    }

    public void setGlobalVariable(MgGlobalVariable globalVariable) {
        this.globalVariable = globalVariable;
    }
}
