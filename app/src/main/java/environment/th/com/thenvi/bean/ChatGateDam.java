package environment.th.com.thenvi.bean;

/**
 * Created by Administrator on 2016/3/21.
 */
public class ChatGateDam {

    private String DIVERSION="";  //-引水量
    private String DRAINAGE="";  //-排水量
    private String UPPERSLUICE="";  //-闸上水量
    private String UNDERSLUICE="";  //-闸下水量
    private String DATE="";

    public String getDIVERSION() {
        return DIVERSION;
    }

    public void setDIVERSION(String DIVERSION) {
        this.DIVERSION = DIVERSION;
    }

    public String getDRAINAGE() {
        return DRAINAGE;
    }

    public void setDRAINAGE(String DRAINAGE) {
        this.DRAINAGE = DRAINAGE;
    }

    public String getUPPERSLUICE() {
        return UPPERSLUICE;
    }

    public void setUPPERSLUICE(String UPPERSLUICE) {
        this.UPPERSLUICE = UPPERSLUICE;
    }

    public String getUNDERSLUICE() {
        return UNDERSLUICE;
    }

    public void setUNDERSLUICE(String UNDERSLUICE) {
        this.UNDERSLUICE = UNDERSLUICE;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }
}
