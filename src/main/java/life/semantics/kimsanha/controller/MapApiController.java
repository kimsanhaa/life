package life.semantics.kimsanha.controller;


import life.semantics.kimsanha.service.MapService;
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

@Controller
public class MapApiController {
    private static final Logger log = LoggerFactory.getLogger(MapApiController.class);

    private MapService mapService;

    @Autowired
    public MapApiController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("")
    public String index(){
        log.info("index() Controller");
        return "main";
    }

    @ResponseBody
    @GetMapping("/hospitalApi")
    public JSONArray callApi(@RequestParam("lat") String lat, @RequestParam("lng") String lng) throws ParseException, IOException {
        log.info("callApi() 호출");
        return mapService.createJsonData(lat,lng);
    }

    @PostMapping("/myLocation")
    @ResponseBody
    public ResponseEntity<?> save(@RequestParam("locationName") String locationName,
                     @RequestParam("location") String location,
                     @RequestParam("phoneNum") String phoneNum,
                     @RequestParam("coordinate") String coordinate){
        log.info("save() controller");
        return  mapService.saveLocation(locationName, location, phoneNum, coordinate);
    }

    @GetMapping("/myLocation")
    @ResponseBody
    public List<MapVo> search(){
        log.info("search() controller");
        return mapService.placeSearch();
    }

    @DeleteMapping("/myLocation")
    @ResponseBody
    public ResponseEntity<?> delete(@RequestParam("locationName") String locationName){
        log.info("delete() controller");
           return  mapService.deleteLocation(locationName);
    }

    @GetMapping("/scroll")
    @ResponseBody
    public HashSet<MapVo> scrollEvent(@RequestParam List<String> SCROLLLIST){
        return mapService.scrollevent(SCROLLLIST);
    }// end scrollevent


}
