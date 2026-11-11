package finalproject;

import java.awt.BorderLayout;

import java.awt.Color; 
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Polygon;
import java.awt.Point;
import java.awt.RenderingHints;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class RubiksCubeGUI extends JFrame {

    private Cube cube;
    private MoveHistory history;

    private JLabel movesLabel;
    private JLabel capacityLabel;
    private JLabel statusLabel;

    private JLabel arraySizeLabel;
    private JLabel resizeLabel;

    private javax.swing.JProgressBar usageBar;

    private JTextArea historyArea;

    private JPanel cubePanel;

    private int resizeEvents = 0;
    private int lastCapacity = 2;


    // COLORS
    private static final Color BACKGROUND =  new Color(5, 8, 13);
    private static final Color PANEL = 	 new Color(10, 16, 24);
    private static final Color CYAN =    new Color(0, 210, 255);
    private static final Color GREEN =   new Color(0, 230, 140);
    private static final Color PURPLE =  new Color(190, 70, 255);
    private static final Color RED =	 new Color(255, 50, 70);


    // CONSTRUCTOR
    public RubiksCubeGUI() {

        cube = new Cube();
        history = new MoveHistory();

        setTitle( "Dynamic Cube - Rubik's Cube Simulator");
        setSize(1100, 750);
        setMinimumSize(new Dimension (900,650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buildGUI();
        updateGUI();

        setVisible(true);
  }

    // BUILD GUI
    private void buildGUI() {

        JPanel root = new JPanel (new BorderLayout(12, 12));

        root.setBackground(BACKGROUND); 
        root.setBorder(new EmptyBorder(15, 15, 15, 15));

        // HEADER
        root.add(createHeader(), BorderLayout.NORTH);

        // CENTER
        JPanel center = new JPanel(new GridLayout(1, 2, 12, 0));
        
        center.setOpaque(false);

        center.add(createCubeSection());

        center.add(createInformationSection());

        root.add(center, BorderLayout.CENTER);
  
     // CONTROLS
        root.add(createControls(),BorderLayout.SOUTH);

        setContentPane(root);
}

    // HEADER
    private JPanel createHeader() {

        JPanel panel = new JPanel(new BorderLayout());
        
        panel.setOpaque(false);

    //TITLE
        JLabel title = new JLabel("DYNAMIC CUBE");
               
        title.setForeground(Color.WHITE);

        title.setFont(new Font("Arial",Font.BOLD,30));

        JLabel subtitle = new JLabel("RUBIK'S CUBE SIMULATOR");

        subtitle.setForeground(CYAN);

        subtitle.setFont(new Font ("Arial",Font.PLAIN,12));


        JPanel text = new JPanel(new GridLayout(2,1));

        text.setOpaque(false);
       
        text.add(title);
        text.add(subtitle);

        JLabel status = new JLabel("● READY");

        status.setForeground(GREEN);

        status.setFont(new Font("Arial",Font.BOLD,13));

        panel.add(text,BorderLayout.WEST );

        panel.add(status,BorderLayout.EAST);

  	return panel;
}
 
    // CUBE SECTION
       private JPanel createCubeSection() {

        JPanel panel = createPanel(CYAN);

        panel.setLayout(new BorderLayout());

        panel.add(createSectionTitle("CUBE VIEW",CYAN),BorderLayout.NORTH);

    // REPLACE THE OLD PLACEHOLDER HERE
        cubePanel = new CubeCanvas();

        cubePanel.setBackground(new Color(3,7,12));

        panel.add(cubePanel,BorderLayout.CENTER);

        JLabel instruction = new JLabel("3D CUBE VIEW • DRAG TO ROTATE",SwingConstants.CENTER);

        instruction.setForeground(new Color(100,160,180));

        instruction.setFont(new Font("Arial",Font.PLAIN,11));

        panel.add(instruction,BorderLayout.SOUTH);

     return panel;
}

    /// INFORMATION SECTION
    private JPanel createInformationSection() {

        JPanel panel = new JPanel(new GridLayout(3,1,0,10));
                 
        panel.setOpaque(false);

        panel.add(createSessionPanel());
      
        panel.add(createArrayPanel());

        panel.add(createHistoryPanel());

    return panel;
}
    
    // INFORMATION ROW
	 private JPanel createInfoRow(String labelText,JLabel valueLabel)
  {
     JPanel row = new JPanel(new BorderLayout());

     row.setOpaque(false);

     JLabel label = createInfoLabel(labelText);

    // Fixed width for the label column
     label.setPreferredSize(new Dimension(120,20));

     row.add(label,BorderLayout.WEST);

     row.add(valueLabel,BorderLayout.CENTER);

   return row;
 }
    
 //SESSION PANEL
	private JPanel createSessionPanel() {
	
	  JPanel panel = createPanel(CYAN);
	
	  panel.setLayout(new BorderLayout(0,5));

 // TITLE
	  panel.add(createSectionTitle("SESSION",CYAN),BorderLayout.NORTH);

 // INFORMATION
	  JPanel info = new JPanel();
	
	  info.setLayout(new BoxLayout(info,BoxLayout.Y_AXIS));
	
	  info.setOpaque(false);

   // MOVES
	  movesLabel = createInfoLabel("0");
	
	  info.add(createInfoRow("MOVES",movesLabel));

  // STATUS
	  statusLabel = createInfoLabel("SOLVED");
	
	  info.add(createInfoRow("STATUS",statusLabel));

  // SYSTEM
	  JLabel systemLabel = createInfoLabel("ONLINE");
	
	  info.add(createInfoRow("SYSTEM",systemLabel));
	
	  panel.add(info,BorderLayout.CENTER);

  return panel;
}
 
  //DYNAMIC ARRAY PANEL
	private JPanel createArrayPanel() {
	
	 JPanel panel = createPanel(GREEN);
	
	 panel.setLayout(new BorderLayout(0,3));

 // TITLE
	 panel.add(createSectionTitle("DYNAMIC ARRAY",GREEN),BorderLayout.NORTH);

 // INFORMATION
	 JPanel info = new JPanel();
	
	 info.setLayout(new BoxLayout(info,BoxLayout.Y_AXIS));
	
	 info.setOpaque(false);

 // SIZE
	 arraySizeLabel = createInfoLabel("0");
	
	 info.add(createInfoRow("SIZE",arraySizeLabel));

 // CAPACITY
	 capacityLabel = createInfoLabel("2");
	
	 info.add(createInfoRow("CAPACITY",capacityLabel));
	
 // RESIZE EVENTS
	 resizeLabel = createInfoLabel("0");
	
	 info.add(createInfoRow("RESIZE EVENTS",resizeLabel));
	
// STRUCTURE
	
	 JLabel structureLabel = createInfoLabel("MOVE HISTORY");
	
	 info.add(createInfoRow("STRUCTURE",structureLabel));
	
	 panel.add(info,BorderLayout.CENTER);

 // =====================================================
 // ARRAY USAGE
 // =====================================================

	 JPanel usagePanel =new JPanel(new BorderLayout(0,2));    
	
	 usagePanel.setOpaque(false);
	
	 JLabel usageLabel = createInfoLabel("ARRAY USAGE");
	
		 usageBar = new JProgressBar(0, 100);
		      
		 usageBar.setValue(0);
		
		 usageBar.setStringPainted(true);
		
		 usageBar.setString("0%");
		
		 usageBar.setForeground(GREEN);
		
		 usageBar.setBackground(new Color(25,35,45));
		
		 usageBar.setBorderPainted(false);
	
	 usagePanel.add(usageLabel,BorderLayout.NORTH);
	
	 usagePanel.add(usageBar,BorderLayout.CENTER);
	
	 panel.add(usagePanel,BorderLayout.SOUTH);


 return panel;
}

    // HISTORY
    private JPanel createHistoryPanel() {

        JPanel panel = createPanel(PURPLE);

        panel.setLayout(new BorderLayout());

        panel.add(createSectionTitle("MOVE HISTORY", PURPLE),BorderLayout.NORTH);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        
        historyArea.setBackground(new Color(3,7,12));

        historyArea.setForeground(Color.WHITE);

        historyArea.setFont(new Font("Monospaced",Font.PLAIN,12));

        JScrollPane scroll = new JScrollPane(historyArea);

        scroll.setBorder(BorderFactory.createLineBorder(new Color(40,60,80)));
    
        panel.add(scroll,BorderLayout.CENTER);
    
      return panel;
}
    
    // CONTROLS
    private JPanel createControls() {

        JPanel panel = createPanel(CYAN);

        panel.setLayout(new BorderLayout(0,8));

        panel.add( createSectionTitle("FACE MOVES", CYAN),BorderLayout.NORTH);

        JPanel moves = new JPanel(new GridLayout(2,6,6,5));
                      
        moves.setOpaque(false);

        String[] moveNames = {
                "U", "U'",
                "D", "D'",
                "L", "L'",
                "R", "R'",
                "F", "F'",
                "B", "B'"      
        };

        for (
                String move : moveNames) {

            JButton button = createButton(move,CYAN);

            button.addActionListener(e -> performMove(move));

            moves.add(button);
        }

        panel.add(moves,BorderLayout.CENTER);


        JPanel actions = new JPanel(
                         new GridLayout(
                                1,
                                3,
                                6,
                                0));
                     
        actions.setOpaque(false);

        JButton undo = createButton("UNDO", Color.YELLOW);

        JButton shuffle =createButton("SHUFFLE", PURPLE);

        JButton reset = createButton("RESET",RED);

        undo.addActionListener(
                e -> undoMove());

        shuffle.addActionListener(
                e -> shuffle());

        reset.addActionListener(
                e -> reset());

        actions.add(undo);
        actions.add(shuffle);
        actions.add(reset);

        panel.add(actions, BorderLayout.SOUTH);

        return panel;
    }

    
    // PERFORM MOVE

    private void performMove(
            String move) {

        cube.move(move);

        history.addMove(move);

        updateGUI();
    }


    // UNDO

    private void undoMove() {

        if (
                history.isEmpty()
        ) {

            return;
        }

        Move last = history.getLastMove();

        cube.move(last.getInverse()
        );

        history.removeLastMove();

        updateGUI();
    }

    // SHUFFLE

    private void shuffle() {

        String[] moves = {
                "U", "U'",
                "D", "D'",
                "L", "L'",
                "R", "R'",
                "F", "F'",
                "B", "B'"
        };


        Random random = new Random();

        for (
                int i = 0;
                i < 20;
                i++
        ) {

            String move = moves[random.nextInt(
                          moves.length)
                    ];


            cube.move(move);

            history.addMove(move);
        }

        updateGUI();
    }

    // RESET

    private void reset() {

        cube.reset();

        history.clear();

        resizeEvents = 0;

        lastCapacity = history.capacity();

        updateGUI();
    }


 // =========================================================
 // UPDATE GUI
 // =========================================================

 private void updateGUI() {

     int currentSize =
             history.size();

     int currentCapacity =
             history.capacity();


     // -----------------------------------------------------
     // DETECT DYNAMIC ARRAY RESIZING
     // -----------------------------------------------------

     if (currentCapacity > lastCapacity) {

         resizeEvents++;

         lastCapacity =
                 currentCapacity;
     }


     // -----------------------------------------------------
     // SESSION
     // -----------------------------------------------------

     movesLabel.setText(
             String.valueOf(currentSize)
     );


     // -----------------------------------------------------
     // ARRAY INFORMATION
     // -----------------------------------------------------

     arraySizeLabel.setText(
             String.valueOf(currentSize)
     );

     capacityLabel.setText(
             String.valueOf(currentCapacity)
     );

     resizeLabel.setText(
             String.valueOf(resizeEvents)
     );


     // -----------------------------------------------------
     // ARRAY USAGE
     // -----------------------------------------------------

     int usage = 0;

     if (currentCapacity > 0) {

         usage =
                 (
                         currentSize * 100
                 )
                 /
                 currentCapacity;
     }


     usage =
             Math.max(
                     0,
                     Math.min(
                             100,
                             usage
                     )
             );


     usageBar.setValue(
             usage
     );

     usageBar.setString(
             usage + "%"
     );


     // STATUS

     if (cube.isSolved()) {

         statusLabel.setText(
                 "SOLVED"
         );

     } else {

         statusLabel.setText(
                 "SCRAMBLED"
         );
     }

     
     // MOVE HISTORY

     historyArea.setText(
             history.getHistoryText()
     );


     // REDRAW CUBE

     cubePanel.repaint();
 }


    // PANEL

    private JPanel createPanel(Color accent) {

        JPanel panel = new JPanel();

        panel.setBackground(PANEL);
        
        panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder( new Color(
                                        accent.getRed(),
                                        accent.getGreen(),
                                        accent.getBlue(),
                                        150)),
                new EmptyBorder(
                                8,
                                10,
                                10,
                                10)
                	)
        		);


        return panel;
    }

    
    // SECTION TITLE

    private JLabel createSectionTitle(String text,Color accent) {

        JLabel label = new JLabel(text);

        label.setForeground(accent);

        label.setFont(new Font("Arial",Font.BOLD,13));

        return label;
    }

    // INFO LABEL

    private JLabel createInfoLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setForeground(Color.WHITE);

        label.setFont(
                new Font("Monospaced",Font.BOLD,12));

        return label;
    }

    // BUTTON

    private JButton createButton(String text,Color accent) {

        JButton button = new JButton(text);

        button.setForeground(Color.WHITE);
       
        button.setBackground(new Color(
                        8,
                        16,
                        25));
       
        button.setFont(new Font("Arial",Font.BOLD,12));

        button.setFocusPainted(false);
        
        button.setBorder(BorderFactory.createLineBorder(accent));
        
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }
    
 // =========================================================
 // ACTUAL 3D CUBE
 // =========================================================

 private class CubeCanvas extends JPanel {

     private double rotationX = -20;
     private double rotationY = 35;

     private double zoom = 1.0;

     private int mouseX;
     private int mouseY;

     private boolean dragging = false;


     // =====================================================
     // STICKER
     // =====================================================

     private class Sticker {

         double[] p1;
         double[] p2;
         double[] p3;
         double[] p4;

         double[] normal;

         double depth;

         Color color;


         Sticker(
                 double[] p1,
                 double[] p2,
                 double[] p3,
                 double[] p4,
                 double[] normal,
                 double depth,
                 Color color
         ) {

             this.p1 = p1;
             this.p2 = p2;
             this.p3 = p3;
             this.p4 = p4;

             this.normal = normal;

             this.depth = depth;

             this.color = color;
         }
     }


     // =====================================================
     // CONSTRUCTOR
     // =====================================================

     public CubeCanvas() {

         setBackground(
                 new Color(
                         3,
                         7,
                         12
                 )
         );

         setOpaque(true);


         MouseAdapter mouse =
                 new MouseAdapter() {

             @Override
             public void mousePressed(
                     MouseEvent e
             ) {

                 mouseX =
                         e.getX();

                 mouseY =
                         e.getY();

                 dragging = true;
             }


             @Override
             public void mouseReleased(
                     MouseEvent e
             ) {

                 dragging = false;
             }


             @Override
             public void mouseDragged(MouseEvent e) {

                 if (!dragging) {
                     return;
                 }

                 int dx =
                         e.getX() - mouseX;

                 int dy =
                         e.getY() - mouseY;


                 rotationY +=
                         dx * 0.7;

                 rotationX -=
                         dy * 0.7;


                 mouseX =
                         e.getX();

                 mouseY =
                         e.getY();


                 repaint();
             }


             @Override
             public void mouseWheelMoved(
                     MouseWheelEvent e
             ) {

                 zoom -=
                         e.getWheelRotation()
                                 * 0.08;


                 zoom =
                         Math.max(
                                 0.65,
                                 Math.min(
                                         1.45,
                                         zoom
                                 )
                         );


                 repaint();
             }
         };


         addMouseListener(mouse);

         addMouseMotionListener(mouse);

         addMouseWheelListener(mouse);
     }


     // =====================================================
     // PAINT
     // =====================================================

     @Override
     protected void paintComponent(
             Graphics graphics
     ) {

         super.paintComponent(
                 graphics
         );


         Graphics2D g =
                 (Graphics2D)
                 graphics.create();


         g.setRenderingHint(
                 RenderingHints.KEY_ANTIALIASING,
                 RenderingHints.VALUE_ANTIALIAS_ON
         );


         g.setRenderingHint(
                 RenderingHints.KEY_RENDERING,
                 RenderingHints.VALUE_RENDER_QUALITY
         );


         drawBackgroundGlow(g);


         List<Sticker> stickers =
                 createStickers();


         /*
          * Painter's algorithm.
          *
          * Farthest stickers are drawn first.
          */

         stickers.sort(
                 Comparator.comparingDouble(
                         s -> s.depth
                 )
         );


         /*
          * Back-face culling.
          *
          * Only draw surfaces facing the viewer.
          */

         for (
                 Sticker sticker :
                 stickers
         ) {

             if (
                     sticker.normal[2] > 0
             ) {

                 drawSticker(
                         g,
                         sticker
                 );
             }
         }


         g.dispose();
     }


     // =====================================================
     // BACKGROUND GLOW
     // =====================================================

     private void drawBackgroundGlow(
             Graphics2D g
     ) {

         int centerX =
                 getWidth() / 2;

         int centerY =
                 getHeight() / 2;


         for (
                 int radius = 360;
                 radius >= 40;
                 radius -= 20
         ) {

             int alpha =
                     Math.max(
                             1,
                             22 - radius / 18
                     );


             g.setColor(
                     new Color(
                             0,
                             120,
                             255,
                             alpha
                     )
             );


             g.fillOval(
                     centerX - radius / 2,
                     centerY - radius / 2,
                     radius,
                     radius
             );
         }
     }


     // =====================================================
     // CREATE STICKERS
     // =====================================================

     private List<Sticker> createStickers() {

         List<Sticker> list =
                 new ArrayList<>();


         addFace(
                 list,
                 Cube.Face.U
         );


         addFace(
                 list,
                 Cube.Face.D
         );


         addFace(
                 list,
                 Cube.Face.F
         );


         addFace(
                 list,
                 Cube.Face.B
         );


         addFace(
                 list,
                 Cube.Face.L
         );


         addFace(
                 list,
                 Cube.Face.R
         );


         return list;
     }


     // =====================================================
     // ADD FACE
     // =====================================================

     private void addFace(
             List<Sticker> list,
             Cube.Face face
     ) {

         char[][] colors =
                 cube.getFaceColors(
                         face
                 );


         double[] normal =
                 getNormal(face);


         double[] right =
                 getRight(face);


         double[] down =
                 getDown(face);


         for (
                 int row = 0;
                 row < 3;
                 row++
         ) {

             for (
                     int col = 0;
                     col < 3;
                     col++
             ) {

                 double[] center =
                         multiply(
                                 normal,
                                 1.52
                         );


                 center =
                         add(
                                 center,
                                 multiply(
                                         right,
                                         col - 1
                                 )
                         );


                 center =
                         add(
                                 center,
                                 multiply(
                                         down,
                                         row - 1
                                 )
                         );


                 double half =
                         0.46;


                 double[] p1 =
                         add(
                                 center,
                                 add(
                                         multiply(
                                                 right,
                                                 -half
                                         ),
                                         multiply(
                                                 down,
                                                 -half
                                         )
                                 )
                         );


                 double[] p2 =
                         add(
                                 center,
                                 add(
                                         multiply(
                                                 right,
                                                 half
                                         ),
                                         multiply(
                                                 down,
                                                 -half
                                         )
                                 )
                         );


                 double[] p3 =
                         add(
                                 center,
                                 add(
                                         multiply(
                                                 right,
                                                 half
                                         ),
                                         multiply(
                                                 down,
                                                 half
                                         )
                                 )
                         );


                 double[] p4 =
                         add(
                                 center,
                                 add(
                                         multiply(
                                                 right,
                                                 -half
                                         ),
                                         multiply(
                                                 down,
                                                 half
                                         )
                                 )
                         );


                 p1 =
                         rotate(
                                 p1
                         );


                 p2 =
                         rotate(
                                 p2
                         );


                 p3 =
                         rotate(
                                 p3
                         );


                 p4 =
                         rotate(
                                 p4
                         );


                 double[] rotatedNormal =
                         rotate(
                                 normal
                         );


                 double depth =
                         (
                                 p1[2]
                                 + p2[2]
                                 + p3[2]
                                 + p4[2]
                         ) / 4.0;


                 list.add(
                         new Sticker(
                                 p1,
                                 p2,
                                 p3,
                                 p4,
                                 rotatedNormal,
                                 depth,
                                 getCubeColor(
                                         colors[row][col]
                                 )
                         )
                 );
             }
         }
     }


     // =====================================================
     // FACE NORMALS
     // =====================================================

     private double[] getNormal(
             Cube.Face face
     ) {

         switch (face) {

             case U:

                 return new double[] {
                         0, -1, 0
                 };


             case D:

                 return new double[] {
                         0, 1, 0
                 };


             case F:

                 return new double[] {
                         0, 0, 1
                 };


             case B:

                 return new double[] {
                         0, 0, -1
                 };


             case L:

                 return new double[] {
                         -1, 0, 0
                 };


             case R:

                 return new double[] {
                         1, 0, 0
                 };


             default:

                 return new double[] {
                         0, 0, 0
                 };
         }
     }


     // =====================================================
     // FACE RIGHT
     // =====================================================

     private double[] getRight(
             Cube.Face face
     ) {

         switch (face) {

             case U:
                 return new double[] {
                         1, 0, 0
                 };


             case D:
                 return new double[] {
                         1, 0, 0
                 };


             case F:
                 return new double[] {
                         1, 0, 0
                 };


             case B:
                 return new double[] {
                         -1, 0, 0
                 };


             case L:
                 return new double[] {
                         0, 0, 1
                 };


             case R:
                 return new double[] {
                         0, 0, -1
                 };


             default:
                 return new double[] {
                         1, 0, 0
                 };
         }
     }


     // =====================================================
     // FACE DOWN
     // =====================================================

     private double[] getDown(
             Cube.Face face
     ) {

         switch (face) {

             case U:
                 return new double[] {
                         0, 0, 1
                 };


             case D:
                 return new double[] {
                         0, 0, -1
                 };


             case F:
             case B:
             case L:
             case R:

                 return new double[] {
                         0, 1, 0
                 };


             default:

                 return new double[] {
                         0, 1, 0
                 };
         }
     }


     // =====================================================
     // VECTOR ADDITION
     // =====================================================

     private double[] add(
             double[] a,
             double[] b
     ) {

         return new double[] {

                 a[0] + b[0],

                 a[1] + b[1],

                 a[2] + b[2]
         };
     }


     // =====================================================
     // VECTOR MULTIPLICATION
     // =====================================================

     private double[] multiply(
             double[] vector,
             double value
     ) {

         return new double[] {

                 vector[0] * value,

                 vector[1] * value,

                 vector[2] * value
         };
     }


     // =====================================================
     // 3D ROTATION
     // =====================================================

     private double[] rotate(
             double[] point
     ) {

         double x =
                 point[0];

         double y =
                 point[1];

         double z =
                 point[2];


         double rx =
                 Math.toRadians(
                         rotationX
                 );


         double ry =
                 Math.toRadians(
                         rotationY
                 );


         // X-axis rotation

         double y1 =
                 y * Math.cos(rx)
                 - z * Math.sin(rx);


         double z1 =
                 y * Math.sin(rx)
                 + z * Math.cos(rx);


         // Y-axis rotation

         double x2 =
                 x * Math.cos(ry)
                 + z1 * Math.sin(ry);


         double z2 =
                 -x * Math.sin(ry)
                 + z1 * Math.cos(ry);


         return new double[] {

                 x2,
                 y1,
                 z2
         };
     }


     // =====================================================
     // PROJECT 3D → 2D
     // =====================================================

     private Point project(
             double[] point
     ) {

         double camera =
                 8.0;


         double depth =
                 camera - point[2];


         if (
                 depth < 1
         ) {

             depth = 1;
         }


         double scale =
                 105
                 * zoom
                 * (
                         camera / depth
                 );


         int x =
                 (int)
                 (
                         getWidth() / 2.0
                         + point[0] * scale
                 );


         int y =
                 (int)
                 (
                         getHeight() / 2.0
                         + point[1] * scale
                 );


         return new Point(
                 x,
                 y
         );
     }


     // =====================================================
     // DRAW STICKER
     // =====================================================

     private void drawSticker(
             Graphics2D g,
             Sticker sticker
     ) {

         Point p1 =
                 project(
                         sticker.p1
                 );


         Point p2 =
                 project(
                         sticker.p2
                 );


         Point p3 =
                 project(
                         sticker.p3
                 );


         Point p4 =
                 project(
                         sticker.p4
                 );


         Polygon polygon =
                 new Polygon();


         polygon.addPoint(
                 p1.x,
                 p1.y
         );


         polygon.addPoint(
                 p2.x,
                 p2.y
         );


         polygon.addPoint(
                 p3.x,
                 p3.y
         );


         polygon.addPoint(
                 p4.x,
                 p4.y
         );


         // -------------------------------------------------
         // GLOW
         // -------------------------------------------------

         g.setColor(
                 new Color(
                         sticker.color.getRed(),
                         sticker.color.getGreen(),
                         sticker.color.getBlue(),
                         45
                 )
         );


         g.setStroke(
                 new BasicStroke(
                         10f
                 )
         );


         g.drawPolygon(
                 polygon
         );


         // -------------------------------------------------
         // FACE
         // -------------------------------------------------

         g.setColor(
                 sticker.color
         );


         g.fillPolygon(
                 polygon
         );


         // -------------------------------------------------
         // BLACK SEPARATION
         // -------------------------------------------------

         g.setColor(
                 new Color(
                         0,
                         0,
                         0,
                         240
                 )
         );


         g.setStroke(
                 new BasicStroke(
                         3f
                 )
         );


         g.drawPolygon(
                 polygon
         );


         // -------------------------------------------------
         // HIGHLIGHT
         // -------------------------------------------------

         g.setColor(
                 new Color(
                         255,
                         255,
                         255,
                         80
                 )
         );


         g.setStroke(
                 new BasicStroke(
                         1f
                 )
         );


         g.drawLine(
                 p1.x,
                 p1.y,
                 p2.x,
                 p2.y
         );
     }


     // =====================================================
     // RUBIK'S CUBE COLORS
     // =====================================================

     private Color getCubeColor(
             char color
     ) {

         switch (color) {

             case 'W':

                 return new Color(
                         245,
                         245,
                         250
                 );


             case 'Y':

                 return new Color(
                         255,
                         215,
                         20
                 );


             case 'G':

                 return new Color(
                         0,
                         225,
                         115
                 );


             case 'B':

                 return new Color(
                         35,
                         105,
                         255
                 );


             case 'O':

                 return new Color(
                         255,
                         125,
                         20
                 );


             case 'R':

                 return new Color(
                         255,
                         45,
                         60
                 );


             default:

                 return Color.GRAY;
         }
     }
 }
}