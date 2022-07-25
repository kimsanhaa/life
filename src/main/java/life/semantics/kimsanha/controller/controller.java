package life.semantics.kimsanha.controller;


import life.semantics.kimsanha.handler.apiHandler;
import life.semantics.kimsanha.service.service;
import life.semantics.kimsanha.vo.vo;
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
public class controller {
    private static final Logger log = LoggerFactory.getLogger(controller.class);

    private service service;

    @Autowired
    public controller(service service) {
        this.service = service;
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
        return service.createJsonData(lat,lng);
    }

    @PostMapping("/myLocation")
    @ResponseBody
    public ResponseEntity<?> save(@RequestParam("locationName") String locationName,
                     @RequestParam("location") String location,
                     @RequestParam("phoneNum") String phoneNum,
                     @RequestParam("coordinate") String coordinate){
        log.info("save() controller");
        return  service.saveLocation(locationName, location, phoneNum, coordinate);
    }

    @GetMapping("/myLocation")
    @ResponseBody
    public List<vo> search(){
        log.info("search() controller");
        return service.placeSearch();
    }

    @DeleteMapping("/myLocation")
    @ResponseBody
    public ResponseEntity<Void> delete(@RequestParam("locationName") String locationName){
        log.info("delete() controller");
           return  service.deleteLocation(locationName);
    }

    @GetMapping("/scroll")
    @ResponseBody
    public HashSet<vo> scrollEvent(@RequestParam List<String> scrollList){
        log.info("scroll() event");
        return service.scrollevent(scrollList);
    }// end scrollevent


}
