<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:erp="http://www.wyona.org/erp/1.0"
    version="1.0">
    <xsl:template match="erp:invoice">
        <invoice id="{erp:content/@id}">
            <xsl:apply-templates select="erp:content/*" mode="copy"/>
        </invoice>
    </xsl:template>
    <xsl:template match="*" mode="copy">
        <xsl:element name="{local-name()}">
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates mode="copy"/>
        </xsl:element>
    </xsl:template>
    <!--xsl:template match="@*|node()" priority="-1" mode="copy">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" mode="copy"/>
        </xsl:copy>
    </xsl:template-->
    <xsl:template match="*"/>
</xsl:stylesheet>
