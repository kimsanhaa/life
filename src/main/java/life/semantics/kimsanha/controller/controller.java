package life.semantics.kimsanha.controller;


import life.semantics.kimsanha.dao.dao;
import life.semantics.kimsanha.handler.apiHandler;
import life.semantics.kimsanha.service.service;
import life.semantics.kimsanha.vo.vo;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class controller {

    private apiHandler apihandler;
    private  dao dao;
    private vo vo;
    private service service;

    @Autowired
    public controller(apiHandler apihandler, life.semantics.kimsanha.dao.dao dao, life.semantics.kimsanha.vo.vo vo,service service) {
        this.apihandler = apihandler;
        this.dao = dao;
        this.vo = vo;
        this.service = service;
    }

    @GetMapping("")
    public String index(){
       // System.out.println("index.html");
        return "main";
    }



    @ResponseBody
    @GetMapping("/hospitaApi")
    public JSONArray callApi(@RequestParam("Lat") String Lat, @RequestParam("Lng") String Lng) throws ParseException {
       // System.out.println("controller 호출");
        return apihandler.Callapi(Lat,Lng);
    }

    @PostMapping("/myLocation")
    @ResponseBody
    public String[] save(@RequestParam("location_name") String location_name,
                     @RequestParam("location") String location,
                     @RequestParam("phoneNum") String phoneNum,
                     @RequestParam("coordinate") String coordinate){

     String [] list =  service.save(location_name, location, phoneNum, coordinate);
        return list;

    }

    @GetMapping("/myLocation")
    @ResponseBody
    public List<vo> search(){
        return service.search();
    }

    @DeleteMapping("/myLocation")
    @ResponseBody
    public String delete(@RequestParam("location_name") String location_name){
       // System.out.println("location_name=="+location_name);
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
        return service.scrollevent(scrollList);
    }// end scrollevent


}
