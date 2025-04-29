package vn.hsu.StudentInformationSystem.service.dto;

public class ResLoginDto {
    private String accessToken;

    public ResLoginDto() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
