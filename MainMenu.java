import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {

    private JButton agregarOrdenBtn;
    private JButton agregarAnalisisBtn;
    private JButton editarAnalisisBtn;
    private JButton eliminarAnalisisBtn;
    private JButton mostrarOrdenesBtn;
    private JButton salirBtn;

    public MainMenu() {
        setTitle("SISTEMA DE ORDENES DE TRABAJO");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout vertical para los botones
        setLayout(new GridLayout(6, 1, 10, 10));

        agregarOrdenBtn = new JButton("Agregar Orden de Trabajo");
        agregarAnalisisBtn = new JButton("Agregar Análisis a una Orden");
        editarAnalisisBtn = new JButton("Editar Análisis");
        eliminarAnalisisBtn = new JButton("Eliminar Análisis");
        mostrarOrdenesBtn = new JButton("Mostrar Órdenes y Análisis");
        salirBtn = new JButton("Salir");

        agregarOrdenBtn.addActionListener(this);
        agregarAnalisisBtn.addActionListener(this);
        editarAnalisisBtn.addActionListener(this);
        eliminarAnalisisBtn.addActionListener(this);
        mostrarOrdenesBtn.addActionListener(this);
        salirBtn.addActionListener(this);

        add(agregarOrdenBtn);
        add(agregarAnalisisBtn);
        add(editarAnalisisBtn);
        add(eliminarAnalisisBtn);
        add(mostrarOrdenesBtn);
        add(salirBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == agregarOrdenBtn) {
            JOptionPane.showMessageDialog(this, "Agregar Orden de Trabajo");
        } else if (e.getSource() == agregarAnalisisBtn) {
            JOptionPane.showMessageDialog(this, "Agregar Análisis a una Orden");
        } else if (e.getSource() == editarAnalisisBtn) {
            JOptionPane.showMessageDialog(this, "Editar Análisis");
        } else if (e.getSource() == eliminarAnalisisBtn) {
            JOptionPane.showMessageDialog(this, "Eliminar Análisis");
        } else if (e.getSource() == mostrarOrdenesBtn) {
            JOptionPane.showMessageDialog(this, "Mostrar Órdenes y Análisis");
        } else if (e.getSource() == salirBtn) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu menu = new MainMenu();
            menu.setVisible(true);
        });
    }
}