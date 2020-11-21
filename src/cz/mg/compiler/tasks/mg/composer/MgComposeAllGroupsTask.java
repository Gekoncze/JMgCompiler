package cz.mg.compiler.tasks.mg.composer;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.tasks.mg.composer.group.MgComposeBracketsTask;
import cz.mg.compiler.tasks.mg.composer.group.MgComposeColonsTask;
import cz.mg.language.entities.text.structured.Part;


public class MgComposeAllGroupsTask extends MgComposeTask {
    @Input
    private final List<Part> parts;

    public MgComposeAllGroupsTask(List<Part> parts) {
        this.parts = parts;
    }

    @Override
    protected void onRun() {
        List<List<Part>> groups = new List<>();
        groups.addLast(parts);

        new MgComposeBracketsTask(groups).run();
        new MgComposeColonsTask(groups).run();
    }
}
