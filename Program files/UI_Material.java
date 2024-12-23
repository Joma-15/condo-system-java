import java.io.*;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;
import javax.swing.*;

class UI_Material extends JFrame{

    static class Visuals {
        // text color
        final static String SUCCESS = "\033[0;32m";
        final static String FAILED = "\033[0;31m";
        final static String LOADING = "\033[0;34m";
        final static String RESET = "\033[0m";
        final static String BANNER = "\033[0;36m";

        // images
        static final String imagePath = "D:\\Condominium Mangement System\\UI\\condo images\\";
        static final String[] images = {
            imagePath + "unit1.jpg",
            imagePath + "unit2.jpg",
            imagePath + "unit3.jpg",
            imagePath + "unit4.jpg",
            imagePath + "unit5.jpg"
        };

        static void delayprint(String word, int delaytime) {
            for (char w : word.toCharArray()) {
                try {
                    System.out.print(w);
                    TimeUnit.MILLISECONDS.sleep(delaytime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        static void loadStatement(String word, int delaytime) {
            System.out.printf("%s" + word + "%s", UI_Material.Visuals.LOADING, UI_Material.Visuals.RESET);
            Sounds.playSound(Sounds.sounds[2]);
            try {
                TimeUnit.MILLISECONDS.sleep(delaytime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Sounds {
        static String basePath = "D:\\Condominium Mangement System\\UI\\musicwav\\";
        static String[] sounds = {
            basePath + "login successfully.wav",
            basePath + "login failed.wav",
            basePath + "onclick.wav",
            basePath + "dataOnSave.wav"
        };

        public static void playSound(String soundname) {
            File Soundfile = new File(soundname);

            try (AudioInputStream input = AudioSystem.getAudioInputStream(Soundfile)) {
                Clip clip = AudioSystem.getClip();
                clip.open(input);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    static class Unit1 implements Runnable {
        JFrame frame;
        JLabel label;
        ImageIcon icon;

        @Override
        public void run() {
            frame = new JFrame("Condo Unit image");
            try {
                icon = new ImageIcon(Visuals.images[0]);
                label = new JLabel(icon);
                frame.add(label);

            } catch (Exception e) {
                System.out.printf("%sCannot load the image%s\n", Visuals.FAILED, Visuals.RESET);
                e.printStackTrace();
            }
            frame.pack();
            frame.setVisible(true);
        }
    }

    static class Unit2 implements Runnable {
        JFrame frame;
        JLabel label;
        ImageIcon icon;

        @Override
        public void run() {
            frame = new JFrame("Condo Unit image");
            try {
                icon = new ImageIcon(Visuals.images[1]);
                label = new JLabel(icon);
                frame.add(label);
            } catch (Exception e) {
                System.out.printf("%sCannot load the image%s\n", Visuals.FAILED, Visuals.RESET);
            }
            frame.pack();
            frame.setVisible(true);
        }

    }

    static class Unit3 implements Runnable {
        JFrame frame;
        JLabel label;
        ImageIcon icon;

        @Override
        public void run() {
            frame = new JFrame("Condo Unit image");
            try {
                icon = new ImageIcon(Visuals.images[2]);
                label = new JLabel(icon);
                frame.add(label);
            } catch (Exception e) {
                System.out.printf("%sCannot load the image%s\n", Visuals.FAILED, Visuals.RESET);
            }
            frame.pack();
            frame.setVisible(true);
        }
    }

    static class Unit4 implements Runnable {
        JFrame frame;
        JLabel label;
        ImageIcon icon;

        @Override
        public void run() {
            frame = new JFrame("Condo Unit image");
            try {
                icon = new ImageIcon(Visuals.images[3]);
                label = new JLabel(icon);
                frame.add(label);
            } catch (Exception e) {
                System.out.printf("%sCannot load the image%s\n", Visuals.FAILED, Visuals.RESET);
            }
            frame.pack();
            frame.setVisible(true);
        }
    }

    static class Unit5 implements Runnable {
        JFrame frame;
        JLabel label;
        ImageIcon icon;

        @Override
        public void run() {
            frame = new JFrame("Condo Unit image");
            try {
                icon = new ImageIcon(Visuals.images[4]);
                label = new JLabel(icon);
                frame.add(label);
            } catch (Exception e) {
                System.out.printf("%sCannot load the image%s\n", Visuals.FAILED, Visuals.RESET);
            }
            frame.setBounds(200,200, 500,500);
            frame.pack();
            frame.setVisible(true);
        }
    }
}