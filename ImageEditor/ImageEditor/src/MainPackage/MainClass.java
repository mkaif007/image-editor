
package MainPackage;

import ImagePackage.ImageClass;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import DebuggingPackage.DebuggingClass1;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;


public class MainClass {

    // to find the extension of file
    private static String getFileExtension(String fileName) {
        
        int i = fileName.lastIndexOf('.');
        if (i >= 0) 
            return fileName.substring(i+1);
        return "";
    }

    static JFrame mainFrame;
    static JButton browseBtn, exitBtn;
    static ActionListenerClass listener;
    static Dimension screenSize;
    static int screenHeight, screenWidth;
    public static void main(String[] args) {

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Sets the window style like windows operating system.
            //Constructing the main frame
            mainFrame = new JFrame("Image Editor");
            screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            screenHeight = (int) screenSize.getHeight();
            screenWidth = (int) screenSize.getWidth();
            int w = 20*screenWidth/100;
            int h = 15*screenHeight/100;
            mainFrame.setLocation(w,h);

            mainFrame.pack();
            mainFrame.setVisible(true);
            mainFrame.setResizable(false);
            mainFrame.setSize(300, 160);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            //Creating a panel which contains buttons
            mainFrame.setLayout(null);
            
            listener = new ActionListenerClass();
            
            //Browse Button
            browseBtn = new JButton("Browse");
            browseBtn.setBounds(50, 50, 90, 30);
            mainFrame.add(browseBtn);
            browseBtn.addActionListener(listener);
            
            //Exit Button
            exitBtn = new JButton("Exit");
            exitBtn.setBounds(150, 50, 90, 30);
            mainFrame.add(exitBtn);
            exitBtn.addActionListener(listener);
        }
        catch(HeadlessException | ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException /*| IOException*/ ex){
            DebuggingClass1 err =  new DebuggingClass1();
            err.logException(ex.toString());
        }
    }
    // func to check if file extension is within images format
    public static boolean contains(String [] stringArray, String comparingString)
    {
        for (String stringInArray : stringArray)
            if (comparingString.equals(stringInArray))
                return true;
        return false;
    }
    static class ActionListenerClass implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            // events to be done after pressing browse button
           if (actionEvent.getSource() == browseBtn)
           {
                try
                {
                    String filePath = null;
                    JFileChooser fileChooser = new JFileChooser(filePath);
                    String[] arrayOfFormats = new String[]{"jpg", "png", "gif", "bmp", "jpeg", "tiff", "JPG", "PNG", "GIF", "BMP", "JPEG", "TIFF"};
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Images", arrayOfFormats)); // Sets selectable formats to images only
                    mainFrame.setVisible(false);
                    int choosenBtn = fileChooser.showOpenDialog(fileChooser);
                    if(choosenBtn == JFileChooser.APPROVE_OPTION)
                    {
                        filePath = fileChooser.getSelectedFile().getAbsolutePath(); // Get the path of selected file
                    } else {
                        mainFrame.setVisible(true);
                    }
                    if(contains(arrayOfFormats, getFileExtension(filePath)))
                        new ImageClass(filePath); // Invoke ImageClass to run with filepath got by filechooser
                    else
                    {
                        JOptionPane.showMessageDialog(mainFrame, "This is not an image, please choose a correct file!");
                        mainFrame.setVisible(true);
                    }
                }
                catch(HeadlessException | NullPointerException ex)
                {
                    DebuggingClass1 err =  new DebuggingClass1();
                    err.logException(ex.toString()); //Store exception in error ArrayList
                }
           }
           // events to be done after pressing exit button
           else if (actionEvent.getSource() == exitBtn)
            {
                try
                {
                    if (JOptionPane.showConfirmDialog(mainFrame,"Are you sure?","Query", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                    {
                        System.exit(0);
                    }
                }
                catch(HeadlessException ex)
                {
                    DebuggingClass1 err =  new DebuggingClass1();
                    err.logException(ex.toString()); //Store exception in error ArrayList
                }
            }
        }        
    }
}
