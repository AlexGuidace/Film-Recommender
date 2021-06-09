// Film Recommender

// A class that sorts people by assigned numbers using the Comparator interface.

package reference.comparator;

import java.util.*;

import reference.domain.Person;

public class PersonComparator implements Comparator<Person> {
    private Map<Person, Integer> peopleIdentities;
    
    public PersonComparator(Map<Person, Integer> peopleIdentities) {
        this.peopleIdentities = peopleIdentities;
    }

    // Sort people in descending order.
    @Override
    public int compare(Person p1, Person p2) {
        if (this.peopleIdentities.get(p1) < this.peopleIdentities.get(p2)) {
            return 1;
        } else if (this.peopleIdentities.get(p2) < this.peopleIdentities.get(p1)) {
            return -1;
        // If p1 and p2 have equal values, don't sort them.    
        } else
            return 0;
    }
}
