package src;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

class GestionPersonnesGUI {

    private final JFrame frame;
    private final JLabel nomLabel;
    private final JTextField nom;
    private final JLabel ageLabel;
    private final JTextField age;
    private final JScrollPane sp;
    private final JTable ListeDesPersonnes;
    private final JButton ajouter; 
    private final JButton supprimer;
    private int currentSelectedRow = -1;

    class TableRowClickListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = ListeDesPersonnes.getSelectedRow();
                if (selectedRow >= 0) {
                    currentSelectedRow = selectedRow;
                    supprimer.setEnabled(true);
                } else {
                    currentSelectedRow = -1;
                    supprimer.setEnabled(false);
                }
            }
        }
    }

    class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentSelectedRow >= 0) {
                DefaultTableModel model = (DefaultTableModel) ListeDesPersonnes.getModel();
                model.removeRow(currentSelectedRow);
                currentSelectedRow = -1;
                supprimer.setEnabled(false);
            }
        }
    }


    class SubmitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String nomEtudiant = nom.getText();
            String ageEtudiant = age.getText();
            if (nomEtudiant.isEmpty() || ageEtudiant.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs.");
                return;
            }
            try {
                Integer.parseInt(ageEtudiant);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "L'âge doit être un nombre entier.");
                return;
            }
            String[] row = { nomEtudiant, ageEtudiant };
            DefaultTableModel model = (DefaultTableModel) ListeDesPersonnes.getModel();
            model.addRow(row);
            nom.setText("");
            age.setText("");
        }
    }

    GestionPersonnesGUI() {
        frame = new JFrame("Personne manager (Swing)");

        nomLabel = new JLabel("Nom");
        nom = new JTextField();
        nomLabel.setBounds(50, 10, 300, 30);
        nom.setBounds(50, 40, 150, 20);

        ageLabel = new JLabel("Age");
        age = new JTextField();
        ageLabel.setBounds(50, 80, 300, 30);
        age.setBounds(50, 110, 150, 20);
        ListeDesPersonnes = this.createStudentJTable();
        sp = new JScrollPane(ListeDesPersonnes);
        sp.setBounds(50, 200, 400, 300);

        ajouter = new JButton(" Ajouter Etudiant ");
        ajouter.setBounds(50, 150, 200, 30);
        ajouter.addActionListener(new SubmitButtonListener());
        
        supprimer = new JButton(" Supprimer Etudiant ");
        supprimer.setBounds(260, 150, 200, 30);
        supprimer.addActionListener(new DeleteButtonListener());
        supprimer.setEnabled(false);
    }

    private JTable createStudentJTable() {
        String[] columnNames = {"Nom", "Age"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(new TableRowClickListener());
        table.setBounds(50, 200, 400, 300);
        return table;
    }

    public void run(String[] args) {
        frame.add(sp);
        frame.add(nomLabel);
        frame.add(nom);
        frame.add(ageLabel);
        frame.add(age);
        frame.add(ajouter);
        frame.add(supprimer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
