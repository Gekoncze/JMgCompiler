package cz.mg.compiler.io;

import cz.mg.annotations.storage.Value;

import java.nio.ByteBuffer;


public class Bytes extends IOEntity {
    @Value
    private final ByteBuffer byteBuffer;

    public Bytes(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }
}
