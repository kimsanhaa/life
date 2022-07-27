package life.semantics.kimsanha.controller;


import life.semantics.kimsanha.service.MapServiceImpl;
import life.semantics.kimsanha.vo.MapVo;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@RestController
public class MapApiController {
    private static final Logger log = LoggerFactory.getLogger(MapApiController.class);

    private MapServiceImpl mapServiceImpl;

    @Autowired
    public MapApiController(MapServiceImpl mapServiceImpl) {
        this.mapServiceImpl = mapServiceImpl;
    }


    @GetMapping("/hospitalApi")
    public JSONArray callApi(@RequestParam("lat") String lat, @RequestParam("lng") String lng) throws ParseException, IOException {
        log.info("callApi() 호출");
        return mapServiceImpl.createJsonData(lat,lng);
    }

    @PostMapping("/location")
    public ResponseEntity<?> locationAdd(@RequestParam("locationName") String locationName,
                     @RequestParam("location") String location,
                     @RequestParam("phoneNum") String phoneNum,
                     @RequestParam("coordinate") String coordinate){
        log.info("save() controller");
        return  mapServiceImpl.addLocation(locationName, location, phoneNum, coordinate);
    }

    @GetMapping("/location")
    public List<MapVo> locationList(){
        log.info("search() controller");
        return mapServiceImpl.findTop10List();
    }

    @DeleteMapping("/location")
    public ResponseEntity<?> locationRemove(@RequestParam("locationName") String locationName){
        log.info("delete() controller");
           return  mapServiceImpl.removeLocation(locationName);
    }

    @GetMapping("/scroll")
    public HashSet<MapVo> scrollEvent(@RequestParam List<String> SCROLLLIST){
        return mapServiceImpl.scrollEvent(SCROLLLIST);
    }// end scrollevent


}
