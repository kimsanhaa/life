package life.semantics.kimsanha.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
    private static final Logger log = LoggerFactory.getLogger(MapApiController.class);
    @GetMapping("")
    public String index(){
        log.info("index() Controller");
        return "main";
    }
}
