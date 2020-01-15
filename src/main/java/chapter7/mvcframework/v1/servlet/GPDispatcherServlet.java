package chapter7.mvcframework.v1.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter7.mvcframework.annotation.GPAutowired;
import chapter7.mvcframework.annotation.GPController;
import chapter7.mvcframework.annotation.GPRequestMapping;
import chapter7.mvcframework.annotation.GPService;

/**
 * Servlet implementation class GPDispatcherServlet
 */
public class GPDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<String,Object> mapping = new HashMap<String,Object>();
    /**
     * Default constructor. 
     */
    public GPDispatcherServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    try {
            doDispatch(request,response);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // TODO Auto-generated method stub
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        if(!this.mapping.containsKey(url)) {
            resp.getWriter().write("404 NotFound!!");
            return ;
        }
        Method method = (Method)this.mapping.get(url);
        Map<String, String[]> params = req.getParameterMap();
        String name = method.getDeclaringClass().getName();
        Object object = this.mapping.get(name);
        method.invoke(object,new Object[] {req,resp,params.get("name")[0]});
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        InputStream is = null;
        try {
            Properties configContext = new Properties();
            is = this.getClass().getClassLoader().getResourceAsStream(config.getInitParameter("contextConfigLocation"));
            configContext.load(is);
            String scanPackage = configContext.getProperty("scanPackage");
            //扫描所有包
            doScanner(scanPackage);
            Map<String ,Object> mapping2 = new HashMap<String,Object>();
            for(String className : mapping.keySet()) {
                if(!className.contains(".")) {
                    continue;
                }
                Class<?> clazz = Class.forName(className);
                //是否有GPController注解
                if(clazz.isAnnotationPresent(GPController.class)) {
                    mapping2.put(className, clazz.newInstance());
                    String baseUrl = "";
                    if(clazz.isAnnotationPresent(GPRequestMapping.class)) {
                        GPRequestMapping requestMappint = clazz.getAnnotation(GPRequestMapping.class);
                        baseUrl = requestMappint.value();
                    }
                    Method[] methods = clazz.getMethods();
                    for(Method method : methods) {
                        if(!method.isAnnotationPresent(GPRequestMapping.class)) {
                            continue;
                        }
                        GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
                        String url = (baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
                        // key为url  value 是Method
                        mapping2.put(url, method);
                        System.out.println("Mapped " + url + "," + method);
                    }
                }else if(clazz.isAnnotationPresent(GPService.class)) {
                    GPService service = clazz.getAnnotation(GPService.class);
                    String beanName = service.value();
                    if("".equals(beanName)) {
                        beanName = clazz.getName();
                    }
                    Object instance = clazz.newInstance();
                    
                    mapping2.put(beanName, instance);
                    for(Class<?> i :clazz.getInterfaces()) {
                        //key是接口，value是实现类       如果多个类实现同一个接口可能出问题
                        mapping2.put(i.getName(), instance);
                    }
                }else {
                    continue;
                }
            }
            
            mapping.putAll(mapping2);
            mapping2.clear();
            //依赖注入DI
            for(Object object : mapping.values()) {
                if(object == null) {
                    continue;
                }
                Class<?> clazz =  object.getClass();
                if(clazz.isAnnotationPresent(GPController.class)) {
                    Field[] fields = clazz.getDeclaredFields();
                    for(Field field : fields) {
                        if(!field.isAnnotationPresent(GPAutowired.class)) {
                            continue;
                        }
                        GPAutowired autowired = field.getAnnotation(GPAutowired.class);
                        String beanName = autowired.value();
                        if("".equals(beanName)) {
                            beanName = field.getType().getName();
                            field.setAccessible(true);
                            field.set(mapping.get(clazz.getName()), mapping.get(beanName));
                        }
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
    }

    private void doScanner(String scanPackage) {
        // TODO Auto-generated method stub
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for(File file : classDir.listFiles()) {
            if(file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            }else {
                if(!file.getName().endsWith(".class")) {
                    continue;
                }
                String clazzName = scanPackage + "." + file.getName().replace(".class", "");
                mapping.put(clazzName, null);
            }
        }
        
    }
}
