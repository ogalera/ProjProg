/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 *
 * @author Moises
 * @brief Encarregada de la reproducciÃ³ de Audio
 */
public class Audio {
    
    /**
     * @brief Reprodueix el so quan Pacman agafa una moneda
     */
    public static void reprodueixMenjaMoneda(){
       Thread fil = new Thread(){
           @Override
           public void run(){
               try {
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("res/sons/menjaMoneda.wav"));
                    AudioFormat format = inputStream.getFormat();
                    DataLine.Info info = new DataLine.Info(Clip.class, format);
                    Clip clip = (Clip)AudioSystem.getLine(info);
                    clip.open(inputStream);
                    clip.start();
                    clip.stop();
                    clip.close();
                    inputStream.close();
               } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                   Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
               } 
           }
       };
       fil.start();
    }
    
    /**
     * @brief Reprodueix el so quan algun personatge es menja un item
     */
    public static void reprodueixMenjaItem(){
        Thread fil = new Thread(){
           @Override
           public void run(){
               try {
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("res/sons/menjaItem.wav"));
                    AudioFormat format = inputStream.getFormat();
                    DataLine.Info info = new DataLine.Info(Clip.class, format);
                    Clip clip = (Clip)AudioSystem.getLine(info);
                    clip.open(inputStream);
                    clip.start();
                    clip.stop();
                    clip.close();
                    inputStream.close();
               } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                   Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       };
       fil.start();
    }
    
    /**
     * @brief Reprodueix una melodia al inciar una partida.
     */
    public static void reprodueixInici(){
        Thread fil = new Thread(){
           @Override
           public void run(){
               try {
                  AudioInputStream inputStream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("res/sons/inici.wav"));
                    AudioFormat format = inputStream.getFormat();
                    DataLine.Info info = new DataLine.Info(Clip.class, format);
                    Clip clip = (Clip)AudioSystem.getLine(info);
                    clip.open(inputStream);
                    clip.start();
                    clip.stop();
                    clip.close();
                    inputStream.close();
               } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                   Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       };
        fil.start();
    }
    
    /**
     * @brief Reprodueix un so quan apareix un Item a la pal partida.
     */
    public static void reprodueixAparicioItem(){
        Thread fil = new Thread(){
           @Override
           public void run(){
               try {
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("res/sons/apareixItem.wav"));
                    AudioFormat format = inputStream.getFormat();
                    DataLine.Info info = new DataLine.Info(Clip.class, format);
                    Clip clip = (Clip)AudioSystem.getLine(info);
                    clip.open(inputStream);
                    clip.start();
                    clip.stop();
                    clip.close();
                    inputStream.close();
               } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                   Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       };
        fil.start();
    }
    
    
    /**
     * @brief Reproduccio del so pertinent al event de que un personatge treu els punts a un altre personatge
     */
    public static void reprodueixSubstraccioPunts(){
        Thread fil = new Thread(){
           @Override
           public void run(){
               try {
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("res/sons/treurePunts.wav"));
                    AudioFormat format = inputStream.getFormat();
                    DataLine.Info info = new DataLine.Info(Clip.class, format);
                    Clip clip = (Clip)AudioSystem.getLine(info);
                    clip.open(inputStream);
                    clip.start();
                    clip.stop();
                    clip.close();
                    inputStream.close();
               } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                   Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       };
        fil.start();
  }

}
