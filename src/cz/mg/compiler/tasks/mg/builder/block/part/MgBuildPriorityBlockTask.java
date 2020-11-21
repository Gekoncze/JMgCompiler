package cz.mg.compiler.tasks.mg.builder.block.part;

import cz.mg.collections.Clump;
import cz.mg.compiler.annotations.Output;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildPriorityTask;
import cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;


public class MgBuildPriorityBlockTask extends MgBuildBlockTask {
    private static final PartProcessor PROCESSOR = new PartProcessor<>(
        MgBuildPriorityTask.class,
        MgBuildPriorityBlockTask.class,
        (source, destination) -> destination.priority = source.getPriority()
    );

    @Output
    private int priority;

    public MgBuildPriorityBlockTask(Block block) {
        super(block);
    }

    public int getPriority() {
        return priority;
    }

    @Override
    protected Object getOutput() {
        return priority;
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
