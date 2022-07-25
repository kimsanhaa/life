package life.semantics.kimsanha.controller;


import life.semantics.kimsanha.dao.dao;
import life.semantics.kimsanha.handler.apiHandler;
import life.semantics.kimsanha.service.service;
import life.semantics.kimsanha.vo.vo;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class controller {
    private static final Logger log = LoggerFactory.getLogger(controller.class);

    private apiHandler apihandler;
    private service service;

    @Autowired
    public controller(apiHandler apihandler,service service) {
        this.apihandler = apihandler;
        this.service = service;
    }

    @GetMapping("")
    public String index(){
        log.info("index() Controller");
        return "main";
    }

    @ResponseBody
    @GetMapping("/hospitaApi")
    public JSONArray callApi(@RequestParam("Lat") String Lat, @RequestParam("Lng") String Lng) throws ParseException, IOException {
        log.info("callApi() 호출");
        return apihandler.Callapi(Lat,Lng);
    }

    @PostMapping("/myLocation")
    @ResponseBody
    public String[] save(@RequestParam("location_name") String location_name,
                     @RequestParam("location") String location,
                     @RequestParam("phoneNum") String phoneNum,
                     @RequestParam("coordinate") String coordinate){
        log.info("save() controller");
     String [] list =  service.save(location_name, location, phoneNum, coordinate);
        return list;

    }

    @GetMapping("/myLocation")
    @ResponseBody
    public List<vo> search(){
        log.info("search() controller");
        return service.search();
    }

    @DeleteMapping("/myLocation")
    @ResponseBody
    public String delete(@RequestParam("location_name") String location_name){
        log.info("delete() controller");

        String result;
        try {
            service.deleteLocation(location_name);
            result="ok";
        }catch (Exception e){
            result="error";
        }
        return result;

    }

    @GetMapping("/scroll")
    @ResponseBody
    public HashSet<vo> scrollEvent(@RequestParam List<String> scrollList){
        log.info("scroll() event");
        return service.scrollevent(scrollList);
    }// end scrollevent


}
