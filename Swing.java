import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

class JFrameThread extends Thread {


    private JFrame frame;
    private int X, Y;
    private double XDir, YDir;

    public JFrameThread(JFrame jframes, int x, int y, double xDir, double yDir) {
        this.frame = jframes;
        this.X = x;
        this.Y = y;
        this.XDir = xDir;
        this.YDir = yDir;
    }

    public void run()
    {

        int i = Swing.ID;
        
        System.out.println("Thread " + Swing.ID + " Started");

        while (true) {


            //Where to move the Jframe and how much?
            X += (int) (XDir * Swing.Speeed);
            Y += (int) (YDir * Swing.Speeed);

            //Bouncing off the walls of the screen
            if (X < 0) {
                X = 0;
                XDir *= -1;
                Swing.Points += Swing.PointsPerPoint;
            } else if (X > Swing.screenWidth - frame.getWidth() -1) {
                X = Swing.screenWidth - frame.getWidth() - 1;
                XDir *= -1;
                Swing.Points += Swing.PointsPerPoint;
            }

            //Bouncing off the roof and bottom of the screen
            if (Y < 0) {
                Y = 0;
                YDir *= -1;
                Swing.Points += Swing.PointsPerPoint;
            } else if (Y > Swing.screenHeight - frame.getHeight() - 1) {
                Y = Swing.screenHeight - frame.getHeight() - 1;
                YDir *= -1;
                Swing.Points += Swing.PointsPerPoint;

            }


            //Move the JFrame
            frame.setLocation(X, Y);

            Swing.PointsLabel.setText("Bounces: " + Swing.Points);

            //I needed this earlier so the PointsLabel would Update correctly
            //idk if still needed but why not?
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }




        }
    }
}


public class Swing {

    //lot of variable
    static JFrame[] jframes = new JFrame[100];//here is root of all JFrame

    static int X[] = new int[jframes.length];//X is value off JFrame X value position on screen
    static int Y[] = new int[jframes.length];//Y is value off JFrame Y value position on screen
    static double angle;//this is some cool math shit
    static double XDir[] = new double[jframes.length];//how much it move in X
    static double YDir[] = new double[jframes.length];//how much it move in Y
    static int screenWidth, screenHeight;//it is constant of screen size!!!  So that people of all screen sizes can use this thing

    static JFrame MainFrame = new JFrame();
    static int AmountFrames = 1;
    static double Speeed = 5;//Speed of JFrames
    static int Points = 0;//Amount of point

    static int JFrameCost = 10;//Cost of buy more quantity of JFrame
    static int SpeedCost = 5;//Cost of buy more speed
    static int PointsPerPoint = 1;//How many point i get everytime JFrame bounce
    static int PointsPerPointCost = 100;//Cost of get more point per bounce
    static JLabel PointsLabel = new JLabel("Points: " + Points);//Label shows points

    static int ID = 0;//ID is Variable that define which JFrame when you have multiple

    public static void main(String[] args) {

        int IDTemp = -1;

        //Buy more JFrames button
        JButton BuyJFrame = new JButton("Buy JFrame for " + JFrameCost);
        BuyJFrame.setSize(100,50);
        BuyJFrame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {//Do this when Button is clicked
                if (Points >= JFrameCost){

                    Points -= JFrameCost;
                    ID++;  JFrameCost *= 1.7;
                    BuyJFrame.setText("Buy JFrame for " + JFrameCost);
                    System.out.println("JFrame Bought");}
            }
        });


        //Buy more Speed button
        JButton SpeedButton = new JButton("Upgrade Speeed for " + SpeedCost);
        SpeedButton.setSize(100,50);
        SpeedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {//Do this when Button is clicked
                if (Points >= SpeedCost){

                    Points -= SpeedCost;
                    Speeed += 5;  SpeedCost *= 2;
                    SpeedButton.setText("Upgrade Speeed for " + SpeedCost);
                    System.out.println("Speed Bought");}
            }
        });


        //Buy upgrade for more Points per bounce button
        JButton PPPButton = new JButton("Upgrade Points per bounce for " + PointsPerPointCost);
        PPPButton.setSize(100,50);
        PPPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {//Do this when Button is clicked
                if (Points >= PointsPerPointCost){

                    Points -= PointsPerPointCost;
                    PointsPerPoint *= 1;  PointsPerPointCost *= 4;
                    PPPButton.setText("Upgrade Points per bounce for " + PointsPerPointCost);
                    System.out.println("PPP Bought");}
            }
        });


        //Panel Setting and add Buttons and Label to big JFrame
        JPanel Panel = new JPanel();
        Panel.add(PointsLabel);
        Panel.add(BuyJFrame);
        Panel.add(SpeedButton);
        Panel.add(PPPButton);

        //this is settings for the big JFrame
        MainFrame.add(Panel, BorderLayout.CENTER);
        MainFrame.setVisible(true);
        MainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        MainFrame.setSize(1920, 1080);


        //make screenwidth screenwidth and same for screenheight
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();

        ImageIcon image = new ImageIcon("Axel.png");


        while (true){//loop forever


            if (IDTemp != ID){//Do this once everytime ID Changes


                    //Cool math stuff to make angle and move same speed off all the JFrame and stuff
                    angle = Math.random() * 2 * Math.PI;//its math and stuff
                    XDir[ID] = Math.cos(angle);
                    YDir[ID] = Math.sin(angle);

                    //JFramesettings
                    jframes[ID] = new JFrame();
                    jframes[ID].setSize(100, 100);
                    jframes[ID].setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    jframes[ID].setVisible(true);
                    jframes[ID].setResizable(false);
                    jframes[ID].setAlwaysOnTop(true);
                    jframes[ID].setIconImage(image.getImage());

                    //Spawning at the cool randomized location waaoow!
                    X[ID] = (int) (Math.random() * (screenWidth - jframes[ID].getWidth()));
                    Y[ID] = (int) (Math.random() * (screenHeight - jframes[ID].getHeight()));
                    jframes[ID].setLocation(X[ID], Y[ID]);

                    //math that makes check if direction is doing weird shit then randomize again if thats the case
                    double magnitude = Math.sqrt(XDir[ID] * XDir[ID] + YDir[ID] * YDir[ID]);
                    while (magnitude < 0.5 || magnitude > 1) {
                        angle = Math.random() * 2 * Math.PI;
                        XDir[ID] = Math.cos(angle);
                        YDir[ID] = Math.sin(angle);
                        magnitude = Math.sqrt(XDir[ID] * XDir[ID] + YDir[ID] * YDir[ID]);
                    }
                    JFrameThread jfthread = new JFrameThread(jframes[ID], X[ID], Y[ID], XDir[ID], YDir[ID]);
                    jfthread.start();
                }

            IDTemp = ID;
            }
        }





    }



