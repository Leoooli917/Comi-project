package net.qihoo.corp.ms.umapp.common.util.safety;

import java.io.*;

/**
 * @author wangchuanhai
 * @version 1.0.0
 * @PackageName: net.qihoo.corp.ms.umapp.common.util.safety
 * @ClassName: MsUmappTextEncodeUtil
 * @Description:
 * @date 2022-10-11 17:34:34
 */
public class MsUmappTextEncodeUtil {

    static final String HEX_DIGITS = "0123456789ABCDEF";

    private MsUmappTextEncodeUtil() {
    }

    public static String urlEncode(byte[] rs) {
        StringBuffer result = new StringBuffer(rs.length * 2);
        for (int i = 0; i < rs.length; i++) {
            char c = (char) rs[i];
            switch (c) {
                case '_':
                case '.':
                case '*':
                case '-':
                case '/':
                    result.append(c);
                    break;
                case ' ':
                    result.append('+');
                    break;
                default:
                    if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                        result.append(c);
                    } else {
                        result.append('%');
                        result.append(HEX_DIGITS.charAt((c & 0xF0) >> 4));
                        result.append(HEX_DIGITS.charAt(c & 0x0F));
                    }
            }
        } // for
        return result.toString();
    }

    public static String urlDecode(byte[] bytes, String encoding)
            throws UnsupportedEncodingException,
            IllegalArgumentException {
        if (bytes == null) {
            return null;
        }
        byte[] decodeBytes = new byte[bytes.length];
        int decodedByteCount = 0;
        try {
            for (int count = 0; count < bytes.length; count++) {
                switch (bytes[count]) {
                    case '+':
                        decodeBytes[decodedByteCount++] = (byte) ' ';
                        break;
                    case '%':
                        decodeBytes[decodedByteCount++] = (byte) ((HEX_DIGITS.indexOf(bytes[++count]) << 4) + (HEX_DIGITS.indexOf(bytes[++count])));
                        break;
                    default:
                        decodeBytes[decodedByteCount++] = bytes[count];
                }
            }
        } catch (IndexOutOfBoundsException ae) {
            throw new IllegalArgumentException("Malformed UTF-8 string?");
        }
        String processedPageName = null;
        try {
            processedPageName = new String(decodeBytes, 0, decodedByteCount, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedEncodingException("UTF-8 encoding not supported on this platform");
        }
        return processedPageName;
    }

    public static String urlEncodeUTF8(String text) throws Exception {
        if (text == null) {
            return "";
        }
        byte[] rs;
        try {
            rs = text.getBytes("UTF-8");
            return urlEncode(rs);
        } catch (UnsupportedEncodingException e) {
            throw new Exception("UTF-8 not supported!?!");
        }
    }

    public static String urlDecodeUTF8(String utf8) throws Exception {
        String rs = null;
        if (utf8 == null) return null;
        try {
            rs = urlDecode(utf8.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new Exception("UTF-8 or ISO-8859-1 not supported!?!");
        }
        return rs;
    }

    public static String urlEncode(String data, String encoding) throws Exception {
        // Presumably, the same caveats apply as in FileSystemProvider.
        // Don't see why it would be horribly kludgy, though.
        if ("UTF-8".equals(encoding)) {
            return MsUmappTextEncodeUtil.urlEncodeUTF8(data);
        }
        try {
            return MsUmappTextEncodeUtil.urlEncode(data.getBytes(encoding));
        } catch (UnsupportedEncodingException uee) {
            throw new Exception("Could not encode String into" + encoding);
        }
    }

    public static String urlDecode(String data, String encoding) throws Exception {
        if ("UTF-8".equals(encoding)) {
            return MsUmappTextEncodeUtil.urlDecodeUTF8(data);
        }
        try {
            return MsUmappTextEncodeUtil.urlDecode(data.getBytes(encoding), encoding);
        } catch (UnsupportedEncodingException uee) {
            throw new Exception("Could not decode String into" + encoding);
        }

    }

    public static byte[] fileGetContent(String fileName) {
        try {
            File file = new File(fileName);
            Long fileLength = file.length();
            System.out.println("fileLength length=" + fileLength);
            byte[] fileContent = new byte[fileLength.intValue()];
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
            System.out.println("byte[] length=" + fileContent.length);
            return fileContent;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
