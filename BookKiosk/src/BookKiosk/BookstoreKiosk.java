package BookKiosk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class BookstoreKiosk extends JFrame {
    private JTabbedPane tabbedPane;
    private DefaultTableModel tableModel;

    public BookstoreKiosk() {
        setTitle("중고 서점 '내 책 팔기' 키오스크");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 로고 추가
        addLogo();

        // 탭 생성
        tabbedPane = new JTabbedPane();
        NovelTab novelTab = new NovelTab(tabbedPane, this);
        SocialScienceTab socialScienceTab = new SocialScienceTab(tabbedPane, this);
        EssayTab essayTab = new EssayTab(tabbedPane, this);
        ReviewTab reviewTab = new ReviewTab(tabbedPane, this);

        getContentPane().add(tabbedPane);
        
        
        // DefaultTableModel(JTable에 데이터를 제공하는 모델 클래스) 초기화
        tableModel = new DefaultTableModel() {
        	//isCellEditable 메서드를 오버라이드하여 4번째 열(책 수량)만 수정 가능하도록 설정
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        
        //초기화한 DefaultTableModel을 사용하여 soldBooksTable을 생성
        JTable soldBooksTable = new JTable(tableModel);

        // 테이블 컬럼명 설정
        tableModel.addColumn("도서명");
        tableModel.addColumn("작가");
        tableModel.addColumn("책 상태");
        tableModel.addColumn("가격");
        tableModel.addColumn("수량");
        
        // JScrollPane을 사용하여 테이블을 감싸고 스크롤 가능하도록 함
        JScrollPane tableScrollPane = new JScrollPane(soldBooksTable);
        // 크기 조절
        tableScrollPane.setPreferredSize(new Dimension(450, 120));
        
        // 테이블 패널
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);
        
        // 책 개수 조절을 위한 +, - 버튼과 리셋 버튼, 팔기 버튼
        JButton plusButton = new JButton("+");
        JButton minusButton = new JButton("-");
        JButton deleteButton = new JButton("삭제");
        JButton resetButton = new JButton("초기화");
        JButton sellButton = new JButton("팔기");
        sellButton.setBackground(new Color(220, 0, 0)); //  팔기 버튼 색상
        sellButton.setForeground(Color.WHITE); // 팔기 버튼 텍스트 색상
        
        //버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(plusButton);
        buttonPanel.add(minusButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(sellButton);

        // 테이블과 버튼이 모두 포함된 패널
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(tablePanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        
        // 수량 증가 버튼
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // soldBooksTable에서 현재 선택된 행의 인덱스를 가져오기
                int selectedRow = soldBooksTable.getSelectedRow();

                // 현재 선택된 행과 테이블 모델의 열의 수를 확인하는 조건문
                // 선택된 행이 있고, 수량 열이 정의되어 있다면(5번째 열인 수량이 존재하면)
                if (selectedRow != -1 && tableModel.getColumnCount() > 4) {
                    // 현재 수량 가져오기
                    int currentQuantity = (int) tableModel.getValueAt(selectedRow, 4);

                    // 수량 증가
                    tableModel.setValueAt(currentQuantity + 1, selectedRow, 4);

                    // 가격 업데이트
                    updatePrice(selectedRow);
                }
            }
        });

        // 수량 감소 버튼
        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // soldBooksTable에서 현재 선택된 행의 인덱스를 가져오기
                int selectedRow = soldBooksTable.getSelectedRow();

                // 현재 선택된 행과 테이블 모델의 열의 수를 확인하는 조건문
                // 선택된 행이 있고, 수량 열이 정의되어 있다면(5번째 열인 수량이 존재하면)
                if (selectedRow != -1 && tableModel.getColumnCount() > 4) {
                    // 현재 수량 가져오기
                    int currentQuantity = (int) tableModel.getValueAt(selectedRow, 4);

                    // 수량이 1 이상이면 수량 감소
                    if (currentQuantity > 1) {
                        tableModel.setValueAt(currentQuantity - 1, selectedRow, 4);

                        // 가격 업데이트
                        updatePrice2(selectedRow);
                    }
                }
            }
        });
        
        // 삭제 버튼
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//현재 선택된 행의 인덱스를 가져오기
                int selectedRow = soldBooksTable.getSelectedRow();

                if (selectedRow != -1) {
                    // 선택된 행이 있다면 해당 행 삭제
                    tableModel.removeRow(selectedRow);
                }
            }
        });

        
        // 리셋 버튼 클릭 시 JTable 초기화
        resetButton.addActionListener(new ActionListener() {
        	//tableModel의 행(row) 수를 0으로 설정하여 JTable 빈 상태로 초기화
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
            }
        });
        
        // 판배 버튼 클릭
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // 확인 다이얼로그 표시
                int option = JOptionPane.showConfirmDialog(BookstoreKiosk.this,
                        "판매하시겠습니까?",
                        "판매 하기", JOptionPane.YES_OPTION);
                
                //사용자가 "예"를 선택한 경우에만 saveSellRecordToTextFile() 메서드 호출
                if (option == JOptionPane.YES_OPTION) {
                    saveSellRecordToTextFile();
                    // JTable 초기화
                    tableModel.setRowCount(0);
                }
            }
        });


        // JTable에 MouseListener 추가, 마우스 클릭으로 선택된 행을 감지하고 해당 행을 선택
        soldBooksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 마우스 클릭으로 선택된 행의 인덱스 가져오기
                int selectedRow = soldBooksTable.rowAtPoint(e.getPoint());

                // 선택된 행이 있다면 해당 행 선택
                if (selectedRow != -1) {
                    soldBooksTable.setRowSelectionInterval(selectedRow, selectedRow);
                }
            }
        });


        setSize(800, 1000);
        setVisible(true);
    }
   
    //로고
    private void addLogo() {
    	//로고 이미지 로드
    	ImageIcon logoIcon = new ImageIcon("src\\images\\symbol_01.png"); 
    	//로고 이미지를 지정된 크기로 조절
        Image scaledImage = logoIcon.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH);
        //조절된 이미지로 새로운 ImageIcon을 생성
        logoIcon = new ImageIcon(scaledImage);

        // ImageIcon을 가진 JLabel을 생성
        JLabel logoLabel = new JLabel(logoIcon);
        
        //로고를 담을 JPanel을 생성하고, 왼쪽 정렬된 FlowLayout를 사용
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.add(logoLabel);

        //logoPanel을 NORTH 위치에 추가
        getContentPane().add(logoPanel, BorderLayout.NORTH);
    }
    
    // 팔 책 정보를 JTable에 추가하는 메서드 (BookDetailDialog sellBtn)
    public void addSoldBook(String title, String author, int price, String selectedStatusText) {
        Object[] rowData = {title, author, selectedStatusText, (int) price, 1};
        tableModel.addRow(rowData);
    } 
    
    // '+' 버튼의 가격 업데이트 메서드
    private void updatePrice(int row) {
        // 현재 수량과 가격 가져오기
        int quantity = (int) tableModel.getValueAt(row, 4);
        int price = (int) tableModel.getValueAt(row, 3);

        // 새로운 가격 계산
        int newPrice = quantity * price;

        // 테이블 모델에 새로운 가격 설정
        tableModel.setValueAt(newPrice, row, 3);
    }
    
    // '-' 버튼의 가격 업데이트 메서드
    private void updatePrice2(int row) {
        // 현재 수량과 가격 가져오기
        int quantity = (int) tableModel.getValueAt(row, 4);
        int price = (int) tableModel.getValueAt(row, 3);

        // 새로운 가격 계산
        int newPrice = price / (quantity+1) ;

        // 테이블 모델에 새로운 가격 설정
        tableModel.setValueAt(newPrice, row, 3);
    }
    
    // Text 파일로 저장하는 메소드
    private void saveSellRecordToTextFile() {
        try {
            // 텍스트 파일의 경로 지정
            String filePath = "src\\sell_records.txt";

            // BufferedWriter를 사용하여 파일을 쓰기 위한 스트림 열기
            // true 매개변수 : 파일을 덮어쓰지 않고 이어쓰기 모드
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                // JTable에 있는 데이터 가져와서 파일에 쓰기
            	// JTable의 행을 반복
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                	//각 행의 열 반복
                    for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    	//JTable의 특정 셀의 값을 가져오기
                        Object cellValue = tableModel.getValueAt(row, col);

                        //가져온 값을 문자열로 변환하여 파일에 쓰기
                        writer.write(cellValue.toString());

                        // 열 간에 탭 문자를 추가하여 데이터를 구분
                        if (col < tableModel.getColumnCount() - 1) {
                            writer.write("\t");
                        }
                    }

                    // 새로운 행을 위한 줄 바꿈
                    writer.newLine();
                }
            }

            // 성공 메시지 표시
            JOptionPane.showMessageDialog(BookstoreKiosk.this,
                    "판매가 완료되었습니다.",
                    "저장 완료", JOptionPane.INFORMATION_MESSAGE);

            // JTable에 있는 데이터를 가져와서 표시
            // StringBuilder : 문자열을 변경할 때마다 새로운 문자열을 생성하는 것이 아니라 기존 문자열을 변경할 수 있게 해줌
            StringBuilder receipt = new StringBuilder("판매한 중고 책 \n");

            for (int row = 0; row < tableModel.getRowCount(); row++) {
                String title = (String) tableModel.getValueAt(row, 0);
                String author = (String) tableModel.getValueAt(row, 1);
                String status = (String) tableModel.getValueAt(row, 2);
                int price = (int) tableModel.getValueAt(row, 3);
                int quantity = (int) tableModel.getValueAt(row, 4);

                receipt.append("도서명: ").append(title).append("\n");
                receipt.append("작가: ").append(author).append("\n");
                receipt.append("상태: ").append(status).append("\n");
                receipt.append("가격: ").append(price).append("\n");
                receipt.append("수량: ").append(quantity).append("\n");
                receipt.append("총액: ").append(price * quantity).append("\n");
                receipt.append("\n");
            }

            // 영수증을 메시지 다이얼로그로 표시
            JOptionPane.showMessageDialog(BookstoreKiosk.this, receipt.toString(), "영수증", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
    	/**
    	 * SwingUtilities.invokeLater를 사용하여 GUI를 생성하는 스레드를 호출
    	 * 스레드 안전성 (Thread Safety), 데드락 방지, 일관성 있는 GUI 업데이트
    	 * SwingUtilities.invokeLater를 사용하여 GUI 코드를 실행하면
    	 * Swing 애플리케이션에서 안전하고 예측 가능한 방식으로 GUI를 다루는 데 도움이 됨
    	 */
        SwingUtilities.invokeLater(() -> {
            new BookstoreKiosk();
        });
    }
}


