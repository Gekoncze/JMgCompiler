package cz.mg.compiler.tasks.converters;

import cz.mg.collections.text.ReadableText;
import cz.mg.collections.text.ReadonlyText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.io.Bytes;

import java.nio.charset.Charset;


public class BytesToTextTask extends ConverterTask {
    @Input
    private final Bytes bytes;

    @Input
    private final Charset encoding;

    @Output
    private ReadableText text = null;

    public BytesToTextTask(Bytes bytes, Charset encoding) {
        this.bytes = bytes;
        this.encoding = encoding;
    }

    @Override
    protected void onRun() {
        text = new ReadonlyText(new String(bytes.getByteBuffer().array(), encoding));
    }
}
