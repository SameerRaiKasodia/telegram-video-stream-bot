import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class MyBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final String botToken;

    public MyBot(String botToken) {
        this.botToken = botToken;
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();

            // Handle text commands
            if (message.hasText()) {
                String messageText = message.getText();

                if (messageText.equals("/start")) {
                    sendMessage(chatId, "🎬 Welcome to Video Stream Bot!\n\nSend me any video and I'll give you a streaming link.");
                }
                else if (messageText.equals("/help")) {
                    sendMessage(chatId, "📹 How to use:\n1. Send me any video file\n2. I'll generate a streaming link\n3. Open the link in any browser to watch");
                }
                else {
                    sendMessage(chatId, "Send me a video to get streaming link! Try /help");
                }
            }
            // Handle video files
            else if (message.hasVideo()) {
                Video video = message.getVideo();
                String videoFileId = video.getFileId();
                String streamingLink = "https://api.telegram.org/file/bot" + botToken + "/" + videoFileId;

                sendMessage(chatId, "🎥 Your video is ready!\n\nWatch here: " + streamingLink);
            }
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(text)
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}