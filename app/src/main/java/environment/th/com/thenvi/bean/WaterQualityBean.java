package environment.th.com.thenvi.bean;

import java.io.Serializable;

/**
 * Created by 可爱的蘑菇 on 2016/4/4.
 */
public class WaterQualityBean implements Serializable{

    private String X=""; //-经度
    private String Y=""; //-维度
    private int LEVEL=0; //-等级
    private String NAME="";
    private String CODE="";

    public static String Name="WaterQualityBean";

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getX() {
        return X;
    }

    public void setX(String x) {
        X = x;
    }

    public String getY() {
        return Y;
    }

    public void setY(String y) {
        Y = y;
    }

    public int getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(int LEVEL) {
        this.LEVEL = LEVEL;
    }
}
