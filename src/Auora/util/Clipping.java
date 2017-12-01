package Auora.util;

/**
 * Lol crappy clipping but works w/e
 *
 * @author Canownueasy
 */
public class Clipping {

    public static RSTile[] tiles = {
            //GE
            RSTile.createRSTile(3166, 3467),
            RSTile.createRSTile(3167, 3467),
            RSTile.createRSTile(3168, 3467),
            RSTile.createRSTile(3169, 3467),
            RSTile.createRSTile(3170, 3467),
            RSTile.createRSTile(3171, 3467),
            RSTile.createRSTile(3171, 3468),
            RSTile.createRSTile(3172, 3467),
            RSTile.createRSTile(3173, 3467),
            RSTile.createRSTile(3174, 3467),
            RSTile.createRSTile(3174, 3468),
            RSTile.createRSTile(3175, 3467),
            RSTile.createRSTile(3176, 3467),
            RSTile.createRSTile(3177, 3467),
            RSTile.createRSTile(3177, 3468),
            RSTile.createRSTile(3178, 3467),
            RSTile.createRSTile(3179, 3467),
            RSTile.createRSTile(3180, 3467),
            RSTile.createRSTile(3181, 3467),
            RSTile.createRSTile(3181, 3468),
            RSTile.createRSTile(3182, 3467),
            RSTile.createRSTile(3183, 3467),
            RSTile.createRSTile(3184, 3467),
            RSTile.createRSTile(3184, 3468),
            RSTile.createRSTile(3185, 3467),
            RSTile.createRSTile(3186, 3467),
            RSTile.createRSTile(3187, 3467),
            RSTile.createRSTile(3187, 3468),

            RSTile.createRSTile(3162, 3467),
            RSTile.createRSTile(3162, 3468),
            RSTile.createRSTile(3161, 3467),
            RSTile.createRSTile(3160, 3467),
            RSTile.createRSTile(3159, 3467),
            RSTile.createRSTile(3158, 3468),
            RSTile.createRSTile(3157, 3467),
            RSTile.createRSTile(3156, 3467),
            RSTile.createRSTile(3155, 3467),
            RSTile.createRSTile(3154, 3467),
            RSTile.createRSTile(3154, 3468),
            RSTile.createRSTile(3153, 3467),
            RSTile.createRSTile(3152, 3467),
            RSTile.createRSTile(3151, 3467),
            RSTile.createRSTile(3150, 3467),
            RSTile.createRSTile(3150, 3468),
            RSTile.createRSTile(3149, 3467),
            RSTile.createRSTile(3148, 3467),
            RSTile.createRSTile(3147, 3467),
            RSTile.createRSTile(3146, 3467),
            RSTile.createRSTile(3146, 3468),
            RSTile.createRSTile(3145, 3467),
            RSTile.createRSTile(3144, 3467),
            RSTile.createRSTile(3143, 3467),
            RSTile.createRSTile(3142, 3467),

            //Varrock
            RSTile.createRSTile(3210, 3430),
            RSTile.createRSTile(3210, 3431),
            RSTile.createRSTile(3211, 3430),
            RSTile.createRSTile(3211, 3431),
    };

    public static boolean clipped(RSTile tile) {
        for (RSTile t : tiles) {
            if (t == tile) {
                return true;
            }
        }
        return false;
    }

}
