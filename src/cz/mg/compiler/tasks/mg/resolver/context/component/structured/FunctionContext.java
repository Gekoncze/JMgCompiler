package cz.mg.compiler.tasks.mg.resolver.context.component.structured;

import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Link;
import cz.mg.compiler.annotations.Cache;
import cz.mg.language.entities.mg.runtime.components.MgComponent;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgFunction;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.OperatorCache;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class FunctionContext extends StructuredTypeContext {
    @Optional @Link
    private MgFunction function;

    @Optional @Cache
    private OperatorCache operatorCache;

    public FunctionContext(@Optional Context outerContext) {
        super(outerContext);
    }

    @Override
    public MgComponent getComponent() {
        return function;
    }

    public MgFunction getFunction() {
        return function;
    }

    public void setFunction(MgFunction function) {
        if(this.operatorCache != null) throw new RuntimeException();
        this.function = function;
    }

    public OperatorCache getOperatorCache() {
        if(operatorCache == null) operatorCache = new OperatorCache(this);
        return operatorCache;
    }
}
