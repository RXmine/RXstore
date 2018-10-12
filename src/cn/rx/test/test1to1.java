package cn.rx.test;

import cn.rx.domain.onetoone.ExInfo;
import cn.rx.domain.onetoone.Persion;
import org.junit.Test;

public class test1to1 extends BaseTest {
    /**
     * 测试一对一的保存
     * 一对一需要建立双向联系
     */
    @Test
    public void test1(){
        Persion persion = new Persion();
        persion.setName("李四");
        ExInfo exInfo = new ExInfo();
        exInfo.setInfo("大神");
        //需要建立双向联系
        persion.setExInfo(exInfo);
        exInfo.setPersion(persion);
        session.save(persion);
    }
}
