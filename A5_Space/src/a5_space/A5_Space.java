/*
 David Hu 
 May 21th
 Assignment 5, Question 1: A5_Space
 This program is a game called Space Invaders. Players are able to use arrow keys to control the ship's movement
 left and right, and space bar to shoot missles. Aliens will move across the screen with a certain speed. 
 Player's objective is to destroy all alien ships before they move to the bottom of the screen.
 Points will be awarded as player destroys alien ships. 

 */
package a5_space;

import java.awt.*; //Import java abstract window kit package 
import javax.swing.*; //Import java's toolkit Swing package 
import java.awt.event.*; //Allows java to listen for events
import java.io.*; // Import package to enable images in the program 
import javax.imageio.*; // Import package to enable image interaction in the program 

public class A5_Space extends JPanel implements KeyListener {

    int style = Font.ITALIC; // create a new variable, style, and define the font of the words as ITALIC to italicize them
    Font font = new Font("Ariel", style, 20); //creates a new font, which is "Ariel", itacilized and has a size of 20
    int s = 0; // create a new integer variable called score and initialize its value to 0
    String score = "Score: " + s; // create a string variable called score to display player's score
    String result = ""; // show the result of the round to the player 

    boolean pressedLeft, pressedRight, pressedSpace; // create three boolean variables to record keyboard status 
    int spaceShipX = 400; // create an integer variable spaceShipX and intialize it as 400 (initial X position of the space ship)
    int spaceShipY = 500; // create an integer variable spaceShipY and intialize it as 500 (initial Y position of the space ship)
    int tShot = 100; // create an integer variable tShot as the total number of shots, and set its value to 100
    int bulletsX[] = new int[tShot]; // create an integer type array named bulletsX to record the x position of the bullet, and set its memory as the total number of shots 
    int bulletsY[] = new int[tShot]; // create an integer type array named bulletsY to record the y position of the bullet, and set its memory as the total number of shots 
    int status = 1; // create an integer variable called status to determine the status of the bullet shot
    int bShot = 0; // create an integer variable called bShot to indicate the current bullet shot
    int alienX[] = new int[9]; // create an integer type array called alienX to indicate the x coordinate of the alien ship, and set its memory to 9, meaning 9 aliens in total
    int alienY[] = new int[9]; // create an integer type array called alienY to indicate the Y coordinate of the alien ship, and set its memory to 9, meaning 9 aliens in total
    int alienlife[] = new int[9]; // create an integer type array called alienLife to determine if the alien ship has been destroyed. Since there are 9 aliens in total, the memory for this array is also 9
    int timer = 0; // create an integer variable called timer to define the refresh rate of the screen, or how fast are the alien ships moving 
    int move[] = new int[9]; // create an integer type array called move to define the movement for alien ships per time interval
    Image img, img2, img3, SpaceShip, AlienShip, Missles; // define image variables, and the three image being imported 

    Dimension size; // record the size of the frame

    A5_Space() {
        this.addKeyListener(this); //add the key listener
        setFocusable(true); //set the focus of the keyboard

        // Import images
        try {
            img = ImageIO.read(new File("SpaceShip.PNG")); // read the three images
            img2 = ImageIO.read(new File("AlienShip.PNG"));
            img3 = ImageIO.read(new File("Missles.JPG"));

        } catch (IOException e) { // if images are unfound
            System.out.println("File not found"); // display information that the file is not found 
            System.exit(-1); // exit the program
        }

        SpaceShip = img.getScaledInstance(175, 220, Image.SCALE_SMOOTH); // scale the three images to the corresponding scale 
        AlienShip = img2.getScaledInstance(50, 40, Image.SCALE_SMOOTH);
        Missles = img3.getScaledInstance(30, 50, Image.SCALE_SMOOTH);

        // initialize Alien ship coordinates, not actually drawing them yet 
        for (int i = 0; i < 3; i++) { // define the x coordinate for the alien ships on the first row using a for loop
            alienX[i] = alienX[i] + 90 * (i + 1);
        }

        for (int i = 0; i < 3; i++) { // define the y coordinate for the alien ships on the first row using a for loop
            alienY[i] = 90;
        }

        for (int i = 3; i < 6; i++) { // define the x coordinate for the alien ships on the second row using a for loop
            alienX[i] = alienX[i] + 90 * (i - 2);
        }

        for (int i = 3; i < 6; i++) { // define the y coordinate for the alien ships on the second row using a for loop
            alienY[i] = 135;
        }

        for (int i = 6; i < 9; i++) { // define the x coordinate for the alien ships on the third row using a for loop
            alienX[i] = alienX[i] + 90 * (i - 5);
        }

        for (int i = 6; i < 9; i++) { // define the y coordinate for the alien ships on the third row using a for loop
            alienY[i] = 180;
        }

        for (int i = 0; i < 9; i++) { // define the movement per time frame for all nine alien ships using a for loop
            move[i] = 10;   // set the speed to 10
        }

        for (int i = 0; i < 9; i++) { // define the life for all nine alien ships using a for loop
            alienlife[i] = 1;   // all alien ships have initial life 1
        }

    }

