package com.example;

import domain.*;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.System.exit;

public class Match3Game extends Application {

    private static final int SIZE = 5;
    private Button[][] buttons = new Button[SIZE][SIZE];
    private Label statusLabel=new Label();
    private Label levelLabel=new Label();
    private Label withdrawLabel=new Label();
    boolean[] ifSucess = new boolean[]{true,false,false,false,false,false};
    Button[] stage=new Button[6];
    String path;
    boolean ifenter=true;
    public static void main(String[] args) {
        launch(args);
    }
    public int withdrawnumber=1;
    @Override
    public void start(Stage primaryStage) {
        System.setProperty("prism.order", "sw");
        BorderPane root = new BorderPane();
        AtomicBoolean isHummar = new AtomicBoolean(false);
        // Initialize the board with random values
        Scanner input = new Scanner(System.in);
        ArrayList<Board> boards = new ArrayList<>();
        char[][] map = readMap("src/main/resources/board/1.txt".toCharArray());
        boards.add(new LetterNumber(1, 2, new int[] { 6, 0, 0, 0, 0 }, map));
        map = readMap("src/main/resources/board/2.txt".toCharArray());
        boards.add(new LetterNumber(2, 2, new int[] { 0, 6, 0, 0, 0 }, map));
        map = readMap("src/main/resources/board/3.txt".toCharArray());
        boards.add(new LetterNumber(3, 2, new int[] { 0, 3, 3, 0, 0 }, map));
        map = readMap("src/main/resources/board/4.txt".toCharArray());
        boards.add(new DeleteNumber(4, 2, 9, map));
        map = readMap("src/main/resources/board/5.txt".toCharArray());
        boards.add(new OnceDeleteNumber(5, 2, 5, 2, map));
        map = readMap("src/main/resources/board/6.txt".toCharArray());
        boards.add(new SpecialType(6, 2, 3, map));
        AtomicInteger page= new AtomicInteger();
        //right panel
        VBox rightPane = new VBox();
        for (int i = 0; i < 6; i++) {
            stage[i] = new Button("game"+(i+1));
            stage[i].setTextFill(Color.GRAY);
            int FinalI=i;
            stage[i].addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
                if (event.getButton() == MouseButton.PRIMARY) {
                    if(ifSucess[FinalI]){
                        boards.get(page.get()).restart();
                        upgradeboard(boards.get(page.get()));
                        page.set(FinalI);
                        upgradeboard(boards.get(page.get()));
                    }
                }
            });
        }
        stage[0].setTextFill(Color.RED);
        rightPane.getChildren().addAll(stage[0],stage[1],stage[2],stage[3],stage[4],stage[5]);


        // Left panel
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));
        levelLabel.setText("关卡编号:"+boards.get(page.get()).getLevel());
        statusLabel.setText(boards.get(page.get()).displaystate());
        Label toolLabel = new Label("hummer:"+boards.get(page.get()).getHummer());
        Button hummer=new Button();
        hummer.setText("hummer");
        Button restart=new Button();
        restart.setText("restart");
        Button giveup=new Button();
        giveup.setText("giveup");
        Button withdraw=new Button();
        withdrawLabel.setText("withdraw:"+withdrawnumber);
        withdraw.setText("withdraw");
        hummer.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if (event.getButton() == MouseButton.PRIMARY) {
                isHummar.set(true);
            }
        });
//        restart.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            // 检查是否是鼠标左键点击
//            if (event.getButton() == MouseButton.PRIMARY) {
//                boards.get(page.get()).restart();
//                upgradeboard(boards.get(page.get()));
//            }
//        });
        restart.setOnMouseReleased(mouseEvent->{
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                boards.get(page.get()).restart();
                upgradeboard(boards.get(page.get()));
            }
        });
        giveup.setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if(page.get()+1>=boards.size()){
                    exit(0);
                }
                page.getAndIncrement();
                ifSucess[page.get()]=true;
                upgradeboard(boards.get(page.get()));
            }
        });
        withdraw.setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                boards.get(page.get()).getFormerstate();
                withdrawnumber--;
                upgradeboard(boards.get(page.get()));
            }
        });
//        giveup.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            // 检查是否是鼠标左键点击
//            if (event.getButton() == MouseButton.PRIMARY) {
//                if(page.get()+1>=boards.size()){
//                    exit(0);
//                }
//                page.getAndIncrement();
//                ifSucess[page.get()]=true;
//                upgradeboard(boards.get(page.get()));
//            }
//        });
//        withdraw.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            // 检查是否是鼠标左键点击
//            if (event.getButton() == MouseButton.PRIMARY) {
//                boards.get(page.get()).getFormerstate();
//                withdrawnumber--;
//                upgradeboard(boards.get(page.get()));
//            }
//        });
        leftPanel.getChildren().addAll(levelLabel, statusLabel, withdrawLabel,toolLabel, hummer, restart, giveup,withdraw);

        // Center panel (game board)
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j] = new Button();
                if(boards.get(page.get()).getstate(i, j).getType()>='a'&&boards.get(page.get()).getstate(i, j).getType()<='e'){
                    path="/images/" + (boards.get(page.get()).getstate(i, j).getType()-'a'+1) + ".png";
                } else if (boards.get(page.get()).getstate(i, j).getType()=='|') {
                    path="/images/-.png";
                }
                else{
                path="/images/" + boards.get(page.get()).getstate(i, j).getType() + ".png";}
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(30); // 设置图片宽度为50
                imageView.setFitHeight(30); // 设置图片高度为50
                buttons[i][j].setGraphic(imageView);
                buttons[i][j].setPrefSize(30, 30);
                int finalI = i;
                int finalJ = j;
                buttons[i][j].setOnMouseClicked(mouseEvent -> {
                    if(isHummar.get()){
                        boards.get(page.get()).hummer(finalI,finalJ);
                        upgradeboard(boards.get(page.get()));
                        toolLabel.setText("hummer:"+boards.get(page.get()).getHummer());
                        isHummar.set(false);
                    }else{
                        boards.get(page.get()).setFormerstate();
                        boards.get(page.get()).clear(finalI,finalJ);
                        upgradeboard(boards.get(page.get()));
                    }
                    if(boards.get(page.get()).success())
                    {
                        if(page.get()+1>=boards.size()){
                            exit(0);
                        }
                        page.getAndIncrement();
                        withdrawnumber++;
                        ifSucess[page.get()]=true;
                        upgradeboard(boards.get(page.get()));
                    }
                });
