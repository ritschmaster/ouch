package ouch.gui;

import ouch.transcoders.Transformable;

public interface TranscoderSetable {
	public void setTranscoderInput(Transformable transcoder);
	public void setTranscoderOutput(Transformable transcoder);
}
