package edu.cs244.taskpulse.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ChatListCell extends ListCell<String> {

	private final Text text;

    public ChatListCell() {
        text = new Text();
        text.setTextAlignment(TextAlignment.RIGHT);
        text.setWrappingWidth(300); // Set a preferred width if needed
        setGraphic(text);
        setPadding(new Insets(5));
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            text.setText(item);

            // Align to the right for user messages, and to the left for other messages
            if (isUserMessage(item)) {
                setAlignment(Pos.CENTER_RIGHT);
            } else {
                setAlignment(Pos.CENTER_LEFT);
            }

            setGraphic(text);

            setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    private boolean isUserMessage(String message) {
        // You need to implement a logic to determine if the message is from the user
        // For simplicity, I'm assuming here that user messages start with "You:"
        return message != null && message.startsWith("You:");
    }
}
