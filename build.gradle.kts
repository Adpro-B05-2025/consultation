plugins {
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("jacoco")
    java
}

jacoco {
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    dependsOn(tasks.test)
    doLast {
        val reportFile = file("$buildDir/reports/jacoco/test/jacocoTestReport.xml")
        if (reportFile.exists()) {
            val dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance()
            dbf.isValidating = false
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
            val parser = dbf.newDocumentBuilder()
            val doc = parser.parse(reportFile)
            val packages = doc.getElementsByTagName("package")

            data class Row(
                val file: String,
                val covered: Int,
                val missed: Int,
                val percent: Int,
                val missedLines: List<Int>
            )

            val rows = mutableListOf<Row>()
            for (i in 0 until packages.length) {
                val pkg = packages.item(i)
                val classes = pkg.childNodes
                for (j in 0 until classes.length) {
                    val cls = classes.item(j)
                    if (cls.nodeName == "class") {
                        val fileName = cls.attributes.getNamedItem("sourcefilename").nodeValue
                        // Only show file name
                        val displayName = fileName
                        val counters = cls.childNodes
                        var covered = 0
                        var missed = 0
                        for (k in 0 until counters.length) {
                            val counter = counters.item(k)
                            if (counter.nodeName == "counter" && counter.attributes.getNamedItem("type").nodeValue == "LINE") {
                                missed = counter.attributes.getNamedItem("missed").nodeValue.toInt()
                                covered = counter.attributes.getNamedItem("covered").nodeValue.toInt()
                            }
                        }
                        val total = covered + missed
                        val percent = if (total > 0) (covered * 100 / total) else 100
                        // Missed lines
                        val lines = cls.childNodes
                        val missedLines = mutableListOf<Int>()
                        for (l in 0 until lines.length) {
                            val line = lines.item(l)
                            if (line.nodeName == "line" && line.attributes.getNamedItem("ci").nodeValue == "0") {
                                missedLines.add(line.attributes.getNamedItem("nr").nodeValue.toInt())
                            }
                        }
                        rows.add(Row(displayName, covered, missed, percent, missedLines))
                    }
                }
            }

            // Table header
            val border = "+-------------------------------+----------+----------+----------+"
            println()
            println(" File                          |  Covered |   Missed | Coverage |")
            println(border)
            for (row in rows) {
                println(
                    String.format(
                        "| %-29s | %8d | %8d | %7d%% |",
                        row.file.take(29),
                        row.covered,
                        row.missed,
                        row.percent
                    )
                )
                if (row.missed > 0 && row.missedLines.isNotEmpty()) {
                    println(
                        String.format(
                            "| %-29s | %-36s |",
                            "  Missed lines:",
                            row.missedLines.joinToString(", ")
                        )
                    )
                }
            }
            println(border)
            println()
        }
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    
    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    
    // Database
    runtimeOnly("com.h2database:h2")
    
    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
