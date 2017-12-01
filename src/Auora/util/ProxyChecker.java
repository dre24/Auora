package Auora.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Jonathan on 9/1/2016.
 */
public class ProxyChecker {

    public static boolean isProxy(String ip) {
        ArrayList<String> lines = getPage(ip);
        Iterator<String> iterator = lines.iterator();
        try {
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (line.contains("proxy_test: TRUE")) {
                    return true;
                }
                if (line.contains("proxy_test: FALSE")) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<String> getPage(String ip) {
        ArrayList<String> PAGE_LINES = new ArrayList<String>();
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        try {
            url = new URL("http://whatismyipaddress.com/ip/" + ip);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                PAGE_LINES.add(line);
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
        return PAGE_LINES;
    }
}
