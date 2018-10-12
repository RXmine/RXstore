package cn.rx.domain;

import cn.rx.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.io.Serializable;

/**
 * 查询一个的方法 get和load方法的比较
 *    get(Class clazz,Serializable id)
 *    load(Class clazz,Serializable id)
 *    共同点：都是通过id来进行查询的
 *    区别：1.查询的时机不一样
 *             get方法的查询时机是在每次调用get方法的时候，马上发起查询  立即加载
 *             load的查询时机，每次真正使用的时候发起查询(获取非主键id的时候)
 *             （这里是使用toString的时候，用的是动态代理）
 *             延迟加载 懒加载 惰性加载
 *             load也可以通过配置变成立即加载
 *          2.返回的结果不一样
 *             get方法返回的还是一个实体类对象
 *             load返回的是一个实体类型的代理对象（动态代理）
 *             含有需要查询对象的主键的id
 *          3.获取非主键id的时候报错不一样
 *            get会报空指针异常
 *            load报ObjectNotFoundException 原因是到数据库查询找不到对应的数据
 */
public class HibernateDemo4 {
    @Test
    public void testGet(){
        Session session = HibernateUtils.openSession();
        Transaction ta = session.beginTransaction();
        Customer c = session.get(new Customer().getClass(),2L);
        System.out.println(c.getCustPhone());
    }
    @Test
    public void testLoad(){
        Session session = HibernateUtils.openSession();
        Transaction ta = session.beginTransaction();
        Customer c = session.load(new Customer().getClass(),2L);
        System.out.println(c);
    }
}