//                buttons[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//                    // 检查是否是鼠标左键点击
//                    if (event.getButton() == MouseButton.PRIMARY) {
//                        if(isHummar.get()){
//                            boards.get(page.get()).hummer(finalI,finalJ);
//                            upgradeboard(boards.get(page.get()));
//                            toolLabel.setText("hummer:"+boards.get(page.get()).getHummer());
//                            isHummar.set(false);
//                        }else{
//                            boards.get(page.get()).setFormerstate();
//                            boards.get(page.get()).clear(finalI,finalJ);
//                            upgradeboard(boards.get(page.get()));
//                        }
//                        if(boards.get(page.get()).success())
//                        {
//                            if(page.get()+1>=boards.size()){
//                                exit(0);
//                            }
//                            page.getAndIncrement();
//                            withdrawnumber++;
//                            ifSucess[page.get()]=true;
//                            upgradeboard(boards.get(page.get()));
//                        }
//                    }
//                });
                AtomicReference<Double> startX = new AtomicReference<>((double) 0);
                AtomicReference<Double> startY = new AtomicReference<>((double) 0);
                AtomicLong startTime = new AtomicLong();
                buttons[i][j].setOnDragDetected(event -> {
                    startX.set(Double.valueOf(event.getX()));
                    startY.set(Double.valueOf(event.getY()));
                    startTime.set(System.currentTimeMillis());
                });
                buttons[i][j].setOnMouseDragged(event->{
                    long currentTime = System.currentTimeMillis();
                    long duration = currentTime - startTime.get();
                    if (duration <= 2000) {
                        double x = event.getX() - startX.get();
                        double y = event.getY() - startY.get();
                        if ((Math.abs(x) > 10 || Math.abs(y) > 10 )&&ifenter) {
                            ifenter=false;
                            boards.get(page.get()).setFormerstate();
                            if (Math.abs(x) > Math.abs(y)) {
                                if (x > 0)
                                    boards.get(page.get()).swap(finalI, finalJ, 4);
                                else
                                    boards.get(page.get()).swap(finalI, finalJ, 3);
                            } else {
                                if (y > 0)
                                    boards.get(page.get()).swap(finalI, finalJ, 2);
                                else
                                    boards.get(page.get()).swap(finalI, finalJ, 1);
                            }
                            upgradeboard(boards.get(page.get()));
                        }
                        if (boards.get(page.get()).success()) {
                            if (page.get() + 1 >= boards.size()) {
                                exit(0);
                            }
                            page.getAndIncrement();
                            withdrawnumber++;
                            ifSucess[page.get()] = true;
                            upgradeboard(boards.get(page.get()));
                        }
                    }
                });
                buttons[i][j].setOnMouseReleased(event->{
                    ifenter=true;
                });
                gridPane.add(buttons[i][j], j, i);
            }
        }

        root.setLeft(leftPanel);
        root.setCenter(gridPane);
        root.setRight(rightPane);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("消消乐");
        primaryStage.show();
    }


    public static char[][] readMap(char[] location) {
        String filePath = new String(location);
        char[][] charArray = new char[5][5];
        try {
            Scanner input = new Scanner(new File(filePath));
            for (int i = 0; i < 5; i++) {
                String line = input.nextLine();
                for (int j = 0; j < 5; j++) {
                    charArray[i][j] = line.charAt(j);
                }
            }
            input.close();
            return charArray;
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到");
            return charArray;
        }
    }
    public void upgradeboard(Board board) {
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                String path;
                if(board.getstate(i, j).getType()>='a'&&board.getstate(i, j).getType()<='e'){
                    path="/images/" + (char)(board.getstate(i, j).getType()-'a'+'1') + ".png";
                } else if (board.getstate(i, j).getType()=='|') {
                    path="/images/-.png";
                }
                else{
                    path="/images/" + board.getstate(i, j).getType() + ".png";
                }
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(30); // 设置图片宽度为50
                imageView.setFitHeight(30); // 设置图片高度为50
                buttons[i][j].setGraphic(imageView);
            }
        }
        statusLabel.setText(board.displaystate());
        levelLabel.setText("关卡编号:"+board.getLevel());
        for(int i=0;i<6;i++){
            if(ifSucess[i])
                stage[i].setTextFill(Color.RED);
        }
        withdrawLabel.setText("withdraw:"+withdrawnumber);
    }


    private String getDragDirection(double deltaX, double deltaY) {
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            return deltaX > 0 ? "Right" : "Left";
        } else {
            return deltaY > 0 ? "Down" : "Up";
        }
    }
}
