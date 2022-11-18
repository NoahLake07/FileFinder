import FFM.DirectorySearch;
import acm.graphics.GImage;
import freshui.FreshUI;
import freshui.graphics.FRect;
import freshui.gui.Header;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class FileFinder extends FreshUI {

    GImage chooseDirBtn, chooseOutputLocation, startSearchBtn, searchSettingsBtn;
    Header programHeader;
    double btnScaleFactor = 0.1;
    double startScaleFactor = 0.2;
    double ogStartW, ogStartH;
    File chosenDir, chosenOutput;

    JLabel chosenDirectoryLabel = new JLabel("No Search Directory Selected.");
    JLabel searchDetailsHeader = new JLabel("Deep Disk Search");
    JLabel searchStatus = new JLabel("Please select a directory.");
    JLabel chosenOutputLocation = new JLabel("Search Result File Location: NO CHOSEN OUTPUT LOCATION");

    boolean mouseOverStart;
    Font header = new Font(Font.SANS_SERIF, Font.BOLD, 25);

    MouseAdapter dirMs = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){
            chooseSearchDirectory();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            chooseDirBtn.setImage("imagebtn/chooseDirectory-hover.png");
            chooseDirBtn.scale(btnScaleFactor);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            chooseDirBtn.setImage("imagebtn/chooseDirectory.png");
            chooseDirBtn.scale(btnScaleFactor);
        }
    };
    MouseAdapter outputLocMs = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            setOutputLocation();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            chooseOutputLocation.setImage("imagebtn/chooseOutput-hover.png");
            chooseOutputLocation.scale(btnScaleFactor);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            chooseOutputLocation.setImage("imagebtn/chooseOutput.png");
            chooseOutputLocation.scale(btnScaleFactor);
        }
    };
    MouseAdapter startMs = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            searchStatus.setText("Searching...");
            searchStatus.setForeground(new Color(190, 112, 6));
            searchStatus.setVisible(true);
            beginSearch();
            updateStartBtnLoc();

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            mouseOverStart = true;
            startSearchBtn.setImage("imagebtn/startSearch-hover.png");
            startSearchBtn.scale(startScaleFactor);
            updateStartBtnLoc();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            mouseOverStart = false;
            startSearchBtn.setImage("imagebtn/startSearch.png");
            startSearchBtn.scale(startScaleFactor);
            updateStartBtnLoc();
        }
    };
    MouseAdapter searchSettingsMs = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            openSearchSettings();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            searchSettingsBtn.setImage("imagebtn/searchSettings-hover.png");
            searchSettingsBtn.scale(btnScaleFactor);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            searchSettingsBtn.setImage("imagebtn/searchSettings.png");
            searchSettingsBtn.scale(btnScaleFactor);
        }
    };

    public void init(){
        programHeader = new Header(getWidth(), "File Finder", CENTER,this);
        add(programHeader,0,0);
        programHeader.setHeaderColor(new Color(39, 125, 255));
        programHeader.sendToFront();

        createButtons();
        createLabels();
        createContainers();

        chosenDir = new File("/Volumes/WJ0426/Movies/");
        updateLabels();

    }

    private void createContainers(){
        FRect optionBtnContainer = new FRect(getWidth()*0.5,getHeight());
        add(optionBtnContainer, getWidth() - getWidth()*0.45, getHeight() / 15);
        optionBtnContainer.setFillColor(new Color(166, 170, 171));
        optionBtnContainer.sendToBack();

        FRect searchDetailsHeader = new FRect(optionBtnContainer.getX(), getHeight());
        add(searchDetailsHeader,0,0);
        searchDetailsHeader.sendToBack();
        searchDetailsHeader.setFillColor(new Color(210, 210, 210));
    }

    private void createButtons(){
        chooseDirBtn = new GImage("imagebtn/chooseDirectory.png");
        chooseOutputLocation = new GImage("imagebtn/chooseOutput.png");
        startSearchBtn = new GImage("imagebtn/startSearch.png");
        searchSettingsBtn = new GImage("imagebtn/searchSettings.png");

        chooseDirBtn.scale(btnScaleFactor);
        chooseOutputLocation.scale(btnScaleFactor);
        startSearchBtn.scale(startScaleFactor);
        searchSettingsBtn.scale(btnScaleFactor);

        chooseDirBtn.addMouseListener(dirMs);
        chooseOutputLocation.addMouseListener(outputLocMs);
        startSearchBtn.addMouseListener(startMs);
        searchSettingsBtn.addMouseListener(searchSettingsMs);

        double btnX = getWidth() - (getWidth()*0.4);

        add(searchSettingsBtn, btnX, getHeight() - 3*(getHeight()/4));
        add(chooseOutputLocation, btnX, getHeight() - 2*(getHeight()/4));
        add(chooseDirBtn, btnX,getHeight() - getHeight()/4);
        add(startSearchBtn, 0,0);
        updateStartBtnLoc();

        ogStartW = startSearchBtn.getWidth();
        ogStartH = startSearchBtn.getHeight();
    }

    private void createLabels(){
        int searchLabelX = getWidth()/7;
        int headerX = getWidth()/8;

        searchDetailsHeader.setHorizontalAlignment(SwingConstants.CENTER);
        searchDetailsHeader.setFont(header);
        chosenDirectoryLabel.setSize(300,50);
        chosenOutputLocation.setSize(300,50);
        chosenDirectoryLabel.setVerticalAlignment(SwingConstants.CENTER);
        chosenOutputLocation.setVerticalAlignment(SwingConstants.CENTER);

        // organizational labels
        add(searchStatus, startSearchBtn.getX(), startSearchBtn.getY() + startSearchBtn.getHeight() + startSearchBtn.getHeight()/5);
        add(searchDetailsHeader, headerX, programHeader.getHeight() + programHeader.getHeight()/4);

        // info labels
        add(chosenDirectoryLabel, searchLabelX, searchDetailsHeader.getY() + (1*(getWidth()/10)));
        add(chosenOutputLocation, searchLabelX, searchDetailsHeader.getY() + (2*(getWidth()/10)));

        searchStatus.setVisible(false);
        searchStatus.setSize(500,20);

        updateLabels();
    }
    private void updateStartBtnLoc(){
        startSearchBtn.setLocation((getWidth() * 0.25) - startSearchBtn.getWidth()/2, getHeight() - startSearchBtn.getHeight() - getHeight()/10);
    }

    private void updateLabels(){

        if(chosenDir==null){
            chosenDirectoryLabel.setText("<html>Search Directory:<p>NO DIRECTORY SELECTED.</html>");
        } else {
            chosenDirectoryLabel.setText("<html>Search Directory:<p>"+ chosenDir.getPath() + "</html>");
        }

        if(chosenOutput==null){
            chosenOutputLocation.setText("<html>Search Result File Location:<p>NO CHOSEN OUTPUT LOCATION</html>");
        } else {
            chosenOutputLocation.setText("<html>Search Result File Location:<p>"+ chosenOutput.getPath() + "</html>");
        }

    }

    private void chooseSearchDirectory(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(1);
        int result = fileChooser.showOpenDialog(this);

        if (result == 0) {
            File selectedFile = fileChooser.getSelectedFile();
            chosenDir = selectedFile;
        }
        updateLabels();
    }

    private void setOutputLocation(){
        UIManager.put("FileChooser.saveButtonText","Select Location");
        UIManager.put("FileChooser.fileNameLabelText","File Name");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Select File Location for Search Results");

        int result = fileChooser.showSaveDialog(this);

        if (result == 0) {

            if(fileChooser.getSelectedFile().isDirectory()){
                File selectedFile = new File(fileChooser.getSelectedFile().getPath() + "/Search Results.txt/");
            } else {
                if (fileChooser.getSelectedFile().getPath().contains(".txt")) {
                    File selectedFile = new File(fileChooser.getSelectedFile().getPath());
                } else {
                    File selectedFile = new File(fileChooser.getSelectedFile().getPath() + "/.txt/");
                }
            }

            File selectedFile = new File(fileChooser.getSelectedFile().getPath());

            chosenOutput = selectedFile;
        }

        updateLabels();
    }

    private void beginSearch(){

        if(!(chosenDir==null || chosenOutput==null)) {

            System.out.println("STARTED SEARCH...");
            DirectorySearch.searchDirectory(chosenDir.getPath(), chosenOutput.getPath());
            System.out.println("FINISHED SEARCH.");

            searchStatus.setText("Search Complete.");
            searchStatus.setForeground(new Color(9, 169, 33));
            searchStatus.setVisible(true);

        } else if((chosenDir==null) && !(chosenOutput==null)) {
            searchStatus.setText("Select a directory to search.");
            searchStatus.setForeground(Color.RED);
            searchStatus.setVisible(true);

        } else if((chosenOutput==null) && !(chosenDir==null)){
            searchStatus.setText("Select a search result location.");
            searchStatus.setForeground(Color.RED);
            searchStatus.setVisible(true);

        } else if (((chosenOutput==null) && (chosenDir==null))){
            searchStatus.setText("Select a search directory and output location.");
            searchStatus.setForeground(Color.RED);
            searchStatus.setVisible(true);

        } else {
            searchStatus.setText("ERROR! Check settings.");
            searchStatus.setForeground(Color.RED);
            searchStatus.setVisible(true);
        }

    }

    private void openSearchSettings(){

    }

    public static void main(String[] args) {
        new FileFinder().start();
    }
}