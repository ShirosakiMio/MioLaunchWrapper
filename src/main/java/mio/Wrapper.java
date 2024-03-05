package mio;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Wrapper {
    public static void main(String[] args) {
        try {
            boolean debug = Boolean.parseBoolean(System.getProperty("mioWrapper.debug", "false"));
            Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(args[0]);
            Method main = clazz.getDeclaredMethod("main", String[].class);
            main.setAccessible(true);
            String[] copy = Arrays.copyOfRange(args, 1, args.length);
            if (debug) {
                for (String s : copy) {
                    System.out.println("MioWrapperDebug: " + s);
                }
            }
            main.invoke(null, new Object[]{copy});
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
            return "Error/发生错误:" + ex;
        }
    }
}