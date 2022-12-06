package org.example;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class UsingCharsets {
    public static void main(String... args) throws IOException {
        Charset latin_1 = StandardCharsets.ISO_8859_1;
        Charset utf_8 = StandardCharsets.UTF_8;

        String hello = "Hello World from José";
        System.out.println("Length of hello string is: "+hello.length());

        CharBuffer charBuffer = CharBuffer.allocate(1024 * 1024);
        charBuffer.put(hello);
        charBuffer.flip();

        ByteBuffer byteBuffer = latin_1.encode(charBuffer);
        Path path = Path.of("src/main/resources/hello-latin-1.txt");
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            channel.write(byteBuffer);
        }

        System.out.println("Size of latin-1 file is: "+ Files.size(path));

        charBuffer.flip();
        byteBuffer = utf_8.encode(charBuffer);
        path = Path.of("src/main/resources/hello-utf-8.txt");
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            channel.write(byteBuffer);
        }
        System.out.println("Size of utf-8 file is: "+ Files.size(path));

        byteBuffer.clear();
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            channel.read(byteBuffer);
        }
        byteBuffer.flip();
        charBuffer = utf_8.decode(byteBuffer);
        String fileContents = new String(charBuffer.array());
        System.out.println("Contents from the utf-8 file: "+fileContents);

    }

}
