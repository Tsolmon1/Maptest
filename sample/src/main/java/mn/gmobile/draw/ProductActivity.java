package mn.gmobile.draw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import mn.gmobile.draw.R;
import com.michaldrabik.tapbarmenulib.TapBarMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductActivity extends AppCompatActivity {
    @BindView(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.tapBarMenu)
    public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }
    @OnClick({ R.id.item1, R.id.item2, R.id.item3, R.id.item4 })
    public void onMenuItemClick(View view) {
        tapBarMenu.close();
        switch (view.getId()) {
            case R.id.item1:
                Map();
                //loadFragment(new FirstFragment());
                Log.i("TAG", "Item 1 selected");
                break;
            case R.id.item2:
                News();
                Log.i("TAG", "Item 2 selected");
                break;
            case R.id.item3:
                Product();
                Log.i("TAG", "Item 3 selected");
                break;
            case R.id.item4:
                Feedback();
                Log.i("TAG", "Item 4 selected");
                break;
        }
    }

    public void Map(){

        try{
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        } finally {
            ProductActivity.this.finish();
        }
    }
    public void News(){

        try{
            Intent intent = new Intent(this, NewsActivity.class);
            startActivity(intent);
        } finally {
            ProductActivity.this.finish();
        }
    }
    public void Product(){

        try{
            Intent intent = new Intent(this, ProductActivity.class);
            startActivity(intent);
        } finally {
            ProductActivity.this.finish();
        }

    }
    public void Feedback(){

        try{
            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        } finally {
            ProductActivity.this.finish();
        }
    }

}
