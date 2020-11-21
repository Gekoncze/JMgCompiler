package cz.mg.compiler.tasks.mg.builder.part.chain;

import cz.mg.collections.list.List;
import cz.mg.language.entities.mg.Operators;
import cz.mg.language.entities.text.structured.Part;


public abstract class MgBuildPathTask extends MgBuildChainTask {
    public MgBuildPathTask(List<Part> parts) {
        super(parts, Operators.MEMBER_ACCESS);
    }
}
