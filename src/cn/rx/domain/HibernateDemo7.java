package cn.rx.domain;

import cn.rx.utils.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import java.util.List;

/**
 * hibernate的qbc查询  Query By Criteria
 *     它是一种更加面向对象的查询方式，它把所有的生成语句全部融入到方法之中了（采用字符串拼接的方式），效率比hql慢
 *     涉及的对象
 *     Criteria
 *     如何获取该对象
 *      session.createCriteria(Class clazz)
 *      涉及的方法
 *      createCriteria(Class clazz)
 *      参数的含义
 *       要查询的实体类对象
 */
public class HibernateDemo7 {
    //基本查询
    @Test
    public void test1(){
        //获取session
        Session session = HibernateUtils.openSession();
        //获取事务
        Transaction transaction = session.beginTransaction();

        //获取criteria对象
        Criteria criteria = session.createCriteria(Customer.class);
        List list =criteria.list();
        //自己把Object数组转换成对象
        for (Object o : list) {
            System.out.println(o);
        }
        transaction.commit();
    }
    //条件查询
    @Test
    public void test2(){
        //获取session
        Session session = HibernateUtils.openSession();
        //获取事务
        Transaction ta = session.beginTransaction();

        //获取criteria对象
        Criteria criteria = session.createCriteria(Customer.class);
        //使用criteria对象的add方法来增加条件
        criteria.add(Restrictions.eq("custName","hibernate入门表"));
        criteria.add(Restrictions.like("custName","%表"));
        //只有一个返回的结果集 使用这个方法
//        Object o = criteria.uniqueResult();
//        System.out.println(o);
        List list =criteria.list();
        //自己把Object数组转换成对象
        for (Object o : list) {
            System.out.println(o);
        }
        ta.commit();
    }
    //排序查询
    @Test
    public void test3(){
        //获取session
        Session session = HibernateUtils.openSession();
        //获取事务
        Transaction transaction = session.beginTransaction();

        //获取criteria对象
        Criteria criteria = session.createCriteria(Customer.class);
        criteria.addOrder(Order.desc("custId"));
        List list =criteria.list();
        //自己把Object数组转换成对象
        for (Object o : list) {
            System.out.println(o);
        }
        transaction.commit();
    }
    //分页查询
    @Test
    public void test4(){
        //获取session
        Session session = HibernateUtils.openSession();
        //获取事务
        Transaction transaction = session.beginTransaction();

        //获取criteria对象
        Criteria criteria = session.createCriteria(Customer.class);
        criteria.setFirstResult(0);
        criteria.setMaxResults(2);
        List list =criteria.list();
        //自己把Object数组转换成对象
        for (Object o : list) {
            System.out.println(o);
        }
        transaction.commit();
    }
    //统计(投影)查询
    @Test
    public void test5(){
        //获取session
        Session session = HibernateUtils.openSession();
        //获取事务
        Transaction transaction = session.beginTransaction();

        //获取criteria对象
        Criteria criteria = session.createCriteria(Customer.class);
        //criteria.setProjection(Projections.rowCount());
        criteria.setProjection(Projections.count("custId"));
        List list =criteria.list();
        //自己把Object数组转换成对象
        for (Object o : list) {
            System.out.println(o);
        }
        transaction.commit();
    }
    //离线查询
    /**
     * 不使用session对象去获取criteria对象封装数据 使用DetachedCriteria对象，该对象不需要使用
     * session对象获取，使用该对象邓封装数据，session执行语句的方法称为离线查询
     */
    @Test
    public void test6(){//模拟在表现层的servlet中 可封装参数 并且不需要使用session对象和事务
        //获取离线对象不需要session
        DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
        //封装查询条件
        dc.add(Restrictions.eq("custId",5L));
        //模拟业务层
        List list = testService(dc);
        for (Object o : list) {
            System.out.println(o);
        }
    }

    private List testService(DetachedCriteria dc) {
        Session session = null;
        Transaction ta = null;
        try{
        //所有事务都在业务层
        session = HibernateUtils.getCurrentSession();
        //开启事务
        ta = session.beginTransaction();

        //模拟dao层
        List list = testDao(dc);
        ta.commit();
        return  list;
        }catch (Exception e){
            ta.rollback();
        }
        return  null;
    }

    private List testDao(DetachedCriteria dc) {
        Session session = HibernateUtils.getCurrentSession();
        //将离线的DetachedCriteria对象转成在线的Criteria对象
        Criteria c = dc.getExecutableCriteria(session);
        return c.list();

    }
}
