package life.semantics.kimsanha.service;


import life.semantics.kimsanha.dao.MapDAO;
import life.semantics.kimsanha.handler.apiHandler;
import life.semantics.kimsanha.vo.MapVo;
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
public class MapServiceImpl implements MapService {

    private MapDAO mapDAO;
    private apiHandler apihandler;

    @Autowired
    public MapServiceImpl(MapDAO mapDAO, apiHandler apihandler) {
        this.mapDAO = mapDAO;
        this.apihandler=apihandler;
    }

    public JSONArray createJsonData(String lat, String lng) throws ParseException, IOException {
        return apihandler.Callapi(lat,lng);
    }

    // 저장
    public ResponseEntity<?> addLocation(String locationName, String location, String phoneNum, String coordinate) {
        int count = mapDAO.countLocationList(locationName); //이미 저장된 값이 있는지 확인

        //저장가능
        if(count==0){
            mapDAO.insertLocation(locationName, location, phoneNum, coordinate);
               int num = mapDAO.findLocationNum(locationName); //방금 저장한 num 가져오기
            return new ResponseEntity<Integer>(num,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    //10개만 검색
    public List<MapVo> findTop10List(){
       List<MapVo> locations = mapDAO.findTop10LocationList();
        return locations;
    }

    //저장된 위치 삭제
    public ResponseEntity<Void> removeLocation(String locationName ){
        try {
            mapDAO.deleteLocation(locationName);
           return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    public HashSet<MapVo> scrollEvent(List<String> scrollList) {
        //중복 삭제하기 위한 Hash
        HashSet<String> hashScrollList = new HashSet<>(scrollList);
         scrollList = new ArrayList<>(hashScrollList);

        List<MapVo> allList = mapDAO.findLocationList(); // 전체 저장한 리스트 가져오기

        //전체 장소 list - 현재 화면에 표시된 list 담을 list
        HashSet<MapVo> nextScrollList = new HashSet<>();
        //현재 스크롤 리스트(화면에 뿌려지는 값이) != db에 저장된 list와 사이즈가 다르면 실행 -> 아직 화면에 전체 list가 없다는 뜻
        if(scrollList.size()!=allList.size()) {
            for (MapVo list : allList) { //db 리스트 값 for문
                boolean isCheck = true;
                for (int i = 0; i < scrollList.size(); i++) { //현재 화면에 뿌려진 list
                    if (list.getLocationName().equals(scrollList.get(i))) { //현재 값이 뿌려진 값과 일치하면 break
                        isCheck = false;
                        break;
                    }
                }

                if (isCheck) {
                    nextScrollList.add(list);
                    if (nextScrollList.size() == 5) {
                        break;
                    }
                }
            }
        }
        return nextScrollList;
    }



}
