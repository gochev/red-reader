package sample.service;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Account;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;

public class RedditService {

   private RedditClient redditClient;

   public void login(String username, String password) {
      UserAgent userAgent = new UserAgent("red-reader", "com.blogspot.gochev", "v0.1", "nayden_gochev");

      //TODO change it
      Credentials credentials = Credentials.script("username", "password",
            "18t2hp3BFVnT0Q", "-vwVXoGfNVKUD6yknrYXBlTDxO8");

      // This is what really sends HTTP requests
      NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);

      // Authenticate and get a RedditClient instance
      redditClient = OAuthHelper.automatic(adapter, credentials);

      Account me = redditClient.me().about();
      System.out.println(me.getName());
   }

   public DefaultPaginator<Submission> getTopSubmissions(){
      DefaultPaginator<Submission> paginator = redditClient.frontPage()
            .limit(50) // 50 posts per page
            .sorting(SubredditSort.TOP) // top posts
            .timePeriod(TimePeriod.ALL) // of all time
            .build();

      return paginator;
//      Listing<Submission> top50MostPopular = paginator.next();
//      for(Submission submission : top50MostPopular) {
//         System.out.println(submission.getSubreddit());
//         System.out.println(submission.getTitle());
//         System.out.println(submission.getCreated());
//         System.out.println(submission.getAuthor());
//         System.out.println(submission.getCommentCount());
//         System.out.println(submission.getDistinguished());
//      }
   }

   public String getAccountName(){
      return redditClient.me().about().getName();
   }

}
