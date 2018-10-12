package cn.rx.domain;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * 使用hibernate进行数据库操作步骤
 *   1.解析主配置文件
 *   2.根据主配置文件创建SessionFactor工厂
 *   3.根据SessionFactry创建Session对象
 *   4.开启事务
 *   5.执行操作（这里是保存操作）
 *   6.提交事务
 *   7.释放资源
 */
public class HibernateDemo1 {
    @Test
    public void test(){
                     Customer c = new Customer();
                     c.setCustName("hibernate入门表_3");
                //   1.解析主配置文件
                     Configuration cfg = new Configuration();
                     cfg.configure();//寻找xml配置文件并解析
                     /*加载映射文件，当在主配置文件中没有配置映射文件时，也可以使用这两个个方法。
                     cfg.addResource("cn/rx/domain/Customer.hbp.xml");
                       cfg.addClass(Customer.class);*/
                //   2.根据主配置文件创建SessionFactor工厂
                     SessionFactory factory= cfg.buildSessionFactory();
                //   3.根据SessionFactry创建Session对象
                     Session session = factory.openSession();
                //   4.开启事务
                     Transaction ts = session.beginTransaction();
                //   5.执行操作（这里是保存操作）
                     session.save(c);
                //   6.提交事务
                     ts.commit();
                //   7.释放资源
                     session.close();
                     factory.close();
    }
}
