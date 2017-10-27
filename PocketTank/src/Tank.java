
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RifatRubayatul
 */
public class Tank {
    Image tankImage,canonImage;
    int tankDirection,canonAngle;
    double tankX,tankY,canonX,canonY;
    AnchorPane root;
    ImageView tankImageView,canonImageView;
    boolean isFirable;
    int noOfMoves,score,noOfWeapons;
    int speed;
    int fireIndex;
    
    Tank(int direction,AnchorPane anc,double x,double y,GraphicsContext gc)
    {
        
        tankImage=new Image("tank1new.png");
        canonImage=new Image("canon.png");
        
        if(direction==1)
        {
            canonAngle=-45;
            isFirable=true;
        }
        else
        {
            canonAngle=-180+45;
            isFirable=false;
        }
        tankDirection=direction;
        root=anc;
        tankX=x;
        tankY=y;
        canonX=tankX+10;
        canonY=tankY;
        noOfMoves=4;
        score=0;
        noOfWeapons=10;
        speed=100;
        fireIndex=0;
        drawCanon(gc);
        drawTank(gc);
    }
    
    boolean move(int direction,Mountain m)
    { 
        int count=0;
        int stepCount=0;
        if(direction==1)
        {
            for(int j=(int)tankX+70;j<tankX+70+20 && j<1308;j++)
            {
                if(m.cordY[j]<tankY-15+25)
                {
                    break;
                }
                stepCount++;
            }
            if(stepCount==0 || noOfMoves==0)
                return false;
            tankX+=stepCount;
            tankY=m.cordY[(int)tankX+35]-25 ;
            
            
            for(int i=(int)tankX;i<tankX+70 && i<1308;i++)
            {
                if (m.cordY[i]>tankY+25)
                {
                    count++;
                }
            }
            if (count>35)
            {
                tankY=m.cordY[(int)(tankX+35)]-25;
            }
            if(tankY>653-25)
            {
                tankY=653-25;
            }
        }
        else
        {
            for(int j=(int)tankX;j>tankX-20 && j>0;j--)
            {
                if(m.cordY[j]<tankY-15+25)
                {
                    break;
                }
                stepCount++;
            }
            if(stepCount==0 || noOfMoves==0)
                return false;
            tankX-=stepCount;
            tankY=m.cordY[(int)tankX+35]-20 ;
            
             for(int i=(int)tankX;i<tankX+70 && i<1308;i++)
            {
                if (m.cordY[i]>tankY+25)
                {
                    count++;
                }
            }
            if (count>35)
            {
                tankY=m.cordY[(int)(tankX+35)]-20;
            }
            if(tankY>600-25)
            {
                tankY=600-25;
            }
        } 
        
        tankImageView.setX(tankX);
        tankImageView.setY(tankY);
        noOfMoves--;
        moveCanon();
        return true;
    }
    
    void drawTank(GraphicsContext gc)
    {
        tankImageView=new ImageView(tankImage);
        tankImageView.setX(tankX);
        tankImageView.setY(tankY);
        root.getChildren().add(tankImageView);
        
    }
    
    void drawCanon(GraphicsContext gc)
    {
        canonImageView=new ImageView(canonImage);
        canonImageView.setX(canonX);
        canonImageView.setY(canonY);
        canonImageView.setRotate(canonAngle);
        root.getChildren().add(canonImageView);
        
    }
    
    void moveCanon()
    {
        canonX=tankX+10;
        canonY=tankY;
        canonImageView.setX(canonX);
        canonImageView.setY(canonY);
    }
    
    void rotateCanon()
    {
        canonImageView.setRotate(canonAngle);
    }
    
    void setFall(Mountain m)
    {
        int count=0;
        for(int i=(int)tankX;i<tankX+70 && i<1308;i++)
        {
            if (m.cordY[i]>tankY+25)
            {
                count++;
            }
        }
        if (count>35)
        {
            tankY=m.cordY[(int)(tankX+35)]-25;
        }
        
        if(tankY>600-25)
        {
            tankY=600-25;
        }
        tankImageView.setX(tankX);
        tankImageView.setY(tankY);
        moveCanon();
    }
    
    boolean isHit(double x,double y)
    {
        if(x>tankX-5 && x<tankX+70+5 && y>tankY-5 && y<tankY+25)
        {
            return true;
        }
        return false;
    }  
}
