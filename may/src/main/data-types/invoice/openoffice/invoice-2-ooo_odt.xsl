<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0"
    xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
    xmlns:style="urn:oasis:names:tc:opendocument:xmlns:style:1.0"
    xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
    xmlns:table="urn:oasis:names:tc:opendocument:xmlns:table:1.0"
    xmlns:draw="urn:oasis:names:tc:opendocument:xmlns:drawing:1.0"
    xmlns:xlink="http://www.w3.org/1999/xlink"
    xmlns:dc="http://purl.org/dc/elements/1.1/"
    xmlns:meta="urn:oasis:names:tc:opendocument:xmlns:meta:1.0"
    xmlns:number="urn:oasis:names:tc:opendocument:xmlns:datastyle:1.0"
    xmlns:svg="urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0"
    xmlns:chart="urn:oasis:names:tc:opendocument:xmlns:chart:1.0"
    xmlns:dr3d="urn:oasis:names:tc:opendocument:xmlns:dr3d:1.0"
    xmlns:math="http://www.w3.org/1998/Math/MathML"
    xmlns:form="urn:oasis:names:tc:opendocument:xmlns:form:1.0"
    xmlns:script="urn:oasis:names:tc:opendocument:xmlns:script:1.0"
    xmlns:config="urn:oasis:names:tc:opendocument:xmlns:config:1.0"
    xmlns:invoice="http://www.wyona.org/erp/1.0"
>

    <xsl:output method="xml" indent="no" encoding="UTF-8" version="1.0"/>

    <xsl:template match="invoice:invoice">
        <office:document office:mimetype="application/x-vnd.oasis.openoffice.text" office:version="1.0">
            <office:font-face-decls>
                <style:font-face
                    xmlns:svg="urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0"
                    style:name="Arial" svg:font-family="Arial"
                    style:font-family-generic="roman" style:font-pitch="variable"/>
            </office:font-face-decls>

            <office:styles>
                <style:default-style style:family="paragraph">
                    <style:paragraph-properties style:tab-stop-distance="1.251cm"/>
                    <style:text-properties
                        xmlns:fo="urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0"
                        style:use-window-font-color="true" style:font-name=""
                        style:font-name-asian="" style:font-name-complex=""
                        fo:font-size="12pt" style:font-size-asian="12pt"
                        fo:language="en" fo:country="en"/>
                </style:default-style>

                <style:style style:name="PCustomer" style:display-name="Customer ID" style:family="paragraph" style:parent-style-name="Standard">
                    <style:paragraph-properties fo:background-color="#dddddd">
                        <style:tab-stops/>
                        <style:background-image/>
                    </style:paragraph-properties>
                    <style:text-properties style:font-name="Arial" fo:font-size="14pt"/>
                </style:style>

                <style:style style:name="TCustomerID" style:family="text">
                    <style:text-properties style:font-name="Arial" fo:font-size="18pt" fo:color="#ff0000"/>
                </style:style>

                <style:style style:name="PInvoiceStatus" style:display-name="Invoice Status" style:family="paragraph" style:parent-style-name="Standard">
                    <style:paragraph-properties fo:background-color="#ccccff">
                        <style:tab-stops/>
                        <style:background-image/>
                    </style:paragraph-properties>
                    <style:text-properties style:font-name="Arial" fo:font-size="16pt"/>
                </style:style>

                <style:style style:name="TInvoiceStatus" style:family="text">
                    <style:text-properties style:font-name="Arial" fo:font-size="18pt" fo:font-weight="bold"/>
                </style:style>

            </office:styles>

            <office:body>
                <office:text>
                    <xsl:apply-templates select="invoice:customer"/>
                    <xsl:apply-templates select="invoice:status"/>
<!--
                    <xsl:apply-templates select="content/*"/>
-->
                </office:text>
            </office:body>
        </office:document>
    </xsl:template>

    <xsl:template match="invoice:customer">
        <text:p text:style-name="PCustomer">
            Customer: <text:span text:style-name="TCustomerID"><xsl:value-of select="@id"/></text:span>
        </text:p>
    </xsl:template>

    <xsl:template match="invoice:status">
        <text:p text:style-name="PInvoiceStatus">
            Status: <text:span text:style-name="TInvoiceStatus"><xsl:value-of select="."/></text:span>
        </text:p>
    </xsl:template>
</xsl:stylesheet>
