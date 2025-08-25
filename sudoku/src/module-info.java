module sudoku {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	opens application.controllers to javafx.graphics, javafx.fxml;
}

