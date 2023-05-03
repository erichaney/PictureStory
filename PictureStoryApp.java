import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class PictureStoryApp extends Application
{
    Story story;

    double mouseX;
    double mouseY;

    GraphicsContext gc;
    TextArea textBox;
    Label mouseCoords;

    public void start(Stage stage)
    {
        story = new Story();

        Canvas canvas = new Canvas(600, 600);
        gc = canvas.getGraphicsContext2D();

        textBox = new TextArea(story.text);
        textBox.setFont(new Font(18));
        textBox.setMaxSize(600, 200);
        textBox.setEditable(false);
        textBox.setWrapText(true);

        mouseCoords = new Label();
        Button reset = new Button("Reset story");
        reset.setOnAction(this::handleReset);

        HBox bottomRow = new HBox(reset, new Label("    "), mouseCoords);

        VBox layout = new VBox(canvas, textBox, bottomRow);

        Scene scene = new Scene(layout);
        scene.setOnMouseMoved(this::handleMouseMoved);
        scene.setOnMouseClicked(this::handleMouseClicked);

        stage.setScene(scene);
        stage.show();

        drawCurrentRoom();
    }

    void handleReset(ActionEvent e)
    {
        story = new Story();
        drawCurrentRoom();
        textBox.setText(story.text);

    }

    void handleMouseMoved(MouseEvent m)
    {
        mouseX = m.getX();
        mouseY = m.getY();

        mouseCoords.setText((int)mouseX + ", " + (int)mouseY);
    }

    void handleMouseClicked(MouseEvent m)
    {
        if (story.theEnd == true)
        {
            return;
        }

        story.action(mouseX, mouseY);

        textBox.setText(story.text);

        drawCurrentRoom();
    }

    void drawCurrentRoom()
    {
        if (story.currentRoom.image != null)
        {
            gc.drawImage(story.currentRoom.image, 0, 0);
        }

        for (Feature f : story.currentRoom.features)
        {
            if (f.image != null && f.isHidden == false)
            {
                gc.drawImage(f.image, f.x, f.y);
            }

            if (f.isHidden == false)
            {
                gc.setStroke(f.outlineColor);
                gc.strokeRect(f.x, f.y, f.width, f.height);
            }
        }

        for (Door d : story.currentRoom.doors)
        {
            if (d.image != null)
            {
                gc.drawImage(d.image, d.x, d.y);
            }
        }

        if (story.theEnd == true)
        {
            gc.setFont(new Font(50));
            gc.setFill(Color.BLACK);
            gc.fillText("The End", 200, 300);
            gc.setFill(Color.WHITE);
            gc.fillText("The End", 202, 302);

        }
    }
}
