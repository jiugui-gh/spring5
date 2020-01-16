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
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map.Entry;

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
 * Servlet implementation class GPDispatcherServlet3
 */
public class GPDispatcherServlet3 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // 保存 application.properties配置文件中的内容
    private Properties contextConfig = new Properties();
    // 保存扫描的所有的类名
    private List<String> classNames = new ArrayList<String>();
    // 传说的ioc容器,我们来揭开他的神秘面纱
    // 为了简化程序，暂时不考虑ConcurrentHashMap
    // 主要还是关注设计思想和原理
    private Map<String,Object> ioc = new HashMap<String,Object>();
   
    // 保存所有的url和method的映射关系
    private List<Handler> handlerMapping = new ArrayList<Handler>();
    
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPDispatcherServlet3() {
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
        Handler handler = getHandler(req);
        if(handler == null) {
            resp.getWriter().write("404 not Fount");
            return ;
        }
        
        Method method = handler.method;
        // 请求的参数
        Map<String, String[]> params = req.getParameterMap();
        // 获取方法形参列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        //保存赋值参数的位置
        Object[] paramValues = new Object[parameterTypes.length];
       
        for(Entry<String,String[]> param : params.entrySet()) {
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\}", "").replaceAll("\\s", ",");
            
            if(!handler.paramIndexMapping.containsKey(param.getKey())) {
                continue;
            }
            
            int index = handler.paramIndexMapping.get(param.getKey());
            paramValues[index] = convert(parameterTypes[index],value);
        }
        
        if(handler.paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            int index = handler.paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[index] = req;
        }
        
        if(handler.paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
            int index = handler.paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[index] = resp;
        }
    }

    private Object convert(Class<?> type, String value) {
        // TODO Auto-generated method stub
        if(type == Integer.class){
            return Integer.valueOf(value);
        }//如果这里有其他的类型  则使用策略模式避免多个if else
        return value;
    }

    private Handler getHandler(HttpServletRequest req) {
        // TODO Auto-generated method stub
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for(Handler handler : handlerMapping) {
            // 正则匹配
            Matcher matcher = handler.pattern.matcher(url);
            // 如果没有匹配上，继续匹配下一个
            if(!matcher.matches()) {
                continue;
            }
            return handler;
        }
        return null;
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
                // 支持正则表达式
                Pattern pattern = Pattern.compile(url);
                handlerMapping.add(new Handler(pattern,entry.getValue(),method));
                // handlerMapping.put(url, method);
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
    
    private class Handler{
        protected Object controler; // 保存方法对应的实例
        protected Method method; // 保存映射的方法
        protected Pattern pattern;
        protected Map<String,Integer> paramIndexMapping; //参数顺序
        
        public Handler(Pattern pattern,Object controler,Method method) {
            this.pattern = pattern;
            this.controler = controler;
            this.method = method;
            paramIndexMapping = new HashMap<String,Integer>();
            putParamIndexMapping();
        }

        private void putParamIndexMapping() {
            // TODO Auto-generated method stub
            // 提取方法中加了注解的参数
            Annotation[][] pa = method.getParameterAnnotations();
            for(int i = 0; i < pa.length; i++) {
                for(Annotation a : pa[i]) {
                    if(a.annotationType() == GPRequestParam.class) {
                        GPRequestParam requestParam = (GPRequestParam)a;
                        String paramName = requestParam.value();
                        paramIndexMapping.put(paramName, i);
                    }
                }
                
            }
            // 提取方法中request和response参数
            Class<?>[] paramsType = method.getParameterTypes();
            for(int i = 0; i < paramsType.length; i++) {
                Class<?> type = paramsType[i];
                if(type == HttpServletRequest.class || type == HttpServletResponse.class) {
                    paramIndexMapping.put(type.getName(), i);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        String str =  "/demo/query/\\S";
        String test = "/demo/query/a";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(test);
        System.out.println(matcher.matches());
    }
}
