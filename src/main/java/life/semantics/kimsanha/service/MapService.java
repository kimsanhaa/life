package life.semantics.kimsanha.service;

import life.semantics.kimsanha.vo.MapVo;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public interface MapService {
     JSONArray createJsonData(String lat, String lng) throws ParseException, IOException;
     ResponseEntity<?> addLocation(String locationName, String location, String phoneNum, String coordinate);
     List<MapVo> findTop10List();
     ResponseEntity<Void> removeLocation(String locationName );
     HashSet<MapVo> scrollEvent(List<String> scrollList);
}
