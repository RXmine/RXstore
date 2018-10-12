package cn.rx.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "uid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    @Column(name = "name")
    private String name;
    @OneToMany(targetEntity = Orders.class,mappedBy = "user")
    private Set<Orders> orders = new HashSet<>(0);//将内存是在为0 ，避免浪费

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }
}
