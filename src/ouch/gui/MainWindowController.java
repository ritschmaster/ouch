package ouch.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import ouch.Readers.StringReader;
import ouch.transcoders.Transformable;
import ouch.transcoders.Normal.MorseCodeTranscoder;
import ouch.transcoders.Normal.PlainTranscoder;
import ouch.transcoders.fun.LeetspeakTranscoder;
import ouch.transcoders.fun.MirroredTranscoder;

public class MainWindowController implements Initializable, TranscoderSetable {
	static abstract class MainWindowChangeListener {
		protected TranscoderSetable setter;
		protected boolean isInputListener;
		public MainWindowChangeListener(TranscoderSetable setter,
										boolean isInputListener) {
			this.setter = setter;
			this.isInputListener = isInputListener;
		}
	}
	static class EncodingsChangeListener extends MainWindowChangeListener implements ChangeListener {
		public EncodingsChangeListener(TranscoderSetable setter,
									   boolean isInputListener) {
			super(setter, isInputListener);
		}		
		
		@Override
		public void changed(ObservableValue arg0, Object oldSelect, Object newSelect) {
			if (newSelect != null) {
				String s = (String) newSelect;
				Transformable transcoder = null;
				switch(s) {
				case "Plain":
					transcoder = new PlainTranscoder();
					break;
				case "Morse":
					transcoder = new MorseCodeTranscoder();
					break;
				case "Mirrored":
					transcoder = new MirroredTranscoder();
					break;
				case "Leetspeak":
					transcoder = new LeetspeakTranscoder();
					break;
				}
				if (this.isInputListener)
					this.setter.setTranscoderInput(transcoder);
				else
					this.setter.setTranscoderOutput(transcoder);						
			}
		}			
	}
	
	@FXML
	private ChoiceBox<String> NumberSystemChoiceInput;	
	@FXML
	private ChoiceBox<String> NumberSystemChoiceOutput;
	@FXML
	private TextArea NumberSystemInput;
	@FXML
	private TextArea NumberSystemOutput;
	@FXML
	private TextArea NumberSystemsMetrics;
	
	@FXML
	private ChoiceBox<String> EncodingsChoiceInput;	
	@FXML
	private ChoiceBox<String> EncodingsChoiceOutput;
	@FXML
	private TextArea EncodingsInput;
	@FXML
	private TextArea EncodingsOutput;
	@FXML
	private TextArea EncodingsMetrics;
	
	private Transformable inputTranscoder = new PlainTranscoder();
	private Transformable outputTranscoder = new PlainTranscoder();
	
	private void updateEncodingOutput(String input) {
		StringReader reader = new StringReader(input);
        String output = this.inputTranscoder.decode(reader);
        
        reader = new StringReader(output);
        output = this.outputTranscoder.encode(reader);
        this.EncodingsOutput.setText(output);
        this.EncodingsMetrics.setText(this.outputTranscoder.getLastDiff().toString());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		this.EncodingsChoiceInput.getSelectionModel().selectedItemProperty().addListener(new EncodingsChangeListener(this, true));
		this.EncodingsChoiceOutput.getSelectionModel().selectedItemProperty().addListener(new EncodingsChangeListener(this, false));
		
		this.EncodingsInput.caretPositionProperty().addListener((obs, oldPosition, newPosition) -> {
            String text = EncodingsInput.getText();
            this.updateEncodingOutput(text);
        });
	}

	@FXML
    protected void onQuitApplication(ActionEvent event) {
		System.exit(0);
    }
	
	@FXML
	protected void onAbout(ActionEvent event) {
		
	}

	@Override
	public void setTranscoderInput(Transformable transcoder) {
		this.inputTranscoder = transcoder;
		this.updateEncodingOutput(this.EncodingsInput.getText());
	}

	@Override
	public void setTranscoderOutput(Transformable transcoder) {
		this.outputTranscoder = transcoder;		
		this.updateEncodingOutput(this.EncodingsInput.getText());
	}
}
