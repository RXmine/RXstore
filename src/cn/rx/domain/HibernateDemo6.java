package cn.rx.domain;

import cn.rx.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.List;


/**
 * HQL查询数据库
 */
public class HibernateDemo6 {
    //基本查询
    @SuppressWarnings("")
    @Test
    public void test1(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction ta = session.beginTransaction();
        //查询所有
        Query query = session.createQuery("from Customer");
        List list =query.list();
        //HQL会自己封装对象，不用在转换。
        for (Object o : list) {
            System.out.println(o);
        }
    }
    @Test
    public void test2(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction ta = session.beginTransaction();
        //条件查询
        //可以使用  :名称来指定需要查询的参数，名字任意
        Query query = session.createQuery("from Customer where custLevel = :custLevel and custName like :custName");
        //设置参数
        //query.setString("custLevel","12");//这种方法有参数类型的限制
        //query.setString("custName","%其%");
        //没有参数类型的限制
        query.setParameter("custLevel","12");
        query.setParameter("custName","%其%");
        List list =query.list();
        //HQL会自己封装对象，不用在转换。
        for (Object o : list) {
            System.out.println(o);
        }
    }

    /**
     * 分页查询
     * mysql分页关键字
     *  limit :两个参数的含义   1 .查询开始记录索引 2.每次查询的条数
     *  hibernate提供的两个方法：
     *    setFirstResult():设置查询开始位置的索引
     *    setMaxResults():设置每次查询的条数
     *    不管用什么数据库这两个方法都能用 依据的是hibernate的方言。
     */
    @Test
    public void test3(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction ta = session.beginTransaction();
        //查询所有
        Query query = session.createQuery("from Customer");
        query.setFirstResult(0);
        query.setMaxResults(2);
        List list =query.list();
        //HQL会自己封装对象，不用在转换。
        for (Object o : list) {
            System.out.println(o);
        }
    }

    /**
     * 统计查询，使用的是聚合函数
     *  count avg max min sum
     */
    @Test
    public void test4(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction ta = session.beginTransaction();
        //查询所有
        Query query = session.createQuery("select count(*) from Customer");

//        List list =query.list();
//        //HQL会自己封装对象，不用在转换。
//        for (Object o : list) {
//            System.out.println(o);
//        }
        Long count = (Long)query.uniqueResult();//当返回结果唯一时，使用这个方法不报错。
        System.out.println(count);
    }

    /**
     * 投影查询
     *     当我们需要查询实体的部分字段时，希望它返回的是一个实体类的包装对象，而不是Object数组
     *     输出的时候其它字段是null，是因为我们的toString方法。
     */
    @Test
    public void test5(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction ta = session.beginTransaction();
        //查询所有
        Query query = session.createQuery("select new cn.rx.domain.Customer(custId,custName)from Customer");
        List list =query.list();
        //HQL会自己封装对象，不用在转换。
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
