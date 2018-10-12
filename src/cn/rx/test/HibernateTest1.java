package cn.rx.test;

import cn.rx.domain.Customer;
import cn.rx.domain.LinkMan;
import cn.rx.domain.Role;
import cn.rx.domain.SysUser;
import cn.rx.utils.HibernateUtils;
import cn.rx.utils.HibernateUtils2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class HibernateTest1 {
    @Test
    public void test1(){
        //让联系人知道属于哪个客户即可
        //正常的保存操作 在主表的客户知道的情况下，创建一个新的联系人
        //获取session
        Session session = HibernateUtils.getCurrentSession();
        Transaction ta = session.beginTransaction();
        Customer c = session.get(Customer.class,1L);
        //创建一个新的联系人
        LinkMan l = new LinkMan();
        l.setLkmName("测试1");
        l.setCustomer(c);
        session.save(l);
        ta.commit();
    }
    @Test
    public void test2(){
        /*实现双向联系
        创建一个新的客户和联系人 创建规则：先保存主表的，在保存从表的
        此时会产生一条updata语句 原因是因为在执行保存时 两个对象都是持久态 都有一级快照和一级缓存
        保存客户后和联系人后，因为客户中的set里的lkm_id不一致了，需要更新
        解决办法;
        让客户在执行操作的时候，放弃维护关联关系的权力 使用属性inverse
        配置方式
            在Customer的映射配置文件的set标签上使用inverse属性
            inverse含义：是否放弃维护关联关系的权力
                 true：放弃 false：不放弃(默认的)
        获取session*/
        Session session = HibernateUtils.getCurrentSession();
        Transaction ta = session.beginTransaction();
        //创建一个客户
        Customer c = new Customer();
        c.setCustName("测试的客户x3");
        //创建一个联系人
        LinkMan l = new LinkMan();
        l.setLkmName("测试x3");
        c.getLinkMans().add(l);
        l.setCustomer(c);
        session.save(c);
        session.save(l);
        ta.commit();
    }

    /**
     * 级联保存 在set或者many-to-one标签上配置 cascade属性，级联保存更新的取值"save-update"(保存，更新，或者保存加更新)
     * 配置过后就可以只保存一个的同时就可以保存两个 优先级在inverse之前
     */
    @Test
    public void test3(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction ta = session.beginTransaction();
        //创建一个客户
        Customer c = new Customer();
        c.setCustName("测试的客户c1");
        //创建一个联系人
        LinkMan l = new LinkMan();
        l.setLkmName("测试c1");

        c.getLinkMans().add(l);
        l.setCustomer(c);
        session.save(c);
        ta.commit();
    }

    /**
     * 删除操作
     *     删除从表的数据就是单表删除
     *     删除主表数据
     *          如果从表没有引用，就是删除单表
     *          有引用,就是多表删除，hibernate在多表删除时
     *          会先把表中的外键字段设置为null，然后在删除主表数据，
     *          如果外键字段有非空约束，则hibernate在执行删除过程中
     *          不把外键先设置为null,就会报错.
     *          可以设置级联删除，但是需要慎重，在cascade属性里面配置delete即可，
     *          但是inverse属性必须为true.
     */
    @Test
    public void test4(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction ta = session.beginTransaction();
        //删除
        Customer c = session.get(Customer.class,19L);
        session.delete(c);
        ta.commit();
    }

    /**
     * 查询操作
     *    第五种查询操作：对象导航查询
     *     当两个实体之间有关联关系时，调用get×××方法即可实现查询，有hibernate提供
     *        例如:customer.getLinkmans() 延迟加载 连查带打印
     *            linkman.getCustomer()
     *     lazy属性：
     *     class上的：它管load方法是否有延迟加载
     *     set标签上的：它管关联对象的集合上对象是否延迟加载
     *     many-to-many上的：它管查询的主表的实体是否立即加载
     *                      它有proxy值：取决于load上的值，load延迟 它也延迟
     *                          false:就表示不需要延迟加载。
     */
    @Test
    public void test5(){
        Session session = HibernateUtils.getCurrentSession();
        Transaction ta = session.beginTransaction();
        //对象导航查询
        Customer c = session.get(Customer.class,16L);
        // c.getLinkMans();
        System.out.println(c.getLinkMans());
        ta.commit();
        System.out.println(c);
        }

    /**
     * 多表保存操作
     */
    @Test
       public void test6(){
            Session session = HibernateUtils2.getCurrentSession();
            Transaction ta = session.beginTransaction();
            //保存操作
            /*
            要求
               两个用户三个角色
               1号用户拥有1号和2号角色
               2号用户拥有3号和2号角色
               双向联系
             */
            SysUser user1 = new SysUser();
            user1.setUserName("王五2");
            session.save(user1);
            SysUser user2 = new SysUser();
            user2.setUserName("liuliu2");
            session.save(user2);
            Role r1 = new Role();
            r1.setRoleName("7号角色");
            session.save(r1);
            Role r2 = new Role();
            r2.setRoleName("8号角色");
            session.save(r2);
            Role r3 = new Role();
            r3.setRoleName("9号角色");
            session.save(r3);
            //建立双向关系
            user1.getRoles().add(r1);
            user1.getRoles().add(r2);
            user2.getRoles().add(r2);
            user2.getRoles().add(r3);
            r1.getUsers().add(user1);
            r2.getUsers().add(user1);
            r2.getUsers().add(user2);
            r3.getUsers().add(user2);
            ta.commit();

       }

    /**
     * 多对多的时候，禁止使用双向多表级联删除，数据会被删完
     */
    @Test
    public void test7(){
        Session session = HibernateUtils2.getCurrentSession();
        Transaction ta = session.beginTransaction();
        SysUser user1 = session.get(SysUser.class,3L);
        session.delete(user1);
        ta.commit();

    }
}
