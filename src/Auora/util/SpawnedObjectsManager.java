package Auora.util;

import Auora.rsobjects.RSObjectsRegion;


public class SpawnedObjectsManager {

    /**
     * Created by Leon
     */

    public static void loadNewObjects() {
        RSObjectsRegion.putObject(new RSObject(13623, 3090, 3498, 0, 10, 3), -1);
        RSObjectsRegion.putObject(new RSObject(4721, 3088, 3503, 0, 10, 3), -1);
    }
    
    public static void DeleteObjects() {
    	
    	int lowerobject = 0;
        int upperobject = 100000;
        
        while (lowerobject < upperobject) {
        	RSObjectsRegion.removeObject(new RSObject(lowerobject, 3101, 3515, 0, 10, 0)); 
        	lowerobject++;
        }
        while (lowerobject < upperobject) {
        	RSObjectsRegion.removeObject(new RSObject(lowerobject, 3101, 3515, 0, 1, 0)); 
        	lowerobject++;
        }
        while (lowerobject < upperobject) {
        	RSObjectsRegion.removeObject(new RSObject(lowerobject, 3101, 3515, 0, 2, 0)); 
        	lowerobject++;
        }
        while (lowerobject < upperobject) {
        	RSObjectsRegion.removeObject(new RSObject(lowerobject, 3101, 3515, 0, 3, 0)); 
        	lowerobject++;
        }
        while (lowerobject < upperobject) {
        	RSObjectsRegion.removeObject(new RSObject(lowerobject, 3101, 3515, 0, 4, 0)); 
        	lowerobject++;
        }
        while (lowerobject < upperobject) {
        	RSObjectsRegion.removeObject(new RSObject(lowerobject, 3101, 3515, 0, 5, 0)); 
        	lowerobject++;
        }
    	
    	
    }
        
      /*  int underx = 3089;
        int upperx = 3102;
        int undery = 3505;
        int uppery = 3515;
        int typeid = 0;
        int lowerobject = 0;
       // int upperobject = 100000;
        
        while (lowerobject < upperobject) {
        	RSObjectsRegion.removeObject(new RSObject(lowerobject, 3101, 3515, 0, 10, 0)); 
        	lowerobject++;
        }
        while (lowerobject < upperobject) {
        	RSObjectsRegion.removeObject(new RSObject(lowerobject, 3101, 3515, 0, 1, 0)); 
        	lowerobject++;
        }
        while (lowerobject < upperobject) {
        	RSObjectsRegion.removeObject(new RSObject(lowerobject, 3101, 3515, 0, 2, 0)); 
        	lowerobject++;
        }
        while (lowerobject < upperobject) {
        	RSObjectsRegion.removeObject(new RSObject(lowerobject, 3101, 3515, 0, 3, 0)); 
        	lowerobject++;
        }
        while (lowerobject < upperobject) {
        	RSObjectsRegion.removeObject(new RSObject(lowerobject, 3101, 3515, 0, 4, 0)); 
        	lowerobject++;
        }
        while (lowerobject < upperobject) {
        	RSObjectsRegion.removeObject(new RSObject(lowerobject, 3101, 3515, 0, 5, 0)); 
        	lowerobject++;
        }
        
       // while (typeid < 10 && upperobject > lowerobject) {
        while (upperx > underx ) {
        	while (uppery > undery)  {
        	RSObjectsRegion.removeObject(new RSObject(26962, underx, undery, 0, 10, 0));        
        	undery++;
        	}
        	underx++;
        }      
      
        //}
        
    }*/
}
