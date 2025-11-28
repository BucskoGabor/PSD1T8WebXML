<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    
    <xsl:key name="tipus-key" match="auto" use="tipus"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>Autótípusok és példányszámuk</title>
                <style>
                    table {
                        border-collapse: collapse;
                        width: 60%;
                        margin: 20px auto;
                    }
                    th, td {
                        border: 1px solid black;
                        padding: 8px;
                        text-align: left;
                    }
                    th {
                        background-color: #9C27B0;
                        color: white;
                    }
                    tr:nth-child(even) {
                        background-color: #f2f2f2;
                    }
                    h1 {
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <h1>Autótípusok és példányaik darabszáma példányszám szerint csökkenő sorrendben</h1>
                <table>
                    <tr>
                        <th>Típus</th>
                        <th>Darabszám</th>
                    </tr>
                    <xsl:for-each select="autok/auto[generate-id() = generate-id(key('tipus-key', tipus)[1])]">
                        <xsl:sort select="count(key('tipus-key', tipus))" data-type="number" order="descending"/>
                        <tr>
                            <td><xsl:value-of select="tipus"/></td>
                            <td><xsl:value-of select="count(key('tipus-key', tipus))"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
