// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.13/esri/copyright.txt for details.
//>>built
require({cache:{"url:esri/dijit/metadata/types/gemini/gmd/extent/templates/GeographicDescription.html":'\x3cdiv data-dojo-attach-point\x3d"containerNode"\x3e\t\r\n\r\n\t\x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/iso/ObjectReference"\r\n\t\tdata-dojo-props\x3d"target:\'gmd:geographicElement\',minOccurs:0,maxOccurs:\'unbounded\',\r\n\t\t\tlabel:\'${i18nIso.EX_GeographicDescription.caption}\',\r\n\t\t\tmatchTopNode: [\r\n\t  \t  {\r\n\t   \t  \tqPath: \'gmd:EX_GeographicDescription\',\r\n\t      \tqValue: null,\r\n\t        qMode: \'must\'\r\n\t      }  \r\n\t  \t]"\x3e\r\n\t\t\t\r\n\t\t\x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/iso/AbstractObject"\r\n\t\t\tdata-dojo-props\x3d"target:\'gmd:EX_GeographicDescription\',minOccurs:0"\x3e\r\n\t\t\t\x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/iso/ObjectReference"\r\n\t\t\t\tdata-dojo-props\x3d"target:\'gmd:geographicIdentifier\',showHeader:false"\x3e\r\n\t\t\t\t\x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/iso/AbstractObject"\r\n\t\t\t\t\tdata-dojo-props\x3d"target:\'gmd:MD_Identifier\'"\x3e\r\n\t\t\t\t\t\x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/Element"\r\n\t\t\t\t\t\tdata-dojo-props\x3d"target:\'gmd:code\',label:\'${i18nIso.MD_Identifier.code}\'"\x3e\r\n\t\t\t\t\t\t\x3cdiv data-dojo-type\x3d"esri/dijit/metadata/form/iso/GcoElement"\r\n\t\t\t\t\t\t\tdata-dojo-props\x3d"target:\'gco:CharacterString\'"\x3e\x3c/div\x3e\r\n\t\t\t\t\t\x3c/div\x3e\r\n\t\t\t\t\x3c/div\x3e\r\n\t\t\t\x3c/div\x3e\r\n\t\t\x3c/div\x3e\r\n\t\t\t\r\n\t\x3c/div\x3e\r\n\t\t\t\r\n\x3c/div\x3e'}});
define("esri/dijit/metadata/types/gemini/gmd/extent/GeographicDescription","dojo/_base/declare dojo/_base/lang dojo/has ../../../../base/Descriptor ../../../../form/Element ../../../../form/iso/AbstractObject ../../../../form/iso/GcoElement ../../../../form/iso/ObjectReference dojo/text!./templates/GeographicDescription.html ../../../../../../kernel".split(" "),function(a,b,c,d,g,h,k,l,e,f){a=a(d,{templateString:e});c("extend-esri")&&b.setObject("dijit.metadata.types.gemini.gmd.extent.GeographicDescription",
a,f);return a});