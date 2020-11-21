package cz.mg.compiler.io;

import cz.mg.collections.text.ReadableText;
import cz.mg.collections.text.ReadonlyText;
import cz.mg.language.Named;
import cz.mg.annotations.storage.Value;

import java.nio.ByteBuffer;
import java.nio.file.Path;


public class FilesystemBytes extends LocableBytes implements Named {
    @Value
    private final Path path;

    public FilesystemBytes(ByteBuffer bytes, Path path) {
        super(bytes);
        this.path = path;
    }

    @Override
    public ReadableText getPath() {
        return new ReadonlyText(path.toString());
    }

    @Override
    public ReadableText getName() {
        return new ReadonlyText(path.getFileName().toString());
    }
}
