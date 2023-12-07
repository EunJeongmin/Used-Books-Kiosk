package BookKiosk;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class SocialScienceTab extends JPanel  {
	
	private JPanel tab2Panel;
    private JTabbedPane tabbedPane;
    private BookstoreKiosk bookstoreKiosk;

    public SocialScienceTab(JTabbedPane tabbedPane, BookstoreKiosk bookstoreKiosk) {
        this.tabbedPane = tabbedPane;
        this.bookstoreKiosk = bookstoreKiosk;
        createTab2Panel();
    }

    private void createTab2Panel() {
        tab2Panel = new JPanel();
        tab2Panel.setLayout(new GridLayout(0, 4));

        // 책 목록 생성
        ArrayList<Book> books = new ArrayList<>();
        try {
        	books.add(new Book("정의란 무엇인가", "마이클 샌델", "src\\images\\정의란 무엇인가.jpg", "와이즈베리", 15000));
    		books.add(new Book("공정하다는 착각", "마이클 샌델", "src\\images\\공정하다는 착각.jpg", "와이즈베리", 18000));
    		books.add(new Book("군주론", "니콜로 마키아벨리", "src\\images\\군주론.jpg", "현대지성", 7700));
    	    books.add(new Book("도둑맞은 집중력", "요한 하리", "src\\images\\도둑맞은 집중력.jpg", "어크로스", 18800));
    	    books.add(new Book("서사의 위기", "한병철", "src\\images\\서사의 위기.jpg", "다산초당(다산북스)", 16800));
    	    books.add(new Book("연결된 고통", "이기병", "src\\images\\연결된 고통.jpg", "아몬드", 17000));
    	    books.add(new Book("총균쇠", "재레드 다이아몬드", "src\\images\\총균쇠.jpg", "김영사", 29800));
    	    books.add(new Book("현대사상 입문", "지바 마사야", "src\\images\\현대사상 입문.jpg", "arte(아르테)", 24000));
    	    books.add(new Book("사피엔스", "유발 하라리", "src\\images\\사피엔스.jpg", "김영사", 26800));
    	    books.add(new Book("호모 데우스", "유발 하라리", "src\\images\\호모 데우스.jpg", "김영사", 26800));
    	    books.add(new Book("커리어 그리고 가정", "클라우디아 골딘", "src\\images\\커리어 그리고 가정.jpg", "생각의힘", 22000));
    	    books.add(new Book("자유론", "존 스튜어트 밀", "src\\images\\자유론.jpg", "현대지성", 7700));
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Book book : books) {
            JPanel bookPanel = createBookPanel(book);
            // 패딩을 추가하기 위해 또 다른 JPanel로 감싸기
            JPanel paddedPanel = new JPanel(new FlowLayout());
            paddedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 상하좌우 패딩 설정
            paddedPanel.add(bookPanel);
            tab2Panel.add(paddedPanel);

            // 마우스 클릭 이벤트 추가
            bookPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 마우스 클릭 시에 상세 정보 다이얼로그 띄우기
                    BookDetailDialog dialog = new BookDetailDialog(bookstoreKiosk, book, bookstoreKiosk);
                    dialog.setVisible(true);
                }
            });
        }

        // 탭 1 생성
        tabbedPane.addTab("사회과학", tab2Panel);
    }

    // 주어진 Book 객체를 기반으로 각 탭에 이미지와 책 정보를 표시하는 JPanel을 생성
    private JPanel createBookPanel(Book book) {
        JPanel bookPanel = new JPanel(new BorderLayout());
        
        // 이미지를 표시할 라벨
        JLabel imageLabel = new JLabel(new ImageIcon(book.getImage()));
        bookPanel.add(imageLabel, BorderLayout.CENTER);

        // 책 제목과 작가를 표시할 라벨
        String bookInfo = "<html><center>" + book.getTitle() + "<br>" + book.getAuthor() + "</center></html>";
        JLabel titleLabel = new JLabel(bookInfo, SwingConstants.CENTER);
        bookPanel.add(titleLabel, BorderLayout.SOUTH);
        
        titleLabel.setBorder(new EmptyBorder(5, 0, 0, 0));

        return bookPanel;
    }

}

