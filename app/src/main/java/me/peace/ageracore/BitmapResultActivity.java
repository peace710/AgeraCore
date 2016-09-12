package me.peace.ageracore;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.agera.Receiver;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;

import me.peace.agera.AgeraFit;
import me.peace.agera.Query;
import me.peace.agera.Rest;
import me.peace.agera.bitmap.BitmapConverterFactory;
import me.peace.agera.core.AgeraReceiver;


/**
 * Created by Kira on 2016/9/12.
 */
public class BitmapResultActivity extends AppCompatActivity {
    public static final String API_URL = "http://ww1.sinaimg.cn";
    private AgeraFit fit;
    private Repository<Result<Bitmap>> repository;
    private ImageView image;
    private String image1 = "a13fa4bbgw1eqk2g9j530j20go0m80v8";
    private String image2 = "a13fa4bbgw1dvqswibmanj";
    private boolean flag = true;


    AgeraReceiver<Bitmap> receiver = new AgeraReceiver<Bitmap>() {
        @Override
        public void accept(@NonNull Bitmap value) {
            image.setImageBitmap(value);
        }

        @Override
        public void update() {
            repository.get().ifFailedSendTo(err).ifSucceededSendTo(receiver);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        setTitle(getString(R.string.bitmap_result));
        image = (ImageView)findViewById(R.id.image);
        fit = new AgeraFit.Builder().setBaseUrl(API_URL).setConverterFactory(BitmapConverterFactory.create()).build();
    }

    public void load(View v){
        Image img = fit.create(Image.class);
        removeUpdatable();
        if (flag) {
            repository = img.load(image1);
        }else{
            repository = img.load(image2);
        }
        flag = !flag;
        repository.addUpdatable(receiver);
    }

    public interface Image{
        @Rest("/mw1024/{image}.jpg")
        Repository<Result<Bitmap>> load(@Query("image")String image);
    }

    private Receiver<Throwable> err = new Receiver<Throwable>() {
        @Override
        public void accept(@NonNull Throwable e) {
            T("err = " + e.getMessage());
        }
    };

    private void T(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeUpdatable();
    }

    private void removeUpdatable() {
        if (null != repository) {
            repository.removeUpdatable(receiver);
            repository = null;
        }
    }
}
