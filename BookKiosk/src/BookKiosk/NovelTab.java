package BookKiosk;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class NovelTab extends JPanel {
    
	private JPanel tab1Panel;
    private JTabbedPane tabbedPane;
    private BookstoreKiosk bookstoreKiosk;

    public NovelTab(JTabbedPane tabbedPane, BookstoreKiosk bookstoreKiosk) {
        this.tabbedPane = tabbedPane;
        this.bookstoreKiosk = bookstoreKiosk;
        createTab1Panel();
    }

    private void createTab1Panel() {
        tab1Panel = new JPanel();
        tab1Panel.setLayout(new GridLayout(0, 4));

        // 책 목록 생성
        ArrayList<Book> books = new ArrayList<>();
        try {
            books.add(new Book("고통에 관하여", "정보라", "src\\images\\고통에 관하여.jpg", "다산책방", 18000));
    		books.add(new Book("저주토끼", "정보라", "src\\images\\저주토끼.jpg", "래빗홀", 15800));
    	    books.add(new Book("설자은, 금성으로 돌아오다", "정세랑", "src\\images\\설자은.jpg", "문학동네", 16800));
    	    books.add(new Book("시선으로부터", "정세랑", "src\\images\\시선으로부터.jpg", "문학동네", 14000));
    	    books.add(new Book("단 한 사람", "최진영", "src\\images\\단 한 사람.jpg", "한겨레출판", 15000));
    	    books.add(new Book("구의 증명", "최진영", "src\\images\\구의 증명.jpg", "은행나무", 12000));
    	    books.add(new Book("지구 끝의 온실", "김초엽", "src\\images\\지구 끝의 온실.jpg", "자이언트북스", 15000));
    	    books.add(new Book("방금 떠나온 세계", "김초엽", "src\\images\\방금 떠나온 세계.jpg", "한겨레출판", 15000));
    	    books.add(new Book("파과", "구병모", "src\\images\\파과.jpg", "위즈덤하우스", 14000));
    	    books.add(new Book("파쇄", "구병모", "src\\images\\파쇄.jpg", "위즈덤하우스", 13000));
    	    books.add(new Book("노랜드", "천선란", "src\\images\\노랜드.jpg", "한겨레출판", 15800));
    	    books.add(new Book("이끼숲", "천선란", "src\\images\\이끼숲.jpg", "자이언트북스", 15800));
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Book book : books) { //books 리스트에 있는 각 Book 객체에 대해 루프
            JPanel bookPanel = createBookPanel(book);
            // 패딩을 추가하기 위해 또 다른 JPanel로 감싸기
            JPanel paddedPanel = new JPanel(new FlowLayout());
            paddedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 상하좌우 패딩 설정
            paddedPanel.add(bookPanel);
            tab1Panel.add(paddedPanel);

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
        tabbedPane.addTab("소설", tab1Panel);
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
