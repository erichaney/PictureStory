import javafx.scene.image.Image;
import java.util.ArrayList;

class Room
{
    String name;
    Image image;
    double width;
    double height;
    
    ArrayList<Feature> features;
    ArrayList<Door> doors;
    
    Room(String name, String filePath)
    {
        try
        {
            this.image = new Image(filePath);
        }
        catch (Exception e)
        {
            System.out.println("Warning: Couldn't find the " + name + " image at " + filePath);
            System.out.println("Using a placeholder image instead.");

            this.image = new Image("images/default-image.png");
        }
        
        this.name = name;
        this.width = image.getWidth();
        this.height = image.getHeight();
        
        features = new ArrayList<Feature>();
        doors = new ArrayList<Door>();
    }
    
    
}