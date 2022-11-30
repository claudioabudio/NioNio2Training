package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ReadingWritingBuffers {
    public static void main(String[] args) throws IOException, URISyntaxException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        byteBuffer.putInt(10);
        byteBuffer.putInt(20);
        byteBuffer.putInt(30);
        System.out.println("Capacity: " + byteBuffer.capacity());
        System.out.println("Position: " + byteBuffer.position());
        System.out.println("Limit: " + byteBuffer.limit());

        byteBuffer.flip();
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        for (int i = 0; i < intBuffer.limit(); i++) {
            System.out.println("Read back: " + intBuffer.get());
        }

        System.out.println("Position: " + byteBuffer.position());
        System.out.println("Limit: " + byteBuffer.limit());

        Path path = Path.of("src/main/resources/ints.bin");
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            channel.write(byteBuffer);
        }

        System.out.println("Size of new file: " + Files.size(path));

        byteBuffer.clear();
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            channel.read(byteBuffer);
        }

        byteBuffer.flip();
        IntBuffer intBuffer1 = byteBuffer.asIntBuffer();
        try {
            while (true) {
                int i = intBuffer1.get();
                System.out.println("Reading back from file: " + i);
            }
        } catch (BufferUnderflowException e) {

        }
        System.out.println("Position: " + byteBuffer.position());
        System.out.println("Limit: " + byteBuffer.limit());
    }
}