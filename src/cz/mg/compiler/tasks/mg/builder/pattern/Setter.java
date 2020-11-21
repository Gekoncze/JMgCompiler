package cz.mg.compiler.tasks.mg.builder.pattern;

import cz.mg.compiler.tasks.mg.builder.MgBuildTask;


public interface Setter<S extends cz.mg.compiler.tasks.mg.builder.MgBuildTask, D extends MgBuildTask> {
    void set(S source, D destination);
}