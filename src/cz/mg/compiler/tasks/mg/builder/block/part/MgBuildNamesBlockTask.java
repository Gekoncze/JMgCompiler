package cz.mg.compiler.tasks.mg.builder.block.part;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.part.chain.list.MgBuildNameListTask;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;


public class MgBuildNamesBlockTask extends MgBuildBlockTask {
    private static final cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor PROCESSOR = new cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor<>(
        MgBuildNameListTask.class,
        MgBuildNamesBlockTask.class,
        (source, destination) -> destination.names.addCollectionLast(source.getNames())
    );

    private static final List<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> PATTERNS = new List<>(
        new cz.mg.compiler.tasks.mg.builder.pattern.Pattern(
            Order.RANDOM,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement.MANDATORY,
            cz.mg.compiler.tasks.mg.builder.pattern.Count.MULTIPLE,
            new cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor<>(
                MgBuildNameBlockTask.class,
                MgBuildNamesBlockTask.class,
                (source, destination) -> destination.names.addLast(source.getName())
            )
        )
    );

    @Output
    private final List<ReadableText> names = new List<>();

    public MgBuildNamesBlockTask(Block block) {
        super(block);
    }

    public List<ReadableText> getNames() {
        return names;
    }

    @Override
    protected Object getOutput() {
        return names;
    }

    @Override
    protected cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor getProcessor() {
        return PROCESSOR;
    }

    @Override
    protected Clump<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> getPatterns() {
        if(names.isEmpty()){
            return PATTERNS;
        } else {
            return null;
        }
    }
}
