package BookKiosk;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.RenderingHints;

//책 정보를 나타내는 Book 클래스
public class Book {
	private String title;          // 책 제목
	private String author;         // 작가
	private ImageIcon image;       // 이미지 아이콘
	private String publisher;      // 출판사
	private int originalPrice;     // 원래 가격
	private int sellingPrice;      // 판매 가격

    public Book(String title, String author, String imagePath, String publisher, int originalPrice) {
        this.title = title;
        this.author = author;
        // 이미지 아이콘을 생성하고 크기를 조절하여 설정
        this.image = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(115, 183, Image.SCALE_SMOOTH));
        this.publisher = publisher;
        this.originalPrice = originalPrice;
        this.sellingPrice = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Image getImage() {
        return image.getImage();
    }

    public String getPublisher() { 
        return publisher;
    }
    
    public int getOriginalPrice() {
        return originalPrice;
    }
    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    // 책 상태에 따른 판매 가격 설정
    public void setPriceForStatus1() {
        this.sellingPrice = (int) (originalPrice * 0.4);
    }

    public void setPriceForStatus2() {
        this.sellingPrice = (int) (originalPrice * 0.3);
    }

    public void setPriceForStatus3() {
        this.sellingPrice = (int) (originalPrice * 0.1);
    }
}
