package com.mdm.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.NetworkInterface;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 构建唯一实体ID,参考Mongdb objectid简化实现<br>
 * 用法：获取全局唯一ID：<code>BeanId.generateUUId()</code>
 */
public class BeanId {
    private final static Log log = LogFactory.getLog(BeanId.class);
    private static final int LOW_ORDER_THREE_BYTES = 0x00ffffff;
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f'};
    private final static int MAC;
    private final static short PROC;
    private final static AtomicInteger NEXT_COUNTER = new AtomicInteger(new SecureRandom().nextInt());
    private final int timestamp;
    private final int machineIdentifier;
    private final short processIdentifier;
    private final int counter;

    static {
        try {
            MAC = createMachineIdentifier();
            PROC = createProcessIdentifier();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BeanId() {
        timestamp = (int) (System.currentTimeMillis() / 1000);
        machineIdentifier = MAC;
        processIdentifier = PROC;
        counter = NEXT_COUNTER.getAndIncrement();
    }

    public BeanId(final String id) {
        ByteBuffer buffer = ByteBuffer.wrap(parseHexString(id));
        timestamp = makeInt(buffer.get(), buffer.get(), buffer.get(), buffer.get());
        machineIdentifier = makeInt((byte) 0, buffer.get(), buffer.get(), buffer.get());
        processIdentifier = (short) makeInt((byte) 0, (byte) 0, buffer.get(), buffer.get());
        counter = makeInt((byte) 0, buffer.get(), buffer.get(), buffer.get());
    }

    public void putToByteBuffer(final ByteBuffer buffer) {
        buffer.put(int3(timestamp));
        buffer.put(int2(timestamp));
        buffer.put(int1(timestamp));
        buffer.put(int0(timestamp));
        buffer.put(int2(machineIdentifier));
        buffer.put(int1(machineIdentifier));
        buffer.put(int0(machineIdentifier));
        buffer.put(short1(processIdentifier));
        buffer.put(short0(processIdentifier));
        buffer.put(int2(counter));
        buffer.put(int1(counter));
        buffer.put(int0(counter));
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getMachineIdentifier() {
        return machineIdentifier;
    }

    public short getProcessIdentifier() {
        return processIdentifier;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return toHexString();
    }

    public String toHexString() {
        char[] chars = new char[24];
        int i = 0;
        ByteBuffer buffer = ByteBuffer.allocate(12);
        putToByteBuffer(buffer);
        for (byte b : buffer.array()) {
            chars[i++] = HEX_CHARS[b >> 4 & 0xF];
            chars[i++] = HEX_CHARS[b & 0xF];
        }
        return new String(chars);
    }

    public static String generateUUId() throws Exception {
        return new BeanId().toString();
    }

    /**
     * 获取机器标识
     *
     * @return
     */
    private static int createMachineIdentifier() {
        // build a 2-byte machine piece based on NICs info
        int machinePiece;
        try {
            StringBuilder sb = new StringBuilder();
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface ni = e.nextElement();
                sb.append(ni.toString());
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    ByteBuffer bb = ByteBuffer.wrap(mac);
                    try {
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                        sb.append(bb.getChar());
                    } catch (BufferUnderflowException shortHardwareAddressException) { // NOPMD
                        // mac with less than 6 bytes. continue
                    }
                }
            }
            machinePiece = sb.toString().hashCode();
        } catch (Throwable t) {
            // exception sometimes happens with IBM JVM, use random
            machinePiece = (new SecureRandom().nextInt());
            log.warn("Failed to get machine identifier from network interface, using random number instead", t);
        }
        machinePiece = machinePiece & LOW_ORDER_THREE_BYTES;
        return machinePiece;
    }

    /**
     * 获得进程标识
     *
     * @return
     */
    private static short createProcessIdentifier() {
        short processId;
        try {
            String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
            if (processName.contains("@")) {
                processId = (short) Integer.parseInt(processName.substring(0, processName.indexOf('@')));
            } else {
                processId = (short) java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();
            }

        } catch (Throwable t) {
            processId = (short) new SecureRandom().nextInt();
            log.warn("Failed to get process identifier from JMX, using random number instead", t);
        }
        return processId;
    }

    private static byte[] parseHexString(final String s) {
        if (!isValid(s)) {
            throw new IllegalArgumentException("invalid hexadecimal representation of an ObjectId: [" + s + "]");
        }

        byte[] b = new byte[12];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    public static boolean isValid(final String hexString) {
        if (hexString == null) {
            throw new IllegalArgumentException();
        }

        int len = hexString.length();
        if (len != 24) {
            return false;
        }

        for (int i = 0; i < len; i++) {
            char c = hexString.charAt(i);
            if (c >= '0' && c <= '9') {
                continue;
            }
            if (c >= 'a' && c <= 'f') {
                continue;
            }
            if (c >= 'A' && c <= 'F') {
                continue;
            }

            return false;
        }

        return true;
    }

    private static int makeInt(final byte b3, final byte b2, final byte b1, final byte b0) {
        // CHECKSTYLE:OFF
        return (((b3) << 24) | ((b2 & 0xff) << 16) | ((b1 & 0xff) << 8) | ((b0 & 0xff)));
        // CHECKSTYLE:ON
    }

    private static byte int3(final int x) {
        return (byte) (x >> 24);
    }

    private static byte int2(final int x) {
        return (byte) (x >> 16);
    }

    private static byte int1(final int x) {
        return (byte) (x >> 8);
    }

    private static byte int0(final int x) {
        return (byte) (x);
    }

    private static byte short1(final short x) {
        return (byte) (x >> 8);
    }

    private static byte short0(final short x) {
        return (byte) (x);
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            String s = BeanId.generateUUId();
            System.out.println(s + "=" + new BeanId(s).toString() + "=" + new BeanId(s).getProcessIdentifier() + "=" + new BeanId(s).getTimestamp());
            Thread.sleep(500);
        }
    }
}
