package nojunit;
import java.io.File;
import java.util.Enumeration;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import br.com.criativasoft.cpluslibparser.SourceParser;

public class RunSourceParser {
    public static void main( String[] args ) {

    Handler consoleHandler = new ConsoleLogger();
    consoleHandler.setLevel(Level.ALL);
    consoleHandler.setFormatter(new LogFormatter("%1$tl:%1$tM:%1$tS [%4$7s] %2$s: %5$s%n"));
    
    Enumeration<String> loggerNames = LogManager.getLogManager().getLoggerNames();
    while (loggerNames.hasMoreElements()) {
        String name = (String) loggerNames.nextElement();
        Logger logger = Logger.getLogger(name);
        logger.setLevel(consoleHandler.getLevel());
        for(Handler handler : logger.getHandlers()) {
          logger.removeHandler(handler);
        }
        logger.addHandler(consoleHandler);
    }
    

//        new SourceParser().parse(new File("/media/Dados/Codigos/Java/Projetos/OpenDevice/opendevice-hardware-libraries/arduino/OpenDevice/DeviceConnection.h"));
//        new SourceParser().parse(new File("/media/Dados/Codigos/Java/Projetos/OpenDevice/opendevice-hardware-libraries/arduino/OpenDevice/DeviceManager.h"));
        new SourceParser().parse(new File("/media/Dados/Codigos/Java/Projetos/OpenDevice/opendevice-hardware-libraries/arduino/OpenDevice/OpenDevice.h"));
//        new SourceParser().parse(new File("/home/ricardo/Documentos/arduino-1.0.6/libraries/Ethernet/examples/AdvancedChatServer/AdvancedChatServer.ino"));
//        new SourceParser().parse(new File("/media/Dados/Programacao/arduino-1.5.8/hardware/arduino/avr/cores/arduino/Arduino.h"));
//        new SourceParser().parse(new File("/media/Dados/Programacao/arduino-src/hardware/arduino/avr/cores/arduino/HardwareSerial.h"));
//        new SourceParser().parse(new File("/media/Dados/Programacao/arduino-1.5.8/hardware/arduino/avr/cores/arduino/USBCore.h"));
    }
}
