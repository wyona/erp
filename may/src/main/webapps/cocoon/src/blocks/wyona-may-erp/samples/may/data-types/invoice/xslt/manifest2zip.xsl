<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 xmlns:zip="http://apache.org/cocoon/zip-archive/1.0"
 xmlns:manifest="urn:oasis:names:tc:opendocument:xmlns:manifest:1.0" version="1.0">
 
     <xsl:param name="invoicepath"></xsl:param>
 
     <xsl:template match="manifest:manifest">
         <zip:archive>
             <xsl:apply-templates/>
             <zip:entry name="META-INF/manifest.xml" src="cocoon:/odtcontents/META-INF/manifest.xml"/>
             <!--zip:entry name="mimetype" src="cocoon:/odtcontents/mimetype"/-->
         </zip:archive>
     </xsl:template>
     
     <xsl:template match="manifest:file-entry[substring(@manifest:full-path, string-length(@manifest:full-path), string-length(@manifest:full-path)) != '/']">
         <xsl:choose>
             <xsl:when test="@manifest:full-path != 'content.xml'">
                 <zip:entry name="{@manifest:full-path}" src="cocoon:/odtcontents/{@manifest:full-path}"/>
             </xsl:when>
             <xsl:otherwise>
                 <zip:entry name="{@manifest:full-path}" src="cocoon:/odtcontents-manipulate/{@manifest:full-path}|{$invoicepath}"/>
             </xsl:otherwise>
         </xsl:choose>
     </xsl:template>

</xsl:stylesheet>