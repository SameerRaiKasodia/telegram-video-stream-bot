import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
        public static void main(String[] args) {

            String botToken = "Your-Bot-Token";

            try {
                TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
                botsApplication.registerBot(botToken, new MyBot(botToken));
                System.out.println("Bot is running! Press Ctrl+C to stop.");


                Thread.currentThread().join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
