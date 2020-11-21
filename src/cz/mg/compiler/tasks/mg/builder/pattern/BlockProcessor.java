package cz.mg.compiler.tasks.mg.builder.pattern;

import cz.mg.language.entities.text.structured.Block;
import cz.mg.compiler.tasks.mg.builder.block.MgBuildBlockTask;


public class BlockProcessor<S extends MgBuildBlockTask, D extends MgBuildBlockTask, B extends Block> extends Processor<S, D, B> {
    public BlockProcessor(
        Class<S> sourceBuildTaskClass,
        Class<D> destinationBuildTaskClass,
        Setter<S, D> setter
    ) {
        super(
            sourceBuildTaskClass,
            destinationBuildTaskClass,
            setter
        );
    }

    public BlockProcessor(
        Class<S> sourceBuildTaskClass,
        Class<D> destinationBuildTaskClass,
        Setter<S, D> setter,
        SourceBuildTaskFactory<B, S, D> sourceBuildTaskFactory
    ) {
        super(
            sourceBuildTaskClass,
            destinationBuildTaskClass,
            setter,
            sourceBuildTaskFactory
        );
    }
}
