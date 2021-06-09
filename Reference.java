// Film Recommender

// A class that compares film ratings of different individuals in order to recommend films.

package reference;

import java.util.*;

import reference.domain.Film;
import reference.domain.Person;
import reference.comparator.FilmComparator;
import reference.domain.Rating;

public class Reference {
    private RatingRegister ratings;
    
    public Reference(RatingRegister ratings) {
        this.ratings = ratings;
    }
    
    // Helper method for recommendFilm() that gets the highest-rated 
    // film according to general audiences.
    public Film getGeneralRecommendation(Person person) {
        // Pass this.ratings to FilmComparator.
        FilmComparator filmComparator = new FilmComparator(this.ratings.filmRatings());
        // Get the average rating of each rated film.
        Map<Film, Integer> filmsAndAverageRatings = filmComparator.getFilmsAndAverageRatings();

        // A variable used to store the greatest film average.
        int greatestAverage = 0;
        // A film object to store the film with the greatest average, according to 
        // greatestAverage.
        Film filmWithGreatestAverage = new Film("");

        // Find the greatest average of all the films, based on their values.
        for (Film film : filmsAndAverageRatings.keySet())
            if (filmsAndAverageRatings.get(film) > greatestAverage) {
                greatestAverage = filmsAndAverageRatings.get(film);
            }
        // Get the actual film with the greatest average, according to greatestAverage,
        // and return it.
        for (Film film : filmsAndAverageRatings.keySet()) {
            if (greatestAverage == filmsAndAverageRatings.get(film)) {
                filmWithGreatestAverage = film;
            }
        }

        return filmWithGreatestAverage;
    }
    
    // Recommends a film to a passed-in person using a mathematical formula (explained later), 
    // based on ratings of films by other people.    
    public Film recommendFilm(Person person) {
        // If the person passed in has not yet rated any films, we have no information from 
        // them regarding their likes and dislikes (on a numerical scale), so we can't make 
        // recommendations based on that; in cases like this, we will recommend the highest- 
        // rated film from our general audiences map.
        if (this.ratings.getPersonalRatings(person).isEmpty()) {
            return getGeneralRecommendation(person);
        }
        // Otherwise, the person passed in HAS rated films, so we can use that information to
        // give them recommendations based on other people's ratings.
        else {                  
            // Declare and initialize vital variables for use in below loops.
            // A variable that stores the sum of all the ratings our passed-in 
            // person has rated.
            int personsRatingsTotal = 0;
            // A variable that stores the sum of all the ratings a rater has rated.
            int ratersRatingsTotal = 0;
            // A variable that stores the product of personRatingsTotal and
            // ratersRatingsTotal.
            int ratingComparison = 0;
            // A list that stores all of our ratingComparison values.
            ArrayList<Integer> listOfRatingComparisons = new ArrayList<Integer>();
            // A map that stores each rater with their associated ratingComparison value.
            HashMap<Person, Integer> ratersAndRatingComparisons = new HashMap<Person, Integer>();
            
            // Get rated films to individual people map for the passed-in person.
            for (Film film : this.ratings.getPeopleFilmsRatingsMap().get(person).keySet()) {
                // Get the rating for each film in this person's register and add them together.
                personsRatingsTotal += this.ratings.getPeopleFilmsRatingsMap().get(person).get(film).getValue();
            }
            
            // Gets another person (a "rater") to compare our passed-in person's ratings to.
            for (Person rater : this.ratings.getPeopleFilmsRatingsMap().keySet()) {
                // Make sure we don't compare our passed-in person with themselves.
                if (!rater.equals(person)) {
                    // Reset the rater's total each loop for each new rater.
                    ratersRatingsTotal = 0;
                    // Get each film the rater has rated.
                    for (Film film : this.ratings.getPeopleFilmsRatingsMap().get(rater).keySet()) {
                        // Get the rating for each film in this rater's register and add them together.
                        ratersRatingsTotal += this.ratings.getPeopleFilmsRatingsMap().get(rater).get(film).getValue();
                    }
                }
                // Calculate the rating comparison value between passed-in person and the rater.
                //     OUR FILM RECOMMENDATION FORMULA: 
                //         (1) For all raters: ((sum of rater's ratings) * (sum of passed-in 
                //             person's ratings) = ratingComparison value). 
                //         (2) Whoever has the highest ratingComparison amongst the raters will 
                //             be the rater who's films are recommended to our passed-in person.
                ratingComparison = ratersRatingsTotal * personsRatingsTotal;
                // Add each ratingComparison to a list to compare later with Collections.max().
                listOfRatingComparisons.add(ratingComparison);
                // Connect each rater with their ratingComparison value.
                ratersAndRatingComparisons.put(rater, ratingComparison);
            }
            
            // Sort all ratingComparisons for highest value.
            int raterWithHighestComparisonValue = Collections.max(listOfRatingComparisons);
            
            // Create a list of the films that will be recommended by our most compatible rater.
            ArrayList<Film> filmsToBeRecommended = new ArrayList<Film>();
            
            // Get each rater and their ratingComparisons.
            for (Person rater : ratersAndRatingComparisons.keySet()) {
                // If the rater's ratingComparison is equal to raterWithHighestComparisonValue,
                // we can then recommend films the rater has watched, with a couple of exceptions
                // (explained shortly below).
                if (ratersAndRatingComparisons.get(rater) == raterWithHighestComparisonValue) {
                    // Get every film our most compatible rater has watched.
                    for (Film film : this.ratings.getPersonalRatings(rater).keySet()) {
                        // If the map of our passed-in person doesn't contain any ratings for any films,
                        // we will recommend them the film most highly-rated by general audiences.
                        if (this.ratings.getPeopleFilmsRatingsMap().get(person).keySet().isEmpty()) {
                            return getGeneralRecommendation(person);
                        }
                        // Otherwise, if our passed-in person has not watched the film and the rater rated 
                        // this film favorably, we add it to our list of films to recommend.
                        else if (this.ratings.getRating(person, film) == Rating.NOT_WATCHED && (this.ratings.getRating(rater, film) == Rating.GOOD || this.ratings.getRating(rater, film) == Rating.FINE)) {
                            filmsToBeRecommended.add(film);
                        }                               
                    }  
                }
            }
            
            // Use an iterator to return each recommended film.
            Iterator iterator = filmsToBeRecommended.iterator();
            
            while (iterator.hasNext()) {
                return (Film) iterator.next();
            }
        }
        
        // Return null in all other scenarios, i.e., if our passed-in person has already 
        // seen and rated all films.
        return null;
    }    
}
