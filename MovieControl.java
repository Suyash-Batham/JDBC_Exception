package JDBC_ExceptionHandling;

import java.sql.*;
import java.util.Scanner;

class ShorterMovieNameException extends Exception {
    public ShorterMovieNameException(String message) {
        super(message);
    }
}

class LesserProductionCostException extends RuntimeException {
    public LesserProductionCostException(String message) {
        super(message);
    }
}

public class MovieControl {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_exception?useSSL=false", "root", "root123");
            MovieControl movieControl = new MovieControl();

            Scanner scanner = new Scanner(System.in);
            int choice;
            do {
                System.out.println("\n-------- Movie Database ---------");
                System.out.println("1. Get all movies");
                System.out.println("2. Get movie by name");
                System.out.println("3. Get movies by production cost");
                System.out.println("4. Get movies by release date");
                System.out.println("5. Insert movie");
                System.out.println("6. Delete movie");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        movieControl.getAllMovies(connection);
                        break;
                    case 2:
                        System.out.print("Enter movie name: ");
                        String movieName = scanner.nextLine();
                        movieControl.getMovieByName(connection, movieName);
                        break;
                    case 3:
                        System.out.print("Enter production cost (in Crores): ");
                        double productionCost = scanner.nextDouble();
                        movieControl.getMoviesByProductionCost(connection, productionCost);
                        break;
                    case 4:
                        System.out.print("Enter release date: ");
                        String releaseDate = scanner.nextLine();
                        movieControl.getMoviesByReleaseDate(connection, releaseDate);
                        break;
                    case 5:
                        Movie newMovie = movieControl.readMovieDetails(scanner);
                        if (newMovie != null) {
                            movieControl.insertMovie(connection, newMovie);
                        }
                        break;
                    case 6:
                        System.out.print("Enter movie name to delete: ");
                        String movieToDelete = scanner.nextLine();
                        movieControl.deleteMovie(connection, movieToDelete);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 0);

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllMovies(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Movies");

            System.out.println("\nMovies in the database:");
            while (resultSet.next()) {
                String movieName = resultSet.getString("movieName");
                String releaseDate = resultSet.getString("releaseDate");
                double productionCost = resultSet.getDouble("productionCost");
                int noOfScreensReleased = resultSet.getInt("noOfScreensReleased");
                String directedBy = resultSet.getString("directedBy");
                String producedBy = resultSet.getString("producedBy");
                boolean status = resultSet.getBoolean("status");
                System.out.println("Movie Name: " + movieName);
                System.out.println("Release Date: " + releaseDate);
                System.out.println("Production Cost: " + productionCost);
                System.out.println("No. of Screens Released: " + noOfScreensReleased);
                System.out.println("Directed By: " + directedBy);
                System.out.println("Produced By: " + producedBy);
                System.out.println("Status: " + status);
                System.out.println();
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getMovieByName(Connection connection, String movieName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Movies WHERE movieName = ?");
            preparedStatement.setString(1, movieName);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("\nMovie details for '" + movieName + "':");
            if (resultSet.next()) {
                String releaseDate = resultSet.getString("releaseDate");
                double productionCost = resultSet.getDouble("productionCost");
                int noOfScreensReleased = resultSet.getInt("noOfScreensReleased");
                String directedBy = resultSet.getString("directedBy");
                String producedBy = resultSet.getString("producedBy");
                boolean status = resultSet.getBoolean("status");

                System.out.println("Release Date: " + releaseDate);
                System.out.println("Production Cost: " + productionCost);
                System.out.println("No. of Screens Released: " + noOfScreensReleased);
                System.out.println("Directed By: " + directedBy);
                System.out.println("Produced By: " + producedBy);
                System.out.println("Status: " + status);
                System.out.println();
            } else {
                System.out.println("Movie not found in the database.");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getMoviesByProductionCost(Connection connection, double productionCost) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Movies WHERE productionCost > ?");
            preparedStatement.setDouble(1, productionCost);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("\nMovies with production cost > " + productionCost + " Crores:");
            while (resultSet.next()) {
                String movieName = resultSet.getString("movieName");
                String releaseDate = resultSet.getString("releaseDate");
                int noOfScreensReleased = resultSet.getInt("noOfScreensReleased");
                String directedBy = resultSet.getString("directedBy");
                String producedBy = resultSet.getString("producedBy");
                boolean status = resultSet.getBoolean("status");

                System.out.println("Movie Name: " + movieName);
                System.out.println("Release Date: " + releaseDate);
                System.out.println("No. of Screens Released: " + noOfScreensReleased);
                System.out.println("Directed By: " + directedBy);
                System.out.println("Produced By: " + producedBy);
                System.out.println("Status: " + status);
                System.out.println();
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getMoviesByReleaseDate(Connection connection, String releaseDate) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Movies WHERE releaseDate = ?");
            preparedStatement.setString(1, releaseDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("\nMovies released on " + releaseDate + ":");
            while (resultSet.next()) {
                String movieName = resultSet.getString("movieName");
                double productionCost = resultSet.getDouble("productionCost");
                int noOfScreensReleased = resultSet.getInt("noOfScreensReleased");
                String directedBy = resultSet.getString("directedBy");
                String producedBy = resultSet.getString("producedBy");
                boolean status = resultSet.getBoolean("status");

                System.out.println("Movie Name: " + movieName);
                System.out.println("Production Cost: " + productionCost);
                System.out.println("No. of Screens Released: " + noOfScreensReleased);
                System.out.println("Directed By: " + directedBy);
                System.out.println("Produced By: " + producedBy);
                System.out.println("Status: " + status);
                System.out.println();
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Movie readMovieDetails(Scanner scanner) {
        try {
            System.out.print("Enter movie name: ");
            String movieName = scanner.nextLine();
            if (movieName.length() < 3) {
                throw new ShorterMovieNameException("Movie name must be at least 3 characters long.");
            }

            // Check if movieName is null or empty
            if (movieName.trim().isEmpty()) {
                throw new IllegalArgumentException("Movie name cannot be empty.");
            }

            System.out.print("Enter release date: ");
            String releaseDate = scanner.nextLine();

            System.out.print("Enter production cost (in Crores): ");
            double productionCost = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            if (productionCost <= 10) {
                throw new LesserProductionCostException("Production cost must be more than 10 Crores.");
            }

            System.out.print("Enter number of screens released: ");
            int noOfScreensReleased = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter director's name: ");
            String directedBy = scanner.nextLine();

            System.out.print("Enter producer's name: ");
            String producedBy = scanner.nextLine();

            System.out.print("Enter status (true/false): ");
            boolean status = scanner.nextBoolean();
            scanner.nextLine(); // Consume newline

            return new Movie(movieName, releaseDate, productionCost, noOfScreensReleased, directedBy, producedBy, status);
        } catch (ShorterMovieNameException | LesserProductionCostException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public void insertMovie(Connection connection, Movie movie) {
        if (connection == null) {
            System.out.println("Invalid database connection. Please provide a valid Connection object.");
            return;
        }

        if (movie == null || movie.getMovieName().trim().isEmpty()) {
            System.out.println("Invalid movie details. Please provide a valid Movie object with a non-null and non-empty movieName.");
            return;
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Movies (movieName, releaseDate, productionCost, noOfScreensReleased, directedBy, producedBy, status) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, movie.getMovieName());
            preparedStatement.setString(2, movie.getReleaseDate());
            preparedStatement.setDouble(3, movie.getProductionCost());
            preparedStatement.setInt(4, movie.getNoOfScreensReleased());
            preparedStatement.setString(5, movie.getDirectedBy());
            preparedStatement.setString(6, movie.getProducedBy());
            preparedStatement.setBoolean(7, movie.getStatus());
            preparedStatement.executeUpdate();

            System.out.println("\nMovie inserted successfully.");
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteMovie(Connection connection, String movieName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Movies WHERE movieName = ?");
            preparedStatement.setString(1, movieName);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nMovie deleted successfully.");
            } else {
                System.out.println("\nMovie not found in the database.");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
