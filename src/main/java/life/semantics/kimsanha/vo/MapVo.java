package life.semantics.kimsanha.vo;

import org.springframework.stereotype.Component;

@Component("MapVo")
public class MapVo {
    private String locationName;
    private String location;
    private String phoneNum;
    private String coordinate;
    private int num;

    public String getLocationName() {
        return locationName;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getCoordinate() {
        return coordinate;
    }
    public int getNum() {
        return num;
    }

    public void setLocatioNname(String locationName) {
        this.locationName = locationName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setNum(int num) {
        this.num = num;
    }
}


