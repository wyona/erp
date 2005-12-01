<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:xhtml="http://www.w3.org/1999/xhtml"
  xmlns:may="http://www.wyona.org/erp/task/1.0"
  >

<xsl:param name="task-id" select="'null'"/>

<xsl:template match="may:task">
<html>
<body>
<h1>Task</h1>
<a href="?cocoon-view=generator-view">XML view</a>

<xsl:apply-templates select="may:description"/>
<xsl:apply-templates select="may:project"/>
<xsl:apply-templates select="may:invoice"/>
<xsl:apply-templates select="may:estimate"/>
<xsl:apply-templates select="may:status"/>
</body>
</html>
</xsl:template>

<xsl:template match="may:description">
<p>
Description: <xsl:value-of select="."/>
</p>
</xsl:template>

<xsl:template match="may:project">
<p>
Project: <a href="../../projects/{@id}.html"><xsl:value-of select="@id"/></a>
</p>
</xsl:template>

<xsl:template match="may:invoice">
<p>
Invoice: <a href="../../invoices/invoice-{@id}.html"><xsl:value-of select="@id"/></a>
</p>
</xsl:template>

<xsl:template match="may:estimate">
<p>
Estimate: <xsl:value-of select="@currency"/> <xsl:value-of select="@amount"/>
</p>
</xsl:template>

<xsl:template match="may:status">
<p>
Status: <xsl:value-of select="@id"/>
</p>
</xsl:template>

</xsl:stylesheet>
