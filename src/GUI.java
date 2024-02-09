import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public abstract class GUI extends Formula1ChampionshipManager {

    static final int MAX_FIELDS_PER_DRIVER = 8;
    static final int INDEX_FIRST_NAME = 0;
    static final int INDEX_LAST_NAME = 1;
    static final int INDEX_TEAM = 2;
    static final int INDEX_COUNTRY = 3;
    static final int INDEX_POINTS = 4;
    static final int INDEX_FIRST_POS = 5;
    static final int INDEX_SECOND_POS = 6;
    static final int INDEX_THIRD_POS = 7;
    static final String[] COL_NAMES = {"First Name", "Last Name", "Team", "Country", "Points", "1st Times", "2nd Times", "3rd Times"};
    static JTable table;
    static DefaultTableModel tableModel;

    public static void createTable(){

        JFrame frame = new JFrame("Formula 1 Championship Manager");
        JPanel topPanel = new JPanel();
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createHorizontalStrut(100));
        topPanel.setBorder(new EmptyBorder(50, 0, 20, 0));

        JLabel logoLabel = new JLabel();
        logoLabel.setMaximumSize(new Dimension(595,120));
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("images/f1logo.png").getImage().getScaledInstance(235, 120, Image.SCALE_DEFAULT));
        logoLabel.setIcon(imageIcon);

        JLabel tableTitle = new JLabel("Leaderboard Table");
        topPanel.add(logoLabel);
        topPanel.add(tableTitle);
        tableTitle.setFont(new Font("Arial",Font.BOLD, 20));


        Collections.sort(drivers, Formula1Driver.formula1DriverDescComparator);


        String[][] rows = new String[drivers.size()][MAX_FIELDS_PER_DRIVER];
        for(int i = 0; i < drivers.size(); i++){
            rows[i][INDEX_FIRST_NAME] = drivers.get(i).getFirstName();
            rows[i][INDEX_LAST_NAME] = drivers.get(i).getLastName();
            rows[i][INDEX_TEAM] = drivers.get(i).getTeam();
            rows[i][INDEX_COUNTRY] = drivers.get(i).getCountry();
            rows[i][INDEX_POINTS] = ""+drivers.get(i).getPointsEarned();
            rows[i][INDEX_FIRST_POS] = ""+drivers.get(i).getFirstPositionTimes();
            rows[i][INDEX_SECOND_POS] = ""+drivers.get(i).getSecondPositionTimes();
            rows[i][INDEX_THIRD_POS] = ""+drivers.get(i).getThirdPositionTimes();
        }

        tableModel = new DefaultTableModel(rows, COL_NAMES);
        table = new JTable(tableModel);

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(0xA64949));
        table.getTableHeader().setForeground(new Color(0xFFFFFF));
        table.setFont(new Font("Arial",Font.PLAIN, 12));
        table.setRowHeight(40);

        table.setEnabled(false);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


        JButton orderData = new JButton("Order by");
        JButton orderBy1st = new JButton("Order by 1st");
        JButton race = new JButton("Race!");

        orderData.addActionListener(new OrderDescAsc());
        orderBy1st.addActionListener(new OrderBy1st());
        race.addActionListener(new Race());

        for(Formula1Driver d : drivers){
            System.out.println(d.getPointsEarned() + " " + d.getFirstName() + " " + d.getLastName());
        }

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(orderData);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(orderBy1st);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(race);
        leftPanel.setBorder(new EmptyBorder(30, 100, 10, 0));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
        rightPanel.setBorder(new EmptyBorder(0, 0, 10, 80));
        rightPanel.add(scrollPane);

//        topPanel.setBackground(Color.CYAN);
//        leftPanel.setBackground(Color.YELLOW);
//        rightPanel.setBackground(Color.GREEN);

        frame.getContentPane().add(topPanel, "North");
        frame.getContentPane().add(leftPanel, "West");
        frame.getContentPane().add(rightPanel, "East");

        frame.setVisible(true);

    }

    static class OrderDescAsc implements ActionListener {
        int counter = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (counter % 2 == 0) {
                Collections.sort(drivers, Formula1Driver.formula1DriverAscComparator);
            } else {
                Collections.sort(drivers, Formula1Driver.formula1DriverDescComparator);
            }
            counter++;
            refreshTable(drivers);
        }
    }

    static class OrderBy1st implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Collections.sort(drivers, Formula1Driver.formula1Driver1stComparator);
            refreshTable(drivers);
        }
    }

    static class Race implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            final int NUMBER_OF_CARS = 10;
            final int[] POINTS_SYSTEM = {25, 18, 15, 12, 10, 8, 6, 4, 2, 1};
            ArrayList<Integer> driversPointsAttributed = new ArrayList<>();
            int carFinished = 0;
            while (carFinished < 10){
                int driverCrossFinishLine = randomPosition(NUMBER_OF_CARS);
                if(!(driversPointsAttributed.contains(driverCrossFinishLine))){
                    if(carFinished == 0){ drivers.get(driverCrossFinishLine).setFirstPositionTimes();}
                    else if (carFinished == 1) { drivers.get(driverCrossFinishLine).setSecondPositionTimes();}
                    else if (carFinished == 2) {drivers.get(driverCrossFinishLine).setThirdPositionTimes();}
                    driversPointsAttributed.add(driverCrossFinishLine);
                    drivers.get(driverCrossFinishLine).updatePoints(POINTS_SYSTEM[carFinished]);
                    carFinished++;
                }
            }
            refreshTable(drivers);
        }
        public int randomPosition(int maxRange) {
            Random rand = new Random();
            return rand.nextInt(maxRange);
        }
    }

    static public void refreshTable(ArrayList<Formula1Driver> driver){
        DefaultTableModel defaultTableModel = (DefaultTableModel)table.getModel();
        defaultTableModel.getDataVector().removeAllElements();
        ((DefaultTableModel)table.getModel()).fireTableDataChanged();
        String[][] rows = new String[driver.size()][MAX_FIELDS_PER_DRIVER];

        for(int i = 0; i < driver.size(); i++){
            rows[i][INDEX_FIRST_NAME] = driver.get(i).getFirstName();
            rows[i][INDEX_LAST_NAME] = driver.get(i).getLastName();
            rows[i][INDEX_TEAM] = driver.get(i).getTeam();
            rows[i][INDEX_COUNTRY] = driver.get(i).getCountry();
            rows[i][INDEX_POINTS] = ""+driver.get(i).getPointsEarned();
            rows[i][INDEX_FIRST_POS] = ""+driver.get(i).getFirstPositionTimes();
            rows[i][INDEX_SECOND_POS] = ""+driver.get(i).getSecondPositionTimes();
            rows[i][INDEX_THIRD_POS] = ""+driver.get(i).getThirdPositionTimes();
            tableModel.addRow(rows[i]);
        }
    }
}
