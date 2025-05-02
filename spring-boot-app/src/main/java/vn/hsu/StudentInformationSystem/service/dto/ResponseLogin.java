package vn.hsu.StudentInformationSystem.service.dto;

public class ResponseLogin {
    private String accessToken;

    public ResponseLogin() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
