package elibrary.utils;

import elibrary.dtos_requests.UploadBookRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class TestUtils {
    public static final String TEST_BOOK_COVER_PAGE_LOCATION = "C:\\Users\\DELL\\Documents\\GitHub\\maverickshub\\src\\main\\resources\\static\\img.png";
    public static final String TEST_SECOND_BOOK_COVER_PAGE_LOCATION = "C:\\Users\\DELL\\Desktop\\my picture.JPG";
    public static UploadBookRequest buildUploadMediaRequest(InputStream inputStream) throws IOException {
        UploadBookRequest uploadBookRequest = new UploadBookRequest();
        MultipartFile file = new MockMultipartFile("Book",inputStream);
        uploadBookRequest.setTitle("BooK Title");
        uploadBookRequest.setDescription("Book description");
        uploadBookRequest.setAuthor("Book author");
        uploadBookRequest.setMediaFile(file);
        uploadBookRequest.setAdminEmail("google-man@gmail.com");
        return uploadBookRequest;
    }
}
