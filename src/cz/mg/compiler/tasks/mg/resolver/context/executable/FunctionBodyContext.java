package cz.mg.compiler.tasks.mg.resolver.context.executable;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.compiler.tasks.mg.resolver.context.component.structured.FunctionContext;


public class FunctionBodyContext extends ExecutableContext {
    public FunctionBodyContext(@Mandatory cz.mg.compiler.tasks.mg.resolver.context.component.structured.FunctionContext outerContext) {
        super(outerContext);
    }

    @Override
    public @Optional cz.mg.compiler.tasks.mg.resolver.context.component.structured.FunctionContext getOuterContext() {
        return (FunctionContext) super.getOuterContext();
    }
}
