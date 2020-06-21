package cn.graydove.security.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String prefix;
    private String token;

    @Override
    public String toString() {
        return String.format("{\"prefix\": \"%s\",\"token\":\"%s\"}", prefix, token);
    }
}
