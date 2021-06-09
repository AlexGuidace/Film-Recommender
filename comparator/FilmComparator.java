// Film Recommender

// A class that sorts films by average rating using the Comparator interface.

package reference.comparator;

import java.util.*;

import reference.domain.Film;
import reference.domain.Rating;

public class FilmComparator implements Comparator<Film> {
    private Map<Film, List<Rating>> filmRatings;
    // A map that stores films and their average ratings.
    private Map<Film, Integer> filmsAndAverageRatings;
    
    public FilmComparator(Map<Film, List<Rating>> filmRatings) {
        this.filmRatings = filmRatings;
        this.filmsAndAverageRatings = new HashMap<Film, Integer>();
    }
    
    // For every rated film, calculates the average rating of a film across all user ratings.
    public void calculateIndividualFilmRatingAverages() { 
        for (Film film : this.filmRatings.keySet()) {
            // Declare and initialize a variable for the sum of ratings of each film.
            int sumOfRatings = 0;
            // Get the list of ratings for that film.
            List<Rating> ratings = this.filmRatings.get(film);
            // For each rating for this film, we add it to sumOfRatings.
            for (Rating rating : ratings) {
                sumOfRatings += rating.getValue();
            }
            
            // Calculate the average for this film.
            int singleFilmAverage = sumOfRatings / ratings.size();
            
            // Add both film and its average to the averages map.
            this.filmsAndAverageRatings.put(film, singleFilmAverage);
        }
    }
    
    // For use in Reference.java. Returns films and their average ratings.
    public Map<Film, Integer> getFilmsAndAverageRatings() {
        calculateIndividualFilmRatingAverages();
        return this.filmsAndAverageRatings;
    }

    @Override
    public int compare(Film f1, Film f2) {
        // Calculate film averages and put them in new HashMap using helper method.
        calculateIndividualFilmRatingAverages();
        // Use our helper HashMap to compare films based on rating averages.
        if (this.filmsAndAverageRatings.get(f1) > this.filmsAndAverageRatings.get(f2))
            return -1;
        else if (this.filmsAndAverageRatings.get(f2) > this.filmsAndAverageRatings.get(f1))
            return 1;
        // If f1 and f2 have equal values, don't sort them.
        else
            return 0;    
    }  
}
