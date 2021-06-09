// Film Recommender

// A class that allows for creation and manipulation of HashMaps (registers)
// containing people and their film ratings.

package reference;

import reference.domain.Film;
import reference.domain.Person;
import reference.domain.Rating;

import java.util.*;

public class RatingRegister {
    // Map of rated films to a general audience's ratings.
    private Map<Film, List<Rating>> filmsAndRatings;
    // Map of rated films to individual people.
    private Map<Person, Map<Film, Rating>> peopleFilmsAndRatings;
    
    public RatingRegister() {
        this.filmsAndRatings = new HashMap<Film, List<Rating>>();
        this.peopleFilmsAndRatings = new HashMap<Person, Map<Film, Rating>>();  
    }
    
    // Adds a new rating to a film for our general audience map. 
    // The same film can have multiple same ratings.    
    public void addRating(Film film, Rating rating) {
        // Get our ratings list for the film passed in.
        List<Rating> ratings = this.filmsAndRatings.get(film);
        // If the ratings list is null, we don't yet have any ratings for that film,
        // so we create a ratings list for that film and append the rating passed in.
        if (ratings == null) {
            ratings = new ArrayList<Rating>();
            ratings.add(rating);
            this.filmsAndRatings.put(film, ratings);
        }
        // Otherwise, we add the rating for the film to its ratings list.
        else {
            ratings.add(rating);
        }
    }
    
    // In Reference.java, returns our map of rated films to individual people.
    public Map<Person, Map<Film, Rating>> getPeopleFilmsRatingsMap() {
        return this.peopleFilmsAndRatings;
    }
    
    // Returns a specifed rated film.    
    public List<Rating> getRatings(Film film) {
        return this.filmsAndRatings.get(film);
    }
    
    // Returns our map of rated films to a general audience's ratings.
    public Map<Film, List<Rating>> filmRatings() {
        return this.filmsAndRatings;
    }
    
    // Adds the rating of a specified film to the specified person passed in.
    public void addRating(Person person, Film film, Rating rating) {
        // If a person entry does not exist in the map, we create it.
        if (this.peopleFilmsAndRatings.get(person) == null) {
            this.peopleFilmsAndRatings.put(person, new HashMap<Film, Rating>());
        }
        // If a person entry does exist, and they have not yet rated this film,
        // we add the film and rating to that entry.
        if (!this.peopleFilmsAndRatings.get(person).containsKey(film)) {
            this.peopleFilmsAndRatings.get(person).put(film, rating);
        }
        // Then we add the rating to our film's general audience ratings' map.
        addRating(film, rating);
    }
    
    public Rating getRating(Person person, Film film) {
        // If our specified person has not rated our specified film,
        // the method returns the NOT_WATCHED constant from Rating.java.
        if (!this.peopleFilmsAndRatings.get(person).containsKey(film))
            return Rating.NOT_WATCHED;
            
        // Otherwise, we return the rating this person has given for this film.
        return this.peopleFilmsAndRatings.get(person).get(film);
    }
    
    public Map<Film, Rating> getPersonalRatings(Person person) {
        // If the specified person is not in the map, we create an entry for them
        // (a workaround to make the MOOC's automated testing work correctly).
        if(this.peopleFilmsAndRatings.get(person) == null){
           this.peopleFilmsAndRatings.put(person, new HashMap<Film, Rating>());
        }
        
        // Otherwise, we return the map of films and their ratings for the specified person.
        return this.peopleFilmsAndRatings.get(person);
    }
    
    // Returns a list of people who have rated films.
    public List<Person> reviewers() {
        ArrayList<Person> reviewers = new ArrayList<Person>();
        
        for (Person person : this.peopleFilmsAndRatings.keySet()) {
            reviewers.add(person);
        }
        
        return reviewers;
    }
}
