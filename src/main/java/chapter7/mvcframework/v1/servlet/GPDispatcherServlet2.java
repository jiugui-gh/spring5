package chapter7.mvcframework.v1.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter7.mvcframework.annotation.GPAutowired;
import chapter7.mvcframework.annotation.GPController;
import chapter7.mvcframework.annotation.GPRequestMapping;
import chapter7.mvcframework.annotation.GPRequestParam;
import chapter7.mvcframework.annotation.GPService;

/**
 * Servlet implementation class GPDispatcherServlet2
 */
public class GPDispatcherServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// 保存 application.properties配置文件中的内容
	private Properties contextConfig = new Properties();
	// 保存扫描的所有的类名
	private List<String> classNames = new ArrayList<String>();
	// 传说的ioc容器,我们来揭开他的神秘面纱
	// 为了简化程序，暂时不考虑ConcurrentHashMap
	// 主要还是关注设计思想和原理
	private Map<String,Object> ioc = new HashMap<String,Object>();
	// 保存url和method的对应关系
	private Map<String,Method> handlerMapping = new HashMap<String,Method>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPDispatcherServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    try {
            doDispatch(request,response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response.getWriter().write("500 Exection,Detail : " + Arrays.toString(e.getStackTrace()));
        }
	}

	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // TODO Auto-generated method stub
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        if(!this.handlerMapping.containsKey(url)) {
            resp.getWriter().write("404 not Fount");
            return ;
        }
        
        Method method = this.handlerMapping.get(url);
        // 第一个参数：方法所在的实例
        // 第二个参数：调用时所需要的实参
        Map<String, String[]> params = req.getParameterMap();
        // 获取方法形参列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        // 保存请求的url参数列表
        Map<String, String[]> parameterMap = req.getParameterMap();
        //保存赋值参数的位置
        Object[] paramValues = new Object[parameterTypes.length];
        //根据参数位置动态赋值
        for(int i = 0; i < paramValues.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if(parameterType == HttpServletRequest.class) {
                paramValues[i] = req;
                continue;
            }else if(parameterType == HttpServletResponse.class) {
                paramValues[i] = resp;
                continue;
            }else if(parameterType == String.class){
                
                //提取方法中加了注解的参数
                Annotation[][] pa = method.getParameterAnnotations();
               /* for(int j = 0; j < pa.length; j++) {
                    for(Annotation a : pa[j])
                }*/
                for(Annotation a : pa[i]) {
                    if(a instanceof GPRequestParam) {
                        String paramName = ((GPRequestParam)a).value();
                        if(!"".equals(paramName.trim())) {
                            String value = Arrays.toString(parameterMap.get(paramName)).replaceAll("\\[|\\]", "").replaceAll("\\s", ",");
                            paramValues[i] = value;
                        }
                            
                    }
                }
            }else if(parameterType == Integer.class) {
                Annotation[][] pa = method.getParameterAnnotations();
                for(Annotation a : pa[i]) {
                    if(a instanceof GPRequestParam) {
                        String paramName = ((GPRequestParam)a).value();
                        if(!"".equals(paramName.trim())) {
                            String value = Arrays.toString(parameterMap.get(paramName)).replaceAll("\\[|\\]", "").replaceAll("\\s", ",");
                            paramValues[i] = Integer.valueOf(value);
                        }
                            
                    }
                }
            }
        }
        
        // 投机取巧的方式
        // 通过反射获得method所在的class,获得class之后还要获得class的名称
        // 再调用tolowerFirstCase 获得beanName
        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        method.invoke(ioc.get(beanName), paramValues);
	}

    @Override
	public void init(ServletConfig config) throws ServletException {
	    // TODO Auto-generated method stub
	    // 1.加载配置文件
	    doLoadConfig(config.getInitParameter("contextConfigLocation"));
	    
	    // 2.扫描相关类
	    doScanner(contextConfig.getProperty("scanPackage"));
	    
	    // 3.初始化扫描到的类，并将它们放入ioc容器中
	    doInstance();
	    
	    // 4.完成依赖注入
	    doAutowired();
	    
	    // 5.初始化HandlerMapping
	    initHandlerMapping();
	    
	    System.out.println("GP spring framework is init");
	}

	// 实现url和method的一对一关系
    private void initHandlerMapping() {
        // TODO Auto-generated method stub
        if(ioc.isEmpty()) {
            return;
        }
        
        for(Entry<String, Object> entry : ioc.entrySet()) {

            Class<? extends Object> clazz = entry.getValue().getClass();
            if(!clazz.isAnnotationPresent(GPController.class)) {
                continue;
            }
            
            String baseUrl = "";
            if(clazz.isAnnotationPresent(GPRequestMapping.class)) {
                GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                baseUrl = requestMapping.value();
            }
            
            // 默认获取所有的public类型的方法
            for(Method method : clazz.getMethods()) {
                if(!method.isAnnotationPresent(GPRequestMapping.class)) {
                    continue;
                }
                GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
                String url = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/") ;
                handlerMapping.put(url, method);
                System.out.println("Mapped :" + url + "," + method);
            }
        }
    }

    private void doAutowired() {
        // TODO Auto-generated method stub
        if(ioc.isEmpty()) {
            return;
        }
        
        for(Entry<String, Object> entry : ioc.entrySet()) {
            // 获取所有字段，包括private、protected、default类型
            // 正常来说,普通的oop变成只能获得public类型的字段
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for(Field field : fields) {
                if(field.isAnnotationPresent(GPAutowired.class)) {
                    GPAutowired autowired = field.getAnnotation(GPAutowired.class);
                    
                    // 如果用户没有自定义beanName，默认就根据类型注入
                    String beanName = autowired.value();
                    if("".equals(beanName)) {
                       // beanName = toLowerFirstCase(field.getType().getSimpleName());
                        beanName = field.getType().getName();
                    }
                     
                    //如果是public以外的类型，只要加了@Autowaired注解都要强制赋值
                    //反射中叫做暴力访问
                    field.setAccessible(true);
                    
                    try {
                        //用反射机制动态给字段赋值
                        field.set(entry.getValue(), ioc.get(beanName));
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void doInstance() {
        // TODO Auto-generated method stub
        // 初始化，为DI做准备
        if(classNames.isEmpty()) {
            return;
        }
        
        try {
            for(String className : classNames) {
                Class<?> clazz = Class.forName(className);
                
                // 什么样的类才需要初始化呢？
                // 加注解的类才需要初始化,怎么判断?
                // 为了简化代码逻辑，主要提花设计思想，只用@Controllerh和@Service举例
                if(clazz.isAnnotationPresent(GPController.class)) {
                    Object instance = clazz.newInstance();
                    //spring 默认类名首字母小写
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName, instance);
                }else if(clazz.isAnnotationPresent(GPService.class)) {
                    // 1.自定义beanName
                    GPService service = clazz.getAnnotation(GPService.class);
                    String beanName = service.value();
                    // 2.默认类名首字母小写
                    if("".equals(beanName.trim())) {
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }
                    
                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);
                    
                    // 3.根据类型自动复制,这是投机取巧的方式
                    for(Class<?> i : clazz.getInterfaces()) {
                        if(ioc.containsKey(i.getName())) {
                            throw new Exception("The " + i.getName() + "is Exists!!");
                        }
                        ioc.put(i.getName(), instance);
                    }
                }else {
                    continue;
                }
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String toLowerFirstCase(String simpleName) {
        // TODO Auto-generated method stub
        char []chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private void doScanner(String scanPackage) {
        // TODO Auto-generated method stub
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        for(File file : classPath.listFiles()) {
            if(file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            }else {
                if(!file.getName().endsWith(".class")) {
                    continue;
                }else {
                    String className = scanPackage + "." + file.getName().replace(".class", "");
                    classNames.add(className);
                }
            }
            
        }
    }

    private void doLoadConfig(String initParameter) {
        // TODO Auto-generated method stub
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(initParameter);
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
          if(null != fis) {
              try {
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
          }
        }
    }
    
   /* public static void main(String[] args) {
        String url = "/java//jdk//a//abc";
        url = url.replaceAll("/+", "/");
        
        System.out.println(url);
    }*/
}
