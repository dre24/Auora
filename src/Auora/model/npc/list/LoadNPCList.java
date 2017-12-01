/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Auora.model.npc.list;

import Auora.model.World;
import Auora.model.npc.Npc;
import Auora.util.Logger;
import Auora.util.RSTile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

/**
 * LoadNPCList.java
 *
 * @author Hadyn Fitzgerald
 * @version 1.7
 */
public class LoadNPCList {

    public LoadNPCList() {
        LoadSpawnList();
        LoadDefList();
    }

    private void LoadSpawnList() {
        String line = null;
        BufferedReader list = null;
        boolean startread;
        int npccount = 0;
        try {
            String spawnListDirectory = "C:/Users/dre/Desktop/Dres Backup/data/npcs/SpawnList.txt";
            list = new BufferedReader(new FileReader(spawnListDirectory));
        } catch (Exception e) {
            Logger.log(this, "Spawn List Not Found.");
        }
        try {
            while ((line = list.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);


               /* if (line.equals("[SPAWNLISTSTART]")) {
                    startread = true;
                }*/
                //if (startread = true) {
                int max = st.countTokens();
                String[] data = new String[max];
                for (int i = 0; i < max; i++) {
                    data[i] = st.nextToken();
                }
                World.getNpcs().add(new Npc(
                        Short.parseShort(data[0]), new RSTile(
                        Short.parseShort(data[1]), Short.parseShort(data[2]),
                        Byte.parseByte(data[3]), 0),
                        Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                        Integer.parseInt(data[6]), Integer.parseInt(data[7]), 2)
                );
                npccount++;
                // }

                if (line.equals("[SPAWNLISTEND]")) {
                    Logger.log(this, "Spawn List Successfully Loaded " + npccount + " NPCs...");
                    list.close();
                    return;
                }


            }
        } catch (Exception e) {
            Logger.log(this, "Spawn List Failed To Load...");
            return;
        }
    }

    private void LoadDefList() {
        String line = null;
        BufferedReader list = null;
        Boolean startread;
        int npccount = 0;
        try {
            String defListDirectory = "C:/Users/dre/Desktop/Dres Backup/data/npcs/DefList.txt";
            list = new BufferedReader(new FileReader(defListDirectory));
        } catch (Exception e) {
            Logger.log(this, "Definition List Not Found...");
            return;
        }
        try {
            while ((line = list.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                if (line.equals("[DEFLISTEND]")) {
                    Logger.log(this, "Definition List Successfully Loaded " + npccount + " NPCs...");
                    list.close();
                    return;
                }

               /* if (line.equals("[DEFLISTSTART]")) {
                    startread = true;
                }*/
                //if (startread = true) {
                NPCDefinition npcd = null;
                int max = st.countTokens();
                String[] data = new String[29];
                for (int i = 0; i < max; i++) {
                    data[i] = st.nextToken();
                }
                setDefinitions(data, npcd, Boolean.parseBoolean(data[1]));
                npccount++;
                //}

            }
        } catch (Exception e) {
            Logger.log(this, "Definition List Failed To Load...");
        }
    }

    private void setDefinitions(String[] data, NPCDefinition npcd, boolean iscombat) {
        if (!iscombat) {
            npcd = new NPCDefinition(Integer.parseInt(data[0]), iscombat);
            for (Npc npc : World.getNpcs()) {
                if (npc.getId() == npcd.getID()) {
                    npc.setCombatDefinitions(npcd);
                }
            }
        }
        if (iscombat) {
            for (int i = 0; i < 29; i++) {
                if (data[i] == null) {
                    data[i] = "-1";
                }
            }
            npcd = new NPCDefinition(Integer.parseInt(data[0]),
                    Boolean.parseBoolean(data[1]),
                    Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]),
                    Boolean.parseBoolean(data[4]),
                    Integer.parseInt(data[5]),
                    Integer.parseInt(data[6]),
                    Integer.parseInt(data[7]),
                    Integer.parseInt(data[8]),
                    Integer.parseInt(data[9]),
                    Integer.parseInt(data[10]),
                    Integer.parseInt(data[11]),
                    Integer.parseInt(data[12]),
                    Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]),
                    Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]),
                    Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]),
                    Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]),
                    Integer.parseInt(data[21]),
                    Boolean.parseBoolean(data[22]),
                    Boolean.parseBoolean(data[23]),
                    Boolean.parseBoolean(data[24]),
                    Boolean.parseBoolean(data[25]),
                    Boolean.parseBoolean(data[26]),
                    Boolean.parseBoolean(data[27]),
                    Integer.parseInt(data[28]));
            for (Npc npc : World.getNpcs()) {
                if (npc.getId() == npcd.getID()) {
                    npc.setCombatDefinitions(npcd);
                }
            }

        }
    }
}