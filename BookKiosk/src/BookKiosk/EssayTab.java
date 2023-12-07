package BookKiosk;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class EssayTab extends JPanel {

	private JPanel tab3Panel;
    private JTabbedPane tabbedPane;
    private BookstoreKiosk bookstoreKiosk;

    public EssayTab(JTabbedPane tabbedPane, BookstoreKiosk bookstoreKiosk) {
        this.tabbedPane = tabbedPane;
        this.bookstoreKiosk = bookstoreKiosk;
        createTab3Panel();
    }

    private void createTab3Panel() {
        tab3Panel = new JPanel();
        tab3Panel.setLayout(new GridLayout(0, 4));

        // 책 목록 생성
        ArrayList<Book> books = new ArrayList<>();
        try {
        	books.add(new Book("H마트에서 울다", "미셸 자우너", "src\\images\\H마트에서 울다.jpg", "문학동네", 16000));
    		books.add(new Book("그 얼굴을 오래 바라보았다", "이상희", "src\\images\\그 얼굴을 오래 바라보았다.jpg","엘리", 16800));
    	    books.add(new Book("마시지 않을 수 없는 밤이니까요", "정지아", "src\\images\\마시지 않을 수 없는 밤이니까요.jpg", "마이디어북스", 17000));
    	    books.add(new Book("물고기는 존재하지 않는다", "룰루 밀러", "src\\images\\물고기는 존재하지 않는다.jpg", "곰출판", 17000));
    	    books.add(new Book("사랑의 기술", "에리히 프롬", "src\\images\\사랑의 기술.jpg", "문예출판사", 16000));
    	    books.add(new Book("사서 일기", "앨리 모건", "src\\images\\사서 일기.jpg", "문학동네", 17000));
    	    books.add(new Book("순도 100퍼센트의 휴식", "박상영", "src\\images\\순도 100퍼센트의 휴식.jpg", "출판사2", 16800));
    	    books.add(new Book("인생의 역사", "신형철", "src\\images\\인생의 역사.jpg", "난다", 18000));
    	    books.add(new Book("천문학자는 별을 보지 않는다", "심채경", "src\\images\\천문학자는 별을 보지 않는다.jpg", "문학동네",15000));
    	    books.add(new Book("최선을 다하면 죽는다", "황선우, 김혼비", "src\\images\\최선을 다하면 죽는다.jpg", "문학동네", 13500));
    	    books.add(new Book("향수가 된 식물들", "장 클로드 엘레나", "src\\images\\향수가 된 식물들.jpg", "아멜리에북스", 25000));
    	    books.add(new Book("푸바오, 매일매일 행복해", "강철원, 류정훈", "src\\images\\푸바오, 매일매일 행복해.jpg", "시공주니어", 20000));
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Book book : books) {
            JPanel bookPanel = createBookPanel(book);
            // 패딩을 추가하기 위해 또 다른 JPanel로 감싸기
            JPanel paddedPanel = new JPanel(new FlowLayout());
            paddedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 상하좌우 패딩 설정
            paddedPanel.add(bookPanel);
            tab3Panel.add(paddedPanel);

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
        tabbedPane.addTab("에세이", tab3Panel);
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
