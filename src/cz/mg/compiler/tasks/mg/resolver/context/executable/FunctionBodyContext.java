package cz.mg.compiler.tasks.mg.resolver.context.executable;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.compiler.tasks.mg.resolver.context.component.structured.FunctionContext;


public class FunctionBodyContext extends ExecutableContext {
    public FunctionBodyContext(@Mandatory FunctionContext outerContext) {
        super(outerContext);
    }

    @Override
    public @Optional FunctionContext getOuterContext() {
        return (FunctionContext) super.getOuterContext();
    }
}
