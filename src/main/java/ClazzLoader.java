
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ClazzLoader extends ClassLoader {

    private static final String PACKAGE_NAME = "by.iba.di";

    public ClazzLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        if (className.startsWith(PACKAGE_NAME)) {
            String file = className.replace('.', File.separatorChar) + ".class";
            try {
                byte bytes[] = getClassBytes(file);
                Class<?> clazz = defineClass(className, bytes, 0, bytes.length);
                resolveClass(clazz);
                if (clazz.getPackage() == null) {
                    String packageName = className.substring(0, className.lastIndexOf('.'));
                    definePackage(packageName, null, null, null, null, null, null, null);
                }
                return clazz;
            } catch (IOException e) {
                return null;
            }
        } else {
            return super.loadClass(className);
        }
    }

    private byte[] getClassBytes(String className) throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(className);
        byte bytes[] = new byte[stream.available()];
        DataInputStream inputStream = new DataInputStream(stream);
        inputStream.readFully(bytes);
        inputStream.close();
        return bytes;
    }


}
