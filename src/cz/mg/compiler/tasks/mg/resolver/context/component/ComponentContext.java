package cz.mg.compiler.tasks.mg.resolver.context.component;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.annotations.storage.Part;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.collections.special.CompositeCollection;
import cz.mg.language.entities.mg.runtime.components.MgComponent;
import cz.mg.compiler.tasks.mg.resolver.command.utilities.Usage;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.search.Source;


public abstract class ComponentContext extends Context {
    @Mandatory @Part
    private final List<Usage> usages = new List<>();

    public ComponentContext(@Optional Context outerContext) {
        super(outerContext);
    }

    public List<Usage> getUsages() {
        return usages;
    }

    public abstract MgComponent getComponent();

    @Override
    public Source getGlobalSource() {
        return () -> new CompositeCollection<>(usages, new Array<>(new Usage(getComponent())));
    }
}
