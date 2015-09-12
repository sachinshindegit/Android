package sachinshinde.mynotemaker;

/**
 * Created by Sachin AKA Scavy on 9/11/2015.
 */
public class MyNote {
    private int id;
    private String note;
    private String path;

    public MyNote(){}
    public int getId() {
        return id;
    }

    public  MyNote(String note, String path){
        this.note = note;
        this.path = path;
    }

    public  MyNote(int id,String note, String path){
        this.id = id;
        this.note = note;
        this.path = path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
