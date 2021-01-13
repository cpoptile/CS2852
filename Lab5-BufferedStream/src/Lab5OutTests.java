/*
 * Course: CS2852
 * Spring 2020
 * File header contains class Lab5OutTests
 * Name: poptilec
 * Created 4/17/2020
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for BufferedOutputStream
 */
public class Lab5OutTests {

    private static final byte[] BYTES = new byte[] {1, 2, 3, 4, 5, 6, 7};
    private BufferedOutputStream outputStream;
    private ByteArrayOutputStream bufferedStream;

    /**
     * Setup called before each test
     */
    @BeforeEach
    void setUp(){
        bufferedStream = new ByteArrayOutputStream(BYTES.length);
        outputStream = new BufferedOutputStream(bufferedStream, BYTES.length);
    }

    /** Tests writeBit method
     * @throws IOException IO exception
     */
    @Test
    void writeBit() throws IOException {
        //write int 101 in bits
        outputStream.writeBit(0);
        outputStream.writeBit(1);
        outputStream.writeBit(1);
        outputStream.writeBit(0);
        outputStream.writeBit(0);
        outputStream.writeBit(1);
        outputStream.writeBit(0);
        outputStream.writeBit(1);
        outputStream.writeBit(0);
        assertThrows(IllegalStateException.class, () -> outputStream.write(1));
        assertThrows(IllegalStateException.class, () -> outputStream.write(new byte[5]));
        //write int 47 in bits
        outputStream.writeBit(0);
        outputStream.writeBit(1);
        outputStream.writeBit(0);
        outputStream.writeBit(1);
        outputStream.writeBit(1);
        outputStream.writeBit(1);
        outputStream.writeBit(1);

        //write as ints
        outputStream.write(9);
        outputStream.write(78);

        outputStream.flush();

        assertArrayEquals(new byte[]{101, 47, 9, 78, 0, 0, 0}, bufferedStream.toByteArray());
    }

    /** Tests write method with integer arguments
     * @throws IOException IO exception
     */
    @Test
    void writeInt() throws IOException {
        for (byte b : BYTES) {
            outputStream.write(b);
        }
        outputStream.flush();
        assertArrayEquals(BYTES, bufferedStream.toByteArray());
    }

    /** Tests write method with array argument
     * @throws IOException IO exception
     */
    @Test
    void writeArray() throws IOException {
        outputStream.write(BYTES);
        outputStream.flush();
        assertArrayEquals(BYTES, bufferedStream.toByteArray());
    }

    /** Tests flush method to fill array
     * @throws IOException iO exception
     */
    @Test
    void flush() throws IOException {
        outputStream.write(23);
        outputStream.write(9);
        outputStream.write(5);
        outputStream.write(16);
        outputStream.write(89);
        outputStream.flush();

        assertArrayEquals(new byte[]{23, 9, 5, 16, 89, 0, 0}, bufferedStream.toByteArray());
    }

    /** Tests bufferedFlush method
     * @throws IOException IO exception
     */
    @Test
    void flushWithoutWriting() throws IOException {
        outputStream.flush();
        assertArrayEquals(new byte[] {}, bufferedStream.toByteArray());
    }

}
