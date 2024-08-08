package game;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Random;

//This is the main frame of the game, included game logics.
public class GameFrame extends JFrame implements KeyListener, ActionListener {

    //the size of puzzle is 4*4
    int[][] data= new int[4][4];

    // records the location of the blank space in the array
    int x=0;
    int y=0;

    //the path to puzzle image folder
    String path = "image\\Cat\\cat2\\";

    //stores the correct order of image fragments定义一个二维数组，存储正确的数据
    int[][] win={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };
    // records the steps of movement
    int step =0;

    //The menu items of function
    JMenuItem replayItem = new JMenuItem("restart game");
    JMenuItem closeItem = new JMenuItem("close game");
    JMenuItem accountItem = new JMenuItem("My selfie");

    //The image options
    JMenuItem TFItem = new JMenuItem("TFboys");
    JMenuItem CatItem = new JMenuItem("Cat");
    JMenuItem ikunItem = new JMenuItem("ikun");

    public GameFrame(){


        //initial game frame
        initJFrame();

        //initial menu items
        initJMenuBar();

        //initial data(shuffle the fragments)
        initData();

        //initial image
        initImage();

        //initial puzzle instruction
        initialInstruction();


        //set the puzzle visible
        this.setVisible(true);
    }

    //Initializes and displays an instructional dialog.
    private void initialInstruction(){
        //create a dialog window
        JDialog jD = new JDialog();
        //contains a JLabel with an image of instruction
        JLabel jLabel = new JLabel(new ImageIcon("sporter\\instruction.png"));
        jLabel.setBounds(0,0,584,312);

        jD.getContentPane().add(jLabel);
        // set the size of dialog
        jD.setSize(600,350);
        // set the dialog on the top
        jD.setAlwaysOnTop(true);
        // display dialog
        jD.setVisible(true);

    }



    //Initializes and shuffles the data array.
    private void initData() {
        int[] tempArr = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

        //Shuffle the array by swapping each element with a randomly chosen element
        Random r = new Random();
        for (int i = 0;i<tempArr.length;i++){
            //Generate a random index
            int index = r.nextInt(tempArr.length);

            //Swap the current element with the element at the random index.
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;

        }

        //Populate a 4x4 matrix with the shuffled numbers.
        for (int i = 0;i<tempArr.length;i++){
            if (tempArr[i]==0){
                x=i/4;
                y=i%4;
            }

            //Assign the shuffled number to the appropriate position in the 4x4 matrix.
            data[i / 4][i % 4] = tempArr[i];
        }

    }

    // Initializes and updates the UI components for the game window.
    private void initImage() {

        //Remove all existing components from the content pane.
        this.getContentPane().removeAll();

        //Check if the victory condition is met.
        if (victory()){

            // Create and add a victory image label to the content pane.
            JLabel winJLabel = new JLabel(new ImageIcon("sporter\\win.jpg"));
            winJLabel.setBounds(190,283,288,175);
            this.getContentPane().add(winJLabel);
        }

        // Create and add a label to display the step counter.
        JLabel stepCount = new JLabel("steps counter: "+step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);

        // Iterate over the grid to populate image tiles.
        for (int i=0;i<4;i++){

            for (int j=0;j<4;j++){

                // Retrieve the number for the current tile from the data array.
                int num = data[i][j];

                // Create a label with an image icon based on the tile number.
                JLabel jLabel = new JLabel(new ImageIcon(path+num+".jpg"));

                // Set a border for the tile image.
                jLabel.setBorder(new BevelBorder(1));

                // Set the position and size of the tile image.
                jLabel.setBounds(105*j+83,105*i+134,105,105);

                // Set the position and size of the tile image.
                this.getContentPane().add(jLabel);

                // Repaint the content pane to reflect the changes.
                this.getContentPane().repaint();
            }
        }

        // Create and add a background image label to the content pane.
        ImageIcon bg = new ImageIcon("sporter\\background.jpg");
        JLabel background = new JLabel(bg);
        background.setBounds(40,40,508,560);

        // Add the background image to the content pane.
        this.getContentPane().add(background);

    }


    // Initializes and sets up the menu bar
    private void initJMenuBar() {
        // Create a new JMenuBar instance to hold the menus.
        JMenuBar jMenuBar = new JMenuBar();

        // Create the main menu items: "Function"
        JMenu functionJMenu = new JMenu("Function");

        // Create a submenu under "Function" for changing pictures.
        JMenu changeMenu = new JMenu("change Picture");

        // Add menu items to the "Function" menu.
        functionJMenu.add(replayItem);
        functionJMenu.add(closeItem);

        // Attach action listeners to menu items to handle user actions.
        replayItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        TFItem.addActionListener(this);
        CatItem.addActionListener(this);
        ikunItem.addActionListener(this);

        // Add the "Function" menus to the menu bar.
        jMenuBar.add(functionJMenu);

        // Add items to the "change Picture" submenu under "Function".
        changeMenu.add(TFItem);
        changeMenu.add(CatItem);
        changeMenu.add(ikunItem);

        // Add the "change Picture" submenu to the "Function" menu.
        functionJMenu.add(changeMenu);

        // Set the menu bar for the current window.
        this.setJMenuBar(jMenuBar);
    }

