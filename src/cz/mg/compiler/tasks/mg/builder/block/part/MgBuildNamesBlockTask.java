package cz.mg.compiler.tasks.mg.builder.block.part;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.mg.builder.part.chain.list.MgBuildNameListTask;
import cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Count;
import cz.mg.compiler.tasks.mg.builder.pattern.Order;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;
import cz.mg.compiler.tasks.mg.builder.pattern.Requirement;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;


public class MgBuildNamesBlockTask extends MgBuildBlockTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildNameListTask.class,
        MgBuildNamesBlockTask.class,
        (source, destination) -> destination.names.addCollectionLast(source.getNames())
    );

    private static final List<Pattern> PATTERNS = new List<>(
        new Pattern(
            Order.RANDOM,
            Requirement.MANDATORY,
            Count.MULTIPLE,
            new BlockProcessor<>(
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
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }

    @Override
    protected Clump<Pattern> getPatterns() {
        if(names.isEmpty()){
            return PATTERNS;
        } else {
            return null;
        }
    }
}
