/*
 * Course: CS2852
 * Spring 2020
 * File header contains class BufferedOutputStream
 * Name: poptilec
 * Created 4/17/2020
 */

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Class creates a buffered output stream to write bits and bytes
 */
public class BufferedOutputStream implements Closeable, AutoCloseable {

    private static final int DEFAULT_SIZE = 32;
    private static final int BYTE_SIZE = 8;
    private OutputStream out;
    private byte[] buffer;
    private int count = 0;
    private int bitCount = 0;
    private int pos;

    /**
     * Constructor creates a buffered output stream with deault size
     *
     * @param out an OutputStream
     */
    public BufferedOutputStream(OutputStream out) {
        this(out, DEFAULT_SIZE);
    }

    /**
     * Constructor creates a buffered output stream with given size
     *
     * @param out  an OutputStream
     * @param size buffer size
     */
    public BufferedOutputStream(OutputStream out, int size) {
        this.out = out;
        buffer = new byte[size];
    }

    /**
     * Method writes the given int to the stream
     *
     * @param b given int
     * @throws IOException IO exception
     * @throws IllegalStateException exception if partial bit was written to stream
     */
    public void write(int b) throws IOException {
        if (bitCount % BYTE_SIZE != 0) {
            throw new IllegalStateException();
        }
        if (count >= buffer.length) {
            flushBuffer();
        }
        buffer[count++] = (byte) b;
    }

    /**
     * Method writes the given bytes to stream
     *
     * @param b an array of bytes
     * @throws IOException some exception
     * @throws IllegalStateException exception if partial byte has been written to stream
     */
    public void write(byte[] b) throws IOException {
        if (bitCount % BYTE_SIZE != 0) {
            throw new IllegalStateException();
        }
        if (b.length >= buffer.length) {
            flushBuffer();
            out.write(b);
        } else if (b.length > buffer.length - count) {
            flushBuffer();
            System.arraycopy(b, 0, buffer, count, b.length);
            count += b.length;
        } else {
            System.arraycopy(b, 0, buffer, count, b.length);
            count += b.length;
        }
    }

    /**
     * Method writes specified bit to stream
     *
     * @param bit bit
     * @throws IOException IO exception
     * @throws IllegalArgumentException exception thrown if bit passed in is not 0 or 1
     */
    public void writeBit(int bit) throws IOException {
        if (bit != 0 && bit != 1) {
            throw new IllegalArgumentException("Can only pass in a 0 or 1");
        }
        if (count >= buffer.length) {
            flushBuffer();
        }
        switch (bitCount % BYTE_SIZE) {
            case 0:
                pos = count++;
                buffer[pos] = (byte) (bit << BYTE_SIZE-1);
                bitCount++;
                break;
            case 1:
                buffer[pos] = (byte) ((bit << BYTE_SIZE-2) | buffer[pos]);
                bitCount++;
                break;
            case 2:
                buffer[pos] = (byte) ((bit << BYTE_SIZE-3) | buffer[pos]);
                bitCount++;
                break;
            case 3:
                buffer[pos] = (byte) ((bit << 4) | buffer[pos]);
                bitCount++;
                break;
            case 4:
                buffer[pos] = (byte) ((bit << 3) | buffer[pos]);
                bitCount++;
                break;
            case BYTE_SIZE-3:
                buffer[pos] = (byte) ((bit << 2) | buffer[pos]);
                bitCount++;
                break;
            case BYTE_SIZE-2:
                buffer[pos] = (byte) ((bit << 1) | buffer[pos]);
                bitCount++;
                break;
            case BYTE_SIZE-1:
                buffer[pos] = (byte) (bit | buffer[pos]);
                bitCount++;
                break;
        }
    }

    /**
     * Method flushes buffered output stream
     *
     * @throws IOException IO exception
     */
    public void flush() throws IOException {
        flushBuffer();
        out.flush();
    }

    /**
     * Method sends buffer array to be read to underlying stream
     *
     * @throws IOException some exception
     */
    private void flushBuffer() throws IOException {
        if (count > 0) {
            out.write(buffer);
            count = 0;
        }
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
