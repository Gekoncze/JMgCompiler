package cz.mg.compiler.tasks.data.output;

import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.io.Bytes;
import cz.mg.language.LanguageException;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;


public class FilesystemOutputTask extends OutputTask {
    @cz.mg.compiler.annotations.Input
    private final ReadableText url;

    @Input
    private final cz.mg.compiler.io.Bytes bytes;

    public FilesystemOutputTask(ReadableText url, Bytes bytes) {
        this.url = url;
        this.bytes = bytes;
    }

    @Override
    protected void onRun() {
        try(FileChannel output = FileChannel.open(new File(url.toString()).toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE)){
            output.write(bytes.getByteBuffer());
        } catch (Exception e){
            throw new LanguageException("Could not save file '" + url + "'.", e);
        }
    }
}
