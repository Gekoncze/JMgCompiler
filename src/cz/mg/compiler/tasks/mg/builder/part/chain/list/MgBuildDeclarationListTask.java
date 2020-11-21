package cz.mg.compiler.tasks.mg.builder.part.chain.list;

import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedVariable;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildDeclarationTask;
import cz.mg.compiler.tasks.mg.builder.part.chain.MgBuildListTask;


public class MgBuildDeclarationListTask extends MgBuildListTask {
    @Output
    private final List<MgUnresolvedVariable> variables = new List<>();

    public MgBuildDeclarationListTask(List<Part> parts) {
        super(parts);
    }

    public List<MgUnresolvedVariable> getVariables() {
        return variables;
    }

    @Override
    protected void buildParts(List<Part> parts) {
        MgBuildDeclarationTask task = new MgBuildDeclarationTask(parts);
        task.run();
        variables.addLast(task.getVariable());
    }
}
