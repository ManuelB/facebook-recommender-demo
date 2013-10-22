package de.apaxo.bedcon;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.MemoryIDMigrator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDPlusPlusFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

/**
 * This class shows some examples which are implemented
 * in java for showing how to use the different recommenders
 * in java.
 * It uses animals and foods as examples.
 * @author Manuel Blechschmidt <blechschmidt@apaxo.de>
 *
 */
public class AnimalFoodRecommender {
	
	private MemoryIDMigrator id2thing = new MemoryIDMigrator();
	private List<String> foods = new ArrayList<String>();
	private List<String> animals = new ArrayList<String>();
	private DataModel model;
	
	private Float[][] preferences = new Float[][]	{
			// Carrots  Grass  Pork	Beef	Corn	Fish
			// Rabbit
			new Float[] {10f,  7f, 1f,  2f, null,  1f},
			// Cow
			new Float[] { 7f, 10f, null, null, null, null},
			// Dog
			new Float[] {null,  1f, 10f, 10f, null, null},
			// Pig
			new Float[] { 5f,  6f,  4f, null,  7f,  3f},
			// Chicken
			new Float[] { 7f,  6f,  2f, null, 10f, null},
			// Pinguin
			new Float[] { 2f,  2f, null,  2f,  2f, 10f},
			// Bear
			new Float[] { 2f, null,  8f,  8f,  2f,  7f},
			// Lion
			new Float[] {null, null,  9f, 10f,  2f, null},
			// Tiger
			new Float[] {null, null,  8f, null, null,  5f},
			// Antilope
			new Float[] { 6f, 10f,  1f,  1f, null, null},
			// Wolf
			new Float[] { 1f, null, null,  8f, null,  3f},
			// Sheep
			new Float[] {null,  8f, null, null, null, 2f}
	};
	
	
	public AnimalFoodRecommender() {
		initMemoryMigrator();
		initDataModel();
		initRecommender();
	}
	
	/**
	 * This function generates ids for
	 * the different things in the demp
	 */
	private void initMemoryMigrator() {
		foods.add("Carrots");
		foods.add("Grass");
		foods.add("Pork");
		foods.add("Beef");
		foods.add("Corn");
		foods.add("Fish");
		for(String food : foods) {
			id2thing.storeMapping(id2thing.toLongID(food), food);
			System.out.println(food+" = "+id2thing.toLongID(food));
		}
		animals.add("Rabbit");
		animals.add("Cow");
		animals.add("Dog");
		animals.add("Pig");
		animals.add("Chicken");
		animals.add("Pinguin");
		animals.add("Bear");
		animals.add("Lion");
		animals.add("Tiger");
		animals.add("Antilope");
		animals.add("Wolf");
		animals.add("Sheep");
		for(String animal : animals) {
			id2thing.storeMapping(id2thing.toLongID(animal), animal);
			System.out.println(animal+" = "+id2thing.toLongID(animal));
		}
	}
	
	public void initDataModel() {
		FastByIDMap<PreferenceArray> preferenceMap = new FastByIDMap<PreferenceArray>();
		for(int i=0;i<animals.size();i++) {
			List<Preference> userPreferences = new ArrayList<Preference>();
			long userId = id2thing.toLongID(animals.get(i));
			for(int j=0;j<foods.size();j++) {
				if(preferences[i][j] != null) {
					userPreferences.add(new GenericPreference(userId, id2thing.toLongID(foods.get(j)), preferences[i][j]));
				}
			}
			GenericUserPreferenceArray userArray = new GenericUserPreferenceArray(userPreferences);
			preferenceMap.put(userId, userArray);
		}
		model = new GenericDataModel(preferenceMap);
	}
	
	public void initRecommender() {
		try {
			
			PearsonCorrelationSimilarity pearsonSimilarity = new PearsonCorrelationSimilarity(model);
			
			// Java: Similarity between Wolf and Bear: 0.8196561646738477
			// R: corr(c(8,3,1),c(8,7,2)): 0.8196562
			System.out.println("Similarity between Wolf and Bear: "+pearsonSimilarity.userSimilarity(id2thing.toLongID("Wolf"), id2thing.toLongID("Bear")));
			// Similarity between Wolf and Rabbit: -0.6465846072812313
			// R: cor(c(8,3,1),c(2,1,10)): -0.6465846
			System.out.println("Similarity between Wolf and Rabbit: "+pearsonSimilarity.userSimilarity(id2thing.toLongID("Wolf"), id2thing.toLongID("Rabbit")));
			// Similarity between Wolf and Pinguin: -0.24019223070763077
			// R: cor(c(8,3,1),c(2,10,2)): -0.2401922
			System.out.println("Similarity between Wolf and Pinguin: "+pearsonSimilarity.userSimilarity(id2thing.toLongID("Wolf"), id2thing.toLongID("Pinguin")));
			
			GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, new NearestNUserNeighborhood(3, pearsonSimilarity, model), pearsonSimilarity);
			for(RecommendedItem r : recommender.recommend(id2thing.toLongID("Wolf"), 3)) {
				// Pork:
				// (0.8196561646738477 * 8 + (-0.6465846072812313) * 1) / (0.8196561646738477 + (-0.6465846072812313)) = 34,15157 ~ 10
				// Grass:
				// (2*(-0.24019223070763077)+7*(-0.6465846072812313)) / ((-0.24019223070763077) + (-0.6465846072812313)) = 5,65
				// Corn:
				// (2*(-0.24019223070763077)+2*(0.8196561646738477)) / (-0.24019223070763077+0.8196561646738477) = 2
				System.out.println("UserBased: Wolf should eat: "+id2thing.toStringID(r.getItemID())+" Rating: "+r.getValue());
			}
			SVDRecommender svdrecommender = new SVDRecommender(model, new SVDPlusPlusFactorizer(model, 4, 1000));
			for(RecommendedItem r : svdrecommender.recommend(id2thing.toLongID("Sheep"), 3)) {
				System.out.println("SVD: Sheep should eat: "+id2thing.toStringID(r.getItemID())+" Rating: "+r.getValue());
			}
		} catch (TasteException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
			new AnimalFoodRecommender();
	}
}
