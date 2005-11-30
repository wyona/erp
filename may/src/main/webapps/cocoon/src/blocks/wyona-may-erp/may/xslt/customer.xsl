<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:xhtml="http://www.w3.org/1999/xhtml"
  xmlns:com="http://www.wyona.org/erp/1.0"
  >

<xsl:param name="company-name" select="'null'"/>

<xsl:template match="com:company">
<html>
<body>
<h1>Company</h1>
<a href="?cocoon-view=generator-view">XML view</a>

<p>
Name: <xsl:value-of select="com:name"/>
<br/>
ID: <xsl:value-of select="com:id"/>
<br/>

<xsl:apply-templates select="com:invoices"/>

<xsl:apply-templates select="com:offers"/>
</p>
</body>
</html>
</xsl:template>

<xsl:template match="com:invoices">
<h4>Invoices</h4>
<ul>
<xsl:for-each select="com:invoice">
<li><a href="../../invoices/invoice-{@id}.html"><xsl:value-of select="@id"/></a></li>
</xsl:for-each>
</ul>
</xsl:template>

<xsl:template match="com:offers">
<h4>Offers</h4>
<ul>
<xsl:for-each select="com:offer">
<li><a href="../../offers/offer-{@id}.html"><xsl:value-of select="@id"/></a></li>
</xsl:for-each>
</ul>
</xsl:template>

</xsl:stylesheet>
