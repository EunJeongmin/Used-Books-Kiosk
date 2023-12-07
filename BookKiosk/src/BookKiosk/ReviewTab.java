package BookKiosk;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ReviewTab extends JPanel {
	
    private JPanel tab4Panel;
    private JTabbedPane tabbedPane;
    private BookstoreKiosk bookstoreKiosk;

    public ReviewTab(JTabbedPane tabbedPane, BookstoreKiosk bookstoreKiosk) {
    	this.tabbedPane = tabbedPane;
        this.bookstoreKiosk = bookstoreKiosk;
        createTab4Panel();
    }

    private void createTab4Panel() {
    	tab4Panel = new JPanel(new BorderLayout());

    	// 폼 필드를 위한 패널 생성
    	JPanel formPanel = new JPanel(new GridLayout(7, 2));
    	
    	// 도서명 콤보 박스 추가
    	JComboBox<String> bookComboBox = createBookComboBox();
    	
    	// 만족도 슬라이더
    	JSlider satisfactionSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
    	// 슬라이더에 눈금 및 레이블 표시 설정 추가
    	satisfactionSlider.setPaintLabels(true);
    	satisfactionSlider.setPaintTicks(true);
    	satisfactionSlider.setMajorTickSpacing(10);
    	satisfactionSlider.setMinorTickSpacing(5);
    	
    	// 리뷰 입력에 대한 JTextArea
    	JTextArea reviewTextArea = new JTextArea();
    	reviewTextArea.setLineWrap(true);  // 자동 줄바꿈 활성화
    	reviewTextArea.setWrapStyleWord(true);  // 단어 단위로 줄바꿈
    	JScrollPane reviewScrollPane = new JScrollPane(reviewTextArea);
    	
    	// 모든 JLabel에 대해 폰트 크기를 설정
        Font labelFont = new Font("돋움", Font.PLAIN, 18); 

    	formPanel.add(new JLabel(" 도서명")).setFont(labelFont);
    	formPanel.add(bookComboBox);
    	
    	formPanel.add(new JLabel(" 이름")).setFont(labelFont);
    	formPanel.add(new JTextField());
    	
    	formPanel.add(new JLabel(" 전화번호")).setFont(labelFont);
    	formPanel.add(new JTextField());
    	
    	formPanel.add(new JLabel(" 리뷰")).setFont(labelFont);
    	formPanel.add(reviewScrollPane);

    	formPanel.add(new JLabel(" 만족도")).setFont(labelFont);
    	formPanel.add(satisfactionSlider);
    	
    	formPanel.add(new JLabel(" 정보 수집 동의")).setFont(labelFont);
    	JCheckBox consentCheckBox = new JCheckBox("동의");
    	formPanel.add(consentCheckBox);
    	
    	// 폼 패널을 tab4Panel의 중앙에 추가하면서 주위에 여백 설정
    	formPanel.setBorder(BorderFactory.createEmptyBorder(200, 30, 10, 30));
    	tab4Panel.add(formPanel, BorderLayout.CENTER);

    	// 버튼을 위한 패널 생성
    	JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    	// 버튼 생성
    	JButton registerBtn = new JButton("등록하기");
    	registerBtn.setEnabled(false);  // 처음에는 비활성화 상태

    	// 버튼을 버튼 패널에 추가
    	btnPanel.add(registerBtn);

    	// 버튼 패널을 tab4Panel의 남쪽에 추가
    	tab4Panel.add(btnPanel, BorderLayout.SOUTH);

    	// 간격을 조절하기 위해 테두리를 설정
    	btnPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

    	// 버튼의 크기를 조절
    	registerBtn.setPreferredSize(new Dimension(400, 100));
    	
    	// 체크박스의 선택 상태 변경 이벤트를 모니터링
        consentCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // 체크박스가 선택되었을 때 등록 버튼 활성화
                registerBtn.setEnabled(consentCheckBox.isSelected());
            }
        });
    	
    	 // 버튼 클릭 이벤트 처리
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 알림창 표시
                JOptionPane.showMessageDialog(tab4Panel, "리뷰가 등록되었습니다.", "알림", 
                		JOptionPane.INFORMATION_MESSAGE);
                
                // 캑 제목 콤보박스 리셋
                bookComboBox.setSelectedIndex(0);

                // 이름 입력값 리셋
                JTextField nameField = (JTextField) formPanel.getComponent(3);
                nameField.setText("");

                // 전화번호 입력값 리셋
                JTextField phoneNumberField = (JTextField) formPanel.getComponent(5);
                phoneNumberField.setText("");

                // 리뷰 내용 리셋
                JScrollPane reviewScrollPane = (JScrollPane) formPanel.getComponent(7);
                JTextArea reviewTextArea = new JTextArea();
                reviewScrollPane.setViewportView(reviewTextArea);

                // 만족도 값 리셋
                satisfactionSlider.setValue(50);
                
                // 체크박스 선택 해제
                consentCheckBox.setSelected(false);
            }
        });
        
        tabbedPane.addTab("리뷰", tab4Panel);
    }
    
    // Book 클래스를 사용하지 않고 책 제목 배열을 사용하는 코드
    private JComboBox<String> createBookComboBox() {
        // 책 제목 배열
        String[] bookTitles = {"고통에 관하여", "저주토끼", "설자은, 금성으로 돌아오다", "시선으로부터", "단 한 사람", "구의 증명", 
        		"지구 끝의 온실", "방금 떠나온 세계", "파과", "파쇄", "노랜드", "이끼숲", "정의란 무엇인가", "공정하다는 착각",
        		"군주론", "도둑맞은 집중력", "서사의 위기", "연결된 고통", "총균쇠", "현대사상 입문", "사피엔스", "호모 데우스", "커리어 그리고 가정", "자유론",
        		"H마트에서 울다", "그 얼굴을 오래 바라보았다", "마시지 않을 수 없는 밤이니까요", "물고기는 존재하지 않는다", "사랑의 기술",
        		"사서 일기", "순도 100퍼센트의 휴식", "인생의 역사", "천문학자는 별을 보지 않는다", "최선을 다하면 죽는다", "향수가 된 식물들", "푸바오, 매일매일 행복해"};

        // Book 객체의 도서명들을 콤보박스에 추가
        JComboBox<String> comboBox = new JComboBox<>(bookTitles);
        
        return comboBox;
    }

}
