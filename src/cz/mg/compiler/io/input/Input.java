package cz.mg.compiler.io.input;

import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.io.IOEntity;
import cz.mg.compiler.tasks.data.input.InputTask;


public abstract class Input extends IOEntity {
    public abstract InputTask createTask(ReadableText url);
}
