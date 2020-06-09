package nhatro.auth.common;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class JwtData {
    Integer userId;
    String userName;
    Integer role;
    String deviceId;
    Timestamp timeCreate;

    @Override
    public String toString() {
        return "JwtData{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", role=" + role +
                ", deviceId='" + deviceId + '\'' +
                ", timeCreate=" + timeCreate +
                '}';
    }
}
