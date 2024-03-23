package cn.ksmcbrigade.cllb;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.ModInitializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ChunkLoadingLimitBreached implements ModInitializer {

    public static boolean init = false;
    public static int max = 256;

    @Override
    public void onInitialize() {
        if(!init){
            try {
                init();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void init() throws IOException{
        File config = new File("config/cllb-confg.json");
        if(!config.exists()){
            JsonObject object = new JsonObject();
            object.addProperty("max",max);
            Files.write(config.toPath(),object.toString().getBytes());
        }
        JsonObject json = JsonParser.parseString(Files.readString(config.toPath())).getAsJsonObject();
        max = json.get("max").getAsInt();

        init=true;
    }
}
