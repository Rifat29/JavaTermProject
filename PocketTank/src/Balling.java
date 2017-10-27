
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import sun.audio.AudioPlayer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RifatRubayatul
 */
class Balling {

    double initialX=206;
    double initialY=336;
    double time=0;
    double speed=100;
    int angle=95;
    double gravity=10;
    double pi=3.1416;
    AnimationTimer at;
    AnchorPane aroot;
    Image fireImage;
    ImageView iv2;
    int damage;
    int damageRadius;
    Balling(double x,double y,AnchorPane root, double radius,Color color,int direction,int canonAngle){
       
       aroot=root;
    }
    public void rotateFire(double x,double y)
    {
        double p=Math.atan((y)/(x))*180/pi;
        iv2.setRotate(-p);
    }
    public void updateCenter()
    {
        iv2.setX(initialX+speed*(cos(angle*pi/180)*time));
        iv2.setY(initialY-(speed*(sin(angle*pi/180)*time)-.5*gravity*time*time));
        rotateFire(speed*(cos(angle*pi/180)),(speed*(sin(angle*pi/180))-gravity*time));
        
        time+=.1;    
    }
    public void setAngle(int ang)
    {
        angle = ang;
    }
    
    public void setSpeed(int spd)
    {
        speed=spd;
    }
    public void setInitialCordinate(int x,int y)
    {
        initialX=x;
        initialY=y;
    }
    public void runTimer(Mountain m,GraphicsContext gc,AnchorPane root,Tank tank,
            Tank enemy,Controller cont,Text txt,Text txt1,Text txtMoveNum,Text score1,Text score2,ImageView arrowView)
    {
        at=new AnimationTimer() {
            @Override
            public void handle(long now) {
               updateCenter();
               if(m.detectCollision(iv2.getX(), iv2.getY(), 10))
               {
                   playSound();
                   m.drawImpact(iv2.getX(), iv2.getY(), gc,damageRadius);
                   removeBall(root);
                   tank.setFall(m);
                   enemy.setFall(m);
                   enemy.isFirable=true;
                   cont.next();
                   if(enemy.tankDirection==1)
                   {
                       txt.setText(String.format("%d",-enemy.canonAngle));
                   }
                   else
                   {
                       txt.setText(String.format("%d",180+enemy.canonAngle));
                   }
                   txt1.setText(String.format("%d",enemy.speed-50));
                   txtMoveNum.setText(String.format("%d",enemy.noOfMoves));
                   checkEnd(tank,enemy,root);
                   stopTimer();
                   changeArrow(root,arrowView,enemy);
                   return;
               }
               else if(enemy.isHit(iv2.getX(),iv2.getY()))
               {
                   playSound();
                   tank.score+=damage;
                   m.drawImpact(iv2.getX(), iv2.getY(), gc,damageRadius);
                   tank.setFall(m);
                   enemy.setFall(m);
                   enemy.isFirable=true;
                   removeBall(root);
                   cont.next();
                   if(enemy.tankDirection==1)
                   {
                       txt.setText(String.format("%d",-enemy.canonAngle));
                   }
                   else
                   {
                       txt.setText(String.format("%d",180+enemy.canonAngle));
                   }
                   txt1.setText(String.format("%d",enemy.speed-50));
                   txtMoveNum.setText(String.format("%d",enemy.noOfMoves));
                   if(tank.tankDirection==1)
                   {
                       score1.setText(String.format("%d",tank.score));
                   }
                   else
                   {
                       score2.setText(String.format("%d",tank.score));
                   }
                   checkEnd(tank,enemy,root);
                   changeArrow(root,arrowView,enemy);
                   stopTimer();
               }
               else if(tank.isHit(iv2.getX(),iv2.getY()))
               {
                   playSound();
                   tank.score-=damage;
                   m.drawImpact(iv2.getX(), iv2.getY(), gc,damageRadius);
                   tank.setFall(m);
                   enemy.setFall(m);
                   enemy.isFirable=true;
                   removeBall(root);
                   cont.next();
                    if(enemy.tankDirection==1)
                   {
                       txt.setText(String.format("%d",-enemy.canonAngle));
                   }
                   else
                   {
                       txt.setText(String.format("%d",180+enemy.canonAngle));
                   }
                   txt1.setText(String.format("%d",enemy.speed-50));
                   txtMoveNum.setText(String.format("%d",enemy.noOfMoves));
                   if(tank.tankDirection==1)
                   {
                       score1.setText(String.format("%d",tank.score));
                   }
                   else
                   {
                       score2.setText(String.format("%d",tank.score));
                   }
                   checkEnd(tank,enemy,root);
                  changeArrow(root,arrowView,enemy);
                   stopTimer();
               }
               else if(iv2.getX()>1308 || iv2.getX()<0 || iv2.getY()>653)
                {

                    removeBall(root);
                    enemy.isFirable=true;
                    cont.next();
                   if(enemy.tankDirection==1)
                   {
                       txt.setText(String.format("%d",-enemy.canonAngle));
                   }
                   else
                   {
                       txt.setText(String.format("%d",180+enemy.canonAngle));
                   }
                    txt1.setText(String.format("%d",enemy.speed-50));
                    txtMoveNum.setText(String.format("%d",enemy.noOfMoves));
                    checkEnd(tank,enemy,root);
                    changeArrow(root,arrowView,enemy);
                    stopTimer();
                  
                }
            }
        };
    }
    public void checkEnd(Tank tank,Tank enemy,AnchorPane root)
    {
        if(tank.noOfWeapons==0 && enemy.noOfWeapons==0)
        {
            Text score1;
            if(tank.score>enemy.score)
            {
                score1= new Text(500,300,"Player "+tank.tankDirection+" Wins");
                score1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
            }
            else if(enemy.score>tank.score)
            {
                score1= new Text(500,300,"Player "+enemy.tankDirection+" Wins");
                score1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
            }
            else
            {
                score1= new Text(500,300,"Match Draw");
                score1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
            }
            tank.isFirable=false;
            enemy.isFirable=false;
            root.getChildren().add(score1);
        }
    }
    public void startTimer()
    {
        at.start();
    }
    public void stopTimer()
    {
        at.stop();
    }
    void removeBall(AnchorPane root)
    {
        root.getChildren().remove(iv2);
    }
    void changeArrow(AnchorPane root,ImageView arrowView,Tank enemy)
    {
        arrowView.setX(enemy.tankX);
        arrowView.setY(enemy.tankY-200);
    }
    void playSound()
    {
        String path2 = AudioPlayer.class.getResource("/bomb-02.mp3").toString();
        Media media2 = new Media(path2);
        MediaPlayer mp2 = new MediaPlayer(media2);
        mp2.play();
    }
    @Override
    protected void finalize() throws Throwable {
        
        super.finalize();
        
    } 
}