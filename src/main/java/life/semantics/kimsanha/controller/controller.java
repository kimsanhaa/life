package life.semantics.kimsanha.controller;


import life.semantics.kimsanha.dao.dao;
import life.semantics.kimsanha.handler.apiHandler;
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

    @Autowired
    apiHandler apihandler;

    @Autowired
    dao dao;

    @Autowired
    vo vo;

    @GetMapping("")
    public String index(){
       // System.out.println("index.html");
        return "main";
    }

    @ResponseBody
    @GetMapping("/hospitaApi")
    public JSONArray temp(@RequestParam("Lat") String Lat, @RequestParam("Lng") String Lng) throws ParseException {
       // System.out.println("controller 호출");
        return apihandler.Callapi(Lat,Lng);
    }

    @PostMapping("/myLocation")
    @ResponseBody
    public String[] save(@RequestParam("location_name") String location_name,
                     @RequestParam("location") String location,
                     @RequestParam("phoneNum") String phoneNum,
                     @RequestParam("coordinate") String coordinate){

        String num;
        String check;
        String [] list = new String[2];
        int i = dao.searchCheck(location_name);
//        System.out.println(i);
//        if(i ==0){
//                dao.save(location_name, location, phoneNum, coordinate);
//               int search = dao.search().size(); //게시글 갯수 확인
//
//               if(search==10){
//                   check="0"; //불가능
//               }
//               else{check="1";} //가능
//                num = String.valueOf(dao.numCheck());  //동적 태그 생성을 위한 id 값
//
//            } else{
//                num="실패";
//            }
//        list[0]=num;
//        list[1]=check;
        if(i==0){
            dao.save(location_name, location, phoneNum, coordinate);
            num = String.valueOf(dao.numCheck());
            check ="0";
        }else{
            num = "";
            check="1";
        }

        list[0]=num;
        list[1]=check;
        return list;

    }

    @GetMapping("/myLocation")
    @ResponseBody
    public List<vo> search(){
        List<vo> list = dao.search();
        for (vo vo1 : list) {
         //   System.out.println(vo1.getLocation_name());
          //  System.out.println(vo1.getNum());
        }

        return list;

    }

    @DeleteMapping("/myLocation")
    @ResponseBody
    public String delete(@RequestParam("location_name") String location_name){
       // System.out.println("location_name=="+location_name);
        String result;
        try {
            dao.delete(location_name);
        }catch (Exception e){
            result="error";
        }
        result="ok";
        return result;

    }

    @GetMapping("/scroll")
    @ResponseBody
    public HashSet<vo> scrollEvent(@RequestParam List<String> scrollList){
//        System.out.println("--------------------------------------");
//        System.out.println("넘어온 값 =="+scrollList.size());
        HashSet<String> temp = new HashSet<>(scrollList);
        scrollList = new ArrayList<>(temp);
        for(int i=0; i<scrollList.size(); i++){
           // System.out.println("현재 표시된 값 =="+scrollList.get(i));
        }

        List<vo> allList = dao.allSearch(); // 전체 가져오기
       // System.out.println("전체 사이즈 :=="+allList.size());
        HashSet<vo> nextScrollList = new HashSet<>();
        if(scrollList.size()!=allList.size()) {
            for (vo vo1 : allList) {
                boolean check = true;
                for (int i = 0; i < scrollList.size(); i++) {
                    if (vo1.getLocation_name().equals(scrollList.get(i))) {
                        check = false;
                    }
                }

                if (check) {
                  //  System.out.println("화면에 띄울거에요 ==" + vo1.getLocation_name());
                    nextScrollList.add(vo1);
                    if (nextScrollList.size() == 5) {
                        break;
                    }
                }
            }
        }else if(scrollList.size()>=allList.size()){
            HashSet<vo> nullList = new HashSet<>();
            vo vo = new vo();
            nullList.add(vo);
            //System.out.println("모든값 넣어짐");
            return nullList;
        }
//
//        System.out.println("사이즈는 ? =="+nextScrollList.size());
//        if(nextScrollList.size()!=0){
//            System.out.println("값 넣어서 return");
//            return nextScrollList;
//        }else{
//            List<vo> temp = new ArrayList<>();
//            vo vo = new vo();
//            temp.add(vo);
//            System.out.println("null값 return");
//            return temp;
//        }
        return nextScrollList;

    }



}
