package Auora.model.player.clan;

import com.thoughtworks.xstream.XStream;

import java.io.*;

/**
 * @author crezzy 20%
 */
@SuppressWarnings("unchecked")
public final class XMLHandler {

    private static XStream xmlHandler;

    static {
        xmlHandler = new XStream();
        xmlHandler.alias("clan", Clan.class);
    }

    private XMLHandler() {
    }

    public static void toXML(String file, Object object) throws IOException {
        OutputStream out = new FileOutputStream(file);
        try {
            xmlHandler.toXML(object, out);
            out.flush();
        } finally {
            out.close();
        }
    }

    public static <T> T fromXML(String file) throws IOException {
        InputStream in = new FileInputStream(file);
        try {
            return (T) xmlHandler.fromXML(in);
        } finally {
            in.close();
        }
    }

}
