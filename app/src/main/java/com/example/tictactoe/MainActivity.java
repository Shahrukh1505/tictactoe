package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private Button[][] buttons = new Button[3][3]; // 2 d array of buttons created
private boolean player1Turn = true; //x player will start the game
private int roundCount; //9 counts our rounds. There are 9 rounds and if we do not have a winner, then we call it a draw

    private int player1Points;  //calculates the point of player1
   private int player2Points;

   private TextView textViewPlayer1;  //this will display the points of player1
   private TextView textViewPlayer2; //this will display the points of player2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayer1 = findViewById(R.id.text_view_p1); //taking refernces to our textviews
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        for (int i = 0; i <3 ; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonId = "button_" + i + j; //
                int resID = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[i][j] = findViewById(resID); //get references to all our button
            buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.resetButton); //reset button for resetting
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        //Here button is a class v is it's object
        //getText() - Return the text that TextView is displaying.
if(!((Button) v).getText().toString().equals("")) //check if it is  empty
{
    return;
}
/* If it's player 1 turn then set the correspondin button value to X
else it will change to O
 */
if (player1Turn){ //This would be checked as player1Turn is boolean
    ((Button) v).setText("X");
}
else{
    ((Button) v).setText("O");
}
roundCount++; //after someone's turn
if(checkForWin()) {
    if(player1Turn) {
        player1Wins();
    }
    else{
        player2Wins();
    }

}
else if(roundCount == 9){
    draw();
}
else{
    player1Turn = !player1Turn; //switch turns player1's turn to its opposite
}
    }
    //LOGIC OF THE GAME
    private boolean checkForWin() {
String[][] field = new String[3][3];
//go through all our buttons and save them in this
for(int i = 0;i < 3;i++){
    for (int j = 0;j< 3;j++){
        field[i][j] = buttons[i][j].getText().toString();
    }
}
//checking rows
for (int i = 0;i<3;i++){
    if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")){
        return true;
    }
}
//checking columns
        //!field[0][i].equals("") this probably used if there is not three times empty field
        for (int i = 0;i<3;i++){
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")){
                return true;
            }
        }


        //checking right diagonals;
        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")){
            return true;
        }
        //checking left diagonal
        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")){
            return true;
        }
        return false;
    }
    private void player1Wins() {
player1Points++; //player1 points incremented
//display toast saying
Toast.makeText(this, "Player 1 Wins", Toast.LENGTH_SHORT).show();
  updatePointsText(); //yes it's understandable new game you win
  resetboard(); // reset the board a round is over
    }
    private void player2Wins(){
player2Points++;//player2 points incremented
//display toast saying
        Toast.makeText(this, "Player 2 wins", Toast.LENGTH_SHORT).show();
        updatePointsText();//yes it's understandable new game you win
        resetboard();// reset the board a round is over
    }
    private void draw(){
        Toast.makeText(this, "draw", Toast.LENGTH_SHORT).show();
//no increment no player has wun as well as points would not be also updated
        resetboard();
    }
    //here Textviews are appended
    private void updatePointsText(){
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }
    //reset function invoked when someone wins or is a draw round that is roundcount ==9
    private void resetboard() {
        for(int i = 0;i<3;i++){
            for(int j = 0;j < 3;j++){
                buttons[i][j].setText("");
            }
        }
        //for the new gane started
        roundCount = 0;
        player1Turn = true;
    }
    //function to perform when our reset button is clicked
    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetboard();
    }
//onSaveInstanceState() and onRestoreInstanceState() are only explicitly called by Android when the Activity needs to be recreated, generally after a configuration change (ex. changing orientation).

    @Override
    protected void onSaveInstanceState( Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");

    }
}