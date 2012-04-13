package ch.morefx.xbmc.net.commands;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import ch.morefx.xbmc.model.PlayerInfo;
import ch.morefx.xbmc.model.PlayerInfo.PlayerType;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Implements the Player.GetActivePlayers json command.
 * See http://wiki.xbmc.org/index.php?title=JSON-RPC_API/v3#Player.GetActivePlayers
 */
public class PlayerGetActivePlayersCommand extends JsonCommand
	implements JsonCommandResponseHandler{

	
	private List<PlayerInfo> playerInfos;
	
	public PlayerGetActivePlayersCommand() {
		super("Player.GetActivePlayers");
	}
	
	
	public void handleResponse(JsonCommandResponse response) {
		PlayerInfo[] playerArray = response.asArrayResultWithCreator(PlayerInfo[].class, PlayerInfo.class, new PlayerInfoCreator());
		playerInfos = Arrays.asList(playerArray);
	}
	

	public PlayerInfo getAudioPlayerInfo(){
		return getPlayerInfo(PlayerType.Audio);
	}

	public PlayerInfo getVideoPlayerInfo(){
		return getPlayerInfo(PlayerType.Video);
	}
	
	public PlayerInfo getPicturePlayerInfo(){
		return getPlayerInfo(PlayerType.Picture);
	}
	
	private PlayerInfo getPlayerInfo(PlayerType type){
		for(PlayerInfo info : playerInfos){
			if (info.getType() == type){
				return info;
			}
		}
		
		return null;
	}
	
	
	private static final class PlayerInfoCreator implements JsonDeserializer<PlayerInfo> {
		
		public PlayerInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			
			JsonObject jo = (JsonObject)json;
			String type = jo.get("type").getAsString();
			int playerId = jo.get("playerid").getAsInt();
			if (type.equals("audio"))
				return new PlayerInfo(PlayerType.Audio, playerId);
			if (type.equals("video"))
				return new PlayerInfo(PlayerType.Video, playerId);
			if (type.equals("picture"))
				return new PlayerInfo(PlayerType.Picture, playerId);

			throw new JsonParseException("unable to create player for type " + type);
		}
	}
}
