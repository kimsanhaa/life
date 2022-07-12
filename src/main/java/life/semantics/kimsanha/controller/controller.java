package life.semantics.kimsanha.controller;


import life.semantics.kimsanha.handler.apiHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class controller {

    @Autowired
    apiHandler apihandler;

    @ResponseBody
    @GetMapping("/hospitaApi")
    public JSONArray temp(@RequestParam("Lat") String Lat, @RequestParam("Lng") String Lng) throws ParseException {
        System.out.println("controller 호출");
        return apihandler.Callapi(Lat,Lng);
    }


}
