package cz.mg.compiler.tasks.mg.resolver;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.command.MgResolveCommandTask;
import cz.mg.compiler.tasks.mg.resolver.component.MgResolveClassFunctionDefinitionTask;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.context.architecture.ApplicationContext;
import cz.mg.compiler.tasks.mg.resolver.link.MgResolveBaseClassesTask;
import cz.mg.compiler.tasks.mg.resolver.link.MgResolveComponentStampTask;
import cz.mg.compiler.tasks.mg.resolver.link.MgResolveVariableDatatypeTask;
import cz.mg.language.entities.mg.unresolved.architecture.MgUnresolvedApplication;
import cz.mg.language.entities.mg.runtime.architecture.MgApplication;


public class MgResolveApplicationTask extends MgPostponeResolveTask {
    @Input
    private final MgUnresolvedApplication logicalApplication;

    @Output
    private MgApplication application;

    public MgResolveApplicationTask(Context context, MgUnresolvedApplication logicalApplication) {
        super(new cz.mg.compiler.tasks.mg.resolver.context.architecture.ApplicationContext(context));
        this.logicalApplication = logicalApplication;
    }

    @Override
    protected cz.mg.compiler.tasks.mg.resolver.context.architecture.ApplicationContext getContext() {
        return (ApplicationContext) super.getContext();
    }

    public MgApplication getApplication() {
        return application;
    }

    @Override
    protected void onRun() {
        application = new MgApplication(logicalApplication.getName());
        getContext().setApplication(application);

        postpone(MgResolveLocationTask.class, () -> {
            MgResolveLocationTask task = new MgResolveLocationTask(getContext(), logicalApplication.getRoot());
            task.run();
            application.getRoot().getComponents().addCollectionLast(task.getLocation().getComponents());
        });

        postpone(MgAddBuildinComponentsTask.class, () -> {
            MgAddBuildinComponentsTask task = new MgAddBuildinComponentsTask(application.getRoot());
            task.run();
        });

        resolvePostponedTasks();
    }

    private void resolvePostponedTasks(){
        resolve(MgResolveLocationTask.class);
        resolve(MgAddBuildinComponentsTask.class);

        resolve(cz.mg.compiler.tasks.mg.resolver.component.MgResolveStampDefinitionTask.class);
        resolve(cz.mg.compiler.tasks.mg.resolver.component.MgResolveClassDefinitionTask.class);
        resolve(cz.mg.compiler.tasks.mg.resolver.component.MgResolveCollectionDefinitionTask.class);
        resolve(cz.mg.compiler.tasks.mg.resolver.component.MgResolveFunctionVariableDefinitionTask.class);
        resolve(cz.mg.compiler.tasks.mg.resolver.component.MgResolveClassVariableDefinitionTask.class);
        resolve(MgResolveClassFunctionDefinitionTask.class);

        resolve(MgResolveUsageTask.class);
        resolve(MgResolveComponentStampTask.class);
        resolve(MgResolveBaseClassesTask.class);
        resolve(MgResolveVariableDatatypeTask.class);
        resolve(MgResolveCommandTask.class);
    }
}
