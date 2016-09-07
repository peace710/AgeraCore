package me.peace.ageracore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.agera.gson.GsonConverterFactory;
import com.google.android.agera.Receiver;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;

import java.util.List;

import me.peace.agera.AgeraFit;
import me.peace.agera.Get;
import me.peace.agera.Post;
import me.peace.agera.Query;
import me.peace.agera.Rest;
import me.peace.agera.core.AgeraReceiver;
import me.peace.ageracore.data.Contributor;

public class StringResultActivity extends AppCompatActivity{
    public static final String API_URL = "https://api.github.com";
    private TextView txtUrl;
    private TextView txtResult;
    private AgeraFit fit;
    private Repository<Result<String>> repositoryGet;
    private Repository<Result<String>> repositoryPost;
    private Repository<Result<String>> repositoryRest;

    private AgeraReceiver<String> receiverGet = new AgeraReceiver<String>() {
        @Override
        public void accept(@NonNull String contributors) {
            txtResult.append(contributors);
            T("Get update!");
        }

        @Override
        public void update() {
            repositoryGet.get().ifFailedSendTo(err).ifSucceededSendTo(receiverGet);
        }
    };

    private AgeraReceiver<String> receiverPost = new AgeraReceiver<String>() {
        @Override
        public void accept(@NonNull String contributors) {
            txtResult.append(contributors);
            T("Post update!");
        }

        @Override
        public void update() {
            repositoryPost.get().ifFailedSendTo(err).ifSucceededSendTo(receiverPost);
        }
    };

    private AgeraReceiver<String> receiverRest = new AgeraReceiver<String>() {
        @Override
        public void accept(@NonNull String contributors) {
            txtResult.append(contributors);
            T("Rest update!");
        }

        @Override
        public void update() {
            repositoryRest.get().ifFailedSendTo(err).ifSucceededSendTo(receiverRest);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.app_name));
        txtUrl = (TextView)findViewById(R.id.url);
        txtResult = (TextView)findViewById(R.id.result);
        fit = new AgeraFit.Builder().setBaseUrl(API_URL).build();
    }

    public void get(View v){
        txtUrl.setText("https://api.github.com/repos/google/agera/contributors(Get)");
        GetGitHub git = fit.create(GetGitHub.class);
        repositoryGet = git.contributors();
        repositoryGet.addUpdatable(receiverGet);
        txtResult.setText("");
    }

    public interface GetGitHub{
        @Get("/repos/google/agera/contributors")
        Repository<Result<String>> contributors();
    }


    public void post(View v){
        txtUrl.setText("https://api.github.com/repos/alibaba/AndFix/contributors(Post)");
        PostGitHub git = fit.create(PostGitHub.class);
        repositoryPost = git.contributors();
        repositoryPost.addUpdatable(receiverPost);
        txtResult.setText("");
    }

    public interface PostGitHub{
        @Post("/repos/alibaba/AndFix/contributors")
        Repository<Result<String>> contributors();
    }

    public void rest(View v){
        txtUrl.setText("https://api.github.com/repos/square/retrofit/contributors(Rest)");
        RestGitHub git = fit.create(RestGitHub.class);
        repositoryRest = git.contributors("square", "retrofit");
        repositoryRest.addUpdatable(receiverRest);
        txtResult.setText("");
    }

    public interface RestGitHub {
        @Rest("/repos/{owner}/{repo}/contributors")
        Repository<Result<String>> contributors(@Query("owner") String owner,
                                                           @Query("repo") String repo);
    }

    private Receiver<Throwable> err = new Receiver<Throwable>() {
        @Override
        public void accept(@NonNull Throwable e) {
            txtResult.setText("err = " + e.getMessage());
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != repositoryGet){
            repositoryGet.removeUpdatable(receiverGet);
        }
        if (null != repositoryPost){
            repositoryPost.removeUpdatable(receiverPost);
        }
        if (null != repositoryRest){
            repositoryRest.removeUpdatable(receiverRest);
        }
    }

    private void T(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }
}
