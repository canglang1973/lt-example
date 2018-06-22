package lt.ratelimiter.example.redis_lua.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author leitao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateLimiterVo {
    private String name;
    private String apps;
    private Integer maxPermits;
    private Integer rate;
    private Integer currPermits;
    private String lastPermitTimestamp;

}
