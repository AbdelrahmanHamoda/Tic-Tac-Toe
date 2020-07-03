package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttons [][] = new Button[3][3]; //two dimensional array 3*3
    private Button reset;

    private boolean p1Turn=true;

    private int roundCount;

    private int p1Score;
    private int p2Score;

    private TextView p1Text; //linking with layout
    private TextView p2Text; //linking with layout  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();

    }

    public void setupUI(){
        p1Text = findViewById(R.id.player1);
        p2Text = findViewById(R.id.player2);
        // array structure as following
        // {0,0} {0,1} {0,2}
        // {1,0} {1,1} {1,2}
        // {2,0} {2,1} {2,2}
        for(int i= 0 ; i<3;i++){
            for(int j = 0 ; j<3 ; j++){
                // getting buttons id dynamically
                String ButtonID = "btn" + i + j ;
                // code below replaces R.id. to assign id dynamically to array after that
                int resID = getResources().getIdentifier(ButtonID,"id",getPackageName());
                // finally assigning id values to our array
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
                p1Turn=true;
                p1Score=0;
                p1Text.setText("Player1: " + p1Score);
                p2Score=0;
                p2Text.setText("Player2: " + p2Score);
                Toast.makeText(getApplicationContext(),"game has been reset successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override // for buttons in the array
    public void onClick(View v) {
        // checks if button clicked before or not
        if(!((Button)v).getText().toString().equals("")){
            return;
        }

        if(p1Turn){
            ((Button)v).setText("X");
            p1Turn=false;
        }else{
            ((Button)v).setText("O");
            p1Turn=true;
        }

        roundCount++;
        if(winCheck()){
            if(!p1Turn){
                // p1 wins
                p1win();
            }else {
                // p2 wins
                p2win();
            }
        }else if (roundCount==9){
            // draw
            Toast.makeText(this,"draw !",Toast.LENGTH_SHORT).show();
            resetBoard();
        }
    }

    private boolean winCheck() {
        String[][] sequence = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sequence[i][j] = buttons[i][j].getText().toString();
            }
        }
        // rows check
        for(int i = 0 ; i<3;i++){
            if(sequence[i][0]== sequence[i][1] && sequence[i][0] == sequence[i][2] && !sequence[i][0].equals("")){
                return true;
            }
        }
        // column check
        for(int i = 0 ; i<3;i++){
            if(sequence[0][i]== sequence[1][i] && sequence[0][i] == sequence[2][i] && !sequence[0][i].equals("")){
                return true;
            }
        }
        // first diagonal check
        if(sequence[0][0]== sequence[1][1] && sequence[0][0] == sequence[2][2] && !sequence[0][0].equals("")){
            return true;
        }
        // first diagonal check
        if(sequence[0][2]== sequence[1][1] && sequence[0][2] == sequence[2][0] && !sequence[0][2].equals("")){
            return true;
        }

        return false;
    }

    private void p1win(){
        p1Score++;
        Toast.makeText(this,"player 1 wins",Toast.LENGTH_SHORT).show();
        p1Text.setText("Player1: " + p1Score);
        resetBoard();
    }

    private void p2win(){
        p2Score++;
        Toast.makeText(this,"player 2 wins",Toast.LENGTH_SHORT).show();
        p2Text.setText("Player2: " + p2Score);
        resetBoard();
    }

    private void resetBoard(){
        for(int i= 0 ; i<3;i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount=0;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // saving changes when app rotate or home button pressed
        outState.putInt("roundCount",roundCount);
        outState.putInt("p1sc",p1Score);
        outState.putInt("p2sc",p2Score);
        outState.putBoolean("p1turn",p1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // restoring changes when app rotate or reopening app again
        roundCount = savedInstanceState.getInt("roundCount");
        p1Score = savedInstanceState.getInt("p1sc");
        p2Score = savedInstanceState.getInt("p2sc");
        p1Turn = savedInstanceState.getBoolean("p1turn");
    }
}