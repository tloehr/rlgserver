import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';

class MainView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;">
 <vaadin-horizontal-layout class="header" style="width: 100%; min-height: var(--lumo-size-l); background-color: var(--lumo-contrast-10pct)"></vaadin-horizontal-layout>
 <vaadin-horizontal-layout style="width: 100%; flex: 1; min-height: 0;">
  <vaadin-vertical-layout class="sidebar" style="min-width: calc(7*var(--lumo-size-s)); background-color: var(--lumo-contrast-5pct);" id="sideview"></vaadin-vertical-layout>
  <vaadin-vertical-layout class="content" style="flex: 1;" id="mainview"></vaadin-vertical-layout>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="footer" style="width: 100%; min-height: var(--lumo-size-l); background-color: var(--lumo-contrast-10pct)"></vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'main-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(MainView.is, MainView);
