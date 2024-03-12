package com.example.android_team4_project

import org.w3c.dom.Document
import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory

class XmlParser {

    fun parseXml(xmlData: String): RoutineData {
        // XML 파싱을 수행하는 로직
        val builderFactory = DocumentBuilderFactory.newInstance()
        val builder = builderFactory.newDocumentBuilder()
        val input = ByteArrayInputStream(xmlData.toByteArray())
        val document: Document = builder.parse(input)

        // 여기에서 Document 객체를 이용하여 XML 데이터를 파싱하고 필요한 정보를 추출
        val title = getNodeContent(document, "title")
        val selectedSpinnerItem = getNodeContent(document, "selectedSpinnerItem")
        val con1 = getNodeContent(document, "con1")
        val con2 = getNodeContent(document, "con2")
        val con3 = getNodeContent(document, "con3")
        val con4 = getNodeContent(document, "con4")
        val con5 = getNodeContent(document, "con5")
        val edm1 = getNodeContent(document, "edm1")
        val edm2 = getNodeContent(document, "edm2")
        val edm3 = getNodeContent(document, "edm3")
        val edm4 = getNodeContent(document, "edm4")
        val edm5 = getNodeContent(document, "edm5")
        val eds1 = getNodeContent(document, "eds1")
        val eds2 = getNodeContent(document, "eds2")
        val eds3 = getNodeContent(document, "eds3")
        val eds4 = getNodeContent(document, "eds4")
        val eds5 = getNodeContent(document, "eds5")

        return RoutineData(title, selectedSpinnerItem, con1, con2, con3, con4, con5, edm1, edm2, edm3, edm4, edm5, eds1, eds2, eds3, eds4, eds5)
    }

    private fun getNodeContent(document: Document, tagName: String): String {
        val nodeList = document.getElementsByTagName(tagName)
        return if (nodeList.length > 0) {
            nodeList.item(0).textContent
        } else {
            ""
        }
    }
}

