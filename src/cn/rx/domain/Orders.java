package cn.rx.domain;

import javax.persistence.*;

@Entity//表明这是一个实体类
@Table(name = "orders")
public class Orders {
    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oid;
    @Column(name = "name")
    private String name;
    @ManyToOne(targetEntity = User.class,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "uid",referencedColumnName = "uid")
    private User user;

    public Orders() {
    }

    public Orders(String name) {
        this.name = name;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
