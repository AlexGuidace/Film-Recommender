// Film Recommender

// Creates a Film object.

package reference.domain;

public class Film {
    private String filmName;
    
    public Film(String name) {
        this.filmName = name;
    }
    
    public String getName() {
        return this.filmName;
    }
    
    @Override 
    public String toString() {
        return this.filmName;
    }

    // Has contract with and is used in conjunction with equals().
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (this.filmName != null ? this.filmName.hashCode() : 0);
        return hash;
    }
    
    // Used to compare films.    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Film other = (Film) obj;
        
        if ((this.filmName == null) ? (other.filmName != null) : !this.filmName.equals(other.filmName)) {
            return false;
        }
        
        return true;
    }
}
