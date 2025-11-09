package file.compression.tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;

public class FileCompressionTool {
    private static String selectedAlgorithm = null;
    private static List<String[]> allStatsData = new ArrayList<>();
    private static List<String> generatedFiles = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("File Compression Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
        
        Color WelcomePanelColor = new Color(173, 216, 230);// Light Blue
        Color backgroundColor = new Color(245, 245, 245); // Light Gray
        Color panelColor = new Color(220, 220, 220); // Light Gray
        Color buttonColor = new Color(0, 102, 204); // Electric Blue
        Color buttonHoverColor = new Color(0, 85, 163); // Darker Blue for hover effect
        Color buttonTextColor = Color.WHITE; // White
        Color titleBackgroundColor = new Color(0, 0, 128); // Navy Blue;
        Color titleTextColor = Color.WHITE; // White

        // Create a CardLayout to switch between panels
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // Create the welcome panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
        welcomePanel.setBackground(WelcomePanelColor); // Light green background

        // Add images to the top left and top right
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(WelcomePanelColor);

        ImageIcon leftImageIcon = new ImageIcon(new ImageIcon("C:/Users/ABDUL RAFAY/Desktop/SSUET_Logo.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        ImageIcon rightImageIcon = new ImageIcon(new ImageIcon("C:/Users/ABDUL RAFAY/Desktop/SED.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

        JLabel leftImageLabel = new JLabel(leftImageIcon);
        JLabel rightImageLabel = new JLabel(rightImageIcon);

        imagePanel.add(leftImageLabel, BorderLayout.WEST);
        imagePanel.add(rightImageLabel, BorderLayout.EAST);
        welcomePanel.add(imagePanel, BorderLayout.NORTH);

        // Add center content
        JPanel centerPanel = new JPanel(new GridLayout(4, 1));
        centerPanel.setBackground(WelcomePanelColor);

        JLabel titleLabel = new JLabel("Sir Syed University of Engineering & Technology", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel deptLabel = new JLabel("\"Software Engineering Department\"", JLabel.CENTER);
        deptLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel projectLabel = new JLabel("Data Structures and Algorithm Project", JLabel.CENTER);
        projectLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel appNameLabel = new JLabel("File Compression Tool", JLabel.CENTER);
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        centerPanel.add(titleLabel);
        centerPanel.add(deptLabel);
        centerPanel.add(projectLabel);
        centerPanel.add(appNameLabel);

        welcomePanel.add(centerPanel, BorderLayout.CENTER);

        // Add start button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(WelcomePanelColor);

        JButton startButton = createButton("Get Started", buttonColor, buttonHoverColor, buttonTextColor);
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(new Color(0, 102, 204));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "mainPanel");
            }
        });

        buttonPanel.add(startButton);
        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(backgroundColor);

