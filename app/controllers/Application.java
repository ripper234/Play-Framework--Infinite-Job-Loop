package controllers;

import play.*;
import play.mvc.*;
import play.libs.F;
import play.jobs.Job;
import java.util.*;

import com.google.common.collect.Collections2;
import com.google.common.base.Function;
import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }


    public static boolean checkTwoUrls() {
      String a = "http://facebook.cozzzm/";
      String b = "http://google.com/";

      return checkUrlsAreReachable(a, b, a, b);
    }
  
    private static boolean checkUrlsAreReachable(String... urls) {
        Collection<F.Promise<Boolean>> promises = Collections2.transform(Arrays.asList(urls), new Function<String, F.Promise<Boolean>>() {
            @Override
            public F.Promise<Boolean> apply(String url) {
                return new Job<Boolean>() {
                    int f = foo();
                    int foo() {
                        int x = 1;
                        x++;
                        return x;
                    }
                    @Override
                    public Boolean doJobWithResult() throws Exception {
                        try {
                            Thread.sleep(100);
			    System.out.println("Sleeping");
                            return true;
                        } catch (Exception e) {
                            return false;
                        }
                    }
                }.now();
            }
        });

        F.Promise<List<Boolean>> future = F.Promise.waitAll(promises);
        List<Boolean> results = await(future);
        return !results.contains(Boolean.FALSE);
    }

}