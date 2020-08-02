package de.ichexample.canvas_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {
    LinearLayout mLinearLayout;

@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Toolbar myToolbar1 =  findViewById(R.id.toolOben);
        setSupportActionBar( myToolbar1);
        myToolbar1.inflateMenu(R.menu.menu_top);


        Toolbar myToolbar2 =  findViewById(R.id.toolUnten);
        setSupportActionBar( myToolbar2);
        myToolbar2.setTitle( "Canvae3_U" );

        MyCanvasView myCanvasView;
        myCanvasView = new MyCanvasView(this);
        LinearLayout mLinearLayout;
        mLinearLayout = findViewById( R.id.lekview );
        mLinearLayout.addView(myCanvasView);
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_foot, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //       displayTextView = findViewById( R.id.textView );
        switch (item.getItemId()){
            case R.id.favorites_page:
                 return true;

            case R.id.web_page:
                return true;
            case R.id.lektion:

                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }


}// ende

