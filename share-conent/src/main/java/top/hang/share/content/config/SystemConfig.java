package top.hang.share.content.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemConfig {

    @Value("${system.notice.enabled}")
    private boolean systemNoticeEnabled;
    @Value("${system.notice.message}")
    private String systemNoticeMessage;

}