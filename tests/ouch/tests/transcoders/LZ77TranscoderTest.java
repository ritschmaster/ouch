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

package ouch.tests.transcoders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ouch.Readers.FileTextReader;
import ouch.Readers.StringReader;
import ouch.transcoders.Compressions.LZ77Transcoder;

public class LZ77TranscoderTest {
	public static final char FILE_SEPERATOR = (char) 28;
	public static final String[] SHORT_STRINGS = {
			"hello world",
			"abracadabra",
			"abababababab",
			"aaaaaaaaaaaaaaa",
			"aaaaaaaaaaaaaab",
			"In Ulm, um Ulm, und um Ulm herum.",
			"abababababababab",
			"abcdefghijklmnopqrstuvwxyz0123456789!\"§$%&/()=?²³{[]}\\",
			"",
			" ",
			"1"
			
			//"Some test sentence which is a bit longer than usual."
	};
	public static final String[] LONG_STRINGS = {"The long-string instrument is an instrument in which the string is of such a length that the fundamental transverse wave is below what a person can hear as a tone (±20 Hz). If the tension and the length result in sounds with such a frequency, the tone becomes a beating frequency that ranges from a short reverb (approx 5–10 meters) to longer echo sounds (longer than 10 meters). Besides the beating frequency, the string also gives higher pitched natural overtones. Since the length is that long, this has an effect on the attack tone. The attack tone shoots through the string in a longitudinal wave and generates the typical science-fiction laser-gun sound as heard in Star Wars.[1] The sound is also similar to that occurring in upper electricity cables for trains (which are ready made long-string instruments in a way). One early example of a long-string instrument was invented by the American composer Ellen Fullman. It is tuned in just intonation[2] and played by walking along the length of its approximately 100 90-foot-long strings and rubbing them with rosined hands and producing longitudinal vibrations. A C-clamp is used on each string for putting tension on the strings, much like a guitar capo, and a resonator is placed on the end the musician faces. This long-string instrument's range is centered on the octave of middle C[citation needed] and extends above and below this by an octave. The strings of the bass octave extend the instrument's full 90 feet.lman is not the only person who has built long-string instruments. Alvin Lucier employed a monochord of up to 25 meters in his piece Music on a Long Thin Wire (1977).[3] In 1981 Terry Fox also recorded two musical pieces called Berlin Attic Wire, Beating with a long string instrument [4] Since 1983, in addition to his work on and about the violin, Jon Rose has been bowing and recording the music of fences worldwide.[5] Throughout his career Paul Panhuysen made many large sound installations with groups of long strings.[6] George Smits built long string instruments acoustically amplified with styrofoam.The experimental luthier and recording artist Yuri Landman built a portable electric long-string instrument.[7] A three-minute solo that he recorded in a garden on this instrument can be heard on YouTube, uploaded in 2011.[8]ound lab and research centre Sonoscopia in Porto, Portugal has a permanent electric amplified long-string instrument mounted to the wall in the hall of their building. A transverse wave is a moving wave that consists of oscillations occurring perpendicular (or right angled) to the direction of energy transfer. If a transverse wave is moving in the positive x-direction, its oscillations are in up and down directions that lie in the y–z plane. Light is an example of a transverse wave. With regard to transverse waves in matter, the displacement of the medium is perpendicular to the direction of propagation of the wave. A ripple in a pond and a wave on a string are easily visualized as transverse waves. Transverse waves are waves that are oscillating perpendicularly to the direction of propagation. If you anchor one end of a ribbon or string and hold the other end in your hand, you can create transverse waves by moving your hand up and down. Notice though, that you can also launch waves by moving your hand side-to-side. This is an important point. There are two independent directions in which wave motion can occur. In this case, these motions are the y and z directions mentioned above, while the wave propagates away in the x direction. Continuing with the string example, if you carefully move your hand in a clockwise circle, you will launch waves in the form of a left-handed helix as they propagate away. Similarly, if you move your hand in a counter-clockwise circle, a right-handed helix will form. These phenomena of simultaneous motion in two directions go beyond the kinds of waves you can create on the surface of water; in general a wave on a string can be two-dimensional. Two-dimensional transverse waves exhibit a phenomenon called polarization. A wave produced by moving your hand in a line, up and down for instance, is a linearly polarized wave, a special case. A wave produced by moving your hand in a circle is a circularly polarized wave, another special case. If your motion is not strictly in a line or a circle your hand will describe an ellipse and the wave will be elliptically polarized. Electromagnetic waves behave in this same way, although it is slightly harder to see. Electromagnetic waves are also two-dimensional transverse waves. Ray theory does not describe phenomena such as interference and diffraction, which require wave theory (involving the phase of the wave). You can think of a ray of light, in optics, as an idealized narrow beam of electromagnetic radiation. Rays are used to model the propagation of light through an optical system, by dividing the real light field up into discrete rays that can be computationally propagated through the system by the techniques of ray tracing. [1] A light ray is a line or curve that is perpendicular to the light's wavefronts (and is therefore collinear with the wave vector). Light rays bend at the interface between two dissimilar media and may be curved in a medium in which the refractive index changes. Geometric optics describes how rays propagate through an optical system.[1] This two-dimensional nature should not be confused with the two components of an electromagnetic wave, the electric and magnetic field components, which are shown in the electromagnetic wave diagram here. The light wave diagram shows linear polarization. Each of these fields, the electric and the magnetic, exhibits two-dimensional transverse wave behavior, just like the waves on a string. The transverse plane wave animation shown is also an example of linear polarization. The wave shown could occur on a water surface."
	};
		//"Some test sentence which is a bit longer than usual."

	protected String[] ENCODED_STRINGS = new String[3];	
	
	private void testEncodeDecode(String[] strings) {
		for (String s : SHORT_STRINGS) {
			LZ77Transcoder t = new LZ77Transcoder();
			String s1 = t.encode(new StringReader(s));
			String out = t.decode(new StringReader(s1));
			assertEquals(s + FILE_SEPERATOR, out);
		}
		
	}
	
	@Test
	public void testEncodeDecodeShortStrings() {
		testEncodeDecode(SHORT_STRINGS);
	}
	
	@Test
	public void testEncodeDecodeLongString() {
		testEncodeDecode(LONG_STRINGS);
	}
	
	@Test
	public void testEncodeDecodeFile() {
		LZ77Transcoder t = new LZ77Transcoder();
		FileTextReader rdr = new FileTextReader("tests/testfile");
		String in = rdr.getEntireString();
		String s1 = t.encode(rdr);
		String out = t.decode(new StringReader(s1));
		assertEquals(in + FILE_SEPERATOR, out);
	}
	

	@Test
	public void testEncodeDuration1MB() {
		LZ77Transcoder t = new LZ77Transcoder();
		FileTextReader rdr = new FileTextReader("tests/1mb");
		t.encode(rdr);
	}
	
	@Test
	public void testEncodeTriple() {
		
	}
	
	@Test
	public void testDecodeTriple() {
		
	}
	

}
