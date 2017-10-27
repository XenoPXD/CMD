package impl;
import javax.swing.JFrame;
 
public class Fenetre extends JFrame {
  public Fenetre(){
    this.setTitle("Test fenetre");
    this.setSize(400, 200);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
    this.setVisible(true);
  }
}