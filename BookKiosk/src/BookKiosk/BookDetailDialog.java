package BookKiosk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//책 상세 정보 다이얼로그를 나타내는 BookDetailDialog 클래스
public class BookDetailDialog extends JDialog {

    private BookstoreKiosk bookstoreKiosk;  // BookstoreKiosk 인스턴스 추가
    private Book book;  // Book 객체 추가
    private JButton sellBtn;
    private JRadioButton status1Radio; 
    private JRadioButton status2Radio; 
    private JRadioButton status3Radio;

    public BookDetailDialog(JFrame parent, Book book, BookstoreKiosk bookstoreKiosk) {
        super(parent, "책 상세 정보", false);
        this.bookstoreKiosk = bookstoreKiosk;  // BookstoreKiosk 인스턴스 초기화
        this.book = book;  // Book 객체 초기화

        // 책 정보를 이용하여 UI 구성
        initComponents(book);

        setSize(450, 600);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    //UI 컴포넌트를 초기화하는 메서드
    private void initComponents(Book book) {
        JPanel panel = new JPanel(new BorderLayout());

        // 전체 정보 패널
        JPanel bookImagePanel = new JPanel(new FlowLayout());
        // 책 정보 패널
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        // 책 이미지를 표시할 라벨
        JLabel imageLabel = new JLabel(new ImageIcon(book.getImage()));

        // 패널에 라벨들 추가
        bookImagePanel.add(imageLabel);
        bookImagePanel.add(infoPanel);

        // 책 정보를 보여줄 라벨들
        JLabel titleLabel = new JLabel("도서명 : " + book.getTitle());
        JLabel authorLabel = new JLabel("지은이 : " + book.getAuthor());
        JLabel publisherLabel = new JLabel("출판사 : " + book.getPublisher());

        // 패널에 라벨들 추가
        infoPanel.add(titleLabel);
        infoPanel.add(authorLabel);
        infoPanel.add(publisherLabel);

        // 책 상태 패널
        JPanel statusPanel = new JPanel(new GridLayout(9, 1));
        statusPanel.add(new JLabel("책 상태"));

        // 라디오 버튼 그룹 생성
        ButtonGroup priceGroup = new ButtonGroup();
        status1Radio = new JRadioButton("최상 : 새것에 가까운 책");
        status2Radio = new JRadioButton("좋음 : 약간의 사용감은 있으나 깨끗한 책");
        status3Radio = new JRadioButton("보통 : 사용감이 많으며 헌 느낌이 나는 책");

        // 라디오 버튼을 그룹에 추가
        priceGroup.add(status1Radio);
        priceGroup.add(status2Radio);
        priceGroup.add(status3Radio);

        // 패널에 라디오 버튼 추가
        statusPanel.add(status1Radio);
        statusPanel.add(status2Radio);
        statusPanel.add(status3Radio);

        // 버튼 패널
        JPanel btnPanel = new JPanel(new GridLayout(1, 2));

        // 버튼 생성
        JButton cancelBtn = new JButton("취소");
        sellBtn = new JButton("팔기");  // 판매 버튼을 클래스 변수로 변경

        // 버튼 패널에 버튼 추가
        btnPanel.add(cancelBtn);
        btnPanel.add(sellBtn);

        sellBtn.setEnabled(false);

        // 취소 버튼에 대한 ActionListener
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 창을 닫음
                dispose();
            }
        });

        // 라디오 버튼 선택 이벤트 리스너 추가
        ActionListener radioListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton selectedButton = (JRadioButton) e.getSource();

                // 라디오 버튼이 선택되면 팔기 버튼 활성화
                if (selectedButton == status1Radio || selectedButton == status2Radio || selectedButton == status3Radio) {
                    sellBtn.setEnabled(true);
                }
            }
        };

        // 각 라디오 버튼에 리스너 추가
        status1Radio.addActionListener(radioListener);
        status2Radio.addActionListener(radioListener);
        status3Radio.addActionListener(radioListener);

        // 새로운 패널을 만들어 가격 정보 표시 라벨을 추가
        JPanel pricePanel = new JPanel(new GridLayout(1, 1));
        JLabel priceLabel = new JLabel("판매 가격 : ");
        priceLabel.setForeground(Color.BLUE);  // 글자색을 파란색으로 설정
        pricePanel.add(priceLabel);

        // 가격 정보 패널을 상태 패널에 추가
        statusPanel.add(pricePanel);

        // 라디오 버튼 선택 이벤트 리스너 추가
        ActionListener radioListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton selectedButton = (JRadioButton) e.getSource();

                // 라디오 버튼이 선택되면 팔기 버튼 활성화
                if (selectedButton == status1Radio) {
                    book.setPriceForStatus1();
                } else if (selectedButton == status2Radio) {
                    book.setPriceForStatus2();
                } else if (selectedButton == status3Radio) {
                    book.setPriceForStatus3();
                }

                // 가격 정보를 표시하는 라벨 업데이트
                int sellingPrice = (int) book.getSellingPrice();
                priceLabel.setText("판매 가격 : " + sellingPrice + "원");

                // 가격 정보 패널을 다시 그리기
                pricePanel.revalidate();
                pricePanel.repaint();
            }
           
        };
        
        // 판매 버튼에 대한 ActionListener
        sellBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// 선택한 라디오버튼의 텍스트를 BookstoreKiosk로 전달
                String selectedStatusText = getSelectedStatusText();
                //판매할 책에 대한 정보를 BookstoreKiosk에 추가
                bookstoreKiosk.addSoldBook(book.getTitle(), book.getAuthor(), book.getSellingPrice(), selectedStatusText);
                
                // 창을 닫음
                setVisible(false);
            }
        });

        // 각 라디오 버튼에 리스너 추가
        status1Radio.addActionListener(radioListener2);
        status2Radio.addActionListener(radioListener2);
        status3Radio.addActionListener(radioListener2);

        
        // 상단, 하단 패널을 다이얼로그에 추가
        panel.add(bookImagePanel, BorderLayout.NORTH);
        panel.add(statusPanel);
        panel.add(btnPanel, BorderLayout.SOUTH);

        // 마진
        bookImagePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 30, 50));

        // 버튼 크기 조절
        cancelBtn.setPreferredSize(new Dimension(100, 50));
        sellBtn.setPreferredSize(new Dimension(100, 50));

        // 패널을 다이얼로그에 추가
        add(panel);
    }
        
    //선택한 라디오버튼의 텍스트 (책 상태)를 BookstoreKiosk로 전달하는 메서드
    public String getSelectedStatusText() {
        if (status1Radio.isSelected()) {
            String fullText = status1Radio.getText();
            return fullText.length() >= 2 ? fullText.substring(0, 2) : fullText;
        } else if (status2Radio.isSelected()) {
            String fullText = status2Radio.getText();
            return fullText.length() >= 2 ? fullText.substring(0, 2) : fullText;
        } else if (status3Radio.isSelected()) {
            String fullText = status3Radio.getText();
            return fullText.length() >= 2 ? fullText.substring(0, 2) : fullText;
        }
        return ""; // 선택된 라디오버튼이 없을 경우 빈 문자열 반환
    }

}
