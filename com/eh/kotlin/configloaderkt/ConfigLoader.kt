package com.eh.kotlin.configloaderkt

import java.io.*
import java.util.Properties

object ConfigLoader {

//    @JvmStatic
//    fun main(args: Array<String>) {
//        if (args.size > 0) {
//            val path = args[0]
//            val config = ConfigLoader[path]
//            println(config.readProp())
//            println(config.setProp("new", "144").save())
//        }
//    }

    private fun fromJarPath(fname: String): String {
        return "${File(
            ConfigLoader::class.java.protectionDomain.codeSource.location
                .toURI()
        ).parent}${File.separator}$fname"
    }

    fun initPropFile(keys: Array<String>, path: String = fromJarPath("config.properties")) {
        val config = Config(path)
        for (key in keys) {
            config.setProp(key, "")
        }
        config.save()
    }

    operator fun get(path: String): Config {
        return Config(path)
    }

    class Config(private val path: String) {
        private var prop: Properties = Properties()

        init {
            prop = loadProp(path)
        }

        fun readProp(): Properties {
            return this.prop
        }

        fun getProp(key: String): String {
            return this.prop.getProperty(key)
        }

        fun setProp(key: String, value: String): Config {
            this.prop.setProperty(key, value)
            return this
        }

        fun save(): Boolean {
            try {
                FileOutputStream(path).use { output ->
                    prop.store(output, null)
                    println(prop)
                    return true
                }
            } catch (io: IOException) {
                io.printStackTrace()
                return false
            }

        }

        private fun loadProp(path: String): Properties {
            //            try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(path)) {
            try {
                FileInputStream(path).use { input ->
                    val prop = Properties()
                    prop.load(input)
                    return prop
                }
            } catch (ex: IOException) {
                ex.printStackTrace()
                val f = File(path)
                f.mkdirs()
                f.createNewFile()
                return Properties()
            }

        }


    }

}
