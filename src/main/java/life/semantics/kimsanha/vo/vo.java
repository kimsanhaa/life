package life.semantics.kimsanha.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component("vo")
public class vo {
    private String location_name;
    private String location;
    private String phoneNum;
    private String coordinate;
    private int num;



    public String getLocation_name() {
        return location_name;
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

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
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

    public vo() {
        this.location_name = "null";
        this.location = "null";
        this.phoneNum = "null";
        this.coordinate = "null";
        this.num = 0;
    }
}


