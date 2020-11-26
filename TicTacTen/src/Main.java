import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.scene.text.FontWeight;
import javafx.scene.Group;

/**
 * JavaFX Tic-Tac-Ten Project
 * github.com/shrays
 * */


public class Main extends Application {

    private boolean pTurn = true;
    private Button[][] btn = new Button[9][9];
    private boolean gameOver = false;
    private GridPane board;//, box;
    private GridPane [] box = new GridPane[9];

    private Text score, turn;
    private TextFlow titleFlow, scoreFlow, creditFlow;
    private Group titleGroup, scoreGroup, creditGroup;
    private StackPane headerStack;
    private BorderPane bPane;
    private String font, font2;
    private double size1, size2, size3;

    private int a = 0, b = 0;

    @Override
    public void start(Stage primaryStage) {

        //=====================Header Text====================================
        bPane = new BorderPane();
        font = "Consolas";
        font2 = "Courier New";
        size1 = 53;
        size2 = 32;
        size3 = 22;

        titleFlow = new TextFlow();
        Text title1 = new Text("Tic");
        title1.setFont(Font.font(font, FontWeight.BOLD, size1));
        title1.setFill(Color.rgb(79, 167, 236));
        Text title2 = new Text("Tac");
        title2.setFill(Color.rgb(236, 79, 79));
        title2.setFont(Font.font(font, FontWeight.BOLD, size1));
        Text title3 = new Text("Ten");
        title3.setFill(Color.rgb(79, 167, 236));
        title3.setFont(Font.font(font, FontWeight.BOLD, size1));
        titleFlow.getChildren().addAll(title1, title2, title3);
        titleGroup = new Group(titleFlow);

        scoreFlow = new TextFlow();
        score = new Text("X");
        score.setFont(Font.font(font2, FontWeight.BOLD, size2));
        score.setFill(Color.rgb(236, 79, 79));
        turn = new Text("'s Turn");
        turn.setFont(Font.font(font2, FontWeight.BOLD, size2));
        turn.setFill(Color.rgb(255, 255, 255));
        scoreFlow.getChildren().addAll(score, turn);
        scoreGroup = new Group(scoreFlow);

        creditFlow = new TextFlow();
        Text credit1 = new Text("github.com/\nshrays");
        credit1.setFont(Font.font(font2,FontWeight.BOLD, size3));
        credit1.setFill(Color.rgb(150, 150, 150));
        creditFlow.getChildren().addAll(credit1);
        creditGroup = new Group(creditFlow);

        headerStack = new StackPane(titleGroup, scoreGroup, creditGroup);
        StackPane.setAlignment(titleGroup, Pos.CENTER);
        StackPane.setAlignment(scoreGroup, Pos.BOTTOM_RIGHT);
        StackPane.setAlignment(creditGroup, Pos.CENTER_LEFT);
        headerStack.setPadding(new Insets(9,30,5,30));
        bPane.setTop(headerStack);
        bPane.setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0),CornerRadii.EMPTY,Insets.EMPTY)));

        //=====================GRID====================================
        board = new GridPane();
        int counter = 0;
        for (int blockColumn = 0; blockColumn < 3 ; blockColumn++) {
            for (int blockRow = 0; blockRow < 3; blockRow++)
            {
                box[counter] = new GridPane();
                box[counter].setStyle("-fx-background-color: black; -fx-background-insets: 0, 3; -fx-padding: 3;"); //fx padding 2 original
                for (int column = 0; column < 3; column++) {
                    for (int row = 0 ; row < 3; row++)
                    {
                        btn[a][b] = new Button();
                        btn[a][b].setMinSize(70, 70);
                        btn[a][b].setMaxSize(70, 70);

                        btn[a][b].setBackground(new Background(new BackgroundFill(Color.rgb(37,37,37),CornerRadii.EMPTY,Insets.EMPTY)));
                        btn[a][b].setBorder(new Border(new BorderStroke(Color.rgb(0, 0, 0), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0))));
                        btn[a][b].setOnAction(new Handler());

                        btn[a][b].setOnMouseEntered(t -> {
                            if(((Button)t.getSource()).getBackground().getFills().get(0).getFill().equals(Color.rgb(37,37,37)))
                            ((Button)t.getSource()).setBackground(new Background(new BackgroundFill(Color.rgb(60, 60, 60), CornerRadii.EMPTY, Insets.EMPTY)));
                            });

                        btn[a][b].setOnMouseExited(t -> {
                            if(((Button)t.getSource()).getBackground().getFills().get(0).getFill().equals(Color.rgb(60,60,60)))
                                ((Button)t.getSource()).setBackground(new Background(new BackgroundFill(Color.rgb(37, 37, 37), CornerRadii.EMPTY, Insets.EMPTY)));
                            });

                        GridPane.setConstraints(btn[a][b], row, column);
                        box[counter].getChildren().add(btn[a][b]);
                        b++;
                    }
                }
                GridPane.setConstraints(box[counter], blockRow, blockColumn);
                board.getChildren().add(box[counter]);
                a++;
                b = 0;
                counter++;
            }
        }

        //=====================SET STAGE===========================
        board.setPadding(new Insets(0,20,20,20));
        primaryStage.setScene(new Scene(bPane));
        bPane.setCenter(board);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static boolean firstMove = true;
    public static int lastPos1, lastPos2;
    public static boolean [] xBoxes = new boolean[9];
    public static boolean [] oBoxes = new boolean[9];

    private class Handler implements EventHandler<ActionEvent>
    {
        private int boxX, boxY, boardX, boardY, pos1, pos2;
        private String check;
        Node boxSource, boardSource;

        public void handle(ActionEvent e)
        {
            /** SET STANDARD [8][8] COORDINATES */
            boxSource = (Node)e.getSource();
            boardSource = ((Node)e.getSource()).getParent();

            boardX = GridPane.getColumnIndex(boardSource);
            boardY = GridPane.getRowIndex(boardSource);
            boxX = GridPane.getColumnIndex(boxSource);
            boxY = GridPane.getRowIndex(boxSource);

            switch(boardY)
            {
                case 0: pos1 = boardX + boardY + 0;
                    break;
                case 1: pos1 = boardX + boardY + 2;
                    break;
                case 2: pos1 = boardX + boardY + 4;
                    break;
            }
            switch(boxY)
            {
                case 0: pos2 = boxX + boxY + 0;
                    break;
                case 1: pos2 = boxX + boxY + 2;
                    break;
                case 2: pos2 = boxX + boxY + 4;
                    break;
            }

            /** FIRST MOVE LOGIC */
            if(firstMove)
            {
                markSquare();
                lastPos1 = pos1;
                lastPos2 = pos2;
                box[pos2].setStyle("-fx-background-color: #c8c8c8; -fx-background-insets: 0, 3; -fx-padding: 3;");
                score.setText("O");
                score.setFill(Color.rgb(79, 167, 236));
                pTurn = !pTurn;
                firstMove = false;
            }

            /** MAIN GAME LOGIC */
            if(btn[pos1][pos2].getText() == "" && !gameOver && (pos1 == lastPos2 || (xBoxes[lastPos2] || oBoxes[lastPos2])) && (xBoxes[pos1] != true && oBoxes[pos1] != true))
            {
                if(pTurn) /** CHANGE TURN INDICATOR */
                {
                    score.setText("O");
                    score.setFill(Color.rgb(79, 167, 236));
                }
                else
                {
                    score.setText("X");
                    score.setFill(Color.rgb(236, 79, 79));
                }

                markSquare();
                lastPos1 = pos1;
                lastPos2 = pos2;

                /** CHECK BOXES */
                check = "X";
                for (int i = 0; i < 2; i++)
                {
                    if ((btn[pos1][0].getText() == check && btn[pos1][1].getText() == check && btn[pos1][2].getText() == check) ||
                            (btn[pos1][3].getText() == check && btn[pos1][4].getText() == check && btn[pos1][5].getText() == check) ||
                            (btn[pos1][6].getText() == check && btn[pos1][7].getText() == check && btn[pos1][8].getText() == check) ||
                            (btn[pos1][0].getText() == check && btn[pos1][3].getText() == check && btn[pos1][6].getText() == check) ||
                            (btn[pos1][1].getText() == check && btn[pos1][4].getText() == check && btn[pos1][7].getText() == check) ||
                            (btn[pos1][2].getText() == check && btn[pos1][5].getText() == check && btn[pos1][8].getText() == check) ||
                            (btn[pos1][0].getText() == check && btn[pos1][4].getText() == check && btn[pos1][8].getText() == check) ||
                            (btn[pos1][2].getText() == check && btn[pos1][4].getText() == check && btn[pos1][6].getText() == check) )
                    {
                        if (check == "X")
                            xBoxes[pos1] = true;
                        else
                            oBoxes[pos1] = true;
                    }
                    check = "O";
                }
                /** CHECK BOARD */
                if ((xBoxes[0] && xBoxes[1] && xBoxes[2]) || (xBoxes[3] && xBoxes[4] && xBoxes[5]) || (xBoxes[6] && xBoxes[7] && xBoxes[8]) || (xBoxes[0] && xBoxes[3] && xBoxes[6]) ||
                        (xBoxes[1] && xBoxes[4] && xBoxes[7]) || (xBoxes[2] && xBoxes[5] && xBoxes[8]) || (xBoxes[0] && xBoxes[4] && xBoxes[8]) || (xBoxes[2] && xBoxes[4] && xBoxes[6]))
                    gameOver = true;
                if ((oBoxes[0] && oBoxes[1] && oBoxes[2]) || (oBoxes[3] && oBoxes[4] && oBoxes[5]) || (oBoxes[6] && oBoxes[7] && oBoxes[8]) || (oBoxes[0] && oBoxes[3] && oBoxes[6]) ||
                        (oBoxes[1] && oBoxes[4] && oBoxes[7]) || (oBoxes[2] && oBoxes[5] && oBoxes[8]) || (oBoxes[0] && oBoxes[4] && oBoxes[8]) || (oBoxes[2] && oBoxes[4] && oBoxes[6]))
                    gameOver = true;


                /** FILLS BOXES THAT ARE COMPLETED WITH WINNING COLOR - drawing? */
                for(int i = 0; i < 9; i++)
                {
                    if(xBoxes[i])
                    {
                        for(int j = 0; j < 9; j++) {
                            btn[i][j].setBackground(new Background(new BackgroundFill(Color.rgb(194, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
                            btn[i][j].setStyle("-fx-font-size: 3em; -fx-text-fill: #ec4f4f");
                        }
                    }
                    if(oBoxes[i])
                    {
                        for(int j = 0; j < 9; j++) {
                            btn[i][j].setBackground(new Background(new BackgroundFill(Color.rgb(0, 117, 195), CornerRadii.EMPTY, Insets.EMPTY)));
                            btn[i][j].setStyle("-fx-font-size: 3em; -fx-text-fill: #4fa7ec");
                        }
                    }
                }

                /** INDICATOR */
                for(int i = 0; i < 9; i++)
                {
                    box[i].setStyle("-fx-background-color: black; -fx-background-insets: 0, 3; -fx-padding: 3;");
                }

                box[pos2].setStyle("-fx-background-color: #c8c8c8; -fx-background-insets: 0, 3; -fx-padding: 3;");

                if(oBoxes[pos2] == true || xBoxes[pos2] == true)
                {
                    for(int i = 0; i < 9; i++)
                    {
                        box[i].setStyle("-fx-background-color: #c8c8c8; -fx-background-insets: 0, 3; -fx-padding: 3;");
                    }
                    box[pos2].setStyle("-fx-background-color: black; -fx-background-insets: 0, 3; -fx-padding: 3;");
                }

                pTurn = !pTurn;
            }
            /** GAMEOVER TEST AND VISUALS */
            if(gameOver)
            {
                pTurn = !pTurn;
                for(int i = 0; i < 9; i++)
                {
                    if(pTurn)
                        box[i].setStyle("-fx-background-color: #c20000; -fx-background-insets: 0, 3; -fx-padding: 3;");
                    else
                        box[i].setStyle("-fx-background-color: #0075c3; -fx-background-insets: 0, 3; -fx-padding: 3;");
                }

                turn.setText(" WINS!");
                if(pTurn) {
                    score.setText("X");
                    score.setFill(Color.rgb(236, 79, 79));
                    turn.setFill(Color.rgb(236, 79, 7));
                }
                else
                {
                    score.setText("O");
                    score.setFill(Color.rgb(79, 167, 236));
                    turn.setFill(Color.rgb(79, 167, 236));
                }
            } // else if
        } // Handle

        public void markSquare() /** FILL SQUARE AND CHANGE TEXT */
        {
            btn[pos1][pos2].setBorder(new Border(new BorderStroke(Color.rgb(200,200,200), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0)))); //234, 234, 79
            btn[lastPos1][lastPos2].setBorder(new Border(new BorderStroke(Color.rgb(0, 0, 0), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0))));

            btn[pos1][pos2].setText(pTurn ? "X" : "O");
            if(pTurn) {
                btn[pos1][pos2].setBackground(new Background(new BackgroundFill(Color.rgb(236, 79, 79), CornerRadii.EMPTY, Insets.EMPTY)));
                btn[pos1][pos2].setStyle("-fx-font-size: 3em; -fx-text-fill: #6e1313");
            } else {
                btn[pos1][pos2].setBackground(new Background(new BackgroundFill(Color.rgb(79, 167, 236), CornerRadii.EMPTY, Insets.EMPTY)));
                btn[pos1][pos2].setStyle("-fx-font-size: 3em; -fx-text-fill: #13336e");
            }
        }
    } //Handler Class

    public static void main(String[] args)
    {
        launch(args);
    }
}
