/*
 * Course: CS2852
 * Spring 2020
 * File header contains class BufferedInputStream
 * Name: poptilec
 * Created 4/17/2020
 */

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Class creates a BufferedInputStream that reads in bits and bytes
 */
public class BufferedInputStream implements Closeable, AutoCloseable {

    private static final int DEFAULT_SIZE = 32;
    private static final int BYTE_SIZE = 8;
    private InputStream in;
    private byte[] buffer;
    private int bitCount = 0;
    private int bitsPosition;
    private int pos;

    /**
     * Constructor creates a buffered input stream with default size
     *
     * @param in an InputStream
     */
    public BufferedInputStream(InputStream in) {
        this(in, DEFAULT_SIZE);
    }

    /**
     * Constructor creates a buffered input stream with given size
     *
     * @param in   an InputStream
     * @param size size of stream
     */
    public BufferedInputStream(InputStream in, int size) {
        this.in = in;
        if (size <= 0) {
            throw new IllegalArgumentException("The size of the stream must be positive");
        }
        buffer = new byte[size];
        pos = size;
    }

    /**
     * Method reads next byte of data from input stream
     *
     * @return the first value in the buffer that hasn't already been returned
     * @throws IOException IO exception
     * @throws IllegalStateException exception if byte from input stream is partially read
     */
    public int read() throws IOException, IllegalStateException {
        if (bitCount % BYTE_SIZE != 0) {
            throw new IllegalStateException();
        }
        if (pos == buffer.length) {
            flush();
            in.read(buffer);
        }
        return buffer[pos++];
    }

    /**
     * Method reads bytes from the input stream and stores them into buffer array
     *
     * @param b the array to filled
     * @return the number of bytes that were read
     * @throws IOException IO exception
     * @throws IllegalStateException exception if byte from input stream is partially read
     */
    public int read(byte[] b) throws IOException, IllegalStateException {
        if (bitCount % BYTE_SIZE != 0) {
            throw new IllegalStateException();
        }

        int count = 0;
        if (pos == buffer.length) {
            flush();
            in.read(buffer);
        }
        for (int i = 0; i < b.length; i++) {
            if (pos == buffer.length) {
                flush();
                in.read(buffer);
            }
            if (buffer[pos] == -1) {
                return count;
            }
            b[i] = buffer[pos++];
            count++;
        }
        return count;
    }

    /**
     * Method reads next bit of data from input stream
     *
     * @return the next bit
     * @throws IOException some exception
     */
    public int readBit() throws IOException {
        if (pos == buffer.length) {
            flush();
            in.read(buffer);
        }

        switch (bitCount % BYTE_SIZE) {
            case 0:
                bitCount++;
                bitsPosition = pos++;
                return buffer[bitsPosition] >> BYTE_SIZE-1 & 0b00000001;
            case 1:
                bitCount++;
                return buffer[bitsPosition] >> BYTE_SIZE-2 & 0b00000001;
            case 2:
                bitCount++;
                return buffer[bitsPosition] >> BYTE_SIZE-3 & 0b00000001;
            case 3:
                bitCount++;
                return buffer[bitsPosition] >> 4 & 0b00000001;
            case 4:
                bitCount++;
                return buffer[bitsPosition] >> 3 & 0b00000001;
            case BYTE_SIZE-3:
                bitCount++;
                return buffer[bitsPosition] >> 2 & 0b00000001;
            case BYTE_SIZE-2:
                bitCount++;
                return buffer[bitsPosition] >> 1 & 0b00000001;
            default:
                bitCount++;
                return buffer[bitsPosition] & 0b00000001;
        }
    }

    /**
     * Helper method resets array to default size
     */
    public void flush() {
        // fills rest of array
        Arrays.fill(buffer, (byte) -1);
        pos = 0;
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
