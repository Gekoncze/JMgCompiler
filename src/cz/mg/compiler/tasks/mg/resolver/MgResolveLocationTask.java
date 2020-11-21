package cz.mg.compiler.tasks.mg.resolver;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.component.MgResolveClassDefinitionTask;
import cz.mg.compiler.tasks.mg.resolver.component.MgResolveClassFunctionDefinitionTask;
import cz.mg.compiler.tasks.mg.resolver.component.MgResolveCollectionDefinitionTask;
import cz.mg.compiler.tasks.mg.resolver.component.MgResolveLocationFunctionDefinitionTask;
import cz.mg.compiler.tasks.mg.resolver.component.MgResolveLocationVariableDefinitionTask;
import cz.mg.compiler.tasks.mg.resolver.component.MgResolveStampDefinitionTask;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.context.architecture.LocationContext;
import cz.mg.language.entities.mg.unresolved.components.*;
import cz.mg.language.entities.mg.runtime.components.MgLocation;


public class MgResolveLocationTask extends MgPostponeResolveTask {
    @Input
    private final MgUnresolvedLocation logicalLocation;

    @Output
    private MgLocation location;

    protected MgResolveLocationTask(Context context, MgUnresolvedLocation logicalLocation) {
        super(new LocationContext(context));
        this.logicalLocation = logicalLocation;
    }

    @Override
    protected LocationContext getContext() {
        return (LocationContext) super.getContext();
    }

    public MgLocation getLocation() {
        return location;
    }

    @Override
    protected void onRun() {
        location = new MgLocation(logicalLocation.getName());
        getContext().setLocation(location);

        for(MgUnresolvedComponent logicalComponent : logicalLocation.getComponents()){
            if(logicalComponent instanceof MgUnresolvedLocation){
                postpone(MgResolveLocationTask.class, () -> {
                    MgResolveLocationTask task = new MgResolveLocationTask(getContext(), (MgUnresolvedLocation) logicalComponent);
                    task.run();
                    location.getComponents().addLast(task.getLocation());
                });
            }

            if(logicalComponent instanceof MgUnresolvedCollection){
                postpone(MgResolveCollectionDefinitionTask.class, () -> {
                    MgResolveCollectionDefinitionTask task = new MgResolveCollectionDefinitionTask(getContext(), (MgUnresolvedCollection) logicalComponent);
                    task.run();
                    location.getComponents().addLast(task.getCollection());
                });
            }

            if(logicalComponent instanceof MgUnresolvedClass){
                postpone(MgResolveClassDefinitionTask.class, () -> {
                    MgResolveClassDefinitionTask task = new MgResolveClassDefinitionTask(getContext(), (MgUnresolvedClass) logicalComponent);
                    task.run();
                    location.getComponents().addLast(task.getClazz());
                });
            }

            if(logicalComponent instanceof MgUnresolvedFunction){
                postpone(MgResolveClassFunctionDefinitionTask.class, () -> {
                    MgResolveLocationFunctionDefinitionTask task = new MgResolveLocationFunctionDefinitionTask(getContext(), (MgUnresolvedFunction) logicalComponent);
                    task.run();
                    location.getComponents().addLast(task.getFunction());
                });
            }

            if(logicalComponent instanceof MgUnresolvedStamp){
                postpone(MgResolveStampDefinitionTask.class, () -> {
                    MgResolveStampDefinitionTask task = new MgResolveStampDefinitionTask(getContext(), (MgUnresolvedStamp) logicalComponent);
                    task.run();
                    location.getComponents().addLast(task.getStamp());
                });
            }

            if(logicalComponent instanceof MgUnresolvedVariable){
                postpone(MgResolveLocationVariableDefinitionTask.class, () -> {
                    MgResolveLocationVariableDefinitionTask task = new MgResolveLocationVariableDefinitionTask(getContext(), (MgUnresolvedVariable) logicalComponent);
                    task.run();
                    location.getComponents().addLast(task.getVariable());
                });
            }

            throw new RuntimeException("Unsupported logical component " + logicalComponent.getClass().getSimpleName() + " for resolve.");
        }
    }
}
