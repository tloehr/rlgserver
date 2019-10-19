import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-charts/src/vaadin-chart.js';

class MyTest extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;" id="vaadinVerticalLayout">
 <h1 id="h1">Heading 1</h1>
 <vaadin-chart title="Solar Employment Growth by Sector, 2010-2016" subtitle="Source: thesolarfoundation.com" categories="[2010, 2011, 2012, 2013, 2014, 2015, 206, 2017]" id="vaadinChart">
  <vaadin-chart-series title="Installation" unit="Number of Employees" values="[43934, 52503, 57177, 69658, 97031, 119931, 137133, 154175]"></vaadin-chart-series>
  <vaadin-chart-series title="Manufacturing" unit="Number of Employees" values="[24916, 24064, 29742, 29851, 32490, 30282, 38121, 40434]"></vaadin-chart-series>
  <vaadin-chart-series title="Sales &amp; Distribution" unit="Number of Employees" values="[11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]"></vaadin-chart-series>
  <vaadin-chart-series title="Project Development" unit="Number of Employees" values="[null, null, 7988, 12169, 15112, 22452, 34400, 34227]"></vaadin-chart-series>
  <vaadin-chart-series title="Other" unit="Number of Employees" values="[12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]"></vaadin-chart-series>
 </vaadin-chart>
</vaadin-vertical-layout>
<ul id="ul">
 <li id="li">sdasdasd</li>
</ul>
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
