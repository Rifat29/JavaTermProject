
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RifatRubayatul
 */
public class Mountain {
    double []cordX,cordY;
    Mountain()
    {
        makeArray();
    }
    
    public final void makeArray()
    {
        cordX=new double[1308];
        cordY=new double[1308];
        Random rand=new Random();
        double t=0;
        int v=100;
        int j=0;
        for(int i=0;i<1308;i++)
        {
            cordX[i]=i;
            cordY[i]=400-rand.nextInt(100);
            if(i<254 )
            {
                cordY[i]=400-rand.nextInt(5)-i/10;
            }
            else
            {
                if(i>1054)
                {
                    cordY[i]=400-rand.nextInt(5)+j/10;
                    j++;
                }               
                else
                {
                    cordY[i]=380-(140*t-.5*35.5*t*t)-rand.nextInt(10);
                    t=t+0.01;
                }   
            }    
        }
        cordY[0]=653;
        cordY[1307]=653;
    }
    boolean detectCollision(double x,double y,double radius)
    {
        if(x>=0 && x<=1307)
        {    if(cordY[(int)x]-y<=radius)
            {
                return true;
            } 
        }
        return false;
    }
    
    double[] getCordX()
    {
        return cordX;
    }
    double[] getCordY()
    {
        return cordY;
    }
    
    void drawMountain(GraphicsContext gc)
    {
      gc.setFill(Color.GREEN);
      gc.fillPolygon(cordX,cordY,1308);
      gc.setFill(Color.DARKGRAY);
      gc.fillRect(0, 600, 1308, 53);
    }
    void drawImpact(double x,double y,GraphicsContext gc,int damageRadius)
    {
        gc.clearRect(0, 0, 1308, 653);
        for (int i=(int)x;i<=x+damageRadius && i<=1307;i++ )
        {
            double localY1=y+Math.sqrt(damageRadius*damageRadius-(i-x)*(i-x));
            double localY2=y-Math.sqrt(damageRadius*damageRadius-(i-x)*(i-x));
            if(cordY[i]<=localY1 && cordY[i]>=localY2)
            {
                cordY[i]=localY1;
            }
            else
            {
                if(cordY[i]<localY2)
                {
                    cordY[i]= cordY[i]+(localY1-localY2);
                }
            }
        }
        for (int i=(int)x;i>=x-damageRadius && i>=0;i-- )
        {
            double localY1=y+Math.sqrt(damageRadius*damageRadius-(i-x)*(i-x));
            double localY2=y-Math.sqrt(damageRadius*damageRadius-(i-x)*(i-x));
            if(cordY[i]<=localY1 && cordY[i]>=localY2)
            {
                cordY[i]=localY1;
            }
            else
            {
                if(cordY[i]<localY2)
                {
                    cordY[i]= cordY[i]+(localY1-localY2);
                }
            }
        }
        drawMountain(gc);
    }
}
