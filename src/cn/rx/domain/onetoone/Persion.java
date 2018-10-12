package cn.rx.domain.onetoone;
//基于主键的映射
public class Persion {
    private Long pid;
    private String name;
    private ExInfo exInfo;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExInfo getExInfo() {
        return exInfo;
    }

    public void setExInfo(ExInfo exInfo) {
        this.exInfo = exInfo;
    }
}
