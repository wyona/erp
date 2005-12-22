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
Customer ID: <a href="../../../{invoice:customer/@id}.html"><xsl:value-of select="invoice:customer/@id"/></a>
<br/>
Type: <xsl:value-of select="invoice:type"/>
<br/>
Status: <xsl:value-of select="invoice:status"/>
<xsl:if test="invoice:status = 'paid'">
(Payment received: <xsl:value-of select="invoice:payment-receipt/@date"/>)
</xsl:if>
<br/>
<b>TOTAL</b> (exclusive VAT): <xsl:value-of select="invoice:total-amount/@currency"/><xsl:text> </xsl:text><xsl:value-of select="invoice:total-amount"/>

<br/><br/>
Description: <xsl:value-of select="invoice:description"/>
<br/>
<h4>Resources</h4>
<xsl:apply-templates select="invoice:pdf"/>
<xsl:apply-templates select="invoice:sxc"/>
<xsl:apply-templates select="invoice:odt"/>

<xsl:apply-templates select="invoice:tasks"/>
</p>
</body>
</html>
</xsl:template>

<xsl:template match="invoice:odt">
<br/>
ODT (OOo2): <a href="{$invoice-id}/{@href}"><xsl:value-of select="@href"/></a>
</xsl:template>

<xsl:template match="invoice:sxc">
<br/>
SXC (OOo): <a href="{$invoice-id}/{@href}"><xsl:value-of select="@href"/></a>
</xsl:template>

<xsl:template match="invoice:pdf">
<br/>
PDF: <a href="{$invoice-id}/{@href}"><xsl:value-of select="@href"/></a>
</xsl:template>

<xsl:template match="invoice:tasks">
<h4>Tasks</h4>
<ul>
<xsl:for-each select="invoice:task">
<li><a href="../../../../tasks/task-{@id}.html"><xsl:value-of select="@id"/></a></li>
</xsl:for-each>
</ul>
</xsl:template>


</xsl:stylesheet>
