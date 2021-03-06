package com.stannis.parser.fileHandler

import mu.KotlinLogging
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object DirReader {

        lateinit var folder: String
        private var newFolderPath: String? = null

        fun getAllFilesInResources(finalPath: String, test: (input: Path) -> Boolean): ArrayList<String> {
            folder = finalPath
            OperatingSystem.getOPSystem()
            return getFilesFromDir(finalPath, test)
        }

        private fun getFilesFromDir(finalPath: String, test: (input: Path) -> Boolean) :ArrayList<String> {
            val list = ArrayList<String>()
            val resourcesPath = Paths.get(finalPath)
            Files.walk(resourcesPath)
                .filter { item ->
                    test(item)
                }
                .forEach { item -> list.add(item.toString()) }
            return list
        }

        fun makedir(path: String): String {
            var data = folder.split(OperatingSystem.getSeparator())
            val last = data.last()
            data = data.dropLast(1)
            val x = data.joinToString(OperatingSystem.getSeparator()) + OperatingSystem.getSeparator() + "result" + OperatingSystem.getSeparator() + last
            newFolderPath = x
            val pathNew = x + path
            val dir = File(pathNew.split(OperatingSystem.getSeparator()).dropLast(1).joinToString(OperatingSystem.getSeparator()))
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return x
        }

        fun createfile(p: String, joinToString: String?): File? {
            if (newFolderPath == null) {
                newFolderPath = joinToString
            }
            val file = File(newFolderPath + OperatingSystem.getSeparator() + p)
            val dir = newFolderPath?.let { File(it) }
            try {
                if (dir != null) {
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }
                }
                if (file.createNewFile()) {
                    val logger = KotlinLogging.logger {}
                    logger.info { "File created!" }
                } else {
                    val logger = KotlinLogging.logger {}
                    logger.info { "File alredy exists" }
                    return null
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return file
        }

}