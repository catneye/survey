/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ftsmk.sockets;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import ru.ftsmk.bean.UtilBeanRemote;
import ru.ftsmk.info.CatiAnswersInfo;
import ru.ftsmk.info.CatiLogInfo;
import ru.ftsmk.info.CatiQuestionsInfo;
import ru.ftsmk.info.ChampingInfo;
import ru.ftsmk.info.UsersInfo;

/**
 *
 * @author plintus
 */
public class JsonObjectDecoder implements Decoder.Text<JSONObject> {

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean willDecode(String string) {
        Json.createReader(new StringReader(string));
        return true;
    }

    @Override
    public JSONObject decode(String string) throws DecodeException {

        JsonReader jsonReader = Json.createReader(new StringReader(string));
        JsonObject jo = jsonReader.readObject();

        Logger.getLogger(JsonObjectDecoder.class.getName()).log(Level.INFO, "decode {0}", new Object[]{jo});
        JSONObject ret = new JSONObject();
        ret.setType(jo.getString("type"));
        switch (jo.getString("type")) {
            case "checklogin": {
                JsonObject obj = jo.getJsonObject("object");
                UsersInfo ui = new UsersInfo();
                ui.setLogin(obj.getString("loginname"));
                ui.setSecret(obj.getString("loginsecret"));
                ret.setObject(ui);
                break;
            }
            case "mggetchamping":
            case "mgclonechamping":
            case "mggetscenario":
            case "usergetscenario":
            case "allgetscenario":
            case "allgetnewtoken":
            case "allresettoken":
            case "logout": {
                Integer obj = jo.getInt("object");
                ret.setObject(obj);
                break;
            }
            case "allchecktoken": {
                String obj = jo.getString("object");
                ret.setObject(obj);
                break;
            }
            case "mgsavechamping": {
                JsonObject obj = jo.getJsonObject("object");
                ChampingInfo chi = new ChampingInfo();
                chi.setId(obj.getInt("id"));
                //chi.setChampingtype(obj.getString("type"));
                //chi.setIdcompany(obj.getInt("idcompany"));
                chi.setIsenable(obj.getBoolean("isenable"));
                chi.setIsforall(obj.getBoolean("isforall"));
                chi.setName(obj.getString("name"));
                chi.setDescription(obj.getString("description"));
                String df = determineDate(obj.getString("adddate"));
                if (df != null) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(df);
                        chi.setAdddate(sdf.parse(obj.getString("adddate")));
                    } catch (ParseException ex) {
                    }
                }
                ret.setObject(chi);
                break;
            }
            case "mgsavequestion": {
                JsonObject obj = jo.getJsonObject("object");
                CatiQuestionsInfo cqi = new CatiQuestionsInfo();
                cqi.setId(obj.getInt("id"));
                cqi.setIdchamping(obj.getInt("idchamping"));
                cqi.setIdparent(obj.getInt("idparent"));
                cqi.setLevel(obj.getInt("level"));
                cqi.setSelectmax(obj.getInt("selectmax"));
                cqi.setCondition(obj.getString("condition"));
                cqi.setDirection(obj.getString("direction"));
                cqi.setName(obj.getString("name"));
                cqi.setText(obj.getString("text"));
                cqi.setIsenable(obj.getBoolean("isenable"));
                cqi.setIsrandom(obj.getBoolean("israndom"));
                cqi.setIsrandomchild(obj.getBoolean("israndomchild"));
                cqi.setIsshowanswertext(obj.getBoolean("isshowanswertext"));
                ret.setObject(cqi);
                break;
            }
            case "mgsaveanswer": {
                JsonObject obj = jo.getJsonObject("object");
                CatiAnswersInfo cqi = new CatiAnswersInfo();
                cqi.setId(obj.getInt("id"));
                cqi.setIdcatiquestions(obj.getInt("idcatiquestions"));
                cqi.setIdinclude(obj.getInt("idinclude"));
                cqi.setIseditable(obj.getBoolean("iseditable"));
                cqi.setIsenable(obj.getBoolean("isenable"));
                cqi.setIsfinal(obj.getBoolean("isfinal"));
                cqi.setLevel(obj.getInt("level"));
                cqi.setName(obj.getString("name"));
                cqi.setOptions(obj.getString("options"));
                cqi.setText(obj.getString("text"));
                ret.setObject(cqi);
                break;
            }
            case "allsavescenario": {
                JsonObject obj = jo.getJsonObject("object");
                JsonObject champing = obj.getJsonObject("champing");
                Integer idchamping = champing.getInt("id");
                JsonArray quests = obj.getJsonArray("questions");
                List<CatiLogInfo> clis = new ArrayList();
                for (JsonValue questval : quests) {
                    JsonObject quest = (JsonObject) questval;
                    JsonArray answs = quest.getJsonArray("answers");
                    for (JsonValue answval : answs) {
                        CatiLogInfo cli = new CatiLogInfo();
                        //try {
                            JsonObject answ = (JsonObject) answval;
                            cli.setIdcatiquestions(quest.getInt("id"));
                            cli.setIdcatianswers(answ.getInt("id"));
                            cli.setIdchamping(idchamping);
                            cli.setIscheck(answ.getBoolean("checked"));
                            cli.setValue(answ.getString("value"));
                            clis.add(cli);
                        //} catch (ClassCastException ex) {
                        //    Logger.getLogger(JsonObjectDecoder.class.getName()).log(Level.INFO, "ClassCastException {0}", new Object[]{cli.getIdcatianswers()});
                        //}
                    }
                }
                ret.setObject(clis);
                break;
            }
        }
        return ret;
    }

    public String determineDate(String dateString) {
        Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {
            {
                put("^\\d{8}$", "yyyyMMdd");
                put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
                put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
                put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
                put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
                put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
                put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
                put("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$", "dd.MM.yyyy");
                put("^\\d{4}\\.\\d{1,2}\\.\\d{1,2}$", "yyyy.MM.dd");
                put("^\\d{12}$", "yyyyMMddHHmm");
                put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
                put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
                put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
                put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
                put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
                put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
                put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
                put("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}\\s\\d{1,2}:\\d{2}$", "dd.MM.yyyy HH:mm");
                put("^\\d{4}\\.\\d{1,2}\\.\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy.MM.dd HH:mm");
                put("^\\d{14}$", "yyyyMMddHHmmss");
                put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
                put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
                put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
                put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
                put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
                put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
                put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
                put("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd.MM.yyyy HH:mm:ss");
                put("^\\d{4}\\.\\d{1,2}\\.\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy.MM.dd HH:mm:ss");
            }
        };
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return DATE_FORMAT_REGEXPS.get(regexp);
            }
        }
        return null;
    }

}
