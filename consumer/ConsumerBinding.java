package ro.tuc.ds2020.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ConsumerBinding {

    String channel = "sendingChannel";

    @Input(channel)
    SubscribableChannel sending();
}
