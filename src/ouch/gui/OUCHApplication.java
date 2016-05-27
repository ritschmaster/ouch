/************************************************************************
 * Copyright (C) 2016 Richard Paul Bäck <richard.baeck@free-your-pc.com>
 * 					  Dominik Koller <kollerdominik@icloud.com>
 * 					  Alexander Kopp <alexander.kopp@gmx.at>
 *
 * This file is part of OUCH.
 *
 * OUCH is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OUCH is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OUCH. If not, see <http://www.gnu.org/licenses/>.
 ***********************************************************************/

package ouch.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ouch.Readers.FileTextReader;

public class OUCHApplication extends Application {
	

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("OUCH Conversion Tool");
		System.out.println(System.getProperty("user.dir"));		
        
		Pane myPane = null;
		try {			
			// myPane = (Pane) FXMLLoader.load(getClass().getClassLoader().getResource(System.getProperty("user.dir") + "/src/main/resources/main_window.fxml"));
			myPane = (Pane) FXMLLoader.load(getClass().getClassLoader().getResource("main_window.fxml"));			
		} catch (IOException e) {
			e.printStackTrace();
		}
       Scene myScene = new Scene(myPane);
       primaryStage.setScene(myScene);
       primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
