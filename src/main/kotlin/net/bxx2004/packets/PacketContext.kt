package net.bxx2004.packets


import net.bxx2004.packets.identifier.ContextIdentifier
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

interface PacketContext:Serializable{
    fun write(identifier:ContextIdentifier,value:Any,override:Boolean = false):PacketContext
    fun <T>read(identifier: ContextIdentifier):T?
    fun forEach(func: (identifier:ContextIdentifier,value:Any) -> Unit)
    fun serializable(): ByteArray {
        val out = ByteArrayOutputStream()
        val oos = ObjectOutputStream(out)
        oos.writeObject(this)
        val bytes = out.toByteArray()
        return bytes
    }
    fun size():Int{
        return serializable().size
    }
    fun type():PacketType{
        return read<PacketType>(ContextIdentifier(ContextType.ENUM,"packet_type"))!!
    }
    companion object{
        const val serialVersionUID = 42L
        @JvmStatic
        fun unSerializable(packetContext: ByteArray): PacketContext {
            val ooo = ByteArrayInputStream(packetContext)
            val ois = ObjectInputStream(ooo)
            val person = ois.readObject() as PacketContext
            return person
        }
        @JvmStatic
        fun empty(type:PacketType? = null):PacketContext{
            return PacketContextImpl(type)
        }
    }
}