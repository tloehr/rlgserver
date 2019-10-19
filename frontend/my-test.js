import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';

class MyTest extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<h1>Heading 1</h1>
<vaadin-vertical-layout style="width: 100%; height: 100%;" id="vaadinVerticalLayout"></vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'my-test';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(MyTest.is, MyTest);
