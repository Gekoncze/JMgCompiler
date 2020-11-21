package cz.mg.compiler.tasks.mg.resolver.context;

import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Part;
import cz.mg.compiler.tasks.mg.resolver.search.EmptySource;
import cz.mg.compiler.tasks.mg.resolver.search.Source;


public abstract class Context {
    @Optional @Part
    private final Context outerContext;

    public Context(@Optional Context outerContext) {
        this.outerContext = outerContext;
    }

    public @Optional Context getOuterContext() {
        return outerContext;
    }

    public Source getGlobalSource() {
        return EmptySource.getInstance();
    }

    public Source getTypeSource() {
        return EmptySource.getInstance();
    }

    public Source getInstanceSource() {
        return EmptySource.getInstance();
    }

    public Source getLocalSource() {
        return EmptySource.getInstance();
    }
}
