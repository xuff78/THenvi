package environment.th.com.thenvi.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/27.
 */
public class BookCate implements Serializable{

    public static final String Name="BookCate";
    private String name="";
    private String nextType="";
    private String id="";
    private ArrayList<BookCate> cates=new ArrayList<BookCate>();

    public ArrayList<BookCate> getCates() {
        return cates;
    }

    public void setCates(ArrayList<BookCate> cates) {
        this.cates = cates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNextType() {
        return nextType;
    }

    public void setNextType(String nextType) {
        this.nextType = nextType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
