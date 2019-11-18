package com.fleshgrinder.commons

import java.io.OutputStreamWriter
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

fun <T> String.map(transform: String.() -> T): List<T> =
    split('\t').asSequence().drop(1).map { it.trim().transform() }.toList()

fun <T> List<String>.map(line: Int, transform: String.() -> T): List<T> =
    this[line - 1].map(transform)

fun List<String>.map(line: Int): List<String> =
    map(line) { this }

fun main() {
    val lines = Files.readString(
        Paths.get("/Users/richardfussenegger/Documents/Projects/github.com/Fleshgrinder/iban-data/registry.txt"),
        Charset.forName("WINDOWS-1250")
    ).lines()

    val countryCodes = lines.map(3)
    val sepaCountries = lines.map(5) { equals("yes", ignoreCase = true) }
    val bbanStructures = lines.map(9)
    // TODO what does !n mean?
    val bbanLengths = lines.map(10) { substringBefore('!').toInt() }
    val bankIdPositions = lines.map(11) { if (this == "N/A") null else trim('"').split('-').let { it[0].toInt() to it[1].toInt() } }
    val bankIdPatterns = lines.map(13)
//    val branchIdPositions = lines.map(14) { split('-').let { it[0].toInt() to it[1].toInt() } }
    val branchIdPatterns = lines.map(15)
    val ibanStructures = lines.map(16)
//    val ibanLengths = lines.map(17, String::toInt)

    println(countryCodes)
    println(sepaCountries)
    println(bbanStructures)
    println(bbanLengths)
    println(bankIdPatterns)
    println(bankIdPositions)
//    println(branchIdPositions)
    println(branchIdPatterns)
    println(ibanStructures)
//    println(ibanLengths)

    // OutputStreamWriter(
    //     FileOutputStream(
    //         "/Users/richardfussenegger/Documents/Projects/github.com/Fleshgrinder/kotlin-iban/build/registry.json"
    //     )
    // ).use {
    //     it.write('{')
    //
    //     // region country
    //
    //     // endregion
    //
    //     it.write('}')
    // }
}

fun OutputStreamWriter.write(b: Byte) =
    write(b.toInt())

fun OutputStreamWriter.write(c: Char) =
    write(c.toInt())

class IbanStruct(
    val countryCode: String,        // 3
    val sepa: Boolean,              // 5
    val bbanStructure: String,      // 9
    val bbanLen: Int,               // 10
    val bankIdPos: IntRange,        // 11 + 12 (!!!!)
    val bankIdPattern: String,      // 13
    val branchIdPos: IntRange,      // 14
    val branchIdPattern: String,    // 15
    val ibanStructure: String,      // 16
    val ibanLen: Int                // 17
)
