package cz.mg.compiler.io;

import cz.mg.collections.text.ReadableText;
import java.nio.ByteBuffer;


public abstract class LocableBytes extends Bytes {
    public LocableBytes(ByteBuffer bytes) {
        super(bytes);
    }

    public abstract ReadableText getPath();
}
