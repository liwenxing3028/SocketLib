package com.dydl.socketlib.utils;

import android.annotation.SuppressLint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static String hexStr = "0123456789ABCDEF";
    private static String[] binaryArray =
            {"0000", "0001", "0010", "0011",
                    "0100", "0101", "0110", "0111",
                    "1000", "1001", "1010", "1011",
                    "1100", "1101", "1110", "1111"};
    public static String mOfficeHtmlPath;

    public static boolean isIp(String str) {
        return str
                .matches("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
    }

    @SuppressLint("NewApi")
    public static String toGbk(String str) {
        try {
            return new String(str.getBytes(Charset.forName("gbk")), "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toUtf(String str) {
        try {
            return new String(str.getBytes(Charset.forName("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String utf8UrlEncode(String str) {
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {
                sb.append(String.format("%%%02X", bytes[i]));
            } else {
                sb.append((char) bytes[i]);
            }
        }

        return sb.toString();
    }

    public static String getSuffix(String fileName) {
        File f = new File(fileName);
        int idx = f.getName().lastIndexOf(".");
        if (idx > 0) {
            return f.getName().substring(idx + 1);
        }

        return "";
    }

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 转变成小写
     *
     * @param str
     * @return
     */
    public static String toLowerCase(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') {
                sb.append((char) (str.charAt(i) | 32));
            } else {
                sb.append(str.charAt(i));
            }
        }

        return sb.toString();
    }

    /**
     * 转变成大写
     *
     * @param str
     * @return
     */
    public static String toUpperCase(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                sb.append((char) (str.charAt(i) & ~32));
            } else {
                sb.append(str.charAt(i));
            }
        }

        return sb.toString();
    }

    public static String fuzzyMatchingAndToLowerCase(String appName) {
        appName = appName.replace("*", "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < appName.length(); ++i) {
            if (appName.charAt(i) >= 'A' && appName.charAt(i) <= 'Z') {
                sb.append((char) (appName.charAt(i) | 32));
            } else {
                sb.append(appName.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 转换后缀为html
     *
     * @param path
     * @param endName
     * @return
     */
    public static boolean getConvertOfficeToHtml(String path, String endName) {
        mOfficeHtmlPath = path.replace(endName, "html");
        File file = new File(mOfficeHtmlPath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * ArrayList转String 数组
     */
    public static String[] convertToStringArray(ArrayList<String> list) {
        String[] covertedArray = new String[list.size()];
        covertedArray = list.toArray(covertedArray); // (String[])
        return covertedArray;
    }

    /**
     * 判断是否为数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    // byte[] 转16进制字符串
    public static String bytes2Hex(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }

        char[] res = new char[src.length * 2]; // 每个byte对应两个字符
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int i = 0, j = 0; i < src.length; i++) {
            res[j++] = hexDigits[src[i] >> 4 & 0x0f]; // 先存byte的高4位
            res[j++] = hexDigits[src[i] & 0x0f]; // 再存byte的低4位
        }
        return new String(res);
    }

    /**
     * 将16进制ascii码转换为字符串
     */
    public static String toStringHex1(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "ASCII");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 子域占位，补全位数，不足补" "
     *
     * @param value 子域初值
     * @param size  设置长度
     * @return 子域value
     */
    public static String getValueBit(String value, int size) {
        StringBuilder builder = new StringBuilder();
        builder.append(value);
        int length = value.length();
        if (length < size) {
            int loopTimes = size - length;
            for (int i = 0; i < loopTimes; i++) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    /**
     * 合并byte数组
     */
    public static byte[] unitByteArray(byte[] byte1, byte[] byte2) {
        byte[] unitByte = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, unitByte, 0, byte1.length);
        System.arraycopy(byte2, 0, unitByte, byte1.length, byte2.length);
        return unitByte;
    }

    /**
     * @param bytes
     * @return 将二进制转换为十六进制字符输出
     */
    public static String binaryToHexString(byte[] bytes) {
        String result = "";
        String hex = "";
        for (int i = 0; i < bytes.length; i++) {
            //字节高4位
            hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
            //字节低4位
            hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
            result += hex;
        }
        return result;
    }

    /**
     * @param hexString
     * @return 将十六进制转换为字节数组
     */
    public static byte[] hexStringToBinary(String hexString) {
        //hexString的长度对2取整，作为bytes的长度
        int len = hexString.length() / 2;
        byte[] bytes = new byte[len];
        byte high = 0;//字节高四位
        byte low = 0;//字节低四位
        for (int i = 0; i < len; i++) {
            //右移四位得到高位
            high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
            low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
            bytes[i] = (byte) (high | low);//高地位做或运算
        }
        return bytes;
    }

    private static byte CharToByte(char c) {
        return (byte) hexStr.indexOf(c);
    }

    public static byte[] strHexToBytes(String str) {
        if (str != null && !str.equals("")) {
            str = str.toUpperCase();
            int length = str.length() / 2;
            char[] hexChars = str.toCharArray();
            byte[] d = new byte[length];

            for (int i = 0; i < length; ++i) {
                int pos = i * 2;
                d[i] = (byte) (CharToByte(hexChars[pos]) << 4 | CharToByte(hexChars[pos + 1]));
            }

            return d;
        } else {
            return null;
        }
    }

    public static String bytesToHexStr(byte[] bytes, int aLen) {
        String lstrTem;
        String lstrdata = "";
        for (int i = 0; i < aLen; ++i) {
            int var7 = bytes[i] & 255;
            lstrTem = Integer.toHexString(var7).toUpperCase();
            if (lstrTem.length() == 1) {
                lstrTem = "0" + lstrTem;
            }
            lstrdata = lstrdata + lstrTem;
        }
        return lstrdata;
    }

    /**
     * 判断是否是一个中文汉字
     *
     * @param c 字符
     * @return true表示是中文汉字，false表示是英文字母
     * @throws UnsupportedEncodingException 使用了JAVA不支持的编码格式
     */
    public static boolean isChineseChar(char c)
            throws UnsupportedEncodingException {
        // 如果字节数大于1，是汉字
        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了
        return String.valueOf(c).getBytes("UTF-8").length > 1;
    }

    /**
     * 按字节截取字符串（UTF-8）,GBK乱码
     *
     * @param orignal 原始字符串
     * @param start   起始位置
     * @param count   截取位数
     * @return 截取后的字符串
     * @throws UnsupportedEncodingException 使用了JAVA不支持的编码格式
     */
    public static String parse8583Str(String orignal, int start, int count)
            throws UnsupportedEncodingException {
        // 原始字符不为null，也不是空字符串
        if (StringUtil.isEmpty(orignal) || orignal.length() < start) {
            return "";
        } else {
            // 将原始字符串转换为GBK编码格式
            orignal = new String(orignal.getBytes(), "UTF-8");
            // 要截取的字节数大于0，且小于原始字符串的字节数
            if (count > 0 && count < orignal.getBytes("UTF-8").length) {
                StringBuffer buff = new StringBuffer();
                char c;
                for (int i = 0; i < start; i++) {//处理起始位置
                    c = orignal.charAt(i);
                    if (isChineseChar(c)) {
                        // 遇到中文汉字，截取字节起始位置减1
                        --start;
                    }
                }

                for (int i = start; i < start + count; i++) {//处理截取总长度
                    c = orignal.charAt(i);
                    if (isChineseChar(c)) {
                        // 遇到中文汉字，截取字节总数减1
                        --count;
                    }
                }
                buff.append(orignal, start, start + count);
                return new String(buff.toString().getBytes(), "UTF-8");
            }
        }
        return orignal;
    }

    public static String strHexToStr(String HexStr, String CharSet) {
        String ret = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream(HexStr.length() / 2);
        for (int i = 0; i < HexStr.length(); i += 2) {
            int a = "0123456789ABCDEF".indexOf(HexStr.charAt(i)) << 4 | "0123456789ABCDEF".indexOf(HexStr.charAt(i + 1));
            baos.write(a);
        }
        try {
            ret = new String(baos.toByteArray(), CharSet);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static byte[] strToBytes(String str) {
        byte[] buf = new byte[str.length()];
        for (int i = 0; i < str.length(); i++) {
            buf[i] = (byte) ((byte) str.charAt(i) & 0xFF);
        }
        return buf;
    }

    public static String byteToString(byte[] b) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            stringBuffer.append((char) b[i]);
        }
        return stringBuffer.toString();
    }

    /**
     * 获取含有中文数字的报文长度，gbk中文兩個字节，utf-8中文三个字节
     *
     * @param str 源字符串
     */
    public static int getLengthContainsCn(String str, String charset) {
        int strLength = str.length();
        try {
            String orignal = new String(str.getBytes(), charset);
            // 要截取的字节数大于0，且小于原始字符串的字节数
            for (int i = 0; i < orignal.length(); i++) {
                char c = orignal.charAt(i);
                if (isChineseChar(c)) {
                    // 遇到中文汉字，总长度加1
                    if(charset.equalsIgnoreCase("UTF-8")){
                        strLength += 2;
                    }
                    else {
                    strLength++;}
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strLength;
    }

    /**
     * 获取含有中文数字的报文长度
     *
     * @param str 源字符串
     */
    public static int getLengthContainsCn(String str) {
        int strLength = str.length();
        try {
            String orignal = new String(str.getBytes(), "UTF-8");
            // 要截取的字节数大于0，且小于原始字符串的字节数
            for (int i = 0; i < orignal.length(); i++) {
                char c = orignal.charAt(i);
                if (isChineseChar(c)) {
                    // 遇到中文汉字，总长度加1
                    strLength++;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strLength;
    }
    /**
     * 报文长度补位“0”
     *
     * @param text 报文内容长度 字符串
     * @param ach  补位 内容，此处0
     * @param aLen 补位后长度
     * @param aDir 1前补位，2后补位
     * @return 补位后的内容
     */
    public static String complementBit(String text, String ach, int aLen, int aDir) {
        int tlen = 0;
        try {
            tlen = text.getBytes("GBK").length;
        } catch (UnsupportedEncodingException var7) {
            var7.printStackTrace();
        }
        if (aLen == 0) {
            return text;
        } else if (tlen > aLen) {
            return getSubString(text, 0, aLen);
        } else {
            for (int i = tlen; i < aLen; ++i) {
                if (aDir == 1) {
                    text = ach + text;
                } else if (aDir == 2) {
                    text = text + ach;
                }
            }
            return text;
        }
    }

    private static String getSubString(String str, int pstart, int pend) {
        String resu = "";
        int count1 = 0;
        char[] temp = new char[str.length()];
        str.getChars(0, str.length(), temp, 0);
        boolean[] bol = new boolean[str.length()];

        int temSt;
        for (temSt = 0; temSt < temp.length; ++temSt) {
            bol[temSt] = false;
            if (temp[temSt] > 127) {
                ++count1;
                bol[temSt] = true;
            }
        }
        if (pstart > str.length() + count1) {
            resu = null;
        }
        if (pstart > pend) {
            resu = null;
        }
        int var14;
        if (pstart < 1) {
            var14 = 0;
        } else {
            var14 = pstart - 1;
        }
        int var15;
        if (pend > str.length() + count1) {
            var15 = str.length() + count1;
        } else {
            var15 = pend;
        }
        if (resu != null) {
            int temEd;
            int cont;
            if (var14 == var15) {
                temSt = 0;
                if (var14 == 0) {
                    if (bol[0]) {
                        resu = null;
                    } else {
                        resu = new String(temp, 0, 1);
                    }
                } else {
                    temEd = var14;

                    for (cont = 0; cont < temEd; ++cont) {
                        if (bol[cont]) {
                            ++temSt;
                        }
                        --temEd;
                    }
                    if (temSt == 0) {
                        if (temp[var14] > 127) {
                            resu = null;
                        } else {
                            resu = new String(temp, var14, 1);
                        }
                    } else if (temp[temEd + 1] > 127) {
                        resu = null;
                    } else {
                        resu = new String(temp, temEd + 1, 1);
                    }
                }
            } else {
                temSt = var14;
                temEd = var15 - 1;
                for (cont = 0; cont < temSt; ++cont) {
                    if (bol[cont]) {
                        --temSt;
                    }
                }
                for (cont = 0; cont < temEd; ++cont) {
                    if (bol[cont]) {
                        --temEd;
                    }
                }
                int i;
                if (bol[temSt]) {
                    cont = 0;
                    for (i = 0; i <= temSt; ++i) {
                        ++cont;
                        if (bol[i]) {
                            ++cont;
                        }
                    }
                    if (pstart == cont) {
                        ++temSt;
                    }
                }
                if (bol[temEd]) {
                    cont = 0;
                    for (i = 0; i <= temEd; ++i) {
                        ++cont;
                        if (bol[i]) {
                            ++cont;
                        }
                    }
                    if (pend < cont) {
                        --temEd;
                    }
                }
                if (temSt == temEd) {
                    resu = new String(temp, temSt, 1);
                } else if (temSt > temEd) {
                    resu = null;
                } else {
                    resu = str.substring(temSt, temEd + 1);
                }
            }
        }
        return resu;
    }

    /**
     * 数据格式：|1&;2&;|
     *
     * @param datas 所需拆分的数据集字符串
     * @return 拆分后个数据集中各数据项集合
     * @Description 拆分包含单个数据集的字符串
     */
    public static ArrayList<String[]> splitMsgData(String datas) {
        if (datas.contains(";")) {
            String[] data = datas.split(";");
            ArrayList<String[]> dataList = new ArrayList<String[]>();
            for (int i = 0; i < data.length; i++) {
                String value = data[i];
                if (value.contains("&")) {
                    String[] d = value.split("&", -1);
                    dataList.add(d);
                }
            }
            return dataList;
        }
        return null;
    }

    /**
     * 数据格式：|1&;2&;|
     *
     * @param datas 所需拆分的数据集字符串
     * @return 拆分后个数据集中各数据项集合
     * @Description 拆分包含单个数据集的字符串
     */
    public static ArrayList<String[]> splitMsgData2(String datas) {
        if (datas.contains(";")) {
            String[] data = datas.split(";;;");
            ArrayList<String[]> dataList = new ArrayList<String[]>();
            for (int i = 0; i < data.length; i++) {
                String value = data[i];
                if (value.contains("&")) {
                    String[] d = value.split("&", -1);
                    dataList.add(d);
                }
            }
            return dataList;
        }
        return null;
    }
}
