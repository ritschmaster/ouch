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

package ouch.transcoders.Normal;

import ouch.transcoders.Metricable;

public class SubstitutedMetrics implements Metricable {
	protected int substitutedCharactersAmount;
	
	public SubstitutedMetrics() {
		this.substitutedCharactersAmount = 0;
	}
	
	public int getSubstitutedCharactersAmount() {
		return this.substitutedCharactersAmount;
	}
	
	public void increaseSubstitutedCharacterAmount() {
		this.substitutedCharactersAmount++;
	}
		
	public String toString() {
		return "Substituted characters:" + "\t" + Integer.toString(this.substitutedCharactersAmount);
	}
}
