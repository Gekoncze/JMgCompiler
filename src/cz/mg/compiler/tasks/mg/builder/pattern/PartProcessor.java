package cz.mg.compiler.tasks.mg.builder.pattern;

import cz.mg.annotations.storage.Part;
import cz.mg.compiler.tasks.mg.builder.MgBuildTask;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildPartTask;


public class PartProcessor<S extends MgBuildPartTask, D extends MgBuildTask, P extends Part> extends Processor<S, D, P> {
    public PartProcessor(
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

    public PartProcessor(
        Class<S> sourceBuildTaskClass,
        Class<D> destinationBuildTaskClass,
        Setter<S, D> setter,
        SourceBuildTaskFactory<P, S, D> sourceBuildTaskFactory
    ) {
        super(
            sourceBuildTaskClass,
            destinationBuildTaskClass,
            setter,
            sourceBuildTaskFactory
        );
    }
}
