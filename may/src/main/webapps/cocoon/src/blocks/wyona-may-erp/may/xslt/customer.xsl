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

<xsl:apply-templates select="com:web"/>

<xsl:apply-templates select="com:invoices"/>

<xsl:apply-templates select="com:offers"/>

<xsl:apply-templates select="com:persons"/>

<xsl:apply-templates select="com:projects"/>
</p>
</body>
</html>
</xsl:template>

<xsl:template match="com:invoices">
<h4>Invoices (Debtors)</h4> 
Sort by <a href="">status</a>, <a href="">date</a>, ...
<ul>
<xsl:for-each select="com:invoice">
<li>
  <xsl:choose>
    <xsl:when test="@href">
      <a href="{$company-name}/{@href}.html"><xsl:value-of select="@id"/></a>
    </xsl:when>
    <xsl:otherwise>
      <a href="../invoices/invoice-{@id}.html"><xsl:value-of select="@id"/></a>
    </xsl:otherwise>
  </xsl:choose>
</li>
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

<xsl:template match="com:persons">
<h4>Contact</h4>
<ul>
<xsl:for-each select="com:person">
<li><a href="../../people/{@id}.html"><xsl:value-of select="@id"/></a></li>
</xsl:for-each>
</ul>
</xsl:template>

<xsl:template match="com:projects">
<h4>Projects</h4>
<ul>
<xsl:for-each select="com:project">
<li><a href="../../projects/{@id}.html"><xsl:value-of select="@id"/></a></li>
</xsl:for-each>
</ul>
</xsl:template>

<xsl:template match="com:web">
<br/>
Web: <a href="{.}" target="_blank"><xsl:value-of select="."/></a>
</xsl:template>

</xsl:stylesheet>
