package cz.mg.compiler.tasks.mg.builder.part.chain.path;

import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildPathNameTask;
import cz.mg.compiler.tasks.mg.builder.part.chain.MgBuildPathTask;


public class MgBuildNamePathTask extends MgBuildPathTask {
    @Output
    private final List<ReadableText> names = new List<>();

    public MgBuildNamePathTask(List<Part> parts) {
        super(parts);
    }

    public List<ReadableText> getNames() {
        return names;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        MgBuildPathNameTask task = new MgBuildPathNameTask(parts);
        task.run();
        names.addLast(task.getName());
    }
}