    //Initializes the main JFrame settings
    private void initJFrame() {
        // Set the size of the JFrame
        this.setSize(603,680);
        // Set the title of the JFrame window
        this.setTitle("Puzzle-offline v1.0");
        // Make the JFrame always appear on top of other windows.
        this.setAlwaysOnTop(true);
        // Center the JFrame on the screen.
        this.setLocationRelativeTo(null);
        // Set the default close operation to exit.
        // The parameter value 3 corresponds to JFrame.EXIT_ON_CLOSE.
        this.setDefaultCloseOperation(3);
        // Set the layout manager of the JFrame to null, allowing manual positioning of components.
        this.setLayout(null);
        // Add a key listener to the JFrame to handle key events.
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // Handles the event when a key is pressed
    @Override
    public void keyPressed(KeyEvent e){
        // Retrieve the key code from the KeyEvent object.
        int code = e.getKeyCode();
        // Check if the key code corresponds to the 'A' key
        if (code==65){
            // Remove all components from the content pane
            this.getContentPane().removeAll();
            // display the current puzzle completed image
            JLabel all= new JLabel(new ImageIcon(path+"all.jpg"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);
            // Create an ImageIcon for the background image
            ImageIcon bg = new ImageIcon("sporter\\background.jpg");
            JLabel background = new JLabel(bg);
            background.setBounds(40,40,508,560);
            // Create an ImageIcon for the background image
            this.getContentPane().add(background);
            // Repaint the content pane to reflect the changes
            this.getContentPane().repaint();
        }

    }

    //Handles the event when a key is released
    @Override
    public void keyReleased(KeyEvent e) {
        // Check if the game is in a victory state
        if (victory()){
            return;
        }
        // Retrieve the key code
        int code =e.getKeyCode();

        // Handle the left arrow key
        if (code==37){
            System.out.println("move left");
            if (y==3){
                return;
            }
            //shift the value from the right to the current position.
            data[x][y] = data[x][y+1];
            data[x][y+1]=0;
            // Move the position to the left.
            y++;
            // Increment the step count.
            step++;
            // Refresh the display to reflect changes.
            initImage();

            //Handle the up arrow key
        } else if (code==38) {
            // Increment the step count.
            step++;
            System.out.println("move up");
            // Check if the x position is at the top edge
            if (x==3){
                return;
            }
            //shift the value from below to the above position.
            data[x][y] = data[x+1][y];
            data[x+1][y]=0;
            x++;

            initImage();

            // Handle the right arrow key
        } else if (code==39) {

            step++;
            System.out.println("move right");
            if (y==0){
                return;
            }
            //shift the value from the left to the right position
            data[x][y] = data[x][y-1];
            data[x][y-1]=0;
            y--;

            initImage();


            // Handle the down arrow key
        } else if (code==40) {

            step++;
            System.out.println("move down");
            if (x==0){
                return;
            }
            //shift the value from above to the below position.
            data[x][y] = data[x-1][y];
            data[x-1][y]=0;
            x--;

            initImage();

            // Handle the 'A' key
        } else if (code==65) {
            initImage();

            // Handle the 'W' key
        } else if (code==87) {

            // Reset the data array to its initial state.
            data=new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
            initImage();
        }


    }

    // check if the sort of image fragments matchs the correct sort
    public boolean victory(){
        // Iterate through each row
        for (int i=0; i<data.length;i++){
            // Iterate through each column
            for (int j=0; j<data[i].length;j++){
                // Compare the current element with the corresponding element in the winning state array.
                if (data[i][j] != win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }


    // Handles action events triggered by menu item selections.
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the source of the action event.
        Object obj = e.getSource();

        // Handle the replay item action.
        if (obj==replayItem){
            // Reset the step counter to zero.
            step=0;


            initData();

            initImage();

            // Handle the close item action.
        } else if (obj==closeItem) {
            // Exit the application.
            System.exit(0);

            // Handle the account item action.

            // Handle the TFItem image
        } else if (obj==TFItem) {
            // Handle the TFItem action.
            Random r = new Random();
            int num=r.nextInt(4)+1;

            // Set the image path based on the random number.
            path="image\\TFboys\\tf"+num+"\\";
            initImage();
        } else if (obj==CatItem) {
            Random r = new Random();
            int num=r.nextInt(3)+1;
            path="image\\Cat\\cat"+num+"\\";
            initImage();
        }else if(obj==ikunItem) {
            Random r = new Random();
            int num = r.nextInt(4) + 1;
            path = "image\\ikun\\ikun" + num + "\\";
            initImage();
        }

    }
}