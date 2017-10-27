/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javafx.application.Application;
import static javafx.application.Platform.exit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sun.audio.AudioPlayer;

/**
 *
 * @author RifatRubayatul
 */

public class Main extends Application {
    int i=0;
    
    @Override
    public void start(Stage primaryStage) {
        
        double bx=211,by=300;
        AnchorPane root = new AnchorPane();
       
        String path2 = AudioPlayer.class.getResource("/flourish.mp3").toString();
        Media media2 = new Media(path2);
        MediaPlayer mp2 = new MediaPlayer(media2);
        mp2.play();

        
        Canvas canvas=new Canvas(1308,653);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        Mountain mountain=new Mountain();
        mountain.drawMountain(gc);
        root.getChildren().add(canvas);
       
       HBox hb=new HBox(100);
       hb.setAlignment(Pos.BOTTOM_CENTER);
       
       root.getChildren().add(hb);
       Button btnNw=new Button("Fire");
       
       
       
       
        GridPane grid=new GridPane();
        grid.setAlignment(Pos.BOTTOM_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        
        Button btn=new Button("Play");
        Button btn1=new Button("Sound");
        Button btn2=new Button("Music");
        Button btn3=new Button("Exit");
        Button btn4=new Button("About");
        
        HBox hbox=new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.getChildren().addAll(btn3,btn4,btn,btn1,btn2);
        grid.add(hbox,1,1);
        
        VBox vbox=new VBox(10);
        vbox.setAlignment(Pos.CENTER); 
        
        Button btnBack=new Button("Back");
        Button btnSingle=new Button("Single Player");
        Button btnMulti=new Button("MultiPlayer");
        Button btnLan=new Button("Lan");
        
        vbox.getChildren().addAll(btnSingle,btnMulti,btnLan,btnBack);
        
        Scene scene1=new Scene(grid,1308,653);
        Scene scene2=new Scene(vbox,1308,653);
        Scene scene3=new Scene(root,1308,653);
        
        btn.setOnAction(e->primaryStage.setScene(scene2));
        btnBack.setOnAction(e->primaryStage.setScene(scene1));
        btn3.setOnAction(e->exit());
        btnMulti.setOnAction(e->primaryStage.setScene(scene3));
        scene3.setOnMouseClicked(event->{
        System.out.println(event.getX()+" "+event.getY());
        });
        
          
          Text txt2= new Text(1000,640,"ANGLE :");
          txt2.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
          Text txt= new Text(1130,640,"45");
          txt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
          Text txt1= new Text(730,640,"50");
          txt1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
          Text txt3= new Text(600,640,"SPEED :");
          txt3.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
          Text txtMove= new Text(100,640,"MOVE :");
          txtMove.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
          Text txtMoveNum= new Text(230,640,"4");
          txtMoveNum.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
          
          Text txtScore= new Text(60,50,"Player 1:");
          txtScore.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
          Text txtScore2= new Text(1100,50,"Player 2:");
          txtScore2.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
          
          Text score1= new Text(200,50,"0");
          score1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
          Text score2= new Text(1240,50,"0");
          score2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
          
        

       Controller cont=new Controller(root,mountain,gc);
        
        Image arrow=new Image("arrow.png");
        ImageView arrowView=new ImageView(arrow);
        root.getChildren().add(arrowView);
        arrowView.setX(180);
        arrowView.setY(200);
        
        scene3.setOnKeyPressed((KeyEvent e) -> {
            Tank tank=cont.getTank();
            Tank enemy=cont.getEnemyTank();
            enemy.isFirable=false;
            

            if(e.getCode()==KeyCode.EQUALS && tank.isFirable && tank.speed<150)
            {
                tank.speed++;
                txt1.setText(String.format("%d",tank.speed-50));
            }
            if(e.getCode()==KeyCode.MINUS && tank.isFirable && tank.speed>50)
            {
                tank.speed--;
                txt1.setText(String.format("%d",tank.speed-50));
            }
            if(e.getCode()==KeyCode.UP  && tank.isFirable)
            {
                if(tank.tankDirection==1 && tank.canonAngle>-180)
                {
                    tank.canonAngle--;
                    txt.setText(String.format("%d",-tank.canonAngle));
                }
                else if(tank.tankDirection==2 && tank.canonAngle<0)
                {
                    tank.canonAngle++;
                    txt.setText(String.format("%d",180+tank.canonAngle));
                }
                tank.rotateCanon();
            }
             
            if(e.getCode()==KeyCode.DOWN  && tank.isFirable)
            {
                if(tank.tankDirection==1 && tank.canonAngle<0)
                {
                    tank.canonAngle++;
                    txt.setText(String.format("%d",-tank.canonAngle));
                }
                else if(tank.tankDirection==2 && tank.canonAngle>-180)
                {
                    tank.canonAngle--;
                    txt.setText(String.format("%d",180+tank.canonAngle));
                }
                
                tank.rotateCanon();
            }
            if(e.getCode()==KeyCode.RIGHT && tank.tankX<1308-70-20 && tank.isFirable && tank.noOfMoves>0)
            {
                tank.move(1, mountain); 
                txtMoveNum.setText(String.format("%d",tank.noOfMoves));
            }
            if(e.getCode()==KeyCode.LEFT && tank.tankX>20 && tank.isFirable && tank.noOfMoves>0)
            {
                tank.move(2, mountain);
                txtMoveNum.setText(String.format("%d",tank.noOfMoves));
            }
             
            if(e.getCode()==KeyCode.ENTER && tank.isFirable)
            {
                double t1=bx; double t2=by;
                Balling b=null;
                if(tank.fireIndex%4==0)
                {
                    b=new Shell(t1,t2,root,5,Color.DEEPPINK,tank.tankDirection,tank.canonAngle);
                }
                else if(tank.fireIndex%4==1)
                {
                    b=new Missile(t1,t2,root,5,Color.DEEPPINK,tank.tankDirection,tank.canonAngle);
                }
                else if(tank.fireIndex%4==2)
                {
                    b=new Fire(t1,t2,root,5,Color.DEEPPINK,tank.tankDirection,tank.canonAngle);
                }
                else
                {
                    b=new Nuke(t1,t2,root,5,Color.DEEPPINK,tank.tankDirection,tank.canonAngle);
                }
                tank.fireIndex++;
                b.speed=tank.speed;
                b.angle=-tank.canonAngle;
                b.initialX=tank.tankX+35+25*cos(3.14*b.angle/180);
                b.initialY=tank.canonY-25*sin(3.14*b.angle/180);
                tank.isFirable=false;
                tank.noOfWeapons--;
                i--;
                //root.getChildren().remove(arrowView);
                //setFlag(0);
                
                b.runTimer(mountain,gc,root,tank,enemy,cont,txt,txt1,txtMoveNum,score1,score2,arrowView);
                b.startTimer(); 
                
                
            }
        });
       
       
        root.getChildren().add(txt);
        root.getChildren().add(txt2);
        root.getChildren().addAll(txt1,txt3,txtMove,txtMoveNum,txtScore,txtScore2,score1,score2);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene1);
        scene1.getStylesheets().add(Main.class.getResource("new.css").toExternalForm());
        scene2.getStylesheets().add(Main.class.getResource("new.css").toExternalForm());
        scene3.getStylesheets().add(Main.class.getResource("new_1.css").toExternalForm());
        primaryStage.show();
    }
    
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    int setFlag(int a)
    {
        i=a;
        return (i);
    }
    
}
