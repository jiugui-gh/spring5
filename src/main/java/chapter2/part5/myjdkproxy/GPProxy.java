package chapter2.part5.myjdkproxy;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

// 用来生成源代码的工具
public class GPProxy {
    
    public static final String LN = "\r\n";
    
    // 返回代理对象
    public static Object newProxyInstance(GPClassLoader classLoader, Class<?>[] interfaces,GPInvocationHandler h) {
        
        try {
            //动态生成源代码.java文件
            String src = generateSrc(interfaces);
            
            //java 文件输出磁盘
            String filePath = GPProxy.class.getResource("").getPath();
            File f = new File(filePath + "$Proxy0.java");
            FileWriter fw = new FileWriter(f);
            
            fw.write(src);
            fw.flush();
            fw.close();
            
            //把生成的.java文件编译成.class 文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> iterable = manager.getJavaFileObjects(f);
            
            CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
            task.call();
            manager.close();
            
            //把编译生成的.class文件加载到JVM中
            Class<?> proxyClass = classLoader.findClass("$Proxy0");
            Constructor<?> c = proxyClass.getConstructor(GPInvocationHandler.class);
            f.delete();
            
            return c.newInstance(h);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }

    private static String generateSrc(Class<?>[] interfaces) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        sb.append("package chapter2.part5.myjdkproxy;").append(LN);
        sb.append("import chapter2.part5.Person;").append(LN);
        sb.append("import java.lang.reflect.*;").append(LN);
        sb.append("public class $Proxy0 implements ");
        sb.append(interfaces[0].getName()).append("{").append(LN);
        sb.append("GPInvocationHandler h;").append(LN);
        sb.append("public $Proxy0(GPInvocationHandler h) {").append(LN);
        sb.append("this.h = h;").append(LN);
        sb.append("}").append(LN);
        
        for (Method m : interfaces[0].getMethods()) {
            Class<?>[] params = m.getParameterTypes();
            
            StringBuffer paramNames = new StringBuffer();
            StringBuffer paramValues = new StringBuffer();
            StringBuffer paramClasses = new StringBuffer();
            
            for(int i = 0; i < params.length; i++) {
                Class<?> clazz = params[i];
                String type = clazz.getName();
                String paramName = toLowerFirstCase(clazz.getSimpleName());
                paramNames.append(type).append(" ").append(paramName);
                paramValues.append(paramName);
                paramClasses.append(clazz.getName()).append(".class");
                
                if(i > 0 && i < params.length -1) {
                    paramNames.append(",");
                    paramClasses.append(",");
                    paramValues.append(",");
                }
                
               
            }
            
            sb.append("public ").append(m.getReturnType().getName()).append(" ").append(m.getName());
            sb.append("(").append(paramNames.toString()).append(")").append(" {").append(LN);
            sb.append("try{").append(LN);
            sb.append("Method m  = ").append(interfaces[0].getName()).append(".class.getMethod(\"").append(m.getName())
                .append("\",new Class[] {").append(paramClasses.toString()).append("});").append(LN);
            sb.append(hasReturnValue(m.getReturnType()) ? "return " : "");
            sb.append(getCaseCode("this.h.invoke(this,m,new Object[]{" +paramValues + "});", m.getReturnType())).append(LN);
            
            sb.append("}catch(Error _ex){}");
            sb.append("catch(Throwable e){").append(LN);
            sb.append("throw new UndeclaredThrowableException(e);").append(LN);
            sb.append("}");
            sb.append(getReturnEmptyCode(m.getReturnType()));
            sb.append("}");
        }
        sb.append("}").append(LN);
        return sb.toString();
    }

    

    private static Map<Class<?>,Class<?>>mappings = new HashMap<Class<?>,Class<?>>();
    static {
        mappings.put(int.class, Integer.class);
    }
    
    private static Object getReturnEmptyCode(Class<?> returnClass) {
        // TODO Auto-generated method stub
        if(mappings.containsKey(returnClass)) {
            return "return 0;";
        }else if(returnClass == void.class) {
            return "";
        }else {
            return "return null;";
        }
    }
    
    private static String getCaseCode(String code, Class<?> returnClass) {
        // TODO Auto-generated method stub
        if(mappings.containsKey(returnClass)) {
            return "((" + mappings.get(returnClass).getName() + ")" + code + ")." + returnClass.getSimpleName() + "Value()"; 
        }
        return code;
    }

    private static boolean hasReturnValue(Class<?> clazz) {
        // TODO Auto-generated method stub
        return clazz != void.class;
    }

    private static String toLowerFirstCase(String src) {
        // TODO Auto-generated method stub
        char[] chars = src.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
    
    public static void main(String[] args) {
        System.out.println(GPProxy.class.getName());
        System.out.println(GPProxy.class.getSimpleName());
    }
}
