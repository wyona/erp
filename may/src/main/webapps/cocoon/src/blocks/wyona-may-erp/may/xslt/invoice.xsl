<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:xhtml="http://www.w3.org/1999/xhtml"
  xmlns:invoice="http://www.wyona.org/erp/1.0"
  >

<xsl:param name="invoice-id" select="'null'"/>

<xsl:template match="invoice:invoice">
<html>
<body>
<h1>Invoice</h1>
<a href="?cocoon-view=generator-view">XML view</a>
<p>
Customer ID: <a href="../customers/{invoice:customer/@id}.html"><xsl:value-of select="invoice:customer/@id"/></a>
<br/>
Status: <xsl:value-of select="invoice:status"/>
<xsl:if test="invoice:status = 'paid'">
(Payment received: <xsl:value-of select="invoice:payment-receipt/@date"/>)
</xsl:if>
<br/>
<b>TOTAL</b>: <xsl:value-of select="invoice:total-amount/@currency"/> <xsl:value-of select="invoice:total-amount"/>

<br/><br/>
Description: <xsl:value-of select="invoice:description"/>
<br/>
<h2>Resources</h2>
PDF: <a href="{$invoice-id}/{invoice:pdf/@href}"><xsl:value-of select="invoice:pdf/@href"/></a>
<br/>
SXC: <a href="{$invoice-id}/{invoice:sxc/@href}"><xsl:value-of select="invoice:sxc/@href"/></a>
<br/>
ODT: <a href="{$invoice-id}/{invoice:odt/@href}"><xsl:value-of select="invoice:odt/@href"/></a>
</p>
</body>
</html>
</xsl:template>

<!--
<xsl:template match="@*|node()">
  <xsl:copy><xsl:apply-templates select="@*|node()"/></xsl:copy>
</xsl:template>
-->

</xsl:stylesheet>
