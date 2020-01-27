package mn.gmobile.draw;

public class RogerModel {

    private String shts_name, content;
    private Double latitude, longitude;

    public Double getLatitude(){  return latitude;  }

    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getShts_name() {
        return shts_name;
    }

    public void setShts_name(String shts_name) {
        this.shts_name = shts_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
