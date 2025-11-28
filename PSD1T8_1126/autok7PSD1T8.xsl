<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
    
    <xsl:template match="/">
        <autok_rendezett>
            <xsl:for-each select="autok/auto">
                <xsl:sort select="ar" data-type="number" order="ascending"/>
                <auto>
                    <rendszam><xsl:value-of select="@rsz"/></rendszam>
                    <ar><xsl:value-of select="ar"/></ar>
                </auto>
            </xsl:for-each>
        </autok_rendezett>
    </xsl:template>
</xsl:stylesheet>
