/**
 * 
 */
package de.apaxo.bedcon.test;

import static org.junit.Assert.*;

import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Ignore;
import org.junit.Test;

import de.apaxo.bedcon.FacebookRecommender;

/**
 * @author manuel
 *
 */
public class FacebookRecommenerTest {

	@Test @Ignore
	public void testRecommendThings() {
		FacebookRecommender recommender = new FacebookRecommender();
		recommender.initRecommender();
		try {
			String[] recs = recommender.recommendThings("Manuel Blechschmidt");
            // Some debugging output
            // for(String rec : recs) {
            //    System.out.println(rec);
            //}
	        assertArrayEquals(recs, new String[] {
					"ELVIS PRESLEY",
					"Guns N' Roses",
					"Dance on the Tightrope",
					"The Beatles",
					"Lauryn Hill",
                    "Beastie Boys",
					"Nikka Costa",
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
