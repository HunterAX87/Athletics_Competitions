import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.*;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "Athletics87Bot";
    }

    @Override
    public String getBotToken() {
        return "****";
    }

    private enum options {
         WORK,USERROOT, USERPTIONS, ADMINOPTIONS,  ADDCOMPET, ADDSCHEDULE, ADDATHLETE, ADMINROOT, DELETECOMPET, DELETESCHEDULE
    }

    private final List<Long> adminIds = List.of(672678141L);
    private options option = options.WORK;
    int variable;
    Competitions currentCompet;
    Schedules currentSchedule;
    Athletes currentAthlete;
    private boolean isAdminMode = false;
    static ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();


    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();
        var txt = msg.getText();
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
        System.out.println(update);
        if (update.hasMessage()) {
            switch (txt) {
                case ("/admin") -> {
                    // проверяем, является ли отправитель команды администратором
                    if (adminIds.contains(id)) {
                        isAdminMode = !isAdminMode; // переключаем режим админа
                        String replyText = isAdminMode ? "Режим организатора включен" : "Режим организатора выключен";
                        sendText(id, replyText);
                        if (isAdminMode) {
                            option = options.ADMINROOT;
                        } else option = options.WORK;

                    } else {
                        sendText(id, "У вас нет прав на выполнение этой команды");
                    }
                }
                case "/start" -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    sendPhoto(id, "C:\\bbc.jpg");
                    String str = "Добро пожаловать в бота Athletics Сompetitions" + EmojiParser.parseToUnicode("‼️\uD83C\uDFC3\uD83C\uDFFB\u200D♀️\uD83E\uDD47\uD83C\uDFC3\uD83C\uDFFB️‼️")
                           + "\nС помощью этого бота вы сможете: \nЕсли вы организатор(админ): \n"
                           + EmojiParser.parseToUnicode(" ✅ ")+ " Организовывать соревнования "+ EmojiParser.parseToUnicode("\uD83C\uDFB1")+"\n"
                           + EmojiParser.parseToUnicode(" ✅ ")+ " Выбирать место и дату проведения соревнования "+ EmojiParser.parseToUnicode("\uD83C\uDFB2")+"\n"
                           + EmojiParser.parseToUnicode(" ✅ ")+ " Составлять расписание соревнований "+ EmojiParser.parseToUnicode("✍️")+"\n"
                           + EmojiParser.parseToUnicode(" ✅ ")+ " Удалять соревнования "+ EmojiParser.parseToUnicode("\uD83D\uDDD1️")+"\n"
                           + EmojiParser.parseToUnicode(" ✅ ")+ " Удалять расписания "+ EmojiParser.parseToUnicode("\uD83D\uDDD1️")+"\n"
                           + EmojiParser.parseToUnicode(" ✅ ")+ " Смотреть списки соревнований "+ EmojiParser.parseToUnicode("\uD83D\uDDD2")+"\n"
                           + EmojiParser.parseToUnicode(" ✅ ")+ " Смотреть расписания соревнований "+ EmojiParser.parseToUnicode("⏰")+"\n"
                           + EmojiParser.parseToUnicode(" ✅ ")+ " Смотреть списки зарегистрированных на соревнование атлетов "+ EmojiParser.parseToUnicode("\uD83D\uDC6B")+"\n"
                            + " Если вы тренер: \n"
                            + EmojiParser.parseToUnicode(" ✅ ")+ " Регистрировать своего атлета на соревнования "+ EmojiParser.parseToUnicode("⚔️")+"\n"
                            + EmojiParser.parseToUnicode(" ✅ ")+ " Смотреть списки соревнований "+ EmojiParser.parseToUnicode("\uD83D\uDDD2")+"\n"
                            + EmojiParser.parseToUnicode(" ✅ ")+ " Смотреть расписания соревнований "+ EmojiParser.parseToUnicode("⏰")+"\n"
                            + EmojiParser.parseToUnicode(" ✅ ")+ " Смотреть списки зарегистрированных на соревнование атлетов "+ EmojiParser.parseToUnicode("\uD83D\uDC6B")+"\n"
                            + " Если вы атлет: \n"
                            + EmojiParser.parseToUnicode(" ✅ ")+ " Смотреть списки соревнований "+ EmojiParser.parseToUnicode("\uD83D\uDDD2")+"\n"
                            + EmojiParser.parseToUnicode(" ✅ ")+ " Смотреть расписания соревнований "+ EmojiParser.parseToUnicode("⏰")+"\n"
                            + EmojiParser.parseToUnicode(" ✅ ")+ " Смотреть списки зарегистрированных на соревнование атлетов "+ EmojiParser.parseToUnicode("\uD83D\uDC6B")+"\n";

                    sendText(id, str);


                    if (isAdminMode) {
                        option = options.ADMINROOT;
                    } else option = options.USERROOT;
                }
            }


            switch (option) {
                case USERROOT -> {
                    System.out.println(" ---- МЫ В ПРАВАХ ПОЛЬЗОВАТЕЛЯ ----");
                    sendReplyKeyboardMessage(id, "Что вы хотите сделать?", Arrays.asList("Посмотреть списки доступных соревнований", "Зарегистрировать своего атлета(Тренерам)",
                            "Посмотреть расписания соревнований", "Посмотреть списки зарегистрированных на соревнование атлетов"));
                    if (isAdminMode) {
                        option = options.ADMINOPTIONS;
                    } else option = options.USERPTIONS;
                }

                case USERPTIONS ->  {
                    System.out.println("Я В ОПЦИЯХ ПОЛЬЗОВАТЕЛЯ");
                    switch (txt) {

                        case "Посмотреть списки доступных соревнований" -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            sendText(id, showCompetitions(id, "Competitions"));
                        }


                        case"Зарегистрировать своего атлета(Тренерам)" ->{
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            sendText(id, "Регистрация атлета" +
                                    "\nВведите данные в формате: " +
                                    "\nФАМИЛИЯ ИМЯ ВОЗРАСТ ИМЯ_ТРЕНЕРА ВИД_СОРЕВНОВАНИЯ ");
                            option= Bot.options.ADDATHLETE;
                        }


                        case "Посмотреть расписания соревнований" -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            sendText(id, showSchedules(id, "Schedules"));
                        }

                        case "Посмотреть списки зарегистрированных на соревнование атлетов" -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            sendText(id, "Вот списки всех зарегистрированных атлетов, информация про них! ");
                            sendText(id, showAthletes(id, "Athletes"));
                        }
                    }
                }




                case ADMINROOT -> {
                    System.out.println(" ---- МЫ В ПРАВАХ АДМИНА ----");
                    sendReplyKeyboardMessage(id, "Что вы хотите сделать?", Arrays.asList("Организовать новое соревнование", "Составить расписание для соревнования", "Посмотреть списки соревнований",
                            "Посмотреть расписания соревнований","Посмотреть списки зарегистрированных на соревнование атлетов", "Удалить соревнование","Удалить расписание"));
                    if (isAdminMode) {
                        option = options.ADMINOPTIONS;
                    } else option = options.WORK;
                }
                /**
                 * Блок работает только тогда, когда мы в админе
                 */
                case ADMINOPTIONS -> {
                    System.out.println("Я В ОПЦИЯХ АДМИНА");
                    switch (txt) {

                        case "Посмотреть списки соревнований" -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            sendText(id, showCompetitions(id, "Competitions"));
                        }

                        case "Посмотреть расписания соревнований" -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            sendText(id, showSchedules(id, "Schedules"));
                        }

                        case "Посмотреть списки зарегистрированных на соревнование атлетов" -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            sendText(id, "Вот списки всех зарегистрированных атлетов, информация про них! ");
                            sendText(id, showAthletes(id, "Athletes"));
                        }

                        case "Организовать новое соревнование" -> {
                            sendText(id, "Добавление нового соревнования. " +
                                    "\nВведите Название соревнования," +
                                    "\nМесто проведения соревнования," +
                                    "\nДату проведения соревнования." +
                                    "\nВведите данные в формате: НАЗВАНИЕ МЕСТО ДАТА");
                            System.out.println("Должны перейти в состояние добавления соревнования");
                            option = options.ADDCOMPET;
                        }

                        case "Составить расписание для соревнования" -> {
                            sendText(id, "Добавление нового соревнования. " +
                                    "\nВведите id соревнования," +
                                    "\nТип соревнования," +
                                    "\nСектор проведения соревнования," +
                                    "\nВремя проведения соревнования." +
                                    "\nВведите данные в формате: ID_СОРЕВНОВАНИЯ  ТИП СЕКТОР ВРЕМЯ");
                            System.out.println("Должны перейти в состояние составления расписания");
                            option = options.ADDSCHEDULE;
                        }

                        case "Удалить соревнование" -> {
                            sendText(id, "Выберите соревнование (по айди), которое хотите удалить");
                            sendText(id, showCompetitions(id, "Competitions"));
                            System.out.println("Должны перейти в состояние удаления соревнования");
                            option = options.DELETECOMPET;
                        }

                        case "Удалить расписание" -> {
                            sendText(id, "Выберите расписание (по айди), которое хотите удалить");
                            sendText(id, showSchedules(id, "Schedules"));
                            System.out.println("Должны перейти в состояние удаления расписания");
                            option = options.DELETESCHEDULE;
                        }
                    }
                }


                case DELETECOMPET -> {

                    variable = Integer.parseInt(txt); // тут выбранный варик
                    currentCompet = getObjectByElement(txt, "IdOfCompetition"); // тут получаем все данные о соревновании
                    chosenCompet(id);
                    // TODO: 21.05.2023  если чето не сработает, то проверку на админа вставить
                    deleteCompetById(variable);
                    sendText(id, "Вот список текущих мероприятий");
                    sendText(id, showCompetitions(id, "Competitions"));
//                    option = options.WORK;
                    System.out.println("я в админе");

                    option = options.ADMINROOT;
                }

                case DELETESCHEDULE -> {

                    variable = Integer.parseInt(txt); // тут выбранный варик
                    currentSchedule = getObjectByElementSchedule(txt, "IdOfScheduele"); // тут получаем все данные о расписании
                    chosenSchedule(id);
                    // TODO: 21.05.2023  если чето не сработает, то проверку на админа вставить
                    deleteScheduleById(variable);
                    sendText(id, "Вот список текущих расписаний");
                    sendText(id, showSchedules(id, "Schedules"));
                    System.out.println("я в админе");

                    option = options.ADMINROOT;
                }

                case ADDCOMPET -> {
                    sendText(id, "Ща обновимся");
                    String[] str = txt.split(" ");

                    try {
                        if (str.length < 3) {
                            throw new Exception(" недостаточно аргументов\n");
                        }

                        Competitions currentCompet = new Competitions(str[0], str[1], str[2]);

                        System.out.println(currentCompet);
                        addCompet(currentCompet);
                        sendText(id, "Соревнование добавлено успешно");
                        System.out.println("Идем в режим админа");
                        option = options.ADMINROOT;

                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
//                        sendText(id,"Неправильный формат данных. попробуйте еще раз \n");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        sendText(id, "Неправильный формат данных:\n " + e.getMessage());
                    }
                }

                case ADDSCHEDULE -> {
                    sendText(id, "Ща обновимся");
                    String[] str = txt.split(" ");

                    try {
                        if (str.length < 4) {
                            throw new Exception(" недостаточно аргументов\n");
                        }

                        Schedules currentSchedule = new Schedules(Integer.parseInt(str[0]), str[1], str[2], str[3]);

                        System.out.println(currentSchedule);
                        addSchedule(currentSchedule);
                        sendText(id, "Расписание добавлено успешно");
                        System.out.println("Идем в режим админаааа");
                        option = options.ADMINROOT;

                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
//                        sendText(id,"Неправильный формат данных. попробуйте еще раз \n");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        sendText(id, "Неправильный формат данных:\n " + e.getMessage());
                    }
                }

                case ADDATHLETE -> {
                    String[] strr = txt.split(" ");

                    try {
                        if (strr.length < 5) {
                            throw new Exception("");
                        }

                        Athletes currentAthlete = new Athletes(strr[0], strr[1], Integer.parseInt(strr[2]), strr[3], strr[4]);

                        System.out.println(currentAthlete);
                        addAthlete(currentAthlete);
                        sendText(id, "Атлет зарегистрирован успешно");
                        System.out.println("Идем в режим админаааа");
                        option = options.USERROOT;

                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
//                        sendText(id,"Неправильный формат данных. попробуйте еще раз \n");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        sendText(id, "\n " + e.getMessage());
                    }
                }
            }
        }
    } // тут тхт выступает как кол-во билетов


    public String showCompetitions(Long id, String Database) {
        Connection connection = null;
        String str;
        try {
            connection = DatabaseHandler.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + Database);

            sendText(id, "Вот список всех доступных соревнований \uD83C\uDFC3\uD83C\uDFFB\uD83C\uDFC3\uD83C\uDFFB\u200D♀️");
            str = "";

            while (resultSet.next()) {
                int competId = resultSet.getInt("IdOfCompetition");

                String competName = resultSet.getString("Name");

                String competLocation = resultSet.getString("Location");
                String competData = resultSet.getString("Data");

                str += "\uD83C\uDD94 -> " + competId + " Название соревнования\uD83D\uDC49  " + competName
                        + "\nМесто проведения соревнования\uD83D\uDC49 " + competLocation
                        + "\nДата проведения соревнования \uD83D\uDC49 " + competData + "\n";

            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return str;
    }




    public String showSchedules(Long id, String Database) {
        Connection connection = null;
        String str;
        try {
            connection = DatabaseHandler.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + Database);

            sendText(id, "Вот расписание соревнования ⏰");
            str = "";

            while (resultSet.next()) {
                int schedulId = resultSet.getInt("IdOfScheduele");

                //int schedulIdOfCompetition = resultSet.getInt("IdOfCompetition");

                String schedulType = resultSet.getString("Type");

                String schedulSector = resultSet.getString("Sector");
                String schedulTimme = resultSet.getString("Time");

                str += "\uD83C\uDD94 -> " + schedulId + "\n\uD83C\uDD94 Соревнования-> 1" //+ schedulIdOfCompetition
                        +"\nТип соревнования\uD83D\uDC49  " + schedulType
                        + "\nСектрор проведения типа соревнования\uD83D\uDC49 " + schedulSector
                        + "\nВремя проведения типа соревнования \uD83D\uDC49 " + schedulTimme + "\n";
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return str;
    }


//    private void insertCompetitionData(String sureName, String name,Integer age, String nameOfTrainer, String typeOfCompet)
//            throws SQLException, ClassNotFoundException {
//    // Создание подключения к базе данных
//        System.out.println("Создание подключения к базе данных");
//    Connection conn = DatabaseHandler.getConnection();
//
//    // Создание SQL-запроса для вставки данных о соревновании
//    String sql = "INSERT INTO competitions (SureName, Name, Age, NameOfTrainer, TypeOfCompet) VALUES (?, ?, ?, ?, ?)";
//
//    // Создание подготовленного запроса
//    PreparedStatement pstmt = conn.prepareStatement(sql);
//        pstmt.setString(1, sureName);
//        pstmt.setString(2, name);
//        pstmt.setInt(3, age);
//        pstmt.setString(4, nameOfTrainer);
//        pstmt.setString(5, typeOfCompet);
//
//    // Выполнение запроса
//        pstmt.executeUpdate();
//        System.out.println("Competition data inserted successfully.");
//
//    // Закрытие ресурсов
//        pstmt.close();
//        conn.close();
//}
//
//

    public String showAthletes(Long id, String Database) {
        Connection connection = null;
        String str;
        try {
            connection = DatabaseHandler.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + Database);

            sendText(id, "Вот список участвующих атлетов \uD83D\uDDD2");
            str = "";

            while (resultSet.next()) {
                int AthleteId = resultSet.getInt("IdOfAthlete");

                String AthleteSureName = resultSet.getString("SureName");

                String AthleteName = resultSet.getString("Name");
                int AthleteAge = resultSet.getInt("Age");

                String AthleteNameOfTrainer = resultSet.getString("NameOfTrainer");
                String AthleteTypeOfCompet = resultSet.getString("TypeOfCompet");

                str += "\uD83C\uDD94 -> " + AthleteId  +"\nФамилия атлета\uD83D\uDC49  " + AthleteSureName
                        + "\nИмя атлета\uD83D\uDC49 " + AthleteName
                        + "\nВозраст атлета \uD83D\uDC49 " + AthleteAge
                        + "\nИмя тренера атлета \uD83D\uDC49 " + AthleteNameOfTrainer
                        + "\nТип соревнования, в котором выступает атлет \uD83D\uDC49 " + AthleteTypeOfCompet + "\n";
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return str;
    }




    private void addCompet(Competitions compet) throws SQLException {
        System.out.println("Должен был перейти в метод добавки");
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseHandler.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement("INSERT INTO Competitions (Name ,Location,Data) VALUES (?,?,?)");

//            INSERT INTO Competitions (competName ,competLocation,competData) VALUES ('Первенство_Республики_Татарстан','Казан_стадион_Акбарс','23.06.2023')
            System.out.println(compet.toString());
            pstmt.setString(1, compet.getName());
            pstmt.setString(2, compet.getLocation());
            pstmt.setString(3, compet.getData());
            System.out.println(pstmt);
            pstmt.executeUpdate();
            // подтверждаем транзакцию
            conn.commit();
            System.out.println("Должен был добавить");
        } catch (SQLException e) {
            assert conn != null;
            conn.rollback(); // откатываем транзакцию, если произошла ошибка
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            assert conn != null;
            conn.setAutoCommit(true); // возвращаем в автоматический режим фиксации
            conn.close(); // закрываем подключение
        }
    }


//    private void addSchedule(Schedules schedule) throws SQLException {
//        System.out.println("Должен был перейти в метод добавки9999");
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        try {
//            conn = DatabaseHandler.getConnection();
//            conn.setAutoCommit(false);
//
//            pstmt = conn.prepareStatement("INSERT INTO Schedules (IdOfCompettition ,Type,Sector, Timme) VALUES (?,?,?,?)");
//
////            INSERT INTO Competitions (competName ,competLocation,competData) VALUES ('Первенство_Республики_Татарстан','Казан_стадион_Акбарс','23.06.2023')
//            System.out.println(schedule.toString());
//            pstmt.setInt(1, schedule.getIdOfCompettition());
//            pstmt.setString(2, schedule.getType());
//            pstmt.setString(3, schedule.getSector());
//            pstmt.setString(4, schedule.getTime());
//            System.out.println(pstmt);
//            pstmt.executeUpdate();
//            // подтверждаем транзакцию
//            conn.commit();
//            System.out.println("Должен был добавитььььь99");
//        } catch (SQLException e) {
//            assert conn != null;
//            conn.rollback(); // откатываем транзакцию, если произошла ошибка
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } finally {
//            assert conn != null;
//            conn.setAutoCommit(true); // возвращаем в автоматический режим фиксации
//            conn.close(); // закрываем подключение
//        }
//    }



    private void addSchedule(Schedules schedule) throws SQLException {
        System.out.println("Должен был перейти в метод добавки88888");
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseHandler.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement("INSERT INTO Schedules (IdOfCompettition , Type, Sector, Time) VALUES (?,?,?,?)");
            System.out.println(schedule.toString());
            pstmt.setInt(1, schedule.getIdOfCompettition());
            pstmt.setString(2, schedule.getType());
            pstmt.setString(3, schedule.getSector());
            pstmt.setString(4, schedule.getTime());
            System.out.println(pstmt);
            pstmt.executeUpdate();
            // подтверждаем транзакцию
            conn.commit();
            System.out.println("Должен был добавитььь");
        } catch (SQLException e) {
            assert conn != null;
            conn.rollback(); // откатываем транзакцию, если произошла ошибка
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            assert conn != null;
            conn.setAutoCommit(true); // возвращаем в автоматический режим фиксации
            conn.close(); // закрываем подключение
        }
    }




    private void addAthlete(Athletes athlete) throws SQLException {
        System.out.println("Должен был перейти в метод добавки88888");
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DatabaseHandler.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement("INSERT INTO Athletes (SureName , Name, Age, NameOfTrainer, TypeOfCompet) VALUES (?,?,?,?,?)");
            System.out.println(athlete.toString());
            pstmt.setString(1, athlete.getSureName());
            pstmt.setString(2, athlete.getName());
            pstmt.setInt(3, athlete.getAge());
            pstmt.setString(4, athlete.getNameOfTrainer());
            pstmt.setString(5, athlete.getTypeOfCompet());
            System.out.println(pstmt);
            pstmt.executeUpdate();
            // подтверждаем транзакцию
            conn.commit();
            System.out.println("Должен был добавитььь");
        } catch (SQLException e) {
            assert conn != null;
            conn.rollback(); // откатываем транзакцию, если произошла ошибка
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            assert conn != null;
            conn.setAutoCommit(true); // возвращаем в автоматический режим фиксации
            conn.close(); // закрываем подключение
        }
    }





public Competitions getObjectByElement(String id, String element) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    Competitions chosenEvent = null;

    System.out.println("ID -> " + id + "\n element -> " + element);

    try {
        connection = DatabaseHandler.getConnection();
        statement = connection.prepareStatement("SELECT * FROM Competitions WHERE " + element + " = ?");

        System.out.println("я после запроса");
//            statement = connection.prepareStatement("SELECT * FROM Events WHERE id = ?");
//            statement.setInt(1, Integer.parseInt(id));
        statement.setString(1, id);
        resultSet = statement.executeQuery();
        System.out.println(statement.toString());
        System.out.println(resultSet.toString());
        if (resultSet.next()) {
            // create object with retrieved data
            chosenEvent =new Competitions(resultSet.getInt("IdOfCompetition"),
                        resultSet.getString("Name"), resultSet.getString("Location"),
                        resultSet.getString("Data"));
        }
        System.out.println(chosenEvent.toString());
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    } finally {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return chosenEvent;
}


    public Schedules getObjectByElementSchedule(String id, String element) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Schedules chosenEvent = null;

        System.out.println("ID -> " + id + "\n element -> " + element);

        try {
            connection = DatabaseHandler.getConnection();
            statement = connection.prepareStatement("SELECT * FROM Schedules WHERE " + element + " = ?");

            System.out.println("я после запроса");
//            statement = connection.prepareStatement("SELECT * FROM Events WHERE id = ?");
//            statement.setInt(1, Integer.parseInt(id));
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            System.out.println(statement.toString());
            System.out.println(resultSet.toString());
            if (resultSet.next()) {
                // create object with retrieved data
                chosenEvent =new Schedules(resultSet.getInt("IdOfScheduele"),
                        resultSet.getInt("IdOfCompettition"),
                        resultSet.getString("Type"), resultSet.getString("Sector"),
                        resultSet.getString("Time"));
            }
            System.out.println(chosenEvent.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return chosenEvent;
    }



    public void deleteCompetById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // создаем подключение к базе данных
            conn = DatabaseHandler.getConnection();
            // создаем запрос на удаление данных с указанным id
            pstmt = conn.prepareStatement("DELETE FROM Competitions WHERE IdOfCompetition = ?");
            pstmt.setInt(1, id);
            System.out.println("mi udalili");
            // выполняем запрос на удаление
            pstmt.executeUpdate();
            // обновляем id в оставшихся записях
            pstmt = conn.prepareStatement("UPDATE Competitions SET IdOfCompetition = IdOfCompetition - 1 WHERE IdOfCompetition > ?");
            pstmt.setInt(1, id);
            // выполняем запрос на обновление
            pstmt.executeUpdate();
            // подтверждаем транзакцию
            conn.commit();
            System.out.println("Должен был удалить");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                // закрываем все ресурсы
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    public void deleteScheduleById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // создаем подключение к базе данных
            conn = DatabaseHandler.getConnection();
            // создаем запрос на удаление данных с указанным id
            pstmt = conn.prepareStatement("DELETE FROM Schedules WHERE IdOfScheduele = ?");
            pstmt.setInt(1, id);
            // выполняем запрос на удаление
            pstmt.executeUpdate();
            // обновляем id в оставшихся записях
            pstmt = conn.prepareStatement("UPDATE Schedules SET IdOfScheduele = IdOfScheduele - 1 WHERE IdOfScheduele > ?");
            pstmt.setInt(1, id);
            // выполняем запрос на обновление
            pstmt.executeUpdate();
            // подтверждаем транзакцию
            conn.commit();
            System.out.println("Должен был удалить");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                // закрываем все ресурсы
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    public void chosenCompet(Long id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sendText(id, "вы выбрали мероприятие --> " + this.currentCompet);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void chosenSchedule(Long id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sendText(id, "вы выбрали расписание --> " + this.currentSchedule);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void sendPhoto(long id, String pathToPhoto) {
        // создаем объект InputFile из файла
        File photo = new File(pathToPhoto);
        InputFile inputFile = new InputFile(photo);
        // создаем объект SendPhoto и добавляем в него InputFile
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(id));
        sendPhoto.setPhoto(inputFile);
        // отправляем фото
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }




    public void sendText(Long who, String what) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(who.toString()) // Who are we sending a message to
                .text(what).build();   // Message content

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public void sendReplyKeyboardMessage(Long chatId, String text, List<String> options) {
//        SendMessage message = SendMessage.builder()
//                .chatId(chatId.toString()) // Who are we sending a message to
//                .text(text).build().setReplyMarkup(getReplyKeyboardMarkup(options));
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString()).
                text(text)
                .replyMarkup(getReplyKeyboardMarkup(options)).build();

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboardMarkup getReplyKeyboardMarkup(List<String> options) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        for (String option : options) {
            KeyboardRow row = new KeyboardRow();
            row.add(option);
            keyboard.add(row);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }
}