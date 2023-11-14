import java.time.*;
import java.time.temporal.*;

public class ColorSelector {
    LocalDate today = LocalDate.now();
    LocalDate test = LocalDate.parse("2023-11-11");  //test date, need to implement accepting input from task
	
	long result = today.until(test, ChronoUnit.DAYS);
	String color = ColorOfPostIt(result);



public static String ColorOfPostIt(long result) {
    if (result <= 0) {
	    return "/fxml/Task.fxml";
	}
	else if ((result >= 1) && (result <= 4)) {
	    return "/fxml/TaskDarkYellow.fxml";
	}
	else if ((result >= 5) && (result <= 6)) {
	    return "/fxml/TaskSalmon.fxml";
	}
	else if ((result >= 7) && (result <= 14)) {
	    return "/fxml/TaskPink.fxml";
	}
	else {
	    return "/fxml/TaskBlue.fxml";
	}
	
}
}
