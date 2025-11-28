<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    
    <xsl:key name="varos-key" match="auto" use="tulaj/varos"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>Városonkénti autók darabszáma és összára</title>
                <style>
                    table {
                        border-collapse: collapse;
                        width: 70%;
                        margin: 20px auto;
                    }
                    th, td {
                        border: 1px solid black;
                        padding: 8px;
                        text-align: left;
                    }
                    th {
                        background-color: #FF9800;
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
                <h1>Városonként mennyi az ottani autók darabszáma és összára</h1>
                <table>
                    <tr>
                        <th>Város</th>
                        <th>Darabszám</th>
                        <th>Összár</th>
                    </tr>
                    <xsl:for-each select="autok/auto[generate-id() = generate-id(key('varos-key', tulaj/varos)[1])]">
                        <tr>
                            <td><xsl:value-of select="tulaj/varos"/></td>
                            <td><xsl:value-of select="count(key('varos-key', tulaj/varos))"/></td>
                            <td><xsl:value-of select="sum(key('varos-key', tulaj/varos)/ar)"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
