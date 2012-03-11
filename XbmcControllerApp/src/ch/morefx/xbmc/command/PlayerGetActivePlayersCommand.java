package ch.morefx.xbmc.command;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import ch.morefx.xbmc.model.players.AudioPlayer;
import ch.morefx.xbmc.model.players.PicturePlayer;
import ch.morefx.xbmc.model.players.Player;
import ch.morefx.xbmc.model.players.VideoPlayer;

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
	implements CommandResponseHandler{


	private List<Player> players;
	
	public PlayerGetActivePlayersCommand() {
		super("Player.GetActivePlayers");
	}
	
	public void handleResponse(CommandResponse response) {
		
		Player[] playerArray = response.asArrayResultWithCreator(Player[].class, Player.class, new PlayerCreator());
		players = Arrays.asList(playerArray);
	}
	
	/**
	 * Is xbmc playing a song, video or showing a picture ?
	 * @return if something is playing then it returns true, false otherwise.
	 */
	public boolean isPlaying(){
		return players != null && players.size() > 0;
	}
	
	/**
	 * Gets a list with all active players.
	 * @return List of Player or null, when nothing is playing.
	 */
	public List<Player> getPlayers(){
		return this.players;
	}
	
	
	private static final class PlayerCreator implements JsonDeserializer<Player> {
		
		public Player deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			
			JsonObject jo = (JsonObject)json;
			String type = jo.get("type").getAsString();
			int playerId = jo.get("playerid").getAsInt();
			if (type.equals("audio"))
				return new AudioPlayer(playerId);
			if (type.equals("video"))
				return new VideoPlayer(playerId);
			if (type.equals("picture"))
				return new PicturePlayer(playerId);

			throw new JsonParseException("unable to create player for type " + type);
		}
	}
}
