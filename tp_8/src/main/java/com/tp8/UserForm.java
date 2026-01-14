package com.tp8;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UserForm extends JFrame implements ActionListener {
    
    
    private JTextField txtId;
    private JTextField txtNom;
    private JTextField txtEmail;
    
    private JButton btnAjouter;
    private JButton btnModifier;
    private JButton btnSupprimer;
    private JButton btnEffacer;
    
    private JTable table;
    private DefaultTableModel tableModel;
    
    private UserDAO userDAO;
    
    
    public UserForm() {
        userDAO = new UserDAO();
        
        setTitle("Gestion des Utilisateurs - CRUD");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        createFormPanel();
        createTablePanel();
        createButtonPanel();
        
        refreshTable();
    }
    
    
    private void createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations Utilisateur"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtId = new JTextField(20);
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        formPanel.add(txtId, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Nom:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtNom = new JTextField(20);
        formPanel.add(txtNom, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);
        
        add(formPanel, BorderLayout.NORTH);
    }
    
    
    private void createTablePanel() {
        String[] colonnes = {"ID", "Nom", "Email"};
        
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtNom.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtEmail.setText(tableModel.getValueAt(selectedRow, 2).toString());
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des Utilisateurs"));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    
    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnEffacer = new JButton("Effacer");
        
        btnAjouter.setBackground(new Color(76, 175, 80));
        btnAjouter.setForeground(Color.WHITE);
        
        btnModifier.setBackground(new Color(33, 150, 243));
        btnModifier.setForeground(Color.WHITE);
        
        btnSupprimer.setBackground(new Color(244, 67, 54));
        btnSupprimer.setForeground(Color.WHITE);
        
        btnEffacer.setBackground(new Color(158, 158, 158));
        btnEffacer.setForeground(Color.WHITE);
        
        btnAjouter.addActionListener(this);
        btnModifier.addActionListener(this);
        btnSupprimer.addActionListener(this);
        btnEffacer.addActionListener(this);
        
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnEffacer);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == btnAjouter) {
            ajouterUtilisateur();
        } else if (source == btnModifier) {
            modifierUtilisateur();
        } else if (source == btnSupprimer) {
            supprimerUtilisateur();
        } else if (source == btnEffacer) {
            effacerChamps();
        }
    }
    
    
    private void ajouterUtilisateur() {
        String nom = txtNom.getText().trim();
        String email = txtEmail.getText().trim();

        if (nom.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez remplir tous les champs (Nom et Email).", 
                "Erreur de validation", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = new User(nom, email);
        
        if (userDAO.insertUser(user)) {
            JOptionPane.showMessageDialog(this, 
                "Utilisateur ajouté avec succès!", 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);
            effacerChamps();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de l'ajout de l'utilisateur.", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void modifierUtilisateur() {
        String idStr = txtId.getText().trim();
        String nom = txtNom.getText().trim();
        String email = txtEmail.getText().trim();

        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un utilisateur dans la table.", 
                "Erreur de sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (nom.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez remplir tous les champs (Nom et Email).", 
                "Erreur de validation", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(idStr);
        User user = new User(id, nom, email);
        
        if (userDAO.updateUser(user)) {
            JOptionPane.showMessageDialog(this, 
                "Utilisateur modifié avec succès!", 
                "Succès", 
                JOptionPane.INFORMATION_MESSAGE);
            effacerChamps();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de la modification de l'utilisateur.", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void supprimerUtilisateur() {
        String idStr = txtId.getText().trim();

        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un utilisateur dans la table.", 
                "Erreur de sélection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer cet utilisateur?", 
            "Confirmation de suppression", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(idStr);
            
            if (userDAO.deleteUser(id)) {
                JOptionPane.showMessageDialog(this, 
                    "Utilisateur supprimé avec succès!", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
                effacerChamps();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de la suppression de l'utilisateur.", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    private void effacerChamps() {
        txtId.setText("");
        txtNom.setText("");
        txtEmail.setText("");
        table.clearSelection();
        txtNom.requestFocus();
    }
    
    
    private void refreshTable() {

        tableModel.setRowCount(0);

        List<User> users = userDAO.getAllUsers();

        for (User user : users) {
            Object[] row = {
                user.getId(),
                user.getNom(),
                user.getEmail()
            };
            tableModel.addRow(row);
        }
    }
}
