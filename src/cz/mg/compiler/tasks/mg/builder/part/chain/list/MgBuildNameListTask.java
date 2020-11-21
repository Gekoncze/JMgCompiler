package cz.mg.compiler.tasks.mg.builder.part.chain.list;

import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildNameTask;
import cz.mg.compiler.tasks.mg.builder.part.chain.MgBuildListTask;


public class MgBuildNameListTask extends MgBuildListTask {
    @Output
    private final List<ReadableText> names = new List<>();

    public MgBuildNameListTask(List<Part> parts) {
        super(parts);
    }

    public List<ReadableText> getNames() {
        return names;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        MgBuildNameTask task = new MgBuildNameTask(parts);
        task.run();
        names.addLast(task.getName());
    }
}