    public void keyTyped(KeyEvent e) { //for the key typed

    }

    public void keyPressed(KeyEvent e) { //for the key pressed
        char key = (char) e.getKeyCode(); //acquire the ascii value for the key pressed

        if (key == e.VK_LEFT || key == 'a') { //if the left arrow key is pressed
            pressedLeft = true; // set pressedLeft to be true
            spaceShipX = spaceShipX - 15; // define the speed for space ship by changing the value of the x coordinate 

        } else if (key == e.VK_RIGHT || key == 'd') { //if the right arrow key is pressed
            pressedRight = true; // set pressedRight to be true
            spaceShipX = spaceShipX + 15;// define the speed for space ship by changing the value of the x coordinate 
        } else if (key == e.VK_ESCAPE || key == 'q') // if esc is pressed 
        {
            System.exit(0); //quit the program
        } else if (key == e.VK_SPACE) { // if space bar is pressed 
            pressedSpace = true; // set pressedSpace to be true
            System.out.println("Space pressed"); // output message "Space pressed" in the console
        }

        repaint(); //refresh the frame
    }

    public void paintComponent(Graphics g) { //for paint
        super.paintComponent(g); // clears the screen
        g.setColor(Color.white); //set the color to be white
        size = getSize(); //get dimensions of the frame
        g.fillRect(0, 0, size.width, size.height); // fill the background with color white 
        g.setColor(Color.black); //set the color to be black 
        g.setFont(font); //set the font to be variable font's font

        g.drawString(score, 50, 50); // draw string score in the position of (50,50)
        g.drawString(result, 50, 100); // draw string result in the position of (50,100)

        // Bullet control
        if (status == 1) { // if status is equal to 1 (the initial condition)
            bulletsX[bShot] = spaceShipX; // set the x coordinate the current bullet to be the current x coordinate of the space ship
            bulletsY[bShot] = spaceShipY; // set the y coordinate the current bullet to be the current y coordinate of the space ship
        } // by doing this, we can ensure that the bullet follows the space ship, thus when shooting the bullet, the bullet always appears beside the space ship

        if (pressedSpace) { // if space bar is pressed 
            g.drawImage(Missles, bulletsX[bShot] + 72, bulletsY[bShot], null); // draw the missle image as the bullet. Its x coordinate is at the center of the image 
            bulletsY[bShot] = (bulletsY[bShot] - 8); // its y coordinate is constantly moving upward 
            status = 2; // change the status to 2 (or any number beside 1 to limit the number of bullets the player can shoot 

        }

        if (bulletsY[bShot] < -1) { // if the bullet goes out of the frame (beyond player's sight inside the frame)
            pressedSpace = false; // reset pressedSpace to be false 
            bShot++; // increase the bullet index (the number of the bullet being shot) by 1
            status = 1; // change status back to 1 so that the bullet can move consistently with the space ship
        }

        // Space ship control
        if (spaceShipX < -40) { // if the space ship touches the left edge of the frame 
            spaceShipX = -40; // Forbid the space ship to continually move left by setting its x coordinate to be that of the left edge 
        }
        if (spaceShipX > size.width - 130) { // if the space ship touches the right edge of the frame 
            spaceShipX = size.width - 130; // Forbid the space ship to continually move right by setting its x coordinate to be that of the right edge 
        }
        g.drawImage(SpaceShip, spaceShipX, spaceShipY, null); // draw the space ship image 

        // timer 
        timer++; // increase the timer by 1 to refresh the animation

        // Draw alien ship images 
        if (timer > 3) { // if timer is greater than 5 (the timer is the time interval for the alien ships to move and can be adjusted to change the speed of the alien ships)
            for (int i = 0; i < 9; i++) {
                alienX[i] = alienX[i] + move[i]; // control all aliens movement using a for loop 
            }

            timer = 0; // reset timer to 0
        }
        for (int i = 0; i < 9; i++) {
            g.drawImage(AlienShip, alienX[i], alienY[i], null); // draw all alien ships using a for loop
        }

        // Win / lose condition
        if (alienY[0] > (size.height - 200) || alienY[1] > (size.height - 200) || alienY[2] > (size.height - 200)
                || alienY[3] > (size.height - 200) || alienY[4] > (size.height - 200) || alienY[5] > (size.height - 200)
                || alienY[6] > (size.height - 200) || alienY[7] > (size.height - 200) || alienY[8] > (size.height - 200)) { // if one of the aliens reach the specified height
            for (int k = 0; k < 9; k++) {
                alienX[k] = 1000; // move all alien ships out of the frame 
            }
            score = "";
            result = "You lose! Your final score is " + s + "!"; // display final results
        } else if (alienlife[0] == 0 && alienlife[1] == 0 && alienlife[2] == 0 && alienlife[3] == 0
                && alienlife[4] == 0 && alienlife[5] == 0 && alienlife[6] == 0 && alienlife[7] == 0
                && alienlife[8] == 0) {     // if all of the alien ship's life is 0, meaning all alien ships have been destroyed 
            score = "";
            result = "You win! Your final score is " + s + "!"; // display final results
        }

        // Alien movement
        for (int i = 0; i < 9; i++) {
            if ((alienX[i] > (size.width - 45) || alienX[i] < 0) && alienlife[i] == 1) { // if the alien ship is still alive and it has reached one of the edges of the frame
                move[i] = -move[i]; // reverse the direction of the movement
                alienY[i] = alienY[i] + 45; // move the ship to the next row
                alienX[i] = alienX[i] + move[i]; // continue moving
            }
        }

        // Collision Detection
        for (int i = 0; i < tShot; i++) { // for each of the bullet shot
            for (int j = 0; j < 9; j++) { // for each of the alien ships 
                if ((bulletsX[i] + 72) > (alienX[j] + 5) && (bulletsX[i] + 72) < (alienX[j] + 55)
                        && bulletsY[i] > alienY[j] && bulletsY[i] < (alienY[j] + 40)) { // if the x, y coordinate of the bullet is within the alien ship image's coordinate
                    pressedSpace = false; // reset pressedSpace to false
                    status = 1; // change status back to 1
                    bShot++; // increase the bullet index by 1
                    bulletsX[i] = 10000; // move the current bullet out of the frame 
                    bulletsY[i] = 10000;

                    alienlife[j] = 0; // set the current alienlife to 0
                    alienX[j] = 10000; // move the current alien ship out of the frame, meaning it has been destroyed 

                    s = s + 1000;
                    score = "Score: " + s;

                }
            }
        }

        repaint(); // refresh the frame

        delay(3); // set delay
    }

    public static void delay(int mili) { // delay method
        try {
            Thread.sleep(mili); //run one frame for every x milliseconds
        } catch (InterruptedException e) { //if the delay code is interrupted
            System.out.println("ERROR IN SLEEPING"); // output statement "ERROR IN SLEEPING"
        }
    }

    public void keyReleased(KeyEvent e) { //for key released
        pressedLeft = pressedRight = false; // reset these two booleans as false 
        repaint(); //refresh the frame
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("A5_Space"); //create a new JFrame called Test Frame
        frame.getContentPane().add(new A5_Space()); //Creates a constructor of class called A5_Space
        frame.setSize(900, 700); //Set the size of the frame to be 900 * 700
        frame.setVisible(true); //makes the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // clears the data of the frame upon exit

    }

}



