package org.extendedalpha.pluginbase.jsonchat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonMessage {

    private String msg;

    /**
     * Create a new json message!
     */
    public JsonMessage() {
        msg = "[{\"text\":\"\",\"extra\":[{\"text\": \"\"}";
    }

    private static Class<?> getNmsClass(String nmsClassName) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + nmsClassName);
    }

    private static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

    /**
     * Send the json string to all players on the server.
     */
    public void send() {
        List<Object> players = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) players.add(p);
        send(players.toArray(new Player[players.size()]));
    }

    /**
     * Send the json string to specified player(s)
     * @param player to send the message to.
     */
    public void send(Player... player) {
        String version = getServerVersion();
        String nmsClass = (version.startsWith("v1_8_R") ? "IChatBaseComponent$" : "") + "ChatSerializer";
        for (Player p : player) {
            try {
                Object comp = getNmsClass(nmsClass).getMethod("a", new Class[] { String.class }).invoke(null, msg + "]}]");
                Object packet = getNmsClass("PacketPlayOutChat").getConstructor(new Class[] { getNmsClass("IChatBaseComponent") }).newInstance(comp);
                Object handle = p.getClass().getMethod("getHandle", new Class[0]).invoke(p);
                Object playerConnection;
                playerConnection = handle.getClass().getField("playerConnection").get(handle);
                playerConnection.getClass().getMethod("sendPacket",new Class[] { getNmsClass("Packet") }).invoke(playerConnection, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Append text to the json message.
     * @param text to be appended
     * @return json string builder
     */
    public JsonStringBuilder append(String text) {
        return new JsonStringBuilder(this, esc(text));
    }

    private static String esc(String s) {
        return JSONObject.escape(s);
    }
    /**
     *
     * @author JustisR
     *
     */
    public static class JsonStringBuilder {

        private final JsonMessage message;
        private final String string;
        private String hover = "", click = "";

        /**
         * Settings for the json message's text
         * @param msg the original message
         * @param text the text to be appended to the message.
         */
        private JsonStringBuilder(JsonMessage msg, String text) {
            message = msg;
            string = ",{\"text\":\"" + text + "\"";
        }

        /**
         * Set the hover event's action as showing a tooltip with the given text
         * @param lore the text to be displayed in the tooltip
         * @return the json string builder to which you are applying settings
         */
        public JsonStringBuilder setHoverAsTooltip(String... lore) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lore.length; i++)
                if (i + 1 == lore.length) builder.append(lore[i]);
                else builder.append(lore[i] + "\n");
            hover = ",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + esc(builder.toString()) + "\"}";
            return this;
        }

        /**
         * Set the hover event's action as showing an achievement
         * @param ach the achievement to be displayed.
         * 	for list of achievements, visit <a href="http://minecraft.gamepedia.com/Achievements">here</a>
         * @return the json string builder to which you are applying settings
         */
        public JsonStringBuilder setHoverAsAchievement(String ach) {
            hover = ",\"hoverEvent\":{\"action\":\"show_achievement\",\"value\":\"achievement." + esc(ach) + "\"}";
            return this;
        }

        /**
         * Set the click event's action as redirecting to a URL
         * @param link to redirect to
         * @return the json string builder to which you are applying settings.
         */
        public JsonStringBuilder setClickAsURL(String link) {
            click = ",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + esc(link) + "\"}";
            return this;
        }

        /**
         * Set the click event's action as suggesting a command
         * @param cmd to suggest
         * @return the json string builder to which you are applying settings;
         */
        public JsonStringBuilder setClickAsSuggestCmd(String cmd) {
            click = ",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + esc(cmd) + "\"}";
            return this;
        }

        /**
         * Set the click event's action as executing a command
         * @param cmd
         * @return
         */
        public JsonStringBuilder setClickAsExecuteCmd(String cmd) {
            click = ",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + esc(cmd) + "\"}";
            return this;
        }

        /**
         * Finalize the appending of the text, with settings.
         * @return
         */
        public JsonMessage save() {
            message.msg += string + hover + click + "}";
            return message;
        }
    }

}
