package pro.sky.telegrambot.handle;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.TelegramBotService;

@Service
public class Handlers {

    private final TelegramBotService telegramBotService;

    public Handlers(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    public void handleShelterConsultation(Long chatId, String type) {
        String animalType = null;
        String text = null;
        if (type.equals("/dog")) {
            animalType = "собак";
        } else {
            animalType = "кошек";
        }
        text = "Выберите, какая информация по приюту для " + animalType + " вас интересует:\n" +
                "'/information' - Узнать информацию о приюте \n" +
                "'/howToTakePet' - Как взять животное из приюта\n" +
                "'/3' - Прислать отчет о питомце\n" +
                "'/volunteer' - Позвать волонтера";
        telegramBotService.sendMessage(chatId, text);
    }
    public void handleAdoptionConsultation(Long chatId, String type) {
        String text = "Подскажите, какая информация о приюте вас интересует? \n" +
                "'/about' - рассказать о приюте.\n" +
                "'/workingHours' - выдать расписание работы приюта и адрес, схему проезда.\n" +
                "'/securityNumber' - выдать контактные данные охраны для оформления пропуска на машину.\n" +
                "'/safetyPrecautions' - выдать общие рекомендации о технике безопасности на территории приюта.\n" +
                "'/writeData' - принять и записать контактные данные для связи.";
        telegramBotService.sendMessage(chatId, text);
    }

    public void aboutShelter(Long chatId, String shelterType) {
        String text = null;
        if (shelterType.equals("Приют для кошек")) {
            text = "Приют для кошек работает с 1999 года и имеет уже 3 кошки";
        } else {
            text = "Приют для собак не работал никогда";
        }
        telegramBotService.sendMessage(chatId, text);
    }

    public void workingHours(Long chatId, String shelterType) {
        String text = null;
        if (shelterType.equals("Приют для кошек")) {
            text = "Рабочие часы приюта: 8:00 - 18:00 + \n" +
                    "Приют находится по адресу: г. Калининград, ул. Куклачева, д.9";
        } else {
            text = "Рабочие часы приюта: 6:00 - 20:00 + \n" +
                    "Приют находится по адресу: г. Белгород, ул. Стрелка, д.19/6";
        }
        telegramBotService.sendMessage(chatId, text);
    }

    public void securityNumber(Long chatId, String shelterType) {
        String text = null;
        if (shelterType.equals("Приют для кошек")) {
            text = "Номер охраны: 8-999-999-99-99";
        } else {
            text = "Номер охраны: 8-888-888-88-88";
        }
        telegramBotService.sendMessage(chatId, text);
    }

    public void safetyPrecautions(Long chatId, String shelterType) {
        String text = null;
        if (shelterType.equals("Приют для кошек")) {
            text = "Не быть мышью, принести с собой коробку";
        } else {
            text = "Не быть куском мяса, принести палку";
        }
        telegramBotService.sendMessage(chatId, text);
    }

    public void writeData(Long chatId) {
        telegramBotService.sendMessage(chatId, "Напишите пожалуйста ваш номер, и с вами в ближайшее время позвонит наш оператор");
    }

    public void howToTakePet(Long chatId, String shelterType) {
        StringBuilder stringBuilder = new StringBuilder(
                "Подскажите, какая информация о приюте вас интересует?+\n" +
                "'/welcomeRules' - выдать правила знакомства с животным до того, как забрать его из приюта.+\n" +
                "'/docs' - выдать список документов, необходимых для того, чтобы взять животное из приюта.+\n" +
                "'/petTransportation' - выдать список рекомендаций по транспортировке животного. +\n" +
                "'/babyPetHouse' - выдать список рекомендаций по обустройству дома для щенка/котенка. +\n" +
                "'/petHouse' - выдать список рекомендаций по обустройству дома для взрослого животного.+\n" +
                "'/specialPetHouse' - выдать список рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижение).+\n");
        String text = "'/adviceDogHandler' - выдать советы кинолога по первичному общению с собакой.+\n" +
                "'/dogHandler' - выдать рекомендации по проверенным кинологам для дальнейшего обращения к ним.";
        if (shelterType.equals("Приют для собак")) {
            stringBuilder.append(text);
        }
        String text1 = "'/refusePet' - выдать список причин, почему могут отказать и не дать забрать собаку из приюта. +\n" +
                "'/volunteer' - позвать волонтера.";
        stringBuilder.append(text1);
        telegramBotService.sendMessage(chatId, stringBuilder.toString());
    }

    public void welcomeRules(Long chatId, String shelterType) {
        String text = null;
        if (shelterType.equals("Приют для кошек")) {
            text = "Перед тем, как забрать к себе кошку," +
                    "приедите в приют и понаблюдайте," +
                    "какой у каждого питомца характер, повадки и " +
                    "особенности поведения. При выборе учитывайте и " +
                    "свой собственный распорядок дня: не стоит брать " +
                    "того котика, который не сможет ждать вас с работы, " +
                    "если вы постоянно на ней пропадаете.";
        } else {
            text = "У каждой собаки, живущей в приюте, свои судьба, история, " +
                    "характер. Не торопитесь с выбором: расспросите сотрудника " +
                    "или волонтера о понравившемся четвероногом. Узнайте, какой у " +
                    "питомца характер, как он относится к людям, детям, другим собакам, " +
                    "как ведет себя на прогулке, какие у него есть заболевания. " +
                    "Желательно съездить в приют несколько раз, чтобы пообщаться с " +
                    "четвероногим, можно несколько раз погулять. Это необходимо для того, " +
                    "чтобы вы были уверены в своем выборе.";
        }
        telegramBotService.sendMessage(chatId, text);
    }
    public void docs(Long chatId) {
        telegramBotService.sendMessage(chatId, "Для того, чтобы вы могли забрать питомца, " +
                "От вас потребуется один из следующих документов, удостоверяющего личность:" +
                "1) Паспорт РФ;" +
                "2) Иностранный паспорт");
    }


    public void petTransportation(Long chatId) {
        telegramBotService.sendMessage(chatId, "Живые животные, перевозка которых " +
                "проходит строго по регламенту, перед поездкой должны иметь необходимую " +
                "документацию, подтверждающую наличие прививок и разрешение на " +
                "транспортировку в другой регион. Собаки больших и бойцовских пород " +
                "транспортируются только в намордниках или специальных закрытых контейнерах.");
    }

    public void babyPetHouse(Long chatId) { telegramBotService.sendMessage(chatId, "Прежде чем принести домой щенка или котенка, " +
            "позаботьтесь о том, чтобы обстановка в доме была безопасной для вашего " +
            "нового питомца. Защита вещей от щенков и котят не только спасет вашего " +
            "нового питомца от скрытых опасностей, но и убережет ваше имущество от " +
            "разрушения. Речь идет о коврах, шторах, мебели, бытовой электронике и " +
            "других элементах вашего дома.");
    }

    public void petHouse(Long chatId) {
        telegramBotService.sendMessage(chatId, "Некоторые собаки и кошки, вне зависимости от породы, " +
                "любят спать в домиках, некоторые — только на открытых лежанках. " +
                "Важно, чтобы они подходили по размеру и у питомца оставалось место, " +
                "когда он ляжет так, как ему удобно. Большую роль играет качество домика. " +
                "Необходимо покупать такие, чтобы любители покопать свое место не порвали швы " +
                "и не добрались до синтепона, который находится внутри.");
    }

    public void specialPetHouse(Long chatId) {
        telegramBotService.sendMessage(chatId,"Не забыть найти какой документ и вставить выдержку из него сюда");
    }

    public void adviceDogHandler(Long chatId) {
        telegramBotService.sendMessage(chatId,"Не забыть добавить ссылку на какой сайт с советами кинологов");

    }

    public void dogHandler(Long chatId) {
        telegramBotService.sendMessage(chatId,"Лучшие кинологи: Леха, Вован, Комисар, Чел из 'Я - легенда'");
    }

    public void refusePet(Long chatId) {
        telegramBotService.sendMessage(chatId,"Основные причины, по которым вам могут отказать в выдаче питомца из приюта:" +
                "1 Большое количество животных дома " +
                "2 Нестабильные отношения в семье " +
                "3 Наличие маленьких детей " +
                "4 Съемное жилье " +
                "5 Животное в подарок или для работы");

    }

    public void volunteer(Long chatId) {
        telegramBotService.sendMessage(chatId,"Вам скоро наберет один из наших волонтеров, всего хорошего!");
    }
}
