package cz.mg.compiler.tasks.mg.resolver.context.component.structured;

import cz.mg.annotations.requirement.Optional;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.context.component.ComponentContext;


public abstract class StructuredTypeContext extends ComponentContext {
    public StructuredTypeContext(@Optional Context outerContext) {
        super(outerContext);
    }
}
