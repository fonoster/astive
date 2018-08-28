<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:html="http://www.w3.org/1999/XSL/Format"
                version="1.0">

    <xsl:import href="urn:docbkx:stylesheet"/>

	<xsl:template name="user.head.content">
		<link xmlns="http://www.w3.org/1999/xhtml" rel="stylesheet" type="text/css" href="http://astivetoolkit.org/theme/Bootstrap/custom.css" />
		<link xmlns="http://www.w3.org/1999/xhtml" rel="stylesheet" type="text/css" href="book.css" />
 		<script type="text/javascript">

		  var _gaq = _gaq || [];
		  _gaq.push(['_setAccount', 'UA-28086624-1']);
		  _gaq.push(['_setDomainName', 'astivetoolkit.org']);
		  _gaq.push(['_trackPageview']);

		  (function() {
		    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
		    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
		    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
		  })();

		</script>
	</xsl:template>

	<xsl:param name="local.l10n.xml" select="document('')" />

	<l:i18n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0">
		<l:l10n language="en">
			<l:context name="title-numbered">
				<l:template name="chapter" text="%t" />
			</l:context>
		</l:l10n>
	</l:i18n>

    <!-- Show only Sections up to level 0 in the TOCs -->
	<xsl:param name="toc.section.depth">0</xsl:param>

    <!--###################################################
                     HTML Settings
    ################################################### -->
    
    <xsl:param name="chunk.section.depth">'5'</xsl:param>
    <xsl:param name="use.id.as.filename">'1'</xsl:param>
  <xsl:param name="tablecolumns.extension">0</xsl:param>
    <xsl:param name="graphicsize.extension">0</xsl:param>
    <xsl:param name="ignore.image.scaling">1</xsl:param>
    <!--###################################################
                      Table Of Contents
    ################################################### -->
    <!-- Generate the TOCs for named components only -->
    <xsl:param name="generate.toc">
        book toc
        qandaset toc
    </xsl:param>
    <!--###################################################
                         Labels
    ################################################### -->
    <!-- Label Chapters and Sections (numbering) -->
    <xsl:param name="chapter.autolabel">1</xsl:param>
    <xsl:param name="section.autolabel" select="1"/>
    <xsl:param name="section.label.includes.component.label" select="1"/>
    <!--###################################################
                         Callouts
    ################################################### -->
    <!-- Place callout marks at this column in annotated areas -->
    <xsl:param name="callout.graphics">1</xsl:param>
    <xsl:param name="callout.defaultcolumn">90</xsl:param>
    
    <!--###################################################
                      Admonitions
   ################################################### -->

    <!-- Use nice graphics for admonitions -->
    <xsl:param name="admon.graphics">1</xsl:param>
  <xsl:param name="admon.graphics.path">images/</xsl:param>    
    <!--###################################################
                          Misc
    ################################################### -->
    <!-- Placement of titles -->
    <xsl:param name="formal.title.placement">
        figure after
        example before
        equation before
        table before
        procedure before
    </xsl:param>
    <xsl:template match="author" mode="titlepage.mode">
        <xsl:if test="name(preceding-sibling::*[1]) = 'author'">
            <xsl:text>, </xsl:text>
        </xsl:if>
        <span class="{name(.)}">
            <xsl:call-template name="person.name"/> 
            (<xsl:value-of select="affiliation"/>)
            <xsl:apply-templates mode="titlepage.mode" select="./contrib"/>
            <!--
            <xsl:apply-templates mode="titlepage.mode" select="./affiliation"/>
            -->
        </span>
    </xsl:template>
    <xsl:template match="authorgroup" mode="titlepage.mode">
        <div class="{name(.)}">
            <h2>Authors</h2>
            <p/>
            <xsl:apply-templates mode="titlepage.mode"/>
        </div>
    </xsl:template>
    <!--###################################################
                     Headers and Footers
    ################################################### -->
	<xsl:template name="user.header.navigation">
		<div class="topbar">
			<div class="fill">
				<div class="container">
					<a class="brand" href="#"><img src="http://astivetoolkit.org/theme/Bootstrap/images/atk_logo.png" /></a>
					<ul class="nav">
						<li class="index">
							<a href="http://astivetoolkit.org/"
								title="A toolkit and server to create awesome stuff with Asterisk and Java.">Home</a>
						</li>
						<li class="current active documentation">
							<a href="http://astivetoolkit.org/documentation/" title="Documentation">Documentation
							</a>
						</li>
						<li class="downloads">
							<a href="http://astivetoolkit.org/downloads/" title="Downloads">Downloads
							</a>
						</li>
						<li class="documentation tools">
							<a href="http://astivetoolkit.org/documentation/tools/" title="Tools">Tools</a>
						</li>
						<li class="documentation tools">
							<a href="http://astivetoolkit.org/blog" title="News">Blog
							</a>
						</li>
					</ul>
					<a class="github" href="https://github.com/PhonyTive/astivetoolkit">
						<img
							src="http://astivetoolkit.org/theme/Bootstrap/images/github-hover.png"/>
					</a>
				</div>
			</div>
		</div>


		<div class="sidebar">
			<div class="sidebar-title">
				<p>Contents</p>
			</div>
			<ul>
				<li><a href="Guide.xhtml">Preface</a></li>
				<li><a href="_getting_started.xhtml">Getting Started</a></li>
				<li><a href="_helloworld_tutorial.xhtml">HelloWorld Tutorial</a></li>
				<li><a href="_installation.xhtml">Installation</a></li>
				<li><a href="_atk_overview.xhtml">ATK Overview</a></li>
				<li><a href="_setting_up_asterisk.xhtml">Setting Up Asterisk</a></li>
				<li><a href="_using_the_sdk.xhtml">Using The SDK</a></li>
				<li><a href="_astivlets.xhtml">Astivlets</a></li>
				<li><a href="_the_agi_api_2.xhtml">The AGI API</a></li>
				<li><a href="_the_ami_api_2.xhtml">The AMI API</a></li>
				<li><a href="_menu_and_menuitems.xhtml">Menu and MenuItems</a></li>
				<li><a href="_astive_server.xhtml">Astive Server</a></li>
				<li><a href="_creative_commons_license.xhtml">Creative Commons License</a></li>
				<li><a href="_bibliography.xhtml">Bibliography</a></li>
			</ul>
		</div>
    </xsl:template>
    <!-- no other header navigation (prev, next, etc.) -->
    <xsl:template name="header.navigation">
    </xsl:template>
    <xsl:param name="navig.showtitles">1</xsl:param>
    <xsl:template name="footer.navigation">
        <xsl:param name="prev" select="/foo"/>
        <xsl:param name="next" select="/foo"/>
        <xsl:param name="nav.context"/>
        <xsl:variable name="home" select="/*[1]"/>
        <xsl:variable name="up" select="parent::*"/>
        <xsl:variable name="row1" select="count($prev) &gt; 0
                                        or count($up) &gt; 0
                                        or count($next) &gt; 0"/>
        <xsl:variable name="row2" select="($prev and $navig.showtitles != 0)
                                        or (generate-id($home) != generate-id(.)
                                            or $nav.context = 'toc')
                                        or ($chunk.tocs.and.lots != 0
                                            and $nav.context != 'toc')
                                        or ($next and $navig.showtitles != 0)"/>
        <xsl:if test="$suppress.navigation = '0' and $suppress.footer.navigation = '0'">
            <div class="navfooter">
                <xsl:if test="$footer.rule != 0">
                    <!--<hr/>-->
                </xsl:if>
                <xsl:if test="$row1 or $row2">
                    <table width="100%" summary="Navigation footer">
                        <xsl:if test="$row1">
                            <tr>
                                <td width="40%" align="left">
                                    <xsl:if test="count($prev)>0">
                                        <a accesskey="p">
                                            <xsl:attribute name="href">
                                                <xsl:call-template name="href.target">
                                                    <xsl:with-param name="object" select="$prev"/>
                                                </xsl:call-template>
                                            </xsl:attribute>
                                            <xsl:call-template name="navig.content">
                                                <xsl:with-param name="direction" select="'prev'"/>
                                            </xsl:call-template>
                                        </a>
                                    </xsl:if>
                                    <xsl:text>&#160;</xsl:text>
                                </td>

                                <td width="20%" align="center">
                                    <xsl:choose>
                                        <xsl:when test="$home != . or $nav.context = 'toc'">
                                            <a accesskey="h">
                                                <xsl:attribute name="href">
                                                    <xsl:call-template name="href.target">
                                                        <xsl:with-param name="object" select="$home"/>
                                                    </xsl:call-template>
                                                </xsl:attribute>Index
                                                <!--<xsl:call-template name="navig.content">
						    Index
                                                    <xsl:with-param name="direction" select="'home'"/>
                                                </xsl:call-template>-->
                                            </a>
                                            <xsl:if test="$chunk.tocs.and.lots != 0 and $nav.context != 'toc'">
                                                <xsl:text>&#160;|&#160;</xsl:text>
                                            </xsl:if>
                                        </xsl:when>
                                        <xsl:otherwise>&#160;</xsl:otherwise>
                                    </xsl:choose>
                                    <xsl:if test="$chunk.tocs.and.lots != 0 and $nav.context != 'toc'">
                                        <a accesskey="t">
                                            <xsl:attribute name="href">
                                                <xsl:apply-templates select="/*[1]" mode="recursive-chunk-filename">
                                                    <xsl:with-param name="recursive" select="true()"/>
                                                </xsl:apply-templates>
                                                <xsl:text>-toc</xsl:text>
                                                <xsl:value-of select="$html.ext"/>
                                            </xsl:attribute>
                                            <xsl:call-template name="gentext">
                                                <xsl:with-param name="key" select="'nav-toc'"/>
                                            </xsl:call-template>
                                        </a>
                                    </xsl:if>
                                </td>
                                <td width="40%" align="right">
                                    <xsl:text>&#160;</xsl:text>
                                    <xsl:if test="count($next)>0">
                                        <a accesskey="n">
                                            <xsl:attribute name="href">
                                                <xsl:call-template name="href.target">
                                                    <xsl:with-param name="object" select="$next"/>
                                                </xsl:call-template>
                                            </xsl:attribute>
                                            <xsl:call-template name="navig.content">
                                                <xsl:with-param name="direction" select="'next'"/>
                                            </xsl:call-template>
                                        </a>
                                    </xsl:if>
                                </td>
                            </tr>
                        </xsl:if>
                        <xsl:if test="$row2">
                            <tr>
                                <td width="40%" align="left" valign="top">
                                    <xsl:if test="$navig.showtitles != 0">
                                        <xsl:apply-templates select="$prev" mode="object.title.markup"/>
                                    </xsl:if>
                                    <xsl:text>&#160;</xsl:text>
                                </td>
                                <td width="20%" align="center">
                                    <span style="color:white;font-size:85%;">
                                        <a href="http://phonytive.com" title="PhonyTive LLC">PhonyTive LLC</a>
                                    </span>
                                </td>
                                <td width="40%" align="right" valign="top">
                                    <xsl:text>&#160;</xsl:text>
                                    <xsl:if test="$navig.showtitles != 0">
                                        <xsl:apply-templates select="$next" mode="object.title.markup"/>
                                    </xsl:if>
                                </td>
                            </tr>
                        </xsl:if>
                    </table>
                </xsl:if>
            </div>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
