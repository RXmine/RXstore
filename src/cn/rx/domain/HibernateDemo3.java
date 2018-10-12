package cn.rx.domain;

import cn.rx.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 测试hibernate数据源
 */
public class HibernateDemo3 {
    @Test
    public void  test(){
        Session session =HibernateUtils.openSession();
        //调用doWork方法
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                /**
                 * hibernate中基础的jdbc API的使用
                 *    jdbc的api
                 *       Connetion
                 *       Statement
                 *       PreperaStatement
                 *       ResultSet
                 */
                System.out.println(connection.getClass().getName());
            }
        });
    }
}
