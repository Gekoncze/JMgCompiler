package cz.mg.compiler.io.input;

import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.tasks.data.input.InputTask;
import cz.mg.compiler.tasks.data.input.FilesystemInputTask;


public class FilesystemInput extends Input {
    @Override
    public InputTask createTask(ReadableText url) {
        return new FilesystemInputTask(url);
    }
}
