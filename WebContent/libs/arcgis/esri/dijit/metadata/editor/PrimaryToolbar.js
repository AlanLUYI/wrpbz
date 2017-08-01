// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.13/esri/copyright.txt for details.
//>>built
require({cache:{"url:esri/dijit/metadata/editor/templates/PrimaryToolbar.html":'\x3cdiv class\x3d"gxePrimaryToolbar" data-dojo-attach-point\x3d"containerNode"\x3e\r\n  \x3cdiv class\x3d"gxeTabs gxeFloatLeft"\x3e\r\n\t  \x3cdiv data-dojo-type\x3d"esri/dijit/metadata/base/TabButton" data-dojo-attach-point\x3d"viewButton"\r\n\t  \tdata-dojo-props\x3d"label:\'${i18nBase.editor.primaryToolbar.view}\'"\r\n\t  \tdata-dojo-attach-event\x3d"onClick: _onViewClick"\x3e\'\r\n\t  \x3c/div\x3e\x3cdiv data-dojo-type\x3d"esri/dijit/metadata/base/TabButton" data-dojo-attach-point\x3d"viewXmlButton"\r\n\t  \tdata-dojo-props\x3d"label:\'${i18nBase.editor.primaryToolbar.viewXml}\'"\r\n\t  \tdata-dojo-attach-event\x3d"onClick: _onViewXmlClick"\x3e\r\n\t  \x3c/div\x3e\x3cdiv data-dojo-type\x3d"esri/dijit/metadata/base/TabButton" data-dojo-attach-point\x3d"editButton"\r\n\t  \tdata-dojo-props\x3d"label:\'${i18nBase.editor.primaryToolbar.edit}\'"\r\n\t  \tdata-dojo-attach-event\x3d"onClick: _onEditClick"\x3e\r\n\t  \x3c/div\x3e\r\n\t  \x3cdiv class\x3d"gxeMessageArea" data-dojo-attach-point\x3d"messageArea" style\x3d"display:none"\x3e\r\n\t    \x3cdiv class\x3d"gxeIconWorking" data-dojo-attach-point\x3d"workingIcon" style\x3d"display:none"\x3e\x3c/div\x3e\r\n\t    \x3cspan class\x3d"gxeWorkingMessage" data-dojo-attach-point\x3d"workingNode"\x3e\x3c/span\x3e\r\n\t  \x3c/div\x3e\r\n  \x3c/div\x3e\r\n  \x3cdiv class\x3d"gxeToolsets gxeFloatRight"\x3e\r\n    \x3cdiv class\x3d"gxeToolset" data-dojo-attach-point\x3d"viewToolset" style\x3d"display:none;"\x3e\r\n\t    \x3cbutton class\x3d"gxeButton" data-dojo-attach-event\x3d"onclick: _onDownloadClick"\r\n\t      data-dojo-attach-point\x3d"downloadButton"\x3e${i18nBase.editor.download.caption}\x3c/button\x3e\r\n    \x3c/div\x3e\r\n    \x3cdiv class\x3d"gxeToolset" data-dojo-attach-point\x3d"editToolset" style\x3d"display:none;"\x3e    \r\n\t    \x3cbutton class\x3d"gxeButton" data-dojo-attach-event\x3d"onclick: _onSaveClick"\r\n\t      data-dojo-attach-point\x3d"saveButton"\x3e${i18nBase.editor.save.caption}\x3c/button\x3e\r\n\t    \x3cbutton class\x3d"gxeButton" data-dojo-attach-event\x3d"onclick: _onSaveAndCloseClick"\r\n\t      data-dojo-attach-point\x3d"saveAndCloseButton"\x3e${i18nBase.editor.saveAndClose.caption}\x3c/button\x3e\r\n\t    \x3cbutton class\x3d"gxeButton" data-dojo-attach-event\x3d"onclick: _onSaveDraftClick"\r\n\t      data-dojo-attach-point\x3d"saveDraftButton"\x3e${i18nBase.editor.saveDraft.caption}\x3c/button\x3e\r\n\t    \x3cbutton class\x3d"gxeButton" data-dojo-attach-event\x3d"onclick: _onLoadClick"\r\n\t      data-dojo-attach-point\x3d"loadButton"\x3e${i18nBase.editor.load.caption}\x3c/button\x3e\r\n\t    \x3cbutton class\x3d"gxeButton" data-dojo-attach-event\x3d"onclick: _onTransformClick"\r\n\t      data-dojo-attach-point\x3d"transformButton"\x3e${i18nBase.editor.transform.caption}\x3c/button\x3e\r\n\t    \x3cbutton class\x3d"gxeButton" data-dojo-attach-event\x3d"onclick: _onDeleteClick"\r\n\t      data-dojo-attach-point\x3d"deleteButton"\x3e${i18nBase.editor.del.caption}\x3c/button\x3e\r\n\t  \x3c/div\x3e\r\n\t  \x3cdiv class\x3d"gxeToolset" data-dojo-attach-point\x3d"commonToolset" style\x3d"display:none;"\x3e\r\n\t    \x3cbutton class\x3d"gxeButton" data-dojo-attach-event\x3d"onclick: _onCloseClick"\r\n      \tdata-dojo-attach-point\x3d"closeButton"\x3e${i18nBase.general.close}\x3c/button\x3e\r\n    \x3c/div\x3e\r\n  \x3c/div\x3e\r\n  \x3cdiv class\x3d"gxeClear"\x3e\x3c/div\x3e\r\n\x3c/div\x3e'}});
define("esri/dijit/metadata/editor/PrimaryToolbar","dojo/_base/declare dojo/_base/lang dojo/_base/array dojo/Deferred dojo/_base/fx dojo/dom-class dojo/dom-style dojo/has dojo/query ../base/Templated ./PrimaryToolbarMixin dojo/text!./templates/PrimaryToolbar.html dojo/i18n!../nls/i18nBase ../base/TabButton ../base/ValidationTracker ./util/MessageDialog ../../../kernel".split(" "),function(d,f,n,p,l,c,b,q,r,s,t,u,g,x,m,v,w){d=d([s,t],{_disabled:!0,_mode:null,editor:null,lastSavedXml:null,templateString:u,
canDownloadFiles:!1,postCreate:function(){this.inherited(arguments)},initialize:function(){this.editor.gxeAdaptor.getAllowEditMetadata()||b.set(this.editButton.domNode,"display","none");this.updateUI()},initializeView:function(){this._initializeView()},_checkForChanges:function(a,b){var e=new p,c=this.editor.getEditDocument();if(!c)return e.resolve(!0),e;var k=new m({ignoreErrors:!0});if(c.generateXml(k)===this.lastSavedXml)return e.resolve(!0),e;(new v({title:a,okLabel:b,cancelIsProminent:!0,onOkClick:function(a){e.resolve(!0)},
onCancelClick:function(a){e.resolve(!1)}})).show(g.editor.changesNotSaved.prompt);return e},_close:function(){this.editor.dialogBroker&&this.editor.dialogBroker.dialog&&this.editor.dialogBroker.hide()},_fadeIn:function(a){b.set(this.messageArea,"display","none");b.set(this.workingIcon,"display","none");this.workingNode.innerHTML="";l.fadeIn({node:this.editor.canvasNode,onEnd:f.hitch(this,function(){this._disabled=!1;this.updateUI();a&&a()})}).play()},_fadeOut:function(a,c){this._disabled=!0;b.set(this.messageArea,
"display","inline-block");this.setNodeText(this.workingNode,a);n.forEach(r("button",this.domNode),function(a){a.disabled=!0});l.fadeOut({node:this.editor.canvasNode,onEnd:f.hitch(this,function(){c&&c()})}).play()},_onCloseClick:function(){this._disabled||this._checkForChanges(g.editor.changesNotSaved.dialogTitle,g.editor.changesNotSaved.closeButton).then(f.hitch(this,function(a){a&&this._close()}))},_onDeleteClick:function(){this._disabled||this._confirmAndDelete()},_onDownloadClick:function(){if(!this._disabled){var a=
this.editor.xmlPane.xmlString,b=this.editor.xmlPane.xmlTitle;null!=a&&this._download(a,b,!1)}},_onEditClick:function(a){this._disabled||(a=this.editor.getEditDocument(),this._setMode("edit"),a||this._loadEditor())},_onLoadClick:function(){this._disabled||this._showLoadDialog(null)},_onSaveClick:function(){if(!this._disabled){var a={isSaveAsDraft:!1,isSaveAndClose:!1,bPushToItem:!1,showDialog:!1};this.editor.gxeAdaptor.getAllowPushToItem()&&(a.bPushToItem=!0);this._save(a)}},_onSaveAndCloseClick:function(){if(!this._disabled){var a=
{isSaveAsDraft:!1,isSaveAndClose:!0,bPushToItem:!1,showDialog:!1};this.editor.gxeAdaptor.getAllowPushToItem()&&(a.bPushToItem=!0);this._save(a)}},_onSaveDraftClick:function(){this._disabled||this._save({isSaveAsDraft:!0,isSaveAndClose:!1,bPushToItem:!1,showDialog:!1})},_onTransformClick:function(){if(!this._disabled){var a=this.editor.getEditDocument();if(a){var b=this._getTransformationTypes(a);0<b.length&&this._showTransformDialog(a,b)}}},_onViewClick:function(){this._disabled||this._loadView()},
_onViewXmlClick:function(){if(!this._disabled){var a=this.editor.getEditDocument();if(a){var b=new m({ignoreErrors:!0}),a=a.generateXml(b);this.editor.xmlPane.setXml(a,b.documentTitle);this._setMode("viewXml");this.updateUI()}else this._setMode("viewXml")}},_setMode:function(a){"view"===a?(c.add(this.viewButton.domNode,"current"),c.remove(this.viewXmlButton.domNode,"current"),c.remove(this.editButton.domNode,"current"),b.set(this.editToolset,"display","none"),b.set(this.viewToolset,"display",""),
this.editor.validationPane.clearMessages(),b.set(this.editor.xmlPane.domNode,"display","none"),b.set(this.editor.editDocumentPane.domNode,"display","none"),b.set(this.editor.viewDocumentPane.domNode,"display",""),this.editor.resizeDocument(this.editor.viewDocumentPane)):"viewXml"===a?(c.add(this.viewXmlButton.domNode,"current"),c.remove(this.viewButton.domNode,"current"),c.remove(this.editButton.domNode,"current"),b.set(this.editToolset,"display","none"),b.set(this.viewToolset,"display",""),this.editor.validationPane.clearMessages(),
b.set(this.editor.viewDocumentPane.domNode,"display","none"),b.set(this.editor.editDocumentPane.domNode,"display","none"),b.set(this.editor.xmlPane.domNode,"display",""),this.editor.resizeXmlPane()):"edit"===a&&(c.add(this.editButton.domNode,"current"),c.remove(this.viewButton.domNode,"current"),c.remove(this.viewXmlButton.domNode,"current"),b.set(this.viewToolset,"display","none"),b.set(this.editToolset,"display",""),b.set(this.editor.viewDocumentPane.domNode,"display","none"),b.set(this.editor.xmlPane.domNode,
"display","none"),b.set(this.editor.editDocumentPane.domNode,"display",""),this.editor.resizeDocument(this.editor.editDocumentPane));this._mode=a;this.updateUI()},updateUI:function(){var a=function(a,c){a.disabled=!c;c?b.set(a,"display",""):b.set(a,"display","none")},c=this.editor.xmlPane.xmlString,e=this.editor.xmlPane.xmlTitle,h=this.editor.getEditDocument(),k=this._getTransformationTypes(h),h=null!==h,d=this.editor.gxeAdaptor.getAllowEditMetadata(),f=this.editor.gxeAdaptor.getAllowDeleteMetadata(),
d=d&&h,k=0<k.length,f=f&&null!==this.lastSavedXml,g=this.editor&&this.editor.dialogBroker;a(this.downloadButton,null!==c&&null!==e);a(this.saveButton,d);a(this.saveAndCloseButton,d&&g);a(this.saveDraftButton,h);a(this.loadButton,!0);a(this.transformButton,k);a(this.deleteButton,f);a(this.closeButton,g)}});q("extend-esri")&&f.setObject("dijit.metadata.editor.PrimaryToolbar",d,w);return d});