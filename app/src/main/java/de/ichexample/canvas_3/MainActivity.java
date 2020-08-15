package de.ichexample.canvas_3;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private char mSelectedChar = 'م';
    private char mSelectedChar2 = 'َ';

    String[][] multiLine = new String[][]{
            {"ك"},
            {"كا", "كو", "كي"},
            {"كامِل", "مَلِك", "مَكان", "كَبير"},
            {"كَلِمتكَ", "البَيتُ", "كَبير"},
            {"كَتَبَ", "أَبي", "في", "دَفْتَري"},
            {"مَكانُ", "المَلِكِ", "كَبير"}
    };

    String[] mLines_2 = new String[]{
            "ك",
            "كا  كو  كي",
            "كامِل  مَلِك  مَكان  كَبير",
            "البَيتُ  كَبير",
            "كَتَبَ  أَبي  في  دَفْتَري",
            "مَكانُ  المَلِكِ  كَبير"
    };

    String[] mLines = new String[]{
            "ها هو هي",
            "ه ههه",
            "بِسْمِ ٱللَّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
            "هادي نَهْر",
            "كَتَب واجِباته",
            "حَيَوانٌ جَميلٌ",
            "يُحِبُ ألتُّفّاحَ",
            "زارَ نَبيهٌ أُمَّهُ"
    };

    Line[] lines = new Line[]{
            new Line(multiLine[0], mSelectedChar2),
            new Line(multiLine[1], mSelectedChar2),
            new Line(multiLine[2], mSelectedChar2),
            new Line(multiLine[3], mSelectedChar2),
            new Line(multiLine[4], mSelectedChar2),
            new Line(multiLine[5], mSelectedChar2),
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar1 = findViewById(R.id.toolOben);
        setSupportActionBar(myToolbar1);
        myToolbar1.inflateMenu(R.menu.menu_top);

        Toolbar myToolbar2 = findViewById(R.id.toolUnten);
        setSupportActionBar(myToolbar2);
        myToolbar2.setTitle("Canvae3_U");

        ArabicWithDiacriticsPageView pageView = findViewById(R.id.arabic_view);
        pageView.setBackgroundColorResource(R.color.mycolor);
        pageView.setPage(new Page(lines, mSelectedChar2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_foot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //       displayTextView = findViewById( R.id.textView );
        switch (item.getItemId()) {
            case R.id.favorites_page:
                return true;

            case R.id.web_page:
                return true;
            case R.id.lektion:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}// ende

