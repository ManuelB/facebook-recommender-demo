/**
 * 
 */
package de.apaxo.bedcon.test;

import static org.junit.Assert.*;

import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;

import de.apaxo.bedcon.FacebookRecommender;

/**
 * @author manuel
 *
 */
public class FacebookRecommenerTest {

	@Test
	public void testRecommendThings() {
		FacebookRecommender recommender = new FacebookRecommender();
		recommender.initRecommender();
		try {
			String[] recs = recommender.recommendThings("Manuel Blechschmidt");
			assertArrayEquals(recs, new String[] {
					"ELVIS PRESLEY",
					"Guns N' Roses",
					"Foo Fighters",
					"Dance on the Tightrope",
					"The Beatles",
					"Lauryn Hill",
					"Queens of the Stone Age",
					"Dave Matthews Band",
					"Danko Jones",
					"Rollins, Sonny"	
			});
		} catch (TasteException e) {
			e.printStackTrace();
			fail();
		}
		
	}

}
