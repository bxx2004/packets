# packets
在我的世界客户端与服务器端进行通讯的工具，需自行在各平台实现. 例：on-taboolib 的PacketSender实现


class BukkitSender(val player: Player) : PacketSender {
    override fun sendPacket(channel: Channel, context: PacketContext,breakPoint: Boolean) {
        if (!breakPoint){
            val bytes: ByteArray = context.serializable()
            val buf: ByteBuf = Unpooled.wrappedBuffer(bytes)
            player.sendPluginMessage(bukkitPlugin, channel.toString(), buf.array())
        }else{
            var sendard = PacketContextBreakPointSender(context)
            sendard.sendTo(channel,this)
        }
    }
    override fun uuid(): UUID {
        return player.uniqueId
    }

    override fun name(): String {
        return player.name
    }
}
