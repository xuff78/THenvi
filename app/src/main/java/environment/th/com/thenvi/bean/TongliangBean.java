package environment.th.com.thenvi.bean;

import java.util.ArrayList;

/**
 * Created by 可爱的蘑菇 on 2016/4/17.
 */
public class TongliangBean {

    private String firstName="";
    private String secondId="";
    private String firstId="";
    private String secondName="";
    private ArrayList<TongliangItem> positiveList=new ArrayList<>();
    private ArrayList<TongliangItem> negativeList=new ArrayList<>();
    private ArrayList<TongliangItem> netList=new ArrayList<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public ArrayList<TongliangItem> getPositiveList() {
        return positiveList;
    }

    public void setPositiveList(ArrayList<TongliangItem> positiveList) {
        this.positiveList = positiveList;
    }

    public ArrayList<TongliangItem> getNegativeList() {
        return negativeList;
    }

    public void setNegativeList(ArrayList<TongliangItem> negativeList) {
        this.negativeList = negativeList;
    }

    public ArrayList<TongliangItem> getNetList() {
        return netList;
    }

    public void setNetList(ArrayList<TongliangItem> netList) {
        this.netList = netList;
    }
}
