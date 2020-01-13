package chapter2.part5.myjdkproxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GPClassLoader extends ClassLoader{

    private File classPathFile;
    
    public GPClassLoader() {
        String classPath = GPClassLoader.class.getResource("").getPath();
        // /D:/eclipse-jee-2018-09-win32-x86_64/spring5/springtheory/target/classes/chapter2/part5/myjdkproxy/
        //System.out.println(classPath);
        this.classPathFile = new File(classPath);
    }
    
    protected Class<?> findClass(String name) throws ClassNotFoundException{
        
        //获得类名
        String className = GPClassLoader.class.getPackage().getName()+ "." + name;
        
        if(classPathFile != null) {
            File classFile = new File(classPathFile,name.replaceAll("\\.", "/") + ".class");
            if(classFile.exists()) {
                FileInputStream in = null;
                ByteArrayOutputStream out = null;
                
                try {
                    in = new FileInputStream(classFile);
                    out = new ByteArrayOutputStream();
                    byte [] buff = new byte[1024];
                    
                    int len;
                    while((len = in.read(buff)) != -1) {
                        out.write(buff, 0, len);
                    }
                    
                    return defineClass(className,out.toByteArray(),0,out.size());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally {
                    if(null != in) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    
                    if(null != out) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
    
}
