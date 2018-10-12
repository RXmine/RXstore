package cn.rx.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * 抽取hibernate的工具类
 */
public class HibernateUtils {
    //SessionFactory对象只加载一次
    private static SessionFactory factory;
    //hibernate把可以预见的错误都转成了运行时异常
    static {
        //可以处理一下异常
        try {
            //加载配置文件
            Configuration cfg = new Configuration();
            cfg.configure();
            factory = cfg.buildSessionFactory();
        }catch (ExceptionInInitializerError e){
            //这是一个错误
            throw new ExceptionInInitializerError("初始化SessionFactory，请检查配置文件");
        }
    }

    /**
     * 获取一个新Session对象
     * 每次调用openSession方法都会获取同一个新的Session对象
     * @return
     */
    public static Session openSession(){
        return factory.openSession();
    }

    /**
     * 获取当前线程上的Session对象
     * @return
     */
    public static  Session getCurrentSession(){
        return factory.getCurrentSession();//只有当配置了把session和线程绑定之后才可以使用，否则返回null
    }
    @Test
    public void test(){
        getCurrentSession();
    }
}
