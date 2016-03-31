package environment.th.com.thenvi.bean;

/**
 * Created by Administrator on 2016/3/21.
 */
public class ChatGuokong {

    private String CLCLE=""; //-周期
    private String PH=""; //-pH*
    private String DO=""; //-DO(mg/l)
    private String CODMN=""; //-CODMn(mg/l)
    private String NH3N=""; //-NH3-N(mg/l)
    private String WQUALITY=""; //-本周水质
    private String LQUALITY=""; //-LQUALITY
    private String DATA="";

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getCLCLE() {
        return CLCLE;
    }

    public void setCLCLE(String CLCLE) {
        this.CLCLE = CLCLE;
    }

    public String getPH() {
        return PH;
    }

    public void setPH(String PH) {
        this.PH = PH;
    }

    public String getDO() {
        return DO;
    }

    public void setDO(String DO) {
        this.DO = DO;
    }

    public String getCODMN() {
        return CODMN;
    }

    public void setCODMN(String CODMN) {
        this.CODMN = CODMN;
    }

    public String getNH3N() {
        return NH3N;
    }

    public void setNH3N(String NH3N) {
        this.NH3N = NH3N;
    }

    public String getWQUALITY() {
        return WQUALITY;
    }

    public void setWQUALITY(String WQUALITY) {
        this.WQUALITY = WQUALITY;
    }

    public String getLQUALITY() {
        return LQUALITY;
    }

    public void setLQUALITY(String LQUALITY) {
        this.LQUALITY = LQUALITY;
    }
}
