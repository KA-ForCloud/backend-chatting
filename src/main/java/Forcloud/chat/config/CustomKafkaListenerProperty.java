package Forcloud.chat.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomKafkaListenerProperty {
    private String topic;
    private String listenerClass;
}
