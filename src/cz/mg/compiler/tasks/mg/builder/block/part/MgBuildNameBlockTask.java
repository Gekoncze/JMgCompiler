package cz.mg.compiler.tasks.mg.builder.block.part;

import cz.mg.collections.Clump;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildNameTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;


public class MgBuildNameBlockTask extends MgBuildBlockTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildNameTask.class,
        MgBuildNameBlockTask.class,
        (source, destination) -> destination.name = source.getName()
    );

    @Output
    private ReadableText name;

    public MgBuildNameBlockTask(Block block) {
        super(block);
    }

    public ReadableText getName() {
        return name;
    }

    @Override
    protected Object getOutput() {
        return name;
    }

    @Override
    protected PartProcessor getProcessor() {
        return PROCESSOR;
    }

    @Override
    protected Clump<Pattern> getPatterns() {
        return null;
    }
}
