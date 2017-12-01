package Auora.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MusicManager implements Serializable {


    private static final long serialVersionUID = -1175421989944550213L;
    public static transient short playingMusic;
    private transient Player player;
    private List<Integer> playList;
    private transient boolean playChoosedMusic;

    public MusicManager() {
        this.setPlayList(new ArrayList<Integer>());
    }

    public void playCustomMusic(short musicId) {
        setPlayingMusic(musicId);
        setPlayChoosedMusic(true);
        if (musicId != playingMusic)
            player.getFrames().sendMusic(musicId);
    }

    public void playRegionMusic() {
        if (isPlayChoosedMusic()) {
            player.getFrames().sendMusic(playingMusic);
            return;
        }
        int absX = player.getLocation().getX();
        int absY = player.getLocation().getY();
        short regionId = (short) (((player.getLocation().getRegionX() / 8) << 8) + (player.getLocation().getRegionY() / 8));
        short musicId = 102;
        short songId = 0;
        if (musicId == 102) {
            songId = 0;
        }
        if (absX >= 2839 && absX <= 2853 && absY >= 5205 && absY <= 5224 || absX >= 3143 && absX <= 3191 && absY >= 3469 && absY <= 3512) {
            musicId = 496;
            songId = 1;
        }
        if (absX >= 2053 && absX <= 2081 && absY >= 4369 && absY <= 4391) {
            musicId = 62;
            songId = 2;
        }
        if (absX >= 3179 && absX <= 3194 && absY >= 3432 && absY <= 3446) {
            musicId = 454;
            songId = 3;
        }
        if (absX >= 3136 && absX <= 3327 && absY >= 3520 && absY <= 3607
                || absX >= 3190 && absX <= 3327 && absY >= 3648 && absY <= 3839
                || absX >= 2817 && absX <= 2881 && absY >= 2943 && absY <= 2969
                || absX >= 3078 && absX <= 3106 && absY >= 3480 && absY <= 3520
                || absX >= 3095 && absX <= 3314 && absY >= 3583 && absY <= 3708
                || absX >= 2951 && absX <= 2976 && absY >= 3362 && absY <= 3391
                || absX >= 3200 && absX <= 3390 && absY >= 3840 && absY <= 3967
                || absX >= 2992 && absX <= 3007 && absY >= 3912 && absY <= 3967
                || absX >= 2946 && absX <= 2959 && absY >= 3816 && absY <= 3831
                || absX >= 3008 && absX <= 3199 && absY >= 3856 && absY <= 3903
                || absX >= 3008 && absX <= 3071 && absY >= 3600 && absY <= 3711
                || absX >= 3072 && absX <= 3327 && absY >= 3608 && absY <= 3647) {
            musicId = 719;
            songId = 4;
        }
        if (absX >= 3201 && absX <= 3227 && absY >= 2301 && absY <= 3235
                || absX >= 3091 && absX <= 3098 && absY >= 3488 && absY <= 3499
                || absX >= 3147 && absX <= 3182 && absY >= 3473 && absY <= 3505
                || absX == 0 && absY == 0
                || absX >= 3227 && absX <= 3320 && absY >= 2752 && absY <= 2789
                || absX >= 3264 && absX <= 3279 && absY >= 3672 && absY <= 3695
                || absX >= 2943 && absX <= 2950 && absY >= 3368 && absY <= 3373
                || absX >= 3264 && absX <= 2950 && absY >= 3672 && absY <= 3373
                || absX >= 3009 && absX <= 3018 && absY >= 3353 && absY <= 3358
                || absX >= 2606 && absX <= 2616 && absY >= 3088 && absY <= 3097) {
            musicId = 127;
            songId = 5;
        }
        if (absX >= 2940 && absX <= 3395 && absY >= 3520 && absY <= 4000) {
            musicId = 292;
            songId = 6;
        }
        if (absX >= 3179 && absX <= 3194 && absY >= 3432 && absY <= 3446) {
            musicId = 76;
            songId = 7;
        }
        if (regionId == 12850) {
            musicId = 718;
            songId = 8;
        } else
            //System.out.println("Missing music region : "+regionId);
            if (musicId != playingMusic)
                setPlayingMusic(musicId);
        setMusicList(songId);
        player.getFrames().sendMusic(musicId);
    }

    public void setMusicList(int songId) {
        if (songId == 0) {
            player.getFrames().sendString("Alone", 187, 4);
        } else if (songId == 1) {//SongID insted of musicId so I don't get confused.
            player.getFrames().sendString("The Trade Parade", 187, 4);
        } else if (songId == 2) {
            player.getFrames().sendString("Newbie Melody", 187, 4);
        } else if (songId == 3) {
            player.getFrames().sendString("Home Sweet Home", 187, 4);
        } else if (songId == 4) {
            player.getFrames().sendString("Honkytonky Sea Shanty", 187, 4);
        } else if (songId == 5) {
            player.getFrames().sendString("Nightfall", 187, 4);
        } else if (songId == 6) {
            player.getFrames().sendString("Wilderness", 187, 4);
        } else if (songId == 7) {
            player.getFrames().sendString("Harmony", 187, 4);
        } else if (songId == 8) {
            player.getFrames().sendString("Honkytonky Harmony", 187, 4);
        } else {
            player.getFrames().sendString("Unknown Song", 187, 4);
        }
    }

    public List<Integer> getPlayList() {
        return playList;
    }

    public void setPlayList(List<Integer> playList) {
        this.playList = playList;
    }

    public short getPlayingMusic() {
        return playingMusic;
    }

    public static void setPlayingMusic(short playingMusic) {
        playingMusic = playingMusic;
    }

    public boolean isPlayChoosedMusic() {
        return playChoosedMusic;
    }

    public void setPlayChoosedMusic(boolean playChoosedMusic) {
        this.playChoosedMusic = playChoosedMusic;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
