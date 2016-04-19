package environment.th.com.thenvi.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/19.
 */
public class TongliangYujingBean implements Serializable{
    private String tongLiangYuZhi=""; //: 449.68,
    private String duanMianName=""; //": "思源大桥",
    private String x=""; //": 120.494167498,
    private String y=""; //": 30.8433355772,
    private String provinceName=""; //": "江苏到上海",
    private String chaoBiaoTongLiang=""; //": 12047.29,
    private String chaoBiaoBeiShu=""; //": 26.79

    public String getTongLiangYuZhi() {
        return tongLiangYuZhi;
    }

    public void setTongLiangYuZhi(String tongLiangYuZhi) {
        this.tongLiangYuZhi = tongLiangYuZhi;
    }

    public String getDuanMianName() {
        return duanMianName;
    }

    public void setDuanMianName(String duanMianName) {
        this.duanMianName = duanMianName;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getChaoBiaoTongLiang() {
        return chaoBiaoTongLiang;
    }

    public void setChaoBiaoTongLiang(String chaoBiaoTongLiang) {
        this.chaoBiaoTongLiang = chaoBiaoTongLiang;
    }

    public String getChaoBiaoBeiShu() {
        return chaoBiaoBeiShu;
    }

    public void setChaoBiaoBeiShu(String chaoBiaoBeiShu) {
        this.chaoBiaoBeiShu = chaoBiaoBeiShu;
    }
}
