package blog.be.exception;

public class FileException {
    public static class FileDownloadException extends Throwable {
        public FileDownloadException(String message) {
            super(message);
        }
    }
    public static class SpringBootFileUploadException extends Exception{
        public SpringBootFileUploadException(String message) {
            super(message);
        }
    }
    public static class FileUploadException extends SpringBootFileUploadException{
        public FileUploadException(String message) {
            super(message);
        }
    }
    public static class FileEmptyException extends SpringBootFileUploadException {
        public FileEmptyException(String message) {
            super(message);
        }
    }
}
