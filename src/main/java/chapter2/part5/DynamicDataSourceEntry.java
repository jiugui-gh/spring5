package chapter2.part5;
// 动态切换数据源
public class DynamicDataSourceEntry {
    // 默认数据源
    public final static String DEFAULT_SOURSE = null;
    public final static ThreadLocal<String> LOCAL = new ThreadLocal<String>();
    private DynamicDataSourceEntry() {};
    
    //清空数据源
    public static void clear() {
    	LOCAL.remove();
    }
    
    //获取正在使用的数据源名字
    public static String get() {
    	return LOCAL.get();
    }
    
    //还原当前切换的数据源
    public static void restore() {
    	LOCAL.set(DEFAULT_SOURSE);
    }
    
    //设置已知的数据源
    public static void set(String sourse) {
    	LOCAL.set(sourse);
    }
    
    //根据年份动态设置数据源
    public static void set(int year) {
    	LOCAL.set("DB_"+year);
    }
}
