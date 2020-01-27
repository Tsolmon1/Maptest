package mn.gmobile.draw;

public class NewsModel {

    private String description, content, thumb;

//    private Double latitude, longitude;

    public String getThumb(){  return thumb;  }

    public void setThumb(String thumb){
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
