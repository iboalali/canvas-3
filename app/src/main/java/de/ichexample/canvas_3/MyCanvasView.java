package de.ichexample.canvas_3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;
import android.content.res.Resources;
import androidx.core.content.res.ResourcesCompat;
import java.util.ArrayList;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;


public class MyCanvasView extends View {

    private Paint mTextPaint;
    Paint Rpaint = new Paint();
    private Path mPath;
    private int mDrawColor;
    private int mBackgroundColor;
    private Canvas mExtraCanvas;
    private Bitmap mExtraBitmap;
    private Rect mFrame;
    MediaPlayer mediaPlayer;
    Paint mTextBoundsPaint = new Paint();
    Rect []rectW = new Rect[50]; // Raum f. wort

    float Spacebreite=0;

    ArrayList<String> list = new ArrayList<>();
        Rect TextContener = new Rect();

    private String m_szText;
    private int m_x=0,  m_y=0;
    int woX=0, woY=0;
    int status=1;
    int yy, xx;
    int myzeile=0;


    int ScrX, ScrY;
    float topY, butY;
    int xPos, yPos, yVor;
    int recWnr=0;// Rect Wort Nummer  f. Wort
    int yplus=100;

    //    int yzahl=0;
    int iTimer=0;

    String[] haa = new String[ 8 ];

    MyCanvasView(Context context) {
        this(context, null);
    }

    public MyCanvasView(Context context, AttributeSet attributeSet) {
        super(context);
        woX=0;
        woY=0;
        status=1;
        mBackgroundColor = ResourcesCompat.getColor(getResources(),
                R.color.mycolor, null);
        mDrawColor = ResourcesCompat.getColor(getResources(), R.color.schwarz, null);
        mPath = new Path();
        mTextPaint = new Paint( Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );

        mTextPaint.setTextAlign( Paint.Align.RIGHT ); // rechts schreiben
        mTextPaint.setTextSize( pxFromDp( context, 50 ) );// 43
        mediaPlayer = MediaPlayer.create(context, R.raw.haa);

        Rpaint.setColor(BLUE);
        Rpaint.setStrokeWidth(10);
        Rpaint.setStyle(Paint.Style.STROKE);

        int i;
        for ( i = 0; i < 50; i++)
            rectW[i] = new Rect();

    }//#############################

    @Override
    protected void onSizeChanged(int width, int height,
                                 int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        mExtraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mExtraCanvas = new Canvas(mExtraBitmap);
        mExtraCanvas.drawColor(mBackgroundColor);

        haa[ 0 ] = "ها هو هي";
        haa[ 1 ] = "ه ههه";
        haa[ 2 ] = "ه هاء";
        haa[ 3 ] = "هادي نَهْر";
        haa[ 4 ] = "كَتَب واجِباته";
        haa[ 5 ] = "حَيَوانٌ جَميلٌ";
        haa[ 6 ] = "يُحِبُ ألتُّفّاحَ";
        haa[ 7 ] = "زارَ نَبيهٌ أُمَّهُ";

    }//###################

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ScrX=getWidth();
        int height=getHeight();
        ScrY= height/8;
        ScrY*=0.9;

        if (status == 1){    // ganze seite schreiben und keine zeile markieren
            yy=ScrY;
            xx =ScrX-40;
            xPos = ScrX - 40;
            yPos = ScrY;
            myzeile=1;
            ZeileSchreiben(mExtraCanvas, haa, xPos, yPos, status);
            status=0;
            iTimer=0;
        }
        canvas.drawBitmap(mExtraBitmap, 0, 0, null);

//#############################################
    }// ende ondraw
