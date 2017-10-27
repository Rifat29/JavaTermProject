
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
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
public class Controller {
    Tank []tankArr;
    int i;
    Random rand;
    Controller(AnchorPane root,Mountain mountain,GraphicsContext gc )
    {
        tankArr=new Tank[2];
        rand=new Random();
        int x1=rand.nextInt(200);
        int x2=rand.nextInt(200);
        tankArr[0]=new Tank(1,root,mountain.cordX[x1],mountain.cordY[x1]-20,gc);
        tankArr[1]=new Tank(2,root,mountain.cordX[1055+x2],mountain.cordY[1055+x2]-20,gc);
        tankArr[0].setFall(mountain);
        tankArr[1].setFall(mountain);
        i=0;
    }
    
    Tank getTank()
    {
        return tankArr[i];
    }
    Tank getEnemyTank()
    {
        return tankArr[(i+1)%2];
    }
    void next()
    {
        i=(i+1)%2;
    }
}
