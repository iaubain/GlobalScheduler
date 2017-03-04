/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oltranz.globalscheduler.utilities;

import com.oltranz.globalscheduler.config.StatusConfig;
import java.io.IOException;
import static java.lang.System.out;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Hp
 */
public class DataFactory {
    
    public DataFactory() {
    }
    
    public static final String objectToString(Object object){
        ObjectMapper mapper= new ObjectMapper();

        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            out.println(StatusConfig.APP_DESC+" Mapping object: "+object.getClass().getName());
            String jsonData=mapper.writeValueAsString(object);
            return jsonData;
        } catch (Exception e) {
            out.println(StatusConfig.APP_DESC+" Failed to map object: "+object.getClass().getName()+"  due to Error: "+e.getLocalizedMessage());
        }
        return null;
    }
    
    public static final Object stringToObject(Object className, String jsonString){
        Class mClass=className.getClass();
        ObjectMapper mapper= new ObjectMapper();

            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                out.println(StatusConfig.APP_DESC+" Mapping string: "+ jsonString);
                Object result=mapper.readValue(jsonString,mClass);
                return result;
            } catch (IOException e) {
                out.println(StatusConfig.APP_DESC+" Failed mapping string: "+ jsonString+"  due to Error: "+e.getLocalizedMessage());
            }
        return null;
    }
}
