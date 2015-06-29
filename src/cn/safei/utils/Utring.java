package cn.safei.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @Copyright © 2015 sanbo Inc. All rights reserved.
 * @Description: 自定义String
 * @Version: 1.0
 * @Create: 2015年6月29日 下午8:41:01
 * @Author: sanbo
 */
public class Utring {

    /**
     * 把Stream转换成String
     * 
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
