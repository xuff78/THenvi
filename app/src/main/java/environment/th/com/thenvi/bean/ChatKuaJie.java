package environment.th.com.thenvi.bean;

/**
 * Created by Administrator on 2016/3/21.
 */
public class ChatKuaJie {

    private String NN=""; //-总氮浓度
    private String CODN=""; //-COD浓度
    private String NDN=""; //-氨氮浓度
    private String PN=""; //-总磷浓度
    private String FLOW=""; //-流量
    private String DATA="";

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getNN() {
        return NN;
    }

    public void setNN(String NN) {
        this.NN = NN;
    }

    public String getCODN() {
        return CODN;
    }

    public void setCODN(String CODN) {
        this.CODN = CODN;
    }

    public String getNDN() {
        return NDN;
    }

    public void setNDN(String NDN) {
        this.NDN = NDN;
    }

    public String getPN() {
        return PN;
    }

    public void setPN(String PN) {
        this.PN = PN;
    }

    public String getFLOW() {
        return FLOW;
    }

    public void setFLOW(String FLOW) {
        this.FLOW = FLOW;
    }
}
