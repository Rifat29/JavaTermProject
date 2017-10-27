
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
public class Nuke extends Balling{
    
    public Nuke(double x, double y, AnchorPane root, double radius, Color color, int direction, int canonAngle) {
        super(x, y, root, radius, color, direction, canonAngle);
        
        damage=25;
        damageRadius=80;
        if(direction==1)
           fireImage=new Image("nuke1.gif");
        else
           fireImage=new Image("nuke2.gif");
       iv2 = new ImageView(fireImage);
       iv2.setX(initialX);
       iv2.setY(initialY);
       root.getChildren().add(iv2);
    }
    
}
