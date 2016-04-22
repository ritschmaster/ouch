/************************************************************************
 * Copyright (C) 2016 Richard Paul Bäck <richard.baeck@free-your-pc.com>
 * 					  Dominik Koller <kollerdominik@icloud.com>
 * 					  Alexander Kopp <alexander.kopp@gmx.at>
 * 					  Zhe Wu <wuzhe1996@gmail.com>
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

package ouch.transcoders;

public interface TextReadable {
	public String getEntireString();
	
	/**
	 * @param amount The amount of bytes you want to read
	 * @return Returns the next bytes. If there are fewer bytes available then requested the length of the returned array is less then the given amount. 
	 */
	public byte[] getNextBytes(int amount);
	
	/**
	 * @return True when there are still bytes left to read 
	 */
	public boolean canReadBytes();
	
	/**
	 * Resets the reading for the bytes. This means that getNextBytes() starts with the first bytes again.
	 */
	public void resetByteReader();
	
	public Metricable getMetrics();
}
