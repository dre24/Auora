package Auora.xml;

import com.thoughtworks.xstream.XStream;

import java.io.*;


/**
 * @author 'Mystic Flow <Steven@rune-server.org>
 */
@SuppressWarnings("unchecked")
public final class XMLHandler {

    private static XStream xmlHandler;

    static {
        xmlHandler = new XStream();
        /*xmlHandler.alias("spell", Spell.class);
        xmlHandler.alias("item", Item.class);
		xmlHandler.alias("rectangle", RectangularArea.class);
		xmlHandler.alias("circle", CircularArea.class);
		xmlHandler.alias("irregular", IrregularArea.class);
		xmlHandler.alias("position", Location.class);
		xmlHandler.alias("identifier", Identifier.class);
		xmlHandler.alias("ban", String.class);
		xmlHandler.alias("npcDefinition", NPCDefinition.class);
		xmlHandler.alias("weaponInterface", WeaponInterface.class);
		xmlHandler.alias("weaponButton", WeaponButton.class);*/
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
