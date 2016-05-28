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
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import ouch.Readers.StringReader;
import ouch.transcoders.Transformable;
import ouch.transcoders.Compressions.LZ77Transcoder;
import ouch.transcoders.Compressions.QuotedPrintableTranscoder;
import ouch.transcoders.Normal.MorseCodeTranscoder;
import ouch.transcoders.Normal.PlainTranscoder;
import ouch.transcoders.NumberSystems.NumberSystemTranscoder;
import ouch.transcoders.NumberSystems.NumbersystemTransformable;
import ouch.transcoders.NumberSystems.RomanNumberTranscoder;
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
				case "Quoted Printable":
					transcoder = new QuotedPrintableTranscoder();
					break;
				case "LZ77":
					transcoder = new LZ77Transcoder();
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
	private Tab numberSystemsTab;
	
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
	@FXML
	private Tab encodingsTab;
	
	
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
	
	//update Number Systems Output
	private void updateNumberSystemsOutput(String input, int base, int destBase) {
		StringReader reader = new StringReader(input);
		if (base != -100 && destBase != -100) {
	        NumberSystemTranscoder numTranscoder = new NumberSystemTranscoder();
	        numTranscoder.setSource(base);
	        numTranscoder.setDestination(destBase);
			this.NumberSystemOutput.setText(numTranscoder.encode(reader));
	        this.NumberSystemsMetrics.setText(numTranscoder.getLastDiff().toString());
		}
		else if (destBase == -100){
			NumberSystemTranscoder numTranscoder = new NumberSystemTranscoder();
	        numTranscoder.setSource(base);
	        numTranscoder.setDestination(10);
			RomanNumberTranscoder romanTranscoder = new RomanNumberTranscoder();
			StringReader getNumConverResult = new StringReader(numTranscoder.encode(reader));
			this.NumberSystemOutput.setText(romanTranscoder.encode(getNumConverResult));
			this.NumberSystemsMetrics.setText(romanTranscoder.getLastDiff().toString());
		}
		else if (base == -100){
			NumberSystemTranscoder numTranscoder = new NumberSystemTranscoder();
	        numTranscoder.setSource(10);
	        numTranscoder.setDestination(destBase);
			RomanNumberTranscoder romanTranscoder = new RomanNumberTranscoder();
			StringReader getRomanNumber = new StringReader(romanTranscoder.decode(reader));
			this.NumberSystemOutput.setText(numTranscoder.encode(getRomanNumber));
			this.NumberSystemsMetrics.setText(romanTranscoder.getLastDiff().toString());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		    //Encodings
			this.EncodingsChoiceInput.getSelectionModel().selectedItemProperty().addListener(new EncodingsChangeListener(this, true));
			this.EncodingsChoiceOutput.getSelectionModel().selectedItemProperty().addListener(new EncodingsChangeListener(this, false));
			
			this.EncodingsInput.caretPositionProperty().addListener((obs, oldPosition, newPosition) -> {
	            String text = EncodingsInput.getText();
	            this.updateEncodingOutput(text);
	        });
			
			// Number Systems
			this.NumberSystemChoiceInput.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable,
			            String oldValue, String newValue) {
			    	String text = NumberSystemInput.getText();
			    	int base = getNumSystemBase(NumberSystemChoiceInput.getValue());
		            int destBase = getNumSystemBase(NumberSystemChoiceOutput.getValue());
		            updateNumberSystemsOutput(text, base, destBase);
			    }
			});
			this.NumberSystemChoiceOutput.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable,
			            String oldValue, String newValue) {
			    	String text = NumberSystemInput.getText();
			    	int base = getNumSystemBase(NumberSystemChoiceInput.getValue());
		            int destBase = getNumSystemBase(NumberSystemChoiceOutput.getValue());
		            updateNumberSystemsOutput(text, base, destBase);
			    }
			});
			this.NumberSystemInput.caretPositionProperty().addListener((obs, oldPosition, newPosition) -> {
	            String text = NumberSystemInput.getText();
	            int base = getNumSystemBase(this.NumberSystemChoiceInput.getValue());
	            int destBase = getNumSystemBase(this.NumberSystemChoiceOutput.getValue());
	            this.updateNumberSystemsOutput(text, base, destBase);
	            
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
	
	private int getNumSystemBase(String choiceBoxValue) {
		int ret = 0;
		switch(choiceBoxValue) {
		    case "Plain":
				break;
			case "2 [Binär]":
				ret = 2;
				break;
			case "8 [Oktal]":
				ret = 8;
				break;
			case "10 [Dezimal]":
				ret = 10;
				break;
			case "12 [Duodezimal]":
				ret = 12;
				break;
			case "16 [Hexadezimal]":
				ret = 16;
				break;
			case "Roman Numbers":
				ret = -100;
				break;
			default:
				ret = Integer.parseInt(choiceBoxValue);
		}
		return ret;
	}
}
