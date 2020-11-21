package cz.mg.compiler.tasks.converters;

import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.io.Bytes;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;


public class TextToBytesTask extends ConverterTask {
    @cz.mg.compiler.annotations.Input
    private final ReadableText text;

    @Input
    private final Charset encoding;

    @Output
    private cz.mg.compiler.io.Bytes bytes = null;

    public TextToBytesTask(ReadableText text, Charset encoding) {
        this.text = text;
        this.encoding = encoding;
    }

    public cz.mg.compiler.io.Bytes getBytes() {
        return bytes;
    }

    @Override
    protected void onRun() {
        byte[] javaBytes = text.toString().getBytes(encoding);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(javaBytes.length);
        byteBuffer.put(byteBuffer);
        bytes = new Bytes(byteBuffer);
    }
}
