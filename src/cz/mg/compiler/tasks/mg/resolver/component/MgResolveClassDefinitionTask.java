package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.component.structured.ClassContext;
import cz.mg.language.entities.mg.logical.components.MgLogicalClass;
import cz.mg.language.entities.mg.logical.components.MgLogicalFunction;
import cz.mg.language.entities.mg.logical.components.MgLogicalVariable;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.language.entities.mg.runtime.components.types.classes.MgClass;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.link.MgResolveBaseClassesTask;


public class MgResolveClassDefinitionTask extends MgResolveComponentDefinitionTask {
    @Input
    private final MgLogicalClass logicalClass;

    @Output
    private MgClass clazz;

    public MgResolveClassDefinitionTask(Context context, MgLogicalClass logicalClass) {
        super(new cz.mg.compiler.tasks.mg.resolver.context.component.structured.ClassContext(context), logicalClass);
        this.logicalClass = logicalClass;
    }

    @Override
    protected cz.mg.compiler.tasks.mg.resolver.context.component.structured.ClassContext getContext() {
        return (ClassContext) super.getContext();
    }

    public MgClass getClazz() {
        return clazz;
    }

    @Override
    protected void onResolveComponent(List<MgStamp> stamps) {
        clazz = new MgClass(logicalClass.getName());
        clazz.getStamps().addCollectionLast(stamps);
        getContext().setClazz(clazz);

        postpone(MgResolveBaseClassesTask.class, () -> {
            MgResolveBaseClassesTask task = new MgResolveBaseClassesTask(getContext(), logicalClass.getBaseClasses());
            task.run();
            clazz.setBaseClass(task.getBaseClass());
        });

        for(MgLogicalVariable logicalVariable : logicalClass.getVariables()){
            postpone(MgResolveClassVariableDefinitionTask.class, () -> {
                MgResolveClassVariableDefinitionTask task = new MgResolveClassVariableDefinitionTask(getContext(), clazz, logicalVariable);
                task.run();
                clazz.getVariableDefinitions().addLast(task.getVariable());
            });
        }

        for(MgLogicalFunction logicalFunction : logicalClass.getFunctions()){
            postpone(MgResolveClassFunctionDefinitionTask.class, () -> {
                MgResolveClassFunctionDefinitionTask task = new MgResolveClassFunctionDefinitionTask(getContext(), logicalFunction);
                task.run();
                clazz.getFunctionDefinitions().addLast(task.getFunction());
            });
        }
    }
}
