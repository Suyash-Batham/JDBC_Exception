package JDBC_ExceptionHandling;

class Movie {
    private String movieName;
    private String releaseDate;
    private double productionCost;
    private int noOfScreensReleased;
    private String directedBy;
    private String producedBy;
    private boolean status;
    public Movie(String movieName, String releaseDate, boolean status) {
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.status = status;
    }

    public Movie(String movieName, String directedBy, double productionCost) {
        this.movieName = movieName;
        this.directedBy = directedBy;
        this.productionCost = productionCost;
    }

    public Movie() {
        // Default constructor
    }

    public Movie(String movieName, String releaseDate, double productionCost, int noOfScreensReleased, String directedBy, String producedBy, boolean status) {
    }

    // Constructors, getter, and setter methods

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(double productionCost) {
        this.productionCost = productionCost;
    }

    public int getNoOfScreensReleased() {
        return noOfScreensReleased;
    }

    public void setNoOfScreensReleased(int noOfScreensReleased) {
        this.noOfScreensReleased = noOfScreensReleased;
    }

    public String getDirectedBy() {
        return directedBy;
    }

    public void setDirectedBy(String directedBy) {
        this.directedBy = directedBy;
    }

    public String getProducedBy() {
        return producedBy;
    }

    public void setProducedBy(String producedBy) {
        this.producedBy = producedBy;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
