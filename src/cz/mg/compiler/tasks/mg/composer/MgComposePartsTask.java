package cz.mg.compiler.tasks.mg.composer;

import cz.mg.collections.list.List;
import cz.mg.collections.list.ReadableList;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.Task;
import cz.mg.language.entities.text.plain.Token;
import cz.mg.language.entities.text.structured.Part;


public class MgComposePartsTask extends Task {
    @Input
    private final ReadableList<Token> tokens;

    @Output
    private List<Part> parts = null;

    public MgComposePartsTask(ReadableList<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Part> getParts() {
        return parts;
    }

    @Override
    protected void onRun() {
        MgComposeAllLeavesTask composeLeavesTask = new MgComposeAllLeavesTask(tokens);
        composeLeavesTask.run();
        parts = composeLeavesTask.getParts();

        MgComposeAllGroupsTask composeGroupsTask = new MgComposeAllGroupsTask(parts);
        composeGroupsTask.run();
    }
}
