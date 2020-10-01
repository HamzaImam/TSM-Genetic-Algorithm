package Util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public final class GraphicsUtility
{
//  Current methods supported are (createNewWindow, rndColor, removeCursor, and restoreCursor)
//  Go to each of the sub headings to see what each of them can do with given parameters

    private static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private static GraphicsDevice vc = env.getDefaultScreenDevice();

    private GraphicsUtility() {}
    //The createNewWindow method
    //Creates a new Window
    //-------------------------------------------------------------------------------------
    //new window with a title and an icon
    public static JFrame createNewWindow(String title, int width, int height, boolean resizable, Image icon)
    {
        JFrame temp = new JFrame(title);
        temp.setSize(width, height);
        temp.setLocationRelativeTo(null);
        temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        temp.setVisible(true);
        temp.setResizable(resizable);
        temp.setIconImage(icon);

        return temp;
    }

    //new window with a title and no icon
    public static JFrame createNewWindow(String title, int width, int height, boolean resizable)
    {
        return createNewWindow(title, width, height, resizable, new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB));
    }

    //new window with an icon and no title
    public static JFrame createNewWindow(int width, int height, boolean resizable, Image icon)
    {
        return createNewWindow("", width, height, resizable, icon);
    }

    //new window with no title and no icon
    public static JFrame createNewWindow(int width, int height, boolean resizable)
    {
        return createNewWindow("", width, height, resizable, new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB));
    }
    //-------------------------------------------------------------------------------------

    //The rndColor method
    //Returns a random Color
    //-------------------------------------------------------------------------------------
    public static Color rndColor()
    {
        int r = (int)(Math.random() * 256);
        int g = (int)(Math.random() * 256);
        int b = (int)(Math.random() * 256);

        return new Color(r, g, b);
    }
    //-------------------------------------------------------------------------------------

    public static Color blend(Color... c) {
        if (c == null || c.length <= 0) {
            return null;
        }
        float ratio = 1f / ((float) c.length);

        int a = 0;
        int r = 0;
        int g = 0;
        int b = 0;

        for (int i = 0; i < c.length; i++) {
            int rgb = c[i].getRGB();
            int a1 = (rgb >> 24 & 0xff);
            int r1 = ((rgb & 0xff0000) >> 16);
            int g1 = ((rgb & 0xff00) >> 8);
            int b1 = (rgb & 0xff);
            a += (int)(a1 * ratio);
            r += (int)(r1 * ratio);
            g += (int)(g1 * ratio);
            b += (int)(b1 * ratio);
        }

        return new Color(a << 24 | r << 16 | g << 8 | b);
    }

    //The removeCursor method
    //Removes the cursor from a JFrame or JPanel
    //-------------------------------------------------------------------------------------
    public static void removeCursor(JPanel J)
    {
        J.setCursor(J.getToolkit().createCustomCursor(new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), null ));
    }

    public static void removeCursor(JFrame J)
    {
        J.setCursor(J.getToolkit().createCustomCursor(new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), null ));
    }
    //-------------------------------------------------------------------------------------

    //The restoreCursor method
    //Restores the cursor back to its default for a JFrame or JPanel
    //-------------------------------------------------------------------------------------
    public static void restoreCursor(JPanel J)
    {
        J.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public static void restoreCursor(JFrame J)
    {
        J.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    //-------------------------------------------------------------------------------------

    //The undecorate method
    //Removes the buttons and borders from a JFrame no matter if it's already visible
    //-------------------------------------------------------------------------------------
    public static void undecorate(JFrame J)
    {
        J.dispose();
        J.setUndecorated(true);
        J.setVisible(true);
    }
    //-------------------------------------------------------------------------------------

    //The decorate method
    //Adds the buttons and borders from a JFrame no matter if it's already visible
    //-------------------------------------------------------------------------------------
    public static void decorate(JFrame J)
    {
        J.dispose();
        J.setUndecorated(false);
        J.setVisible(true);
    }
    //-------------------------------------------------------------------------------------

    //The setFullScreen method
    //Sets the JFrame to full screen if it is not fullscreen
    //-------------------------------------------------------------------------------------
    public static void setFullScreen(JFrame J)
    {
        if(vc.getFullScreenWindow() != J)
        {
            if (!J.isUndecorated())
                undecorate(J);
            vc.setFullScreenWindow(J);
        }
    }
    //-------------------------------------------------------------------------------------

    //The restoreScreen method
    //resets the JFrame if it is fullscreen
    //-------------------------------------------------------------------------------------
    public static void restoreScreen(JFrame J)
    {
        if(vc.getFullScreenWindow() == J)
        {
            if (J.isUndecorated())
                decorate(J);
            vc.setFullScreenWindow(null);
        }
    }
    //-------------------------------------------------------------------------------------
}
