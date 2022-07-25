package life.semantics.kimsanha.dao;

import life.semantics.kimsanha.vo.MapVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MapDAO {
    void insertLocation(String locationName,String location, String phoneNum, String coordinate) throws DataAccessException;
    List<MapVo> findTop10LocationList() throws DataAccessException; //10개만 select
    void deleteLocation(String locationName) throws DataAccessException;//삭제
    List<MapVo> findLocationList() throws DataAccessException; //전체검색
    int countLocationList(String locationName) throws  DataAccessException;
    int findLocationNum(String locationName)throws DataAccessException;
}
