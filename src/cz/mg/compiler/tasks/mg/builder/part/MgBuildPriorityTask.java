package cz.mg.compiler.tasks.mg.builder.part;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.language.entities.text.structured.parts.leaves.Value;


public class MgBuildPriorityTask extends MgBuildPartTask {
    @Output
    private int priority;

    public MgBuildPriorityTask(List<Part> parts) {
        super(parts);
    }

    public int getPriority() {
        return priority;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        checkCount(1);
        try {
            priority = get(Value.class, 0).getText().parseInt();
        } catch (Exception e){
            throw new LanguageException("Could not parse priority: " + e.getMessage());
        }
    }
}
