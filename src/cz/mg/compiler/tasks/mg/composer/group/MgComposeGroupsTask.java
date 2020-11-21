package cz.mg.compiler.tasks.mg.composer.group;

import cz.mg.collections.list.List;
import cz.mg.collections.list.ListItem;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.tasks.Task;
import cz.mg.language.entities.text.structured.Part;


public abstract class MgComposeGroupsTask extends Task {
    @Input
    protected final List<List<Part>> groups;

    public MgComposeGroupsTask(List<List<Part>> groups) {
        this.groups = groups;
    }

    @Override
    protected void onRun() {
        for(ListItem<List<Part>> groupItem = groups.getFirstItem(); groupItem != null; groupItem = groupItem.getNextItem()){
            List<Part> group = groupItem.get();
            onRun(group);
        }
    }

    protected void onRun(List<Part> group) {
        for(ListItem<Part> partItem = group.getFirstItem(); partItem != null; partItem = partItem.getNextItem()){
            onRun(partItem);
        }
    }

    protected abstract void onRun(ListItem<Part> partItem);
}
