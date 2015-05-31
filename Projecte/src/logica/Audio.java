/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.IOException;
import java.io.InputStream;
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
 * 
 */
public class Audio {

//    private static final File SO_MENJA_MONEDES = new File(ClassLoader.getSystemResource("res/sons/menjaMoneda.wav").toString());
//    private static final File SO_MENJA_ITEM = new File(ClassLoader.getSystemResource("res/sons/menjaItem.wav").toString());
//    private static final File SO_INICI = new File(ClassLoader.getSystemResource("res/sons/inici.wav").toString());
//    private static final File SO_MOV_FANTASMA = new File (ClassLoader.getSystemResource("res/sons/movFantasma.wav").toString());
//    private static final File SO_APARICIO_TEM = new File (ClassLoader.getSystemResource("res/sons/apareixItem.wav").toString());
//    private static final File SO_SUBSTRACCIO_PUNTS = new File (ClassLoader.getSystemResource("res/sons/treurePunts.wav").toString());
    private static InputStream SO_MENJA_MONEDES;
    private static InputStream SO_MENJA_ITEM;
    private static InputStream SO_INICI;
    private static InputStream SO_MOV_FANTASMA;
    private static InputStream SO_APARICIO_TEM;
    private static InputStream SO_SUBSTRACCIO_PUNTS;
//    private static final String SO_MENJA_MONEDES = "../res/sons/menjaMoneda.wav";
//    private static final String SO_MENJA_ITEM = "../res/sons/menjaItem.wav";
//    private static final String SO_INICI = "../res/sons/inici.wav";
//    private static final String SO_MOV_FANTASMA ="../res/sons/movFantasma.wav";
//    private static final String SO_APARICIO_TEM = "../res/sons/apareixItem.wav";
//    private static final String SO_SUBSTRACCIO_PUNTS = "../res/sons/treurePunts.wav";
    //private static final InputStream is= Audio.class.getResourceAsStream("../res/sons/inici.wav");
    //private static final InputStream moneda= Audio.class.getResourceAsStream("../res/sons/menjaMoneda.wav");
//    
// 
//    public static void iniciarAudios(){
//        try{
//            SO_MENJA_MONEDES = ClassLoader.getSystemResource("res/sons/menjaMoneda.wav").openStream();
//            SO_MENJA_ITEM = ClassLoader.getSystemResource("res/sons/menjaItem.wav").openStream();
//            SO_INICI = ClassLoader.getSystemResource("res/sons/inici.mp3").openStream();
//            SO_MOV_FANTASMA = ClassLoader.getSystemResource("res/sons/movFantasma.wav").openStream();
//            SO_APARICIO_TEM = ClassLoader.getSystemResource("res/sons/apareixItem.wav").openStream();
//            SO_SUBSTRACCIO_PUNTS = ClassLoader.getSystemResource("res/sons/treurePunts.wav").openStream();
//        }
//        catch(Exception e){
//            System.out.println("tiru riur al obrir");
//        }
//    }
//    
//    public static void finalitzarAudios(){
//        try{
//            SO_MENJA_MONEDES.close();
//            SO_MENJA_ITEM.close();
//            SO_INICI.close();
//            SO_MOV_FANTASMA.close();
//            SO_APARICIO_TEM.close();
//            SO_SUBSTRACCIO_PUNTS.close();
//        }
//        catch(Exception e){
//            System.out.println("tiru riur al tancar");
//        }
//    }
    
    
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
