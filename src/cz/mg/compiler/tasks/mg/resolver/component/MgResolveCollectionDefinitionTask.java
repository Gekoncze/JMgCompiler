package cz.mg.compiler.tasks.mg.resolver.component;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.resolver.context.component.structured.CollectionContext;
import cz.mg.language.entities.mg.logical.components.MgLogicalCollection;
import cz.mg.language.entities.mg.runtime.components.stamps.MgStamp;
import cz.mg.language.entities.mg.runtime.components.types.classes.MgCollection;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public class MgResolveCollectionDefinitionTask extends MgResolveComponentDefinitionTask {
    @Input
    private final MgLogicalCollection logicalCollection;

    @Output
    private MgCollection collection;

    public MgResolveCollectionDefinitionTask(Context context, MgLogicalCollection logicalCollection) {
        super(new cz.mg.compiler.tasks.mg.resolver.context.component.structured.CollectionContext(context), logicalCollection);
        this.logicalCollection = logicalCollection;
    }

    @Override
    protected cz.mg.compiler.tasks.mg.resolver.context.component.structured.CollectionContext getContext() {
        return (CollectionContext) super.getContext();
    }

    public MgCollection getCollection() {
        return collection;
    }

    @Override
    protected void onResolveComponent(List<MgStamp> stamps) {
        collection = new MgCollection(logicalCollection.getName());
        collection.getStamps().addCollectionLast(stamps);
        getContext().setCollection(collection);

        // TODO
        /*createAndPostponeMore(
            MgResolveCollectionParameterTask.class,
            logicalCollection.getParameters(),
            parameters -> collection.setParameters(parameters)
        );

        createAndPostponeMore(
            MgResolveClassInheritanceTask.class,
            logicalClass.getBaseClasses(),
            classes -> clazz.setClasses(classes)
        );

        createAndPostponeMore(
            MgResolveVariableDefinitionTask.class,
            logicalClass.getVariables(),
            variables -> clazz.setVariables(variables)
        );

        createAndPostponeMore(
            MgResolveFunctionDefinitionTask.class,
            logicalClass.getFunctions(),
            functions -> clazz.setFunctions(functions)
        );*/
    }
}
