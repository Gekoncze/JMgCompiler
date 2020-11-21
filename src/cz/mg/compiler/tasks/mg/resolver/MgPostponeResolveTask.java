package cz.mg.compiler.tasks.mg.resolver;

import cz.mg.collections.list.List;
import cz.mg.collections.map.Map;
import cz.mg.annotations.storage.Link;
import cz.mg.annotations.storage.Part;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.compiler.tasks.mg.resolver.context.Context;


public abstract class MgPostponeResolveTask extends MgResolveTask {
    @Mandatory
    private final @Part Map<@Link Class, @Part List<@Part Runnable>> map = new Map<>();

    @Mandatory @Part
    private final cz.mg.compiler.tasks.mg.resolver.context.Context context;

    public MgPostponeResolveTask(cz.mg.compiler.tasks.mg.resolver.context.Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    protected void postpone(Class clazz, Runnable runnable){
        List<Runnable> subtasks = map.get(clazz);
        if(subtasks == null) map.set(clazz, subtasks = new List<>());
        subtasks.addLast(runnable);
    }

    protected void resolve(Class clazz){
        List<Runnable> subtasks = map.get(clazz);
        if(subtasks == null) return;
        for(Runnable subtask : subtasks) subtask.run();
    }
}
