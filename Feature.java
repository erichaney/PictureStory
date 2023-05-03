import javafx.scene.image.Image;
import javafx.scene.paint.Color;

class Feature
{
    String name;
    Image image;
    double x;
    double y;
    double width;
    double height;
    boolean isHidden;
    Color outlineColor;

    Feature(String name, String filePath, double x, double y)
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
        this.x = x;
        this.y = y;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.isHidden = false;
        this.outlineColor = Color.TRANSPARENT;
    }

    // This creates a feature without an image
    Feature(String name, double x, double y, double width, double height)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = null;
        this.isHidden = false;
        this.outlineColor = Color.TRANSPARENT;
    }

    boolean isClicked(double mouseX, double mouseY)
    {
        boolean mouseWithinX = x <= mouseX && mouseX <= x + width;
        boolean mouseWithinY = y <= mouseY && mouseY <= y + height;

        return mouseWithinX && mouseWithinY;
    }
}