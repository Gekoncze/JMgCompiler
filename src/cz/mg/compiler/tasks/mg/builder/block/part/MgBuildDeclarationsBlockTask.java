package cz.mg.compiler.tasks.mg.builder.block.part;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.part.chain.list.MgBuildDeclarationListTask;
import cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Count;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;
import cz.mg.compiler.tasks.mg.builder.pattern.Requirement;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedVariable;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;


public class MgBuildDeclarationsBlockTask extends MgBuildBlockTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildDeclarationListTask.class,
        MgBuildDeclarationsBlockTask.class,
        (source, destination) -> destination.variables.addCollectionLast(source.getVariables())
    );

    private static final List<Pattern> PATTERNS = new List<>(
        new Pattern(
            Order.RANDOM,
            Requirement.MANDATORY,
            Count.MULTIPLE,
            new BlockProcessor<>(
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
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }

    @Override
    protected Clump<Pattern> getPatterns() {
        if(variables.isEmpty()){
            return PATTERNS;
        } else {
            return null;
        }
    }
}
