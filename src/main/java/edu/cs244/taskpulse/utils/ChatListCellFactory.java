package edu.cs244.taskpulse.utils;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ChatListCellFactory implements Callback<ListView<String>, ListCell<String>> {

    @Override
    public ListCell<String> call(ListView<String> param) {
        return new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setBackground(null);
                } else {
                    setText(item);

                    // Set the background color for each cell
                    setBackground(null); // Set to null for default appearance
                }
            }
        };
    }
}
