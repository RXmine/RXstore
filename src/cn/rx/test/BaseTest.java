package cn.rx.test;

import cn.rx.utils.HibernateUtils;
import cn.rx.utils.HibernateUtils2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;

import javax.persistence.Table;

public class BaseTest {
    Session session = null;
    @Before
    public void before(){
        session = HibernateUtils2.openSession();
        Transaction ta = session.beginTransaction();

    }
    @After
    public void after(){
        session.getTransaction().commit();
       session.close();
    }

}