        JLabel titleLabelMain = new JLabel("File Compression Tool", SwingConstants.CENTER);
        titleLabelMain.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabelMain.setOpaque(true);
        titleLabelMain.setBackground(titleBackgroundColor);
        titleLabelMain.setForeground(titleTextColor);
        mainPanel.add(titleLabelMain, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBackground(panelColor);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBackground(panelColor);
        JLabel inputLabel = new JLabel("Input File: ");
        JTextField inputField = new JTextField(30);
        inputField.setEditable(false);
        JButton inputButton = createButton("Choose File", buttonColor, buttonHoverColor, buttonTextColor);
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(inputButton);

        JPanel compressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        compressPanel.setBackground(panelColor);
        JLabel compressLabel = new JLabel("Select Compression Algorithm: ");
        JMenuBar menuBar = new JMenuBar();
        JMenu compressionMenu = new JMenu("Choose Algorithm");
        compressionMenu.setFont(new Font("Arial", Font.PLAIN, 14));

        JMenu textMenu = new JMenu("Text");
        textMenu.setFont(new Font("Arial", Font.PLAIN, 14));
        JMenuItem huffmanOption = new JMenuItem("Huffman");
        JMenuItem gzipOption = new JMenuItem("Gzip");
        textMenu.add(huffmanOption);
        textMenu.add(gzipOption);

        JMenu imageMenu = new JMenu("Image");
        imageMenu.setFont(new Font("Arial", Font.PLAIN, 14));
        JMenuItem jpegOption = new JMenuItem("JPEG");
        JMenuItem gifOption = new JMenuItem("GIF");
        imageMenu.add(jpegOption);
        imageMenu.add(gifOption);

        compressionMenu.add(textMenu);
        compressionMenu.add(imageMenu);

        menuBar.add(compressionMenu);
        compressPanel.add(compressLabel);
        compressPanel.add(menuBar);

        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        outputPanel.setBackground(panelColor);
        JLabel outputLabel = new JLabel("Output File: ");
        JTextField outputField = new JTextField(30);
        outputField.setEditable(false);
        JButton outputButton = createButton("Save File", buttonColor, buttonHoverColor, buttonTextColor);
        outputPanel.add(outputLabel);
        outputPanel.add(outputField);
        outputPanel.add(outputButton);

        JButton compressButton = createButton("Compress", buttonColor, buttonHoverColor, buttonTextColor);
        JPanel compressButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton statsButton = createButton("Stats", buttonColor, buttonHoverColor, buttonTextColor);
        compressButtonPanel.setBackground(panelColor);
        compressButtonPanel.add(compressButton);
        compressButtonPanel.add(statsButton);

        panel.add(inputPanel);
        panel.add(compressPanel);
        panel.add(compressButtonPanel);
        panel.add(outputPanel);

        mainPanel.add(panel, BorderLayout.CENTER);

        // Input File Selection Listener
        inputButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                inputField.setText(selectedFile.getAbsolutePath());

                // Clear previous stats data for a new input file
                allStatsData.clear();
                generatedFiles.clear();
            }
        });
        
        outputButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                outputField.setText(selectedDirectory.getAbsolutePath());
            }
        });
        
        huffmanOption.setToolTipText("Huffman Compression: Compresses files without losing data.");
        gzipOption.setToolTipText("Gzip Compression: Common format for compressing files and folders.");
        jpegOption.setToolTipText("JPEG Compression: Reduces image file size with some quality loss.");
        gifOption.setToolTipText("GIF Compression: Lossless compression for static images.");
        
        huffmanOption.addActionListener(e -> {
            selectedAlgorithm = "Huffman";
            JOptionPane.showMessageDialog(frame, "Huffman Compression Selected!");
        });

        gzipOption.addActionListener(e -> {
            selectedAlgorithm = "Gzip";
            JOptionPane.showMessageDialog(frame, "Gzip Compression Selected!");
        });

        jpegOption.addActionListener(e -> {
            selectedAlgorithm = "JPEG";
            JOptionPane.showMessageDialog(frame, "JPEG Compression Selected!");
        });

        gifOption.addActionListener(e -> {
            selectedAlgorithm = "GIF";
            JOptionPane.showMessageDialog(frame, "GIF Compression Selected!");
        });

    compressButton.addActionListener(e -> {
    String inputFilePath = inputField.getText();
    String outputFilePath = outputField.getText();
        if (inputFilePath.isEmpty() || outputFilePath.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Please select both input and output files.");
        return;
    }

        if (selectedAlgorithm == null) {
        JOptionPane.showMessageDialog(frame, "Please select a compression algorithm.");
        return;
    }

        // Check if the selected file supports the algorithm
        String fileExtension = inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1).toLowerCase();
        boolean isTextFile = fileExtension.equals("txt");
        boolean isImageFile = fileExtension.equals("jpeg") || fileExtension.equals("jpg") || fileExtension.equals("png") || fileExtension.equals("gif");

        if (isTextFile && (selectedAlgorithm.equals("JPEG") || selectedAlgorithm.equals("GIF"))) {
            JOptionPane.showMessageDialog(frame,
                    "You selected an image compression algorithm for a text file.\n" +
                    "Please select an appropriate algorithm (e.g., Huffman or Gzip).",
                    "Invalid Compression Choice", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isImageFile && (selectedAlgorithm.equals("Huffman") || selectedAlgorithm.equals("Gzip"))) {
            JOptionPane.showMessageDialog(frame,
                    "You selected a text compression algorithm for an image file.\n" +
                    "Please select an appropriate algorithm (e.g., JPEG or GIF).",
                    "Invalid Compression Choice", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if ("Huffman".equals(selectedAlgorithm)) {
                compressWithHuffman(inputFilePath, outputFilePath + "/compressed.huff");
                JOptionPane.showMessageDialog(frame, "Huffman Compression Completed!");
            } else if ("Gzip".equals(selectedAlgorithm)) {
                compressWithGzip(inputFilePath, outputFilePath + "/compressed.gz");
                JOptionPane.showMessageDialog(frame, "Gzip Compression Completed!");
            } else if ("GIF".equals(selectedAlgorithm)) {  
                compressWithGIF(inputFilePath, outputFilePath + "/compressed.gif");
                JOptionPane.showMessageDialog(frame, "GIF Compression Completed!");
            } else if ("JPEG".equals(selectedAlgorithm)) {
                compressWithJPEG(inputFilePath, outputFilePath + "/compressed_image.jpg", 0.75f);
                JOptionPane.showMessageDialog(frame, "JPEG Compression Completed!");
            } else {
                JOptionPane.showMessageDialog(frame, "Unsupported algorithm selected.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error during compression: " + ex.getMessage());
        }
    });
    
        List<String[]> allStatsData = new ArrayList<>();
            statsButton.addActionListener(e -> {
            String inputFilePath = inputField.getText();
            String outputFilePath = outputField.getText();

            if (inputFilePath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please select an input file to view stats.");
                return;
            }

            if (outputFilePath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please specify an output directory.");
                return;
            }

            JFrame statsFrame = new JFrame("File Compression Stats");
            statsFrame.setSize(600, 300);
            statsFrame.setLayout(new BorderLayout());
            statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            String[] columnHeaders = {"File Type", "Original Size", "Compressed Size"};
            List<String[]> statsData = new ArrayList<>();

            try {
                File originalFile = new File(inputFilePath);

                // Add original file stats
                statsData.add(new String[]{
                    originalFile.getName(),
                    formatFileSize(originalFile.length()),
                    "N/A"
                });

                // Add compressed file stats
                for (String filePath : generatedFiles) {
                    File compressedFile = new File(filePath);
                    if (compressedFile.exists()) {
                        statsData.add(new String[]{
                            getFileType(compressedFile.getName()),
                            formatFileSize(originalFile.length()),
                            formatFileSize(compressedFile.length())
                        });
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error retrieving stats: " + ex.getMessage());
                return;
            }

            allStatsData.clear();
            allStatsData.addAll(statsData);

            String[][] tableData = allStatsData.toArray(new String[0][]);
            JTable statsTable = new JTable(tableData, columnHeaders);
            JScrollPane scrollPane = new JScrollPane(statsTable);
            statsFrame.add(scrollPane, BorderLayout.CENTER);

            statsFrame.setVisible(true);
        });
        cardPanel.add(welcomePanel, "welcomePanel");
        cardPanel.add(mainPanel, "mainPanel");

        // Set up the frame
        frame.add(cardPanel);
        frame.setVisible(true);

        // Show the welcome panel first
        cardLayout.show(cardPanel, "welcomePanel");
    }
        // Helper method to determine file type
    private static String getFileType(String fileName) {
        if (fileName.endsWith(".huff")) {
            return "Huffman";
        } else if (fileName.endsWith(".gz")) {
            return "Gzip";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "JPEG";
        } else if (fileName.endsWith(".gif")) {
            return "GIF";
        } else {
            return "Unknown";
        }
    }

    
    private static String formatFileSize(long size) {
    if (size < 1024) {
        return size + " B"; // Bytes
    } else if (size < 1024 * 1024) {
        return String.format("%.2f KB", size / 1024.0); // Kilobytes
    } else if (size < 1024 * 1024 * 1024) {
        return String.format("%.2f MB", size / (1024.0 * 1024)); // Megabytes
    } else {
        return String.format("%.2f GB", size / (1024.0 * 1024 * 1024)); // Gigabytes
    }
}
    
    // Hover Effect For Buttons
    private static JButton createButton(String text, Color normalColor, Color hoverColor, Color textColor) {
        JButton button = new JButton(text);
        button.setBackground(normalColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setRolloverEnabled(true);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });

        return button;
    }
    
  
    
    // Huffman Compression Method 
    private static void compressWithHuffman(String inputFilePath, String outputFilePath) throws IOException {
        File inputFile = new File(inputFilePath);
        FileInputStream fis = new FileInputStream(inputFile);

        // Count the Frequencies
        Hashtable<Character, Integer> frequencyTable = new Hashtable<>();
        int c;
        while ((c = fis.read()) != -1) {
            char character = (char) c;
            frequencyTable.put(character, frequencyTable.getOrDefault(character, 0) + 1);
        }
        fis.close();

        // Sort them in ascending order
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));
        
        // Create nodes for each character and add to priority queue
        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // Create the Huffman tree
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode(null, left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            pq.add(parent);
        }

        HuffmanNode root = pq.poll();  // Final root of Huffman tree

        // Generate Huffman Codes
        Hashtable<Character, String> huffmanCodes = new Hashtable<>();
        generateHuffmanCodes(root, "", huffmanCodes);

        // Compress the File
        StringBuilder compressedData = new StringBuilder();
        fis = new FileInputStream(inputFile);
        while ((c = fis.read()) != -1) {
            char character = (char) c;
            compressedData.append(huffmanCodes.get(character));
        }
        fis.close();

        // Write Compressed Data to Output File
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFilePath))) {
            // Write compressed data in byte format (grouping every 8 bits)
            for (int i = 0; i < compressedData.length(); i += 8) {
                String byteString = compressedData.substring(i, Math.min(i + 8, compressedData.length()));
                bos.write(Integer.parseInt(byteString, 2));
            }
        }generatedFiles.add(outputFilePath);
    }

    // Assign value of 0 & 1 to left node and right node respectively
    private static void generateHuffmanCodes(HuffmanNode node, String code, Hashtable<Character, String> huffmanCodes) {
        if (node == null) {
            return;
        }
        if (node.character != null) {
            huffmanCodes.put(node.character, code);
        }
        generateHuffmanCodes(node.left, code + "0", huffmanCodes);
        generateHuffmanCodes(node.right, code + "1", huffmanCodes);
    }

    // Huffman Node class
    static class HuffmanNode {
        Character character;
        int frequency;
        HuffmanNode left;
        HuffmanNode right;

        HuffmanNode(Character character, int frequency) {
            this.character = character;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }
    }

    // Gzip Compression Method 
    private static void compressWithGzip(String inputFilePath, String outputFilePath) throws IOException {
        Hashtable<String, String> metadata = new Hashtable<>();
        metadata.put("Compression Method", "Gzip");
        metadata.put("File Path", inputFilePath);

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             FileOutputStream fos = new FileOutputStream(outputFilePath);
             GZIPOutputStream gzipOS = new GZIPOutputStream(fos)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                gzipOS.write(buffer, 0, len);
            }
            generatedFiles.add(outputFilePath);
        }
    }


    // JPEG Compression Method 
    public static void compressWithJPEG(String inputFilePath, String outputFilePath, float quality) throws IOException {
        // Load the image in Memory
        BufferedImage image = ImageIO.read(new File(inputFilePath));
        
        // Stack used to simulate a sequence of compression steps
        Stack<BufferedImage> imageStack = new Stack<>();
        imageStack.push(image);

        // Get a JPEG writer
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
        ImageWriter writer = writers.next();

        // Set up the output stream
        File outputFile = new File(outputFilePath);
        ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile);
        writer.setOutput(ios);

        // Set the compression parameters
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.7f);  

        // Write the image to the output file with the specified compression
        writer.write(null, new IIOImage(imageStack.pop(), null, null), param);

        // Close the writer and stream
        ios.close();
        writer.dispose();
        generatedFiles.add(outputFilePath);
    }
    
    // GIF Compression Method 
    private static void compressWithGIF(String inputFilePath, String outputFilePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(inputFilePath));
        Hashtable<String, String> properties = new Hashtable<>();
        properties.put("Compression Type", "GIF");
        properties.put("File Path", inputFilePath);

        try (OutputStream os = new FileOutputStream(outputFilePath)) {
            ImageIO.write(image, "gif", os);
        }
        generatedFiles.add(outputFilePath);
    }
}

