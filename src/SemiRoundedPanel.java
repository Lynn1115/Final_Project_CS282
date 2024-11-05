import java.awt.*;
import javax.swing.*;

public class SemiRoundedPanel extends JPanel {
    private int radius;
    private boolean topRounded, bottomRounded;

    public SemiRoundedPanel(int radius, boolean topRounded, boolean bottomRounded) 
    {
        this.radius = radius;
        this.topRounded = topRounded;
        this.bottomRounded = bottomRounded;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);

        int topLeftRadius;
        if(topRounded)
        {
            topLeftRadius = radius;  
        }
        else
        {
            topLeftRadius = 0;
        }
        int topRightRadius;
        if(topRounded)
        {
            topRightRadius = radius;
        }
        else
        {
            topRightRadius = 0;
        }
        int bottomLeftRadius;
        if(bottomRounded)
        {
            bottomLeftRadius = radius;
        }
        else
        {
            bottomLeftRadius = 0;
        }
        int bottomRightRadius;
        if(bottomRounded)
        {
            bottomRightRadius = radius;
        }
        else
        {
            bottomRightRadius = 0;
        }
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 
                topLeftRadius, topRightRadius);
        g2.fillRoundRect(0, getHeight() - bottomLeftRadius, getWidth(), 
                bottomLeftRadius, bottomRightRadius, bottomLeftRadius);
        g2.dispose();
    }
}
