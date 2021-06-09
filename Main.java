// Film Recommender

// Inspired by Netflix, this is a film "database" where users can get film recommendations 
// from other users based on general audience ratings, or on ratings made by specified users. 
// Results are displayed in the terminal.

// Main class that the film recommender program runs from.

package reference;

import reference.domain.Film;
import reference.domain.Person;
import reference.domain.Rating;

public class Main {

    public static void main(String[] args) {
        // Create a film rating register.
        RatingRegister ratings = new RatingRegister();
        
        // Create films.
        Film goneWithTheWind = new Film("Gone with the Wind");
        Film theBridgesOfMadisonCounty = new Film("The Bridges of Madison County");
        Film eraserhead = new Film("Eraserhead");
        Film bluesBrothers = new Film("Blues Brothers");
        
        // Create people to rate the films.
        Person john = new Person("John");
        Person jason = new Person("Jason");
        Person mike = new Person("Mike");
        Person nicole = new Person("Nicole");

        // For each person, add ratings for specified films.
        ratings.addRating(john, goneWithTheWind, Rating.BAD);
        ratings.addRating(john, theBridgesOfMadisonCounty, Rating.GOOD);
        ratings.addRating(john, eraserhead, Rating.FINE);

        ratings.addRating(jason, eraserhead, Rating.BAD);

        ratings.addRating(mike, bluesBrothers, Rating.GOOD);
        ratings.addRating(mike, theBridgesOfMadisonCounty, Rating.GOOD);
        
        // Create a Reference object to determine what films will be recommended.
        Reference ref = new Reference(ratings);
        System.out.println("Recommendation for " + john + ": " + ref.recommendFilm(john));
        System.out.println("Recommendation for " + jason + ": " + ref.recommendFilm(jason));
        System.out.println("Recommendation for " + mike + ": " + ref.recommendFilm(mike));
        System.out.println("Recommendation for " + nicole + ": " + ref.recommendFilm(nicole));
    }
}
