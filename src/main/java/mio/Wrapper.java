package mio;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class Wrapper {
    public static void main(String[] args) {
        try {
            Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(args[0]);
            Method main = clazz.getDeclaredMethod("main", String[].class);
            main.setAccessible(true);
            main.invoke(null, new Object[]{args});
        } catch (Throwable e) {
            System.out.println(getStackTraceInfo(e));
            System.exit(1);
        }
    }

    public static String getStackTraceInfo(Throwable e) {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)) {
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            return sw.toString();
        } catch (Exception ex) {
            return "Error/发生错误:"+ ex;
        }
    }
}