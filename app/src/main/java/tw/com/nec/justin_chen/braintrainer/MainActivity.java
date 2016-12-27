package tw.com.nec.justin_chen.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    ArrayList<Integer> answers = new ArrayList<Integer>();;
    int locationOfCorrectAnswer;
    // 分數
    int score = 0;
    // 已答題數
    int numberOfQuestions = 0;
    // 下中(正確/錯誤)
    TextView resultTextView;
    // 上右(答對/作答題數)
    TextView pointsTextView;
    // 上中(a+b的字樣)
    TextView sumTextView;
    // 上左(倒數計時器)
    TextView timerTextView;
    Button button0; // 上左
    Button button1; // 上右
    Button button2; // 下左
    Button button3; // 下右
    Button playAgainButton;
    RelativeLayout gameRelativeLayout;

    public void chooseAnswer(View view){

        //Log.i("Tag", view.getTag().toString());
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            Log.i("Correct","Correct");
            // 答對的話，加入計分表，產生一個新的問題
            score++;
            resultTextView.setText("Correct!");

        } else {
            resultTextView.setText("Wrong!");
        }

        numberOfQuestions++;
        pointsTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));

        // 產生一個新的問題(第二到第n題)
        generateNewQuestion();

    }

    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);
        // play
        playAgain(findViewById(R.id.playAgainButton));

    }

    private void generateNewQuestion(){
        // 隨機產生號碼
        Random random = new Random();
        int a = random.nextInt(21); // random between 0-20
        int b = random.nextInt(21); // random between 0-20
        // 正確答案
        int correctAnswer = a+b;

        // 上中(a+b)
        sumTextView.setText(String.valueOf(a)+" + "+String.valueOf(b));

        // 正確答案要放的位置
        locationOfCorrectAnswer = random.nextInt(4); // 0 1 2 3
        int incorrectAnswer;

        answers.clear();

        // loop 0 1 2 3
        for(int i = 0; i<4; i++){

            // 走到放正確答案的地方
            if(i==locationOfCorrectAnswer){
                // 放入正確答案
                answers.add(correctAnswer);
            } else {
                // 產生錯誤答案
                incorrectAnswer = random.nextInt(41);

                // 如果數字碰巧一樣，再產一次數字
                while(correctAnswer==incorrectAnswer){
                    incorrectAnswer = random.nextInt(41);
                }

                // 放入錯誤答案
                answers.add(incorrectAnswer);

            }

        }

        Log.i("watch answers: ","before xxx");
        // update each button using IDs
        button0.setText(answers.get(0).toString());
        button1.setText(answers.get(1).toString());
        button2.setText(answers.get(2).toString());
        button3.setText(answers.get(3).toString());
    }

    private void showButton(){
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
    }
    private void hideButton(){
        button0.setVisibility(View.INVISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
    }

    public void playAgain(View view){

        showButton();

        score = 0;
        numberOfQuestions=0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        generateNewQuestion();

        // timer runs
        // 左上角的計時器(30秒，每秒tick一次)
        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String remainingTime = String.valueOf(millisUntilFinished/1000)+"s";
                timerTextView.setText(remainingTime);
            }

            @Override
            public void onFinish() {

                playAgainButton.setVisibility(View.VISIBLE);

                timerTextView.setText("0s");

                String[] twoNumbers = pointsTextView.getText().toString().split("/");

                if(Integer.parseInt(twoNumbers[1])!=0){
                    Float correctRate =    (Float.parseFloat(twoNumbers[0]) / Float.parseFloat(twoNumbers[1]))*100;
                    resultTextView.setText("正確率: "+String.format("%.2f%%",correctRate)); // 66.67%
                } else {
                    // 使用者未作答
                }

                hideButton();
            }
        }.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);

        timerTextView = (TextView) findViewById(R.id.timerTextView); // 上左
        sumTextView = (TextView) findViewById(R.id.sumTextView); // 上中
        pointsTextView = (TextView) findViewById(R.id.pointsTextView); // 上右
        resultTextView = (TextView) findViewById(R.id.resultTextView); // 下中

        button0 = (Button) findViewById(R.id.button0); // 上左
        button1 = (Button) findViewById(R.id.button1); // 上右
        button2 = (Button) findViewById(R.id.button2); // 下左
        button3 = (Button) findViewById(R.id.button3); // 下右

        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);

        // 產生一個問題(第一題)
        generateNewQuestion();




    }
}
