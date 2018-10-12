package cn.rx.domain;

import cn.rx.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * hibernate的对象状态
 */
public class HibernateDemo5 {
    @Test
    public void test(){
        //创建session对象
        Session session = HibernateUtils.openSession();
        //开启事务
        Transaction ta = session.beginTransaction();
        Customer c = session.get(Customer.class,5L);
        System.out.println(c.getCustAddress());
        ta.commit();
        session.close();
        System.out.println(c);
        c.setCustAddress("成都市，锦江区");
        Session session1 = HibernateUtils.openSession();
        Transaction ta2 = session1.beginTransaction();
        session1.update(c);
        System.out.println(c);
        ta2.commit();
        System.out.println(c);
        session1.close();
    }
}
