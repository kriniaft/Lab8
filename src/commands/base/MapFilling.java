package commands.base;
import commands.*;
import java.util.HashMap;

public class MapFilling {
    public void mapFill(HashMap<String, Command> map){
        Add.register(map);
        AddIfMax.register(map);
        Clear.register(map);
        CountBySameHeight.register(map);
        Exit.register(map);
        Help.register(map);
        Info.register(map);
        PrintUniqueHair.register(map);
        RemoveByID.register(map);
        RemoveByPassport.register(map);
        RemoveGreater.register(map);
        Show.register(map);
        UpdateID.register(map);
        Save.register(map);
        ExecuteScript.register(map);
    }
    public void mapForEveryOneFill(HashMap<String, Command> map){
        Clear.regForEveryone(map);
        CountBySameHeight.regForEveryone(map);
        Exit.regForEveryone(map);
        Help.regForEveryone(map);
        Info.regForEveryone(map);
        PrintUniqueHair.regForEveryone(map);
        Show.regForEveryone(map);
    }
}
