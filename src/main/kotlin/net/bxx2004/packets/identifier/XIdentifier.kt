package net.bxx2004.packets.identifier

import java.io.Serializable

open class XIdentifier(var namespace:String, var path:String) :Serializable{
    override fun toString(): String {
        return "$namespace:$path"
    }
    fun toClient():Any{
        return "Not impl"
    }
    companion object{
        @JvmStatic
        fun of(o:String): XIdentifier {
            return XIdentifier(o.split(":")[0],o.split(":")[1])
        }
        @JvmStatic
        fun of(namespace:String,path:String): XIdentifier {
            return XIdentifier(namespace, path)
        }
    }

    override fun equals(other: Any?): Boolean {
        return this.toString() == other.toString()
    }
}