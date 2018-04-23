package Controller;
import Model.Entity.Character.CharacterEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;

public class MapGenerator {
    private String mapDir = "/Users/lorenzo/Documents/GitHub/OOP-Iteration-3/resources/maps/";
    private Collection<Point> TerrainPoints;

    public MapGenerator(String mapName, int row, int cols, int id){
        this.mapDir += mapName;
        JSONObject World = new JSONObject();
        JSONObject inner = new JSONObject();
        JSONObject inner2 = new JSONObject();
        JSONObject curMap = new JSONObject();

        curMap.put("currentMap", 1);
        inner.put("currentMap", 1);
        JSONArray Map = new JSONArray();
        JSONArray Tile = new JSONArray();
        for(int i = 0; i<row; i++)
        {

            for(int j = 0; j<cols; j++)
            {
                JSONObject tiles = new JSONObject();
                JSONObject type = new JSONObject();
                type.put("type","none");
                tiles.put("terrain", "grass");
                tiles.put("areaEffect", "none");
                tiles.put("item", "none");
                tiles.put("decal", "none");
                tiles.put("Entity", type);
                tiles.put("x", i);
                tiles.put("y", j);
                Tile.put(tiles);
            }
        }

        inner2.put("id",id);
        inner2.put("rows",row);
        inner2.put("columns",cols);
        inner2.put("Tile", Tile);
        Map.put(inner2);
        inner.put("Map",Map);
        System.out.println((inner2.getJSONArray("Tile")).getClass().getSimpleName());
        World.put("World", inner);
        ObjectOutputStream file = null;

        try {
            file = new ObjectOutputStream(new FileOutputStream(this.mapDir));
            file.writeObject(World.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public MapGenerator(String mapName, int row, int cols, int id, HashMap<Point, String> TerrainMap, HashMap<Point, CharacterEntity> EntityMap, HashMap<Point, String> AreaEffectMap, HashMap<Point, String> ItemsMap, HashMap<Point, String> DecalsMap){
        this.mapDir += mapName;
        JSONObject World = new JSONObject();
        JSONObject inner = new JSONObject();
        JSONObject inner2 = new JSONObject();
        JSONObject curMap = new JSONObject();

        curMap.put("currentMap", 1);
        inner.put("currentMap", 1);
        JSONArray Map = new JSONArray();
        JSONArray Tile = new JSONArray();
        for(int i = 0; i<row; i++)
        {

            for(int j = 0; j<cols; j++)
            {
                JSONObject tiles = new JSONObject();
                JSONObject type = new JSONObject();
                if(EntityMap.get(new Point(i,j)) == null)
                {
                    type.put("type","none");
                }
                else{
                    type.put("type",EntityMap.get(new Point(i,j)).getClass().getName().toString().replace("Model.Entity.Character.",""));
                    type.put("class",EntityMap.get(new Point(i,j)).getSkillClass());
                    type.put("name",EntityMap.get(new Point(i,j)).getName());
                    type.put("level", Integer.toString(EntityMap.get(new Point(i,j)).getLevel()));
                    type.put("maxHealth", Integer.toString(EntityMap.get(new Point(i,j)).getMaxHealth()));
                    type.put("currentHealth", Integer.toString(EntityMap.get(new Point(i,j)).getCurrentHealth()));
                    type.put("maxMana", Integer.toString(EntityMap.get(new Point(i,j)).getMaxMana()));
                    type.put("currentMana", Integer.toString(EntityMap.get(new Point(i,j)).getCurrentMana()));
                    type.put("attack", Integer.toString(EntityMap.get(new Point(i,j)).getAttack()));
                    type.put("defense", Integer.toString(EntityMap.get(new Point(i,j)).getDefense()));
                    type.put("speed", Integer.toString(EntityMap.get(new Point(i,j)).getSpeed()));
                    type.put("Inventory", Integer.toString(EntityMap.get(new Point(i,j)).getAttack()));
                    type.put("Skills", Integer.toString(EntityMap.get(new Point(i,j)).getAttack()));
                    if(EntityMap.get(new Point(i,j)).getPet() != null)
                    {
                        type.put("pet", EntityMap.get(new Point(i,j)).getPetName());
                    }else {
                        type.put("pet", "none");
                    }
                    //UP, UPRIGHT, DOWNRIGHT, DOWN, DOWNLEFT, UPLEFT;
                    String OrString = "";
                    switch (EntityMap.get(new Point(i,j)).getOrientation()) {
                        case UPLEFT:
                            OrString = "upleft";
                            break;
                        case UP:
                            OrString = "up";
                            break;
                        case UPRIGHT:
                            OrString = "upright";
                            break;
                        case DOWNLEFT:
                            OrString = "downleft";
                            break;
                        case DOWN:
                            OrString = "down";
                            break;
                        case DOWNRIGHT:
                            OrString = "downright";
                            break;
                    }
                    type.put("orientation", OrString);


                    //save entity details
                }
                tiles.put("terrain", TerrainMap.get(new Point(i,j)));
                tiles.put("areaEffect", AreaEffectMap.get(new Point(i,j)));
                tiles.put("item", ItemsMap.get(new Point(i,j)));
                tiles.put("decal", DecalsMap.get(new Point(i,j)));
                tiles.put("Entity", type);
                tiles.put("x", i);
                tiles.put("y", j);
                Tile.put(tiles);
            }
        }

        inner2.put("id",id);
        inner2.put("rows",row);
        inner2.put("columns",cols);
        inner2.put("Tile", Tile);
        Map.put(inner2);
        inner.put("Map",Map);
        System.out.println((inner2.getJSONArray("Tile")).getClass().getSimpleName());
        World.put("World", inner);
        ObjectOutputStream file = null;

        try {
            file = new ObjectOutputStream(new FileOutputStream(this.mapDir));
            file.writeObject(World.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
