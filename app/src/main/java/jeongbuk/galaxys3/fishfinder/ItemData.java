package jeongbuk.galaxys3.fishfinder;

public class ItemData {
    String title;
    String content;
    String name;
    String imgpath;
    String date;

    public ItemData(String name, String imgpath, String date, String title, String content) {
        this.name  = name;
        this.imgpath = imgpath;
        this.date = date;
        this.title = title;
        this.content = content;
    }



    public String getName() { return  name;}

    public String getImgpath() {return imgpath;}

    public String getDate() {return date;}

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setImgpath(String imgpath){
        this.imgpath = imgpath;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
