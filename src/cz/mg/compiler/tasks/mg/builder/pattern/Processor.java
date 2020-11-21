package cz.mg.compiler.tasks.mg.builder.pattern;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.requirement.Optional;
import cz.mg.annotations.storage.Part;
import cz.mg.compiler.tasks.mg.builder.MgBuildTask;


public abstract class Processor<S extends MgBuildTask, D extends MgBuildTask, O extends Object> {
    @Mandatory @Part
    private final Class<S> sourceBuildTaskClass;

    @Mandatory @Part
    private final Class<D> destinationBuildTaskClass;

    @Mandatory @Part
    private final Setter<S, D> setter;

    @Optional
    private final SourceBuildTaskFactory<O, S, D> sourceBuildTaskFactory;

    public Processor(
        Class<S> sourceBuildTaskClass,
        Class<D> destinationBuildTaskClass,
        Setter<S, D> setter
    ) {
        this(
            sourceBuildTaskClass,
            destinationBuildTaskClass,
            setter,
            null
        );
    }

    public Processor(
        Class<S> sourceBuildTaskClass,
        Class<D> destinationBuildTaskClass,
        Setter<S, D> setter,
        SourceBuildTaskFactory<O, S, D> sourceBuildTaskFactory
    ) {
        this.sourceBuildTaskClass = sourceBuildTaskClass;
        this.destinationBuildTaskClass = destinationBuildTaskClass;
        this.setter = setter;
        this.sourceBuildTaskFactory = sourceBuildTaskFactory;
    }

    public Class<S> getSourceBuildTaskClass() {
        return sourceBuildTaskClass;
    }

    public Class<D> getDestinationBuildTaskClass() {
        return destinationBuildTaskClass;
    }

    public Setter<S, D> getSetter() {
        return setter;
    }

    public SourceBuildTaskFactory<O, S, D> getSourceBuildTaskFactory() {
        return sourceBuildTaskFactory;
    }

    public interface SourceBuildTaskFactory<O, S, D> {
        public S create(O object, D destinationTask);
    }
}
