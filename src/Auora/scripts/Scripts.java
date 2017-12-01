package Auora.scripts;

import Auora.model.player.Player;
import Auora.net.packethandlers.ItemActionPacketListener;

import java.util.HashMap;
import java.util.Map;

public class Scripts {

    private static final Map<String, Class<?>> dialogueScripts = new HashMap<String, Class<?>>();
    private static final Map<Short, itemScript> itemScripts = new HashMap<Short, itemScript>();
    private static final Map<Short, interfaceScript> interfaceScripts = new HashMap<Short, interfaceScript>();
    private static final Map<Integer, objectScript> objectScripts = new HashMap<Integer, objectScript>();
    private static final Map<Integer, ndropScript> ndropScripts = new HashMap<Integer, ndropScript>();

    public static ndropScript invokeDropScript(int key) {
        if (ndropScripts.containsKey(key))
            return ndropScripts.get(key);
        try {
            ndropScript value = (ndropScript) Class.forName("Auora.scripts.ndrop.n" + key).newInstance();
            ndropScripts.put(key, value);
            return value;
        } catch (InstantiationException ignored) {
        } catch (IllegalAccessException ignored) {
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }

    public static void resetScripts() {
        dialogueScripts.clear();
    }

    public static dialogueScript invokeDialogueScript(String key) {
        if (dialogueScripts.containsKey(key)) {
            try {
                return (dialogueScript) dialogueScripts.get(key).newInstance();
            } catch (InstantiationException ignored) {
            } catch (IllegalAccessException ignored) {
            }
            return null;
        }
        try {
            Class<?> value = Class.forName("Auora.scripts.dialogues." + key);
            dialogueScripts.put(key, value);
            return (dialogueScript) value.newInstance();
        } catch (InstantiationException ignored) {
        } catch (IllegalAccessException ignored) {
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }

    public static itemScript invokeItemScript(Player player, short key) {
        ItemActionPacketListener.clickItem(player, key);
        if (itemScripts.containsKey(key))
            return itemScripts.get(key);
        try {
            itemScript value = (itemScript) Class.forName("Auora.scripts.items.i" + key).newInstance();
            itemScripts.put(key, value);
            return value;
        } catch (InstantiationException ignored) {
        } catch (IllegalAccessException ignored) {
        } catch (ClassNotFoundException ignored) {
        }
        itemScript value = new itemScript();
        itemScripts.put(key, value);
        return value;
    }

    public static interfaceScript invokeInterfaceScript(short key) {
        if (interfaceScripts.containsKey(key)) {

            return interfaceScripts.get(key);
        }
        try {
            interfaceScript value = (interfaceScript) Class.forName("Auora.scripts.interfaces.i" + key).newInstance();
            interfaceScripts.put(key, value);
            return value;
        } catch (InstantiationException ignored) {
        } catch (IllegalAccessException ignored) {
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }

    public static objectScript invokeObjectScript(int key) {
        if (objectScripts.containsKey(key))
            return objectScripts.get(key);
        try {
            objectScript value = (objectScript) Class.forName("Auora.scripts.objects.o" + key).newInstance();
            objectScripts.put(key, value);
            return value;
        } catch (InstantiationException ignored) {
        } catch (IllegalAccessException ignored) {
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }

}
