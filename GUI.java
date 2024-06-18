import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    static JLabel notice = new JLabel("");
    static int ROW = 0;
    static int COL = 0;
    static boolean run = false;
    static JFrame frame = new JFrame("Gauss-Elimination");
    static JPanel panel = new JPanel();
    static JTable table;
    static JTable inverseTable;

    public GUI() {
        frame.setSize(800, 400);
        frame.setLocation(500, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultLookAndFeelDecorated(true);

        if (!run) {
            startUI();
        } else {
            matrixUI();
        }

        frame.setVisible(true);
    }

    public static void startUI() {
        // Panel von startUI()
        panel.setBounds(10, 10, 200, 200);
        frame.add(panel);

        // Schrift 1
        JLabel text = new JLabel("Anzahl der Reihen: (max 6)");
        text.setBounds(50, 50, 180, 30);

        // Box 1
        JTextField textField = new JTextField();
        textField.setBounds(250, 50, 60, 30);

        // Schrift 2
        JLabel textTwo = new JLabel("Anzahl der Spalten: (max 6)");
        textTwo.setBounds(50, 150, 180, 30);

        // Box 2
        JTextField textFieldTwo = new JTextField();
        textFieldTwo.setBounds(250, 150, 60, 30);

        // Notiz
        notice.setBounds(220, 250, 150, 30);

        // Knopf
        JButton button = new JButton("Weiter");
        button.setBounds(50, 250, 100, 30);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textFieldValue = textField.getText();
                String textFieldTwoValue = textFieldTwo.getText();

                if (!textFieldValue.isEmpty() && !textFieldTwoValue.isEmpty()) {
                    try {
                        ROW = Integer.parseInt(textField.getText());
                        COL = Integer.parseInt(textFieldTwo.getText());
                        if (ROW <= 6 && COL <= 6) {
                            panel.removeAll();
                            panel.repaint();
                            run = true;
                            new GUI();
                        } else {
                            notice.setText("Maximal 6");
                        }
                    } catch (NumberFormatException f) {
                        notice.setText("Richtig ausfüllen!");
                    }
                } else {
                    notice.setText("Felder ausfüllen!");
                }
            }
        });

        panel.setLayout(null);
        panel.add(text);
        panel.add(textField);
        panel.add(textTwo);
        panel.add(textFieldTwo);
        panel.add(button);
        panel.add(notice);
    }

    public static void matrixUI() {
        // Schrift
        JLabel text = new JLabel("Matrix eingeben");
        text.setBounds(50, 50, 150, 30);

        // Tabelle
        table = new JTable(ROW, COL);
        table.setBounds(50, 100, (50 * COL), 16 * ROW);

        // Inverse Matrix Tabelle
        inverseTable = new JTable(ROW, ROW);
        inverseTable.setBounds(50 + (50 * COL) + 50, 100, (50 * ROW), 16 * ROW);

        // Titel für die Inverse Matrix
        JLabel inverseTitle = new JLabel("Inverse Matrix:");
        inverseTitle.setBounds(50 + (50 * COL) + 50, 50, 150, 30);

        // Notiz
        notice.setBounds(220, 250, 150, 30);

        // Knopf
        JButton button = new JButton("Weiter");
        button.setBounds(50, 250, 100, 30);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[][] matrix = new double[ROW][COL]; // Normale Matrix
                double[][] inverseMatrix = new double[ROW][ROW]; // Inverse Matrix

                // Inverse Matrix initialisieren
                for (int i = 0; i < ROW; i++) {
                    for (int j = 0; j < ROW; j++) {
                        inverseMatrix[i][j] = (i == j) ? 1.0 : 0.0;
                    }
                }

                TableCellEditor cellEditor = table.getCellEditor();
                if (cellEditor != null) {
                    cellEditor.stopCellEditing();
                }

                try {
                    for (int i = 0; i < ROW; i++) {
                        for (int j = 0; j < COL; j++) {
                            Object cellValue = table.getModel().getValueAt(i, j);
                            String cellValueAsString = cellValue.toString();
                            double value = Double.parseDouble(cellValueAsString);
                            matrix[i][j] = value;
                        }
                    }
                    notice.setText("");

                    // Gauß-Elimination: Berechnung der Inversen
                     Matrix.gaussJordan(matrix, inverseMatrix);

                     for (int i = 0; i < ROW; i++) {
                         for (int j = 0; j < COL; j++) {
                             table.setValueAt(matrix[i][j], i, j);
                         }
                     }

                     for (int i = 0; i < ROW; i++) {
                         for (int j = 0; j < ROW; j++) {
                             inverseTable.setValueAt(inverseMatrix[i][j], i, j);
                         }
                     }

                     if (!run) {
                         panel.removeAll();
                         panel.repaint();
                         new GUI();
                     }
                     run = false;
                } catch (NumberFormatException f) {
                    notice.setText("Richtig ausfüllen!");
                }
            }
        });

        panel.setLayout(null);
        panel.add(text);
        panel.add(table);
        panel.add(button);
        panel.add(notice);
        panel.add(inverseTable);
        panel.add(inverseTitle);
    }
}

