package cz.mg.compiler.tasks.mg.resolver.context.architecture;

import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Link;
import cz.mg.language.entities.mg.runtime.architecture.MgApplication;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class ApplicationContext extends Context {
    @Optional @Link
    private MgApplication application;

    public ApplicationContext(Context outerContext) {
        super(outerContext);
    }

    public MgApplication getApplication() {
        return application;
    }

    public void setApplication(MgApplication application) {
        this.application = application;
    }
}
