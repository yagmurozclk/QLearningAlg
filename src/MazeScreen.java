import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.border.BevelBorder;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.CompoundBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class MazeScreen extends JFrame {

	private JPanel contentPane;
	MazeCanvas mazeCanvas = null;
	private JTextField txtIteration;
	private JPanel panel_3;
	private JComboBox<Node> cbStartNode;
	private JComboBox<Node> cbEndNode;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MazeScreen frame = new MazeScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MazeScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("QLearning ile Labirentte Yol Bulma");
		setBounds(100, 100, 800, 670);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// mazeCanvas.setBounds(10, 10, 600, 600);
		/*
		 * JPanel panel = new JPanel(); panel.setBorder(null);
		 * panel.setBounds(10, 11, 566, 469); panel.add(mazeCanvas);
		 */

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(636, 11, 146, 616);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblBalangDm = new JLabel(
				"Ba\u015Flang\u0131\u00E7 D\u00FC\u011F\u00FCm\u00FC");
		lblBalangDm.setBounds(10, 54, 126, 14);
		panel.add(lblBalangDm);

		JLabel lblBitiDm = new JLabel("Biti\u015F D\u00FC\u011F\u00FCm\u00FC");
		lblBitiDm.setBounds(10, 102, 126, 14);
		panel.add(lblBitiDm);

		cbStartNode = new JComboBox<Node>();
		cbStartNode.setBounds(10, 69, 126, 22);
		panel.add(cbStartNode);

		cbEndNode = new JComboBox<Node>();
		cbEndNode.setBounds(10, 117, 126, 22);
		panel.add(cbEndNode);

		JButton btnNewButton = new JButton("Input Ekle");
		btnNewButton.setBounds(10, 11, 128, 32);
		panel.add(btnNewButton);

		txtIteration = new JTextField();
		txtIteration.setBounds(10, 179, 126, 20);
		panel.add(txtIteration);
		txtIteration.setColumns(10);

		JLabel lblIterasyonSays = new JLabel("\u0130terasyon Say\u0131s\u0131");
		lblIterasyonSays.setBounds(10, 162, 126, 14);
		panel.add(lblIterasyonSays);

		JButton btnNewButton_1 = new JButton("R ve Q Olu\u015Ftur");
		btnNewButton_1.setBounds(10, 210, 126, 23);
		panel.add(btnNewButton_1);

		JButton button = new JButton("Yol \u00C7iz");
		button.setBounds(10, 244, 126, 23);
		panel.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mazeCanvas.setDrawPath(true);
				mazeCanvas.repaint();
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (mazeCanvas == null) {
					JOptionPane.showMessageDialog(btnNewButton_1.getParent()
							.getParent(), "Önce Labirent Oluþturmalýsýnýz!",
							"Uyarý", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (txtIteration.getText() == null
						|| txtIteration.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									btnNewButton_1.getParent().getParent(),
									"Q matrisinin oluþturulmasý için Ýterasyon Sayýsý belirlemelisiniz!",
									"Uyarý", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					mazeCanvas.getQlearning().setIterationNumber(
							Integer.parseInt(txtIteration.getText()));
				} catch (NumberFormatException e) {

					JOptionPane
							.showMessageDialog(
									btnNewButton_1.getParent().getParent(),
									"Q matrisinin oluþturulmasý için Ýterasyon Sayýsý belirlemelisiniz!",
									"Uyarý", JOptionPane.ERROR_MESSAGE);
					return;
				}

				mazeCanvas.qlearning.setStartNode(mazeCanvas
						.getNode(((Node) cbStartNode.getSelectedItem()).getId()));
				mazeCanvas.qlearning.setEndNode(mazeCanvas
						.getNode(((Node) cbEndNode.getSelectedItem()).getId()));
				try {
					mazeCanvas.fillRAndQMatrix();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// mazeCanvas.fillQMatrix();
				/*
				 * if(txtEndNode.getText()==null ||
				 * txtEndNode.getText().isEmpty()){
				 * JOptionPane.showMessageDialog(btnNewButton_1.getParent().
				 * getParent(), "Bitiþ Düðümü Seçmelisiniz!", "Uyarý",
				 * JOptionPane.ERROR_MESSAGE); return; }
				 */
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System
						.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(contentPane);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();

					panel_3.removeAll();
					mazeCanvas = new MazeCanvas(600, 600, selectedFile);
					panel_3.add(mazeCanvas);

					NodeComboBoxModel startNodeModel = new NodeComboBoxModel(
							mazeCanvas.getNodes());
					NodeComboBoxModel endNodeModel = new NodeComboBoxModel(
							mazeCanvas.getNodes());

					cbStartNode.setModel(startNodeModel);
					cbEndNode.setModel(endNodeModel);

				}
			}
		});

		panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(10, 12, 616, 615);
		contentPane.add(panel_3);
		panel_3.setLayout(null);

	}
}