/*
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    static JLabel notice = new JLabel("");
    static int ROW = 0;    //Anzahl der Reihen
    static int COL = 0;    //Anzahl der Spalten
    static boolean run = false;
    static JFrame frame = new JFrame("Gauss-Elimination");
    static JPanel panel = new JPanel();
    static JTable table;

    public GUI(){
        // Fenster einrichten
        frame.setSize(400, 400);
        frame.setLocation(500,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultLookAndFeelDecorated(true);

        if (!run){
            startUI();
        } else {
            matrixUI();
        }

        frame.setVisible(true);
    }
    public static void startUI(){
        // Panel von startUI()
        panel.setBounds(10, 10, 200, 200);
        frame.add(panel);

        // Schrift 1
        JLabel text = new JLabel("Anzahl der Reihen: (max 6)");
        text.setBounds(50,50, 180, 30);

        // Box 1
        JTextField textField = new JTextField();
        textField.setBounds(250,50,60, 30);

        // Schrift 2
        JLabel textTwo = new JLabel("Anzahl der Spalten: (max 6)");
        textTwo.setBounds(50,150, 180, 30);

        // Box 2
        JTextField textFieldTwo = new JTextField();
        textFieldTwo.setBounds(250,150,60, 30);

        // Notiz
        notice.setBounds(220, 250, 150, 30);

        // Knopf
        JButton button = new JButton("Weiter");
        button.setBounds(50, 250, 100, 30);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textFieldValue = textField.getText();
                String textFieldTwoValue = textFieldTwo.getText();

                if (!textFieldValue.isEmpty() && !textFieldTwoValue.isEmpty()) {
                    try {
                        ROW = Integer.parseInt(textField.getText());
                        COL = Integer.parseInt(textFieldTwo.getText());
                        if (ROW <= 6 && COL <= 6){
                            panel.removeAll();
                            panel.repaint();
                            run = true;
                            new GUI();
                        } else {
                            notice.setText("Maximal 6");
                        }
                    } catch (NumberFormatException f) {
                        notice.setText("Richtig ausfüllen!");
                    }
                }
                else  {
                    notice.setText("Felder ausfüllen!");
                }
            }
        });

        panel.setLayout(null);
        panel.add(text);
        panel.add(textField);
        panel.add(textTwo);
        panel.add(textFieldTwo);
        panel.add(button);
        panel.add(notice);

    }

    public static void matrixUI(){
        // Schrift
        JLabel text = new JLabel("Matrix eingeben");
        text.setBounds(50,50, 150, 30);

        // Tabelle
        table = new JTable(ROW, COL);
        table.setBounds(50, 100, (50*COL), 16*ROW);

        // Notiz
        notice.setBounds(220, 250, 150, 30);

        // Knopf
        JButton button = new JButton("Weiter");
        button.setBounds(50, 250, 100, 30);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[][] Matrix = new double[ROW][COL];
                TableCellEditor cellEditor = table.getCellEditor();
                if (cellEditor != null) {
                    cellEditor.stopCellEditing();
                }

                try {
                    for (int i = 0; i < ROW; i++) {
                        for (int j = 0; j < COL; j++) {
                            Object cellValue = table.getModel().getValueAt(i, j);
                            String cellValueAsString = cellValue.toString();
                            double Value = Double.parseDouble(cellValueAsString);
                            Matrix[i][j] = Value;
                        }
                    }
                    notice.setText("");

                    Matrix = GAUSS(Matrix);

                    for (int i = 0; i < ROW; i++) {
                        for (int j = 0; j < COL; j++) {
                            table.setValueAt(Matrix[i][j], i, j);
                        }
                    }

                    if (!run) {
                        panel.removeAll();
                        panel.repaint();
                        new GUI();
                    }
                    run = false;
                } catch (NumberFormatException f) {
                    notice.setText("Richtig ausfüllen!");
                }
            }
        });

        panel.setLayout(null);
        panel.add(text);
        panel.add(table);
        panel.add(button);
        panel.add(notice);
    }

    private static double[][] GAUSS(double[][] matrix) {
        double[][] result = new double[ROW][COL];     // ROW und COL sind Eigenschaften
        result = matrix;
        Integer Rang = 0;

        for(int j = 0; j < COL; j++) {    // Vertikal
            double Pivot = 0;
            Integer index = Rang;          // Reihe der gefundenen Pivot-zahl

            while (Pivot == 0  &&  index < ROW) {        // Ersten Pivot suchen
                if (result[index][j] != 0) {
                    Pivot = result[index][j];
                    break;
                }
                ++index;
            }

            if (Pivot != 0) {
                for (int k = 0; k < COL; k++) {    // Die Reihe der Pivot-pos. nach oben schieben und auf 1 setzten
                    result[index][k] /= Pivot;
                    double sw = result[index][k];
                    result[index][k] = result[Rang][k];
                    result[Rang][k] = sw;
                }

                for (int i = 0; i < ROW; i++) {    // Die anderen aus der Spalte auf 0 setzen
                    if (i == Rang  ||  result[i][j] == 0) { continue; }

                    double div = 0;
                    div = result[i][j] / result[Rang][j];

                    for (int k = j; k < COL; ++k) {
                        result[i][k] -= div * result[Rang][k];
                    }
                }

                ++Rang;
            }
        }

        return result;
    }
}
*/
