package cz.mg.compiler.tasks.mg.resolver.context.executable;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Link;
import cz.mg.annotations.storage.Part;
import cz.mg.collections.list.List;
import cz.mg.compiler.Todo;
import cz.mg.language.entities.mg.runtime.components.variables.MgInstanceVariable;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.search.Source;


public abstract class ExecutableContext extends Context {
    @Mandatory @Part
    private final List<@Mandatory @Link MgInstanceVariable> declaredVariables = new List<>();

    public ExecutableContext(@Optional Context outerContext) {
        super(outerContext);
    }

    public List<MgInstanceVariable> getDeclaredVariables(){
        return declaredVariables;
    }

    @Override
    public Source getGlobalSource() {
        return getOuterContext().getGlobalSource();
    }

    @Override
    public Source getLocalSource() {
        new Todo();
        return null;
//        return () -> new CompositeCollection<>( //todo
//            getOuterContext().getLocalSource().getComponents(),
//            declaredVariables
//        );
    }
}
