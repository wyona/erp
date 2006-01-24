<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:invoice="http://www.wyona.org/erp/1.0"
    xmlns="http://www.wyona.org/erp/1.0" version="1.0">
    <xsl:template match="invoice:invoice">
        <invoice id="{invoice:content/@id}">
            <xsl:apply-templates select="invoice:content/*" mode="copy"/>
        </invoice>
    </xsl:template>
    <xsl:template match="@*|node()" priority="-1" mode="copy">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" mode="copy"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="*"></xsl:template>
</xsl:stylesheet>
