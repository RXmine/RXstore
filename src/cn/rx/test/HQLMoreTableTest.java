package cn.rx.test;

import cn.rx.utils.HibernateUtils;
import cn.rx.utils.HibernateUtils2;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.List;

public class HQLMoreTableTest {
    /**
     * hql内连接查询
     * 这种内连接查询出来的是一个集合里封装的数组对象
     * 不是我们想要的实体对象
     * 解决：使用迫切内连接查询 关键字：fetch
     */
    @Test
    public void test1(){
        Session session = HibernateUtils2.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from User u  join fetch u.orders o ");
        List list = query.list();
        System.out.println(list.size());
        System.out.println();
    }
    /**
     * hql外连接查询
     * 由于一个用户可能有多个订单 作外链接
     * 会有重复的数据出现
     * 解决：
     * 使用关键字去重：distinct
     */
    @Test
    public void test2(){
        Session session = HibernateUtils2.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("select distinct u from User u  left join fetch u.orders o ");
        List list = query.list();
        System.out.println(list.size());
    }
}
