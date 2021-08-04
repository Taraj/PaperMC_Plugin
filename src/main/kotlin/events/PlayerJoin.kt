package events
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
class PlayerJoin : Listener {
    @EventHandler
    fun playerJoin(event: PlayerJoinEvent) {
        val player: Player = event.player
        event.joinMessage(Component.text("Hello ").append(player.displayName()).append(Component.text("!!!")))
    }
}