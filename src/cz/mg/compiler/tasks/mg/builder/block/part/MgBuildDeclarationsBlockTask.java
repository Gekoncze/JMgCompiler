package cz.mg.compiler.tasks.mg.builder.block.part;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.part.chain.list.MgBuildDeclarationListTask;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedVariable;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;


public class MgBuildDeclarationsBlockTask extends MgBuildBlockTask {
    private static final cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor PROCESSOR = new cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor<>(
        MgBuildDeclarationListTask.class,
        MgBuildDeclarationsBlockTask.class,
        (source, destination) -> destination.variables.addCollectionLast(source.getVariables())
    );

    private static final List<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> PATTERNS = new List<>(
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.MANDATORY,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildDeclarationBlockTask.class,
                MgBuildDeclarationsBlockTask.class,
                (source, destination) -> destination.variables.addLast(source.getDeclaration())
            )
        )
    );

    @Output
    private final List<MgUnresolvedVariable> variables = new List<>();

    public MgBuildDeclarationsBlockTask(Block block) {
        super(block);
    }

    public List<MgUnresolvedVariable> getVariables() {
        return variables;
    }

    @Override
    protected Object getOutput() {
        return variables;
    }

    @Override
    protected cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor getProcessor() {
        return PROCESSOR;
    }

    @Override
    protected Clump<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> getPatterns() {
        if(variables.isEmpty()){
            return PATTERNS;
        } else {
            return null;
        }
    }
}
