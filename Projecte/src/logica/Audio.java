/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Moises
 */
public class Audio {

    private static final File SO_MENJA_MONEDES = new File("res/sons/menjaMoneda2.wav");
    private static final File SO_MENJA_ITEM = new File("res/sons/menjaItem.wav");
    private static final File SO_INICI = new File("res/sons/inici.wav");
    private static final File SO_MOV_FANTASMA = new File ("res/sons/movFantasma.wav");

    
    
    public static void reprodueixMenjaMoneda(){
       Thread fil = new Thread(){
           @Override
           public void run(){
            
               try {
                   Clip audio = AudioSystem.getClip();
                   audio.open(AudioSystem.getAudioInputStream(SO_MENJA_MONEDES));
                   audio.start();
               } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                   Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
               } 
           }
       };
       fil.start();
    }
    public static void reprodueixMenjaItem(){
        Thread fil = new Thread(){
           @Override
           public void run(){
               try {
                   Clip audio = AudioSystem.getClip();
                   audio.open(AudioSystem.getAudioInputStream(SO_MENJA_ITEM));
                   audio.start();
               } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                   Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       };
       fil.start();
    }
    
    
    public static void reprodueixInici(){
        Thread fil = new Thread(){
           @Override
           public void run(){
               try {
                   Clip audio = AudioSystem.getClip();
                   audio.open(AudioSystem.getAudioInputStream(SO_INICI));
                   audio.start();
               } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                   Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       };
        fil.start();
    }

}
