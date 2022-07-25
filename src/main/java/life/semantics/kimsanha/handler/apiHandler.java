package life.semantics.kimsanha.handler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class apiHandler {
    public JSONArray Callapi(String lat, String lng) throws ParseException, IOException,ClassCastException {
        StringBuilder sb = new StringBuilder();
            String apiUrl = "http://apis.data.go.kr/B551182/hospInfoService1/getHospBasisList1?&xPos="+lng+"&yPos="+lat+"&radius=3000&ServiceKey=5upcC9FNi3LMpRN35u9OZebvFIfyR4W1FQy%2Fy2wz8IwyS4AytqVcxvmpVvX80colSKHCnt%2BZfA%2B3AwpJalHwog%3D%3D&_type=json";

            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;
            while((returnLine = bufferedReader.readLine()) != null) {
                sb.append(returnLine);
            }
            String callData = sb.toString(); //hospital APi를 통해서 얻은 값
            return getJsonArray(callData);
    }

    private JSONArray getJsonArray(String data) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(data);
        JSONObject response = (JSONObject) obj.get("response");
        JSONObject body = (JSONObject) response.get("body");

       if(body.get("items").equals("")){
           return null;
       }
           JSONObject items = (JSONObject) body.get("items");
           return (JSONArray) items.get("item");

        }
    }


