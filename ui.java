import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ui extends JFrame {
    private JTable contactTable;
    private DefaultTableModel tableModel;

    public ui() {
        setTitle("Phonebook App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Phone", "Email", "Address"}, 0);
        contactTable = new JTable(tableModel);
        add(new JScrollPane(contactTable), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add Contact");
        JButton updateButton = new JButton("Update Contact");
        JButton deleteButton = new JButton("Delete Contact");
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addContact());
        updateButton.addActionListener(e -> updateContact());
        deleteButton.addActionListener(e -> deleteContact());

        loadContacts();
        setVisible(true);
    }

    private void loadContacts() {
        tableModel.setRowCount(0);
        List<Contact> contacts = DatabaseManager.getContacts();
        for (Contact contact : contacts) {
            tableModel.addRow(new Object[]{contact.getId(), contact.getName(), contact.getPhoneNumber(), contact.getEmail(), contact.getAddress()});
        }
    }

    private void addContact() {
        String name = JOptionPane.showInputDialog("Enter Name:");
        String phone = JOptionPane.showInputDialog("Enter Phone:");
        String email = JOptionPane.showInputDialog("Enter Email:");
        String address = JOptionPane.showInputDialog("Enter Address:");
        DatabaseManager.addContact(name, phone, email, address);
        loadContacts();
    }

    private void updateContact() {
        int row = contactTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a contact to update!"); return; }

        int id = (int) tableModel.getValueAt(row, 0);
        String name = JOptionPane.showInputDialog("Enter Name:", tableModel.getValueAt(row, 1));
        String phone = JOptionPane.showInputDialog("Enter Phone:", tableModel.getValueAt(row, 2));
        String email = JOptionPane.showInputDialog("Enter Email:", tableModel.getValueAt(row, 3));
        String address = JOptionPane.showInputDialog("Enter Address:", tableModel.getValueAt(row, 4));

        DatabaseManager.updateContact(id, name, phone, email, address);
        loadContacts();
    }

    private void deleteContact() {
        int row = contactTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a contact to delete!"); return; }

        int id = (int) tableModel.getValueAt(row, 0);
        DatabaseManager.deleteContact(id);
        loadContacts();
    }

    public static void main(String[] args) { new ui(); }
}