//#############################################

    void  ZeileSchreiben(Canvas canvas , String []str ,int x ,int y, int mysatus) {
        int ZeilenLang =0;
        if (mysatus ==1) {

            boolean iOpen=true;
            Paint.FontMetrics fm;
            float ytm ;
            char zeichen;
            int letterZahl=0;
            int rectnr=-1;
            String strgCopy;
            int RectRight=0;
            boolean iSpace= false;
            int x1=0;
            String satz="";

            fm = mTextPaint.getFontMetrics();
            ytm =  fm.bottom - fm.top  ;  // 265.4297
            yPos = (int) ytm;
            int nZeilen = 7;  //aZeilen->GetSize();
            recWnr=0;
            yplus=100;

//##########################################################################
            for (int zeilcount=0; zeilcount < nZeilen; zeilcount++) {    //#
//##########################################################################
                strgCopy = str[zeilcount];
                ZeilenLang = strgCopy.length();

                xPos = ScrX - 40; // xPos als rect.right;
                iOpen = true;

                rectW[recWnr].right = xPos;
                rectW[recWnr].top = yPos;
                mTextPaint.setColor(BLACK);
                // Buchstaben der Zeile ausgeben
                for( int letterCounter=0; letterCounter<ZeilenLang; letterCounter++){
                    zeichen= strgCopy.charAt( letterCounter ); // c++ zeichen= strg.GetAt(letterCounter);

                    //todo hier wird alle Buchstaben und Harekat untersucht
                    if( zeichen==32)
                    {
                        iSpace=true;
                        Spacebreite = mTextPaint.measureText( String.valueOf(zeichen) );
                        mTextPaint.getTextBounds( String.valueOf(zeichen), 0, 1, TextContener );
                        TextContener.right = (int) Spacebreite;
                        RectRight=TextContener.right;
                        letterZahl=ZeilenLang-letterCounter;
                        if(letterZahl>1)
                        {
                            letterZahl= strgCopy.charAt(letterCounter+1);
                            if(letterZahl!=32)
                                rectnr=letterCounter+1;
                        }
                    }
                    else
                        satz+=String.valueOf(zeichen);

                    canvas.drawText(String.valueOf(zeichen), xPos, yPos+yplus, mTextPaint);

                if(iSpace) {
                    iSpace = false;
                    xPos -= RectRight;
                }
                else {
                    x1 = xPos;
                    mTextPaint.getTextBounds(String.valueOf(zeichen), 0, 1, TextContener);
                    xPos -= TextContener.right;  //textBreite; // c++ X -= size.cx;
                }
                        //rectbedienen
                    // bei dem lezten Buchstabe vor Space und Rect Oefnnen
                    if(rectnr==letterCounter){
                        rectW[recWnr].right=x1;
                        rectW[recWnr].top=yPos;
                        iOpen=true;
                        rectnr=-1;
                    }

                    // bei dem letzten Buchstabe der Zeile Rect schliessen
                    letterZahl=ZeilenLang-letterCounter;

                    if(letterZahl==1){
                        if(iOpen){
                            rectW[recWnr].left = xPos;
                            rectW[recWnr].bottom = yPos + (int) ytm;
// canvas.drawRect(rectW[recWnr], Rpaint);
                            iOpen=false;
                            recWnr +=1;
                            list.add(satz);
                            satz="";
                        }
                    }

                    // bei dem lezten Buchstabe vor Space und Rect schliessen
                    // list.add und String loeschen
                    letterZahl=ZeilenLang-letterCounter;
                    if(letterZahl>1)
                    {
                        letterZahl= strgCopy.charAt(letterCounter+1);
                        if(letterZahl==32)
                        {
                            if(iOpen)
                            {
                                rectW[recWnr].left=xPos;
                                rectW[recWnr].bottom= yPos+(int)ytm;// c++tm.tmHeight + tm.tmExternalLeading;
 //canvas.drawRect(rectW[recWnr], Rpaint);
                                iOpen=false;
                                recWnr +=1;
                                list.add(satz);
                                satz="";
                            }
                        }
                    }

                }// zweite for

                xPos = ScrX - 40; //ect.right; //  - 30;//20;
                yPos += (int)ytm; //tm.tmHeight + tm.tmExternalLeading; // +10;
            }// erste for
        } /// if(status== 1)
    }//ende Zeilen schreiben


    // Get the width of the screen
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    // Get the height of the screen
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


//###########################
}// ende der classe

//#############################################
//        canvas.drawText( "X : " + woX + " Y: " + woY, 100, 100, mTextPaint );
//        canvas.drawText( "B : " + ScrX + " L: " + ScrY, 150, 100, mTextPaint );

//        canvas.drawText( "top : " + (int)topY
//                          + " But: " +(int) butY,
//                          150, 2100, mTextPaint );
//        topY = texpaint.getFontMetrics().top;
//        butY = texpaint.getFontMetrics().bottom;
/*
Fontmassen

Textsize = 200
Top = -211.23   <-- startY = mTextPaint.getFontMetrics().top;

Ascent = -185.54  unter top <-- startY = mTextPaint.getFontMetrics().ascent;
Baseline = 0

Descent = 48.8  unten <-- startY = mTextPaint.getFontMetrics().descent;

Bottom = 54.19  unter Descent <-- startY = mTextPaint.getFontMetrics().bottom;

Meeasured width = 1019.0 breite <-- float width = mTextPaint.measureText(mText);

Text bounds  w = 996 h= 193 <-- mTextPaint.getTextBounds(mText, 0, mText.length(), Rect mBounds);

mTextPaint.getFontMetrics();
*/


/*

 bottom = 2
 left = 9
 right = 274
 top = -87
rectW = {Rect@5120} "Rect(9, -87 - 274, 2)"

float width = mTextPaint.measureText(str); // Text width = 279.0

fm = {Paint$FontMetrics@5186}
 ascent = -111.328125
 bottom = 32.51953
 descent = 29.296875
 leading = 0.0
 top = -126.73828


  float heighty = fm.descent - fm.ascent;  // 29.296875 -( -111.328125) = 140,

  float hoch = fm.bottom - fm.top + fm.leading; // 32.51953 -(-126.73828)+0.0 =  265.4297

              canvas.drawText( str, ScrX, 200, mTextPaint);

 */