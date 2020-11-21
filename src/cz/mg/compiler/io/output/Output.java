package cz.mg.compiler.io.output;

import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.io.Bytes;
import cz.mg.compiler.io.IOEntity;
import cz.mg.compiler.tasks.data.output.OutputTask;


public abstract class Output extends IOEntity {
    public abstract OutputTask createTask(ReadableText url, Bytes bytes);
}
