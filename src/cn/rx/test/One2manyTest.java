package cn.rx.test;

import cn.rx.domain.Orders;
import cn.rx.domain.User;
import cn.rx.utils.HibernateUtils2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class One2manyTest {
    /**
     * 一对多保存操作
     */
    @Test
    public void test1(){
        Session session = HibernateUtils2.getCurrentSession();
        Transaction ta = session.beginTransaction();
        User user = new User("张三");
        Orders order1 = new Orders("订单1");
        Orders order2 = new Orders("订单2");
        order1.setUser(user);
        order2.setUser(user);
        user.getOrders().add(order1);
        user.getOrders().add(order2);
        //将对象转换成持久态
        session.save(user);
        session.save(order1);
        session.save(order2);
        //提交事务
        ta.commit();
    }
    /**
     * 一对多级联保存操作
     */
    @Test
    public void test2(){
        Session session = HibernateUtils2.getCurrentSession();
        Transaction ta = session.beginTransaction();
        User user = new User("qiqi");
        Orders order1 = new Orders("订单3");
        Orders order2 = new Orders("订单4");
        order1.setUser(user);
        order2.setUser(user);
        user.getOrders().add(order1);
        user.getOrders().add(order2);
        //将对象转换成持久态
        session.save(user);
        session.save(order1);
        session.save(order2);
        //提交事务
        ta.commit();
    }
}
