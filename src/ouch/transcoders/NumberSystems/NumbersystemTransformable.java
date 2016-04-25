package ouch.transcoders.NumberSystems;

import ouch.transcoders.Transformable;

public interface NumbersystemTransformable extends Transformable {
	public void setSource(int base);
	public void setDestination(int base);
}
