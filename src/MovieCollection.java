import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> castMembers;
    private ArrayList<String> allGenres;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        assignCastMembers();
        assignAllGenres();
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
        //System.out.println("KEYWORD" + movie.getKeywords());
    }

    private void searchCast()
    {
        System.out.println("Enter a cast search term: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<String> searchedCast = new ArrayList<String>();
        for(String c : castMembers) {
            if(c.toLowerCase().contains(searchTerm)) {
                searchedCast.add(c);
            }
        }
        Sort.insertionSortWordList(searchedCast);

        for(int i = 0; i < searchedCast.size(); i++) {
            System.out.println((i+1) + ". " + searchedCast.get(i));
        }
        System.out.println("Which actor would you like to search for?");
        System.out.println("Enter a number:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedActor = searchedCast.get(choice - 1);

        //show a list of movies that the actor plays in
        ArrayList<Movie> moviesWithActor = new ArrayList<Movie>();
        for(Movie m : movies) {
            if(m.getCast().contains(selectedActor)) {
                moviesWithActor.add(m);
            }
        }
        sortResults(moviesWithActor);

        // sort the results by title
        sortResults(moviesWithActor);

        // now, display them all to the user
        for (int i = 0; i < moviesWithActor.size(); i++)
        {
            String title = moviesWithActor.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = moviesWithActor.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();
        ArrayList<String> keywords = new ArrayList<String>();
        while(searchTerm.indexOf(" ") > 0) {
            keywords.add(searchTerm.substring(0, searchTerm.indexOf(" ")));
            if(searchTerm.indexOf(" ") + 1 == searchTerm.length()) {
                searchTerm = "";
            }
            else{
                searchTerm = searchTerm.substring(searchTerm.indexOf(" ") + 1);
            }
            for (int i = 0; i < movies.size(); i++)
            {
                /*String movieTitle = movies.get(i).getTitle();
                movieTitle = movieTitle.toLowerCase();

                if (movieTitle.indexOf(searchTerm) != -1)
                {
                    //add the Movie objest to the results list
                    results.add(movies.get(i));
                }*/
                String k = movies.get(i).getKeywords();
                k = k.toLowerCase();
                boolean containsAllKeywords = true;
                for(String s : keywords) {
                    if(!k.contains(s)) {
                        containsAllKeywords = false;
                    }
                }
                if(containsAllKeywords) {
                    results.add(movies.get(i));
                }
            }
            for (int i = 0; i < results.size(); i++)
            {
                String title = results.get(i).getTitle();

                // this will print index 0 as choice 1 in the results list; better for user!
                int choiceNum = i + 1;

                System.out.println("" + choiceNum + ". " + title);
            }

            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            Movie selectedMovie = results.get(choice - 1);

            displayMovieInfo(selectedMovie);

            System.out.println("\n ** Press Enter to Return to Main Menu **");
            scanner.nextLine();
        }
    }

    private void listGenres()
    {
        for(int i = 0; i < allGenres.size(); i++) {
            System.out.println((i+1) + ". " + allGenres.get(i));
        }
        System.out.println("Which genre would you like to search for?");
        System.out.println("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = allGenres.get(choice - 1);

        ArrayList<Movie> moviesWithSelectedGenre = new ArrayList<Movie>();
        for(Movie m : movies) {
            if(m.getGenres().toLowerCase().contains(selectedGenre.toLowerCase())) {
                moviesWithSelectedGenre.add(m);
            }
        }
        sortResults(moviesWithSelectedGenre);

        for (int i = 0; i < moviesWithSelectedGenre.size(); i++)
        {
            String title = moviesWithSelectedGenre.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = moviesWithSelectedGenre.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Movie> highestRatedMovies = new ArrayList<Movie>();
        for(Movie m : movies) {
            if(highestRatedMovies.size() >= 50) {
                if(highestRatedMovies.get(49).getUserRating() < m.getUserRating()) {
                    highestRatedMovies.remove(49);
                    int addAtIndex = -1;
                    for(int i = 0; i < highestRatedMovies.size(); i++) {
                        if(highestRatedMovies.get(i).getUserRating() <= m.getUserRating()) {
                            addAtIndex = i;
                            break;
                        }
                    }
                    if(addAtIndex == -1) {
                        highestRatedMovies.add(m);
                    }
                    else{
                        highestRatedMovies.add(addAtIndex,m);
                    }
                }
            }
            else{
                if(highestRatedMovies.size() == 0) {
                    highestRatedMovies.add(m);
                }
                else{
                    int addAtIndex = -1;
                    for(int i = 0; i < highestRatedMovies.size(); i++) {
                        if(highestRatedMovies.get(i).getUserRating() <= m.getUserRating()) {
                            addAtIndex = i;
                            break;
                        }
                    }
                    if(addAtIndex == -1) {
                        highestRatedMovies.add(m);
                    }
                    else{
                        highestRatedMovies.add(addAtIndex,m);
                    }
                }
            }
        }
        for(int i = 0; i < highestRatedMovies.size(); i++) {
            System.out.println((i+1) + ". " + highestRatedMovies.get(i).getTitle() + ": " + highestRatedMovies.get(i).getUserRating());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = highestRatedMovies.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }

    private void assignCastMembers() {
        castMembers = new ArrayList<String>();
        for(Movie m : movies) {
            String[] castMems = m.getCast().split("\\|");
            for(String member : castMems) {
                if(!castMembers.contains(member)) {
                    castMembers.add(member);
                }
            }
        }
    }

    private void assignAllGenres() {
        allGenres = new ArrayList<String>();
        for(Movie m : movies) {
            String[] genres = m.getGenres().split("\\|");
            for(String g : genres) {
                if(!allGenres.contains(g)) {
                    allGenres.add(g);
                }
            }
        }
        Sort.insertionSortWordList(allGenres);
    }

}