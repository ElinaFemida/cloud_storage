package Common;

public class Auth  extends AbstractRequest {
    private final String folderName;

    public String getLogin() {
        return folderName;
    }

    public Auth(String folderName) {
        this.folderName = folderName;
    }

}