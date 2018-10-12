package cn.rx.domain;

import cn.rx.utils.HibernateUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.List;

/**
 * hibernate的crud操作
 *
 */
public class HibernateDemo2 {
    @Test
    public void testSave(){
        //获取session对象
        Session session = HibernateUtils.getCurrentSession();
        //开启事务
        Transaction ta = session.beginTransaction();
        //创建一个customer对象
        Customer customer = new Customer();
        customer.setCustLevel("12");
        //保存客户
        session.save(customer);
        //提交事务
        ta.commit();
        //关闭session
        session.close();
    }
    //实际开发中资源的写法
    public void SaveCustomer(Customer c){
        Session session = null;
        Transaction ta = null;
        try {
            session = HibernateUtils.openSession();
            ta = session.beginTransaction();
            //保存客户
            session.save(c);
            ta.commit();
        }catch (Exception e){
            ta.rollback();
        }finally {
            session.close();
        }
    }
    @Test
    public void testFindOne(){
      Session session = HibernateUtils.getCurrentSession();
      Transaction ta = session.beginTransaction();
      //查询功能
        Customer c =session.get(Customer.class,3L);
        System.out.println(c);
        ta.commit();
        session.close();
    }
    @Test
    public void testUpdate(){
      Session session = HibernateUtils.getCurrentSession();
      Transaction ta = session.beginTransaction();
      //创建一个Customer对象
        Customer customer = new Customer();
        Customer c = session.get(Customer.class,7L);
        c.setCustName("测试其hql功能");
        ta.commit();
        session.close();
    }
    @Test
    public void testFindAll(){
        Session session = HibernateUtils.openSession();
        Transaction ta = session.beginTransaction();
        //查询所有
        SQLQuery sqlQuery = session.createSQLQuery("select * from cst_customer");
        List<Object[]> list = sqlQuery.list();
        for (Object[] o : list) {
            System.out.println(".........数组中的内容");
            for (Object o1 : o) {
                System.out.print(o1);
            }
        }
         ta.commit();
        session.close();
    }
    @Test
    public void testDelete(){
       Session session =HibernateUtils.openSession();
       Transaction ta = session.beginTransaction();
       //删除功能
        Customer c = session.get(new Customer().getClass(),4L);
        session.delete(c);
        ta.commit();
        session.close();
    }

}
