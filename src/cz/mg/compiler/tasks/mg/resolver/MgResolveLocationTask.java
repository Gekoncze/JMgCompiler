package cz.mg.compiler.tasks.mg.resolver;

import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.component.MgResolveLocationVariableDefinitionTask;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.context.architecture.LocationContext;
import cz.mg.language.entities.mg.logical.components.*;
import cz.mg.language.entities.mg.runtime.components.MgLocation;


public class MgResolveLocationTask extends MgPostponeResolveTask {
    @Input
    private final MgLogicalLocation logicalLocation;

    @Output
    private MgLocation location;

    protected MgResolveLocationTask(Context context, MgLogicalLocation logicalLocation) {
        super(new cz.mg.compiler.tasks.mg.resolver.context.architecture.LocationContext(context));
        this.logicalLocation = logicalLocation;
    }

    @Override
    protected cz.mg.compiler.tasks.mg.resolver.context.architecture.LocationContext getContext() {
        return (LocationContext) super.getContext();
    }

    public MgLocation getLocation() {
        return location;
    }

    @Override
    protected void onRun() {
        location = new MgLocation(logicalLocation.getName());
        getContext().setLocation(location);

        for(MgLogicalComponent logicalComponent : logicalLocation.getComponents()){
            if(logicalComponent instanceof MgLogicalLocation){
                postpone(MgResolveLocationTask.class, () -> {
                    MgResolveLocationTask task = new MgResolveLocationTask(getContext(), (MgLogicalLocation) logicalComponent);
                    task.run();
                    location.getComponents().addLast(task.getLocation());
                });
            }

            if(logicalComponent instanceof MgLogicalCollection){
                postpone(cz.mg.compiler.tasks.mg.resolver.component.MgResolveCollectionDefinitionTask.class, () -> {
                    cz.mg.compiler.tasks.mg.resolver.component.MgResolveCollectionDefinitionTask task = new cz.mg.compiler.tasks.mg.resolver.component.MgResolveCollectionDefinitionTask(getContext(), (MgLogicalCollection) logicalComponent);
                    task.run();
                    location.getComponents().addLast(task.getCollection());
                });
            }

            if(logicalComponent instanceof MgLogicalClass){
                postpone(cz.mg.compiler.tasks.mg.resolver.component.MgResolveClassDefinitionTask.class, () -> {
                    cz.mg.compiler.tasks.mg.resolver.component.MgResolveClassDefinitionTask task = new cz.mg.compiler.tasks.mg.resolver.component.MgResolveClassDefinitionTask(getContext(), (MgLogicalClass) logicalComponent);
                    task.run();
                    location.getComponents().addLast(task.getClazz());
                });
            }

            if(logicalComponent instanceof MgLogicalFunction){
                postpone(cz.mg.compiler.tasks.mg.resolver.component.MgResolveClassFunctionDefinitionTask.class, () -> {
                    cz.mg.compiler.tasks.mg.resolver.component.MgResolveLocationFunctionDefinitionTask task = new cz.mg.compiler.tasks.mg.resolver.component.MgResolveLocationFunctionDefinitionTask(getContext(), (MgLogicalFunction) logicalComponent);
                    task.run();
                    location.getComponents().addLast(task.getFunction());
                });
            }

            if(logicalComponent instanceof MgLogicalStamp){
                postpone(cz.mg.compiler.tasks.mg.resolver.component.MgResolveStampDefinitionTask.class, () -> {
                    cz.mg.compiler.tasks.mg.resolver.component.MgResolveStampDefinitionTask task = new cz.mg.compiler.tasks.mg.resolver.component.MgResolveStampDefinitionTask(getContext(), (MgLogicalStamp) logicalComponent);
                    task.run();
                    location.getComponents().addLast(task.getStamp());
                });
            }

            if(logicalComponent instanceof MgLogicalVariable){
                postpone(cz.mg.compiler.tasks.mg.resolver.component.MgResolveLocationVariableDefinitionTask.class, () -> {
                    cz.mg.compiler.tasks.mg.resolver.component.MgResolveLocationVariableDefinitionTask task = new MgResolveLocationVariableDefinitionTask(getContext(), (MgLogicalVariable) logicalComponent);
                    task.run();
                    location.getComponents().addLast(task.getVariable());
                });
            }

            throw new RuntimeException("Unsupported logical component " + logicalComponent.getClass().getSimpleName() + " for resolve.");
        }
    }
}
