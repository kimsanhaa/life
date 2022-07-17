package life.semantics.kimsanha.service;


import life.semantics.kimsanha.dao.dao;
import life.semantics.kimsanha.vo.vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component("service")
public class service {

    private dao dao;

    @Autowired
    public service(life.semantics.kimsanha.dao.dao dao) {
        this.dao = dao;
    }

    // 저장
    public String [] save(String location_name, String location, String phoneNum, String coordinate) {
        String check;
        String num;
        String [] list = new String[2];
        int i = dao.searchCheck(location_name);

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


    //모든 장소 검색
    public List<vo> search(){
       List<vo> lists = dao.search();
        return lists;
    }

    //저장된 위치 삭제
    public void deleteLocation(String location_name ){
        dao.delete(location_name);
    }

    public HashSet<vo> scrollevent(List<String> scrollList) {
        //중복 삭제하기 위한 Hash
        HashSet<String> HashList = new HashSet<>(scrollList);
        scrollList = new ArrayList<>(HashList);
        for(int i = 0; i< scrollList.size(); i++){

        }

        List<vo> allList = dao.allSearch(); // 전체 가져오기

        //전체 장소 list - 현재 화면에 표시된 list 담을 list
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
