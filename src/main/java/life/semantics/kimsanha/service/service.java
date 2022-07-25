package life.semantics.kimsanha.service;


import life.semantics.kimsanha.dao.dao;
import life.semantics.kimsanha.handler.apiHandler;
import life.semantics.kimsanha.vo.vo;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component("service")
public class service {

    private dao dao;
    private apiHandler apihandler;

    @Autowired
    public service(dao dao,apiHandler apihandler) {
        this.dao = dao;
        this.apihandler=apihandler;
    }

    public JSONArray createJsonData(String lat, String lng) throws ParseException, IOException {
        return apihandler.Callapi(lat,lng);
    }

    // 저장
    public ResponseEntity<Integer> saveLocation(String locationName, String location, String phoneNum, String coordinate) {
        int count = dao.searchCheck(locationName); //이미 저장된 값이 있는지 확인

        //저장가능
        if(count==0){
            dao.save(locationName, location, phoneNum, coordinate);
               int num = dao.numCheck(locationName); //방금 저장한 num 가져오기
            return new ResponseEntity<Integer>(num,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    //모든 장소 검색
    public List<vo> placeSearch(){
       List<vo> places = dao.search();
        return places;
    }

    //저장된 위치 삭제
    public ResponseEntity<Void> deleteLocation(String locationName ){
        int status;
        try {
            dao.delete(locationName);
           return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }

    public HashSet<vo> scrollevent(List<String> scrollList) {
        //중복 삭제하기 위한 Hash
        HashSet<String> HashList = new HashSet<>(scrollList);
        scrollList = new ArrayList<>(HashList);


        List<vo> allList = dao.allSearch(); // 전체 가져오기

        //전체 장소 list - 현재 화면에 표시된 list 담을 list
        HashSet<vo> nextScrollList = new HashSet<>();
        if(scrollList.size()!=allList.size()) {
            for (vo vo1 : allList) {
                boolean check = true;
                for (int i = 0; i < scrollList.size(); i++) {
                    if (vo1.getLocationName().equals(scrollList.get(i))) {
                        check = false;
                    }
                }

                if (check) {

                    nextScrollList.add(vo1);
                    if (nextScrollList.size() == 5) {
                        break;
                    }
                }
            }
        }else{
            HashSet<vo> nullList = new HashSet<>();
            vo vo = new vo();
            nullList.add(vo);

            return nullList;
        }
        return nextScrollList;
    }



}
